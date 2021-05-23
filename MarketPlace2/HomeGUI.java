import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;


public class HomeGUI extends JFrame implements Runnable {

    private static JButton usersettings;
    private static JLabel valid;
    private static JLabel invalid;


    public void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);


        JFrame frame = new JFrame();
        frame.setDefaultLookAndFeelDecorated(true);
        JPanel panel = new JPanel();
        this.setSize(1181,718);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setLayout(null);

        usersettings = new JButton("Settings");
        usersettings.setBounds(10, 40, 120, 25);
        usersettings.addActionListener(e -> {
            frame.dispose();
            SwingUtilities.invokeLater(new SettingsNavigationGUI());
        });
        panel.add(usersettings);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        panel.add(invalid);

        frame.setVisible(true);
    }

    @Override
    public void run() {

    }
}