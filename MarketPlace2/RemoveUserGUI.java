import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class RemoveUserGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JButton button;
    private static JLabel valid;
    private static JLabel invalid;


    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new RemoveUserGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(550,400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);


        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        panel.add(userIDText);

        button = new JButton("Remove User");
        button.setBounds(10, 80, 120, 25);
        button.addActionListener(new RemoveUserGUI());
        panel.add(button);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == button) {
            String userID = userIDText.getText();
            valid.setText("");
            invalid.setText("User doesn't exist or can't be removed");

            String newUserID;

            try {
                MariaDBDataSource pool = MariaDBDataSource.getInstance();
                invalid.setText("can't remove user");
                ResultSet rs;

                PreparedStatement checkUserIDExist = pool.getConnection().prepareStatement("SELECT name FROM USER_INFORMATION WHERE userID = ?");
                PreparedStatement removeUser = pool.getConnection().prepareStatement("DELETE FROM USER_INFORMATION WHERE userID = ?");

                // check userID exists
                checkUserIDExist.setString(1, userID);

                rs = checkUserIDExist.executeQuery();

                if (rs.next()) {
                    removeUser.setString(1, userID);
                    removeUser.executeUpdate();
                    valid.setText("user was removed");
                    invalid.setText("");
                    userIDText.setText("");
                }

                rs.close();
                checkUserIDExist.close();
                removeUser.close();

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }

    }
}