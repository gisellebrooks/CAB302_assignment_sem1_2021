import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class SettingsNavigationGUI extends JFrame implements ActionListener, Runnable {

    private static JButton loginButton;
    private static JButton signUpButton;
    private static JButton editUserButton;
    private static JButton removeUserButton;
    private static JButton createOrganisationButton;
    private static JButton editOrganisationButton;
    private static JLabel valid;
    private static JLabel invalid;




    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

//         loadMockData(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new SettingsNavigationGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(400,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        signUpButton = new JButton("Sign up new user");
        signUpButton.setBounds(10, 120, 150, 25);
        signUpButton.addActionListener(new SettingsNavigationGUI());
        panel.add(signUpButton);

        editUserButton = new JButton("Edit User");
        editUserButton.setBounds(10, 80, 150, 25);
        editUserButton.addActionListener(new SettingsNavigationGUI());
        panel.add(editUserButton);

        loginButton = new JButton("Login");
        loginButton.setBounds(10, 40, 150, 25);
        loginButton.addActionListener(new SettingsNavigationGUI());
        panel.add(loginButton);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(10, 160, 150, 25);
        removeUserButton.addActionListener(new SettingsNavigationGUI());
        panel.add(removeUserButton);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(200, 40, 150, 25);
        createOrganisationButton.addActionListener(new SettingsNavigationGUI());
        panel.add(createOrganisationButton);

        editOrganisationButton = new JButton("Edit Organisation");
        editOrganisationButton.setBounds(200, 80, 150, 25);
        editOrganisationButton.addActionListener(new SettingsNavigationGUI());
        panel.add(editOrganisationButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == loginButton) {
            SwingUtilities.invokeLater(new LoginGUI());
        }

        if (e.getSource() == signUpButton) {
            SwingUtilities.invokeLater(new SignUpGUI());
        }

        if (e.getSource() == editUserButton) {
            SwingUtilities.invokeLater(new EditUserGUI());
        }

        if (e.getSource() == createOrganisationButton) {
            SwingUtilities.invokeLater(new CreateOrganisationGUI());
        }

        if (e.getSource() == editOrganisationButton) {
            SwingUtilities.invokeLater(new EditOrganisationCreditsGUI());
        }
    }
}