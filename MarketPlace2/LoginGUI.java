import Server.MariaDBDataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel valid;
    private static JLabel invalid;


    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new LoginGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(350,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 160, 25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new LoginGUI());
        panel.add(button);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();
        valid.setText("");
        invalid.setText("");

        try {
            MariaDBDataSource pool = MariaDBDataSource.getInstance();
            invalid.setText("invalid");
            ResultSet rs;

            PreparedStatement getUserExists = pool.getConnection().prepareStatement("SELECT * FROM USER_INFORMATION WHERE userID = ?");
            PreparedStatement getPasswordHash = pool.getConnection().prepareStatement("SELECT passwordHash FROM USER_INFORMATION WHERE userID = ?");

            getUserExists.setString(1,user);
            rs = getUserExists.executeQuery();

            // if user found
            if (rs.next()) {
                getPasswordHash.setString(1, user);
                rs = getPasswordHash.executeQuery();

                // if password hash found
                while (rs.next()) {
                    String dbPasswordHash = rs.getString(1);

                    // if given password matches users saved password
                    if (dbPasswordHash.equals(PasswordFunctions.IntoHash(password))) {
                        invalid.setText("");
                        valid.setText("Login successful!");
                    }
                }
            }

            rs.close();
            getUserExists.close();
            getPasswordHash.close();

        } catch (SQLException | NoSuchAlgorithmException throwable) {
            throwable.printStackTrace();
        }
    }
}