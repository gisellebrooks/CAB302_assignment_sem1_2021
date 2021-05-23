import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EditUserGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JButton find;

    private static JTextField newPasswordText;
    private static JButton resetPassword;
    private static JLabel passwordResetSuccessLabel;

    private static JLabel namePromptLabel;
    private static JTextField nameText;

    private static JLabel organisationPromptLabel;
    private static JComboBox organisationComboBox;

    private static JLabel userTypePromptLabel;
    private static JComboBox userTypeComboBox;

    private static JButton save;
    private static JLabel valid;
    private static JLabel invalid;

    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new EditUserGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(1181,718);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);


        // get all organisations for use in combo box
        ArrayList<String> organisationsList = new ArrayList<String>();
        try {
            MariaDBDataSource pool = MariaDBDataSource.getInstance();
            ResultSet rs;

            PreparedStatement getAllOrganisations = pool.getConnection().prepareStatement("SELECT distinct orgName FROM ORGANISATIONAL_UNIT_INFORMATION");

            rs = getAllOrganisations.executeQuery();

            while (rs.next()) {
                organisationsList.add(rs.getString("orgName"));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        resetPassword = new JButton("Reset Password");
        resetPassword.setBounds(300, 40, 160, 25);
        resetPassword.addActionListener(new EditUserGUI());
        panel.add(resetPassword);

        newPasswordText = new JTextField(20);
        newPasswordText.setBounds(300, 80, 165, 25);
        panel.add(newPasswordText);

        passwordResetSuccessLabel = new JLabel("User ID:");
        passwordResetSuccessLabel.setBounds(10, 120, 160, 25);
        panel.add(passwordResetSuccessLabel);


        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 50, 150, 25);
        panel.add(userIDText);
        userIDText.setText("user ID");

        find = new JButton("Find user");
        find.setBounds(10, 90, 100, 25);
        find.addActionListener(new EditUserGUI());
        panel.add(find);


        namePromptLabel = new JLabel("Full Name:");
        namePromptLabel.setBounds(10, 150, 165, 25);
        panel.add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 170, 165, 25);
        panel.add(nameText);


        organisationPromptLabel = new JLabel("Organisation:");
        organisationPromptLabel.setBounds(10, 210, 165, 25);
        panel.add(organisationPromptLabel);

        organisationComboBox = new JComboBox(organisationsList.toArray());
        organisationComboBox.setBounds(10, 230, 165, 25);
        panel.add(organisationComboBox);


        userTypePromptLabel = new JLabel("User Type:");
        userTypePromptLabel.setBounds(10, 270, 165, 25);
        panel.add(userTypePromptLabel);

        ArrayList<String> userTypes = new ArrayList<String>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 290, 165, 25);
        panel.add(userTypeComboBox);


        save = new JButton("Save");
        save.setBounds(10, 330, 100, 25);
        save.addActionListener(new EditUserGUI());
        panel.add(save);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 370, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 370, 340, 25);
        panel.add(invalid);
    }

    // when find button is pressed
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }

    // when resetPassword button is pressed
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userID = null;
        String newPassword = null;
        String newPasswordHash = null;
        String name = null;
        String userOrganisationID = null;
        String userOrganisationName = null;
        String userType = null;
        boolean validUser = false;

        // if userID is possibly real
        if (userIDText.getText().length() > 2) {

            userID = userIDText.getText();

            try {

                MariaDBDataSource pool = MariaDBDataSource.getInstance();
                ResultSet rs;

                PreparedStatement checkUserIDExist = pool.getConnection().prepareStatement("SELECT name FROM USER_INFORMATION WHERE userID = ?");
                PreparedStatement removeUser = pool.getConnection().prepareStatement("DELETE FROM USER_INFORMATION WHERE userID = ?");
                PreparedStatement updatePassword = pool.getConnection().prepareStatement("UPDATE USER_INFORMATION SET passwordHash = ? WHERE userID = ?");
                PreparedStatement getUsersDetails = pool.getConnection().prepareStatement("SELECT accountType, orgID, name FROM USER_INFORMATION WHERE userID = ?");
                PreparedStatement getOrganisationName = pool.getConnection().prepareStatement("SELECT orgName FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = ?");

                // check userID exists
                checkUserIDExist.setString(1, userID);

                rs = checkUserIDExist.executeQuery();

                if (rs.next()) {
                    validUser = true;
                } else {
                    validUser = false;
                }


                // if find user is clicked
                if (e.getSource() == find) {
                    newPasswordText.setText("");

                    getUsersDetails.setString(1, userID);

                    rs = getUsersDetails.executeQuery();

                    if (rs.next()) {
                        userType = rs.getString("accountType");
                        userOrganisationID = rs.getString("orgID");
                        name = rs.getString("name");
                    }

                    userTypeComboBox.setSelectedItem(userType);
                    nameText.setText(name);

                    getOrganisationName.setString(1, userOrganisationID);
                    rs = getOrganisationName.executeQuery();

                    if (rs.next()) {
                        userOrganisationName = rs.getString("orgName");
                    }

                    organisationComboBox.setSelectedItem(userOrganisationName);



                }

                // if reset password is activated
                if (e.getSource() == resetPassword) {
                    if (validUser) {
                        newPassword = new PasswordFunctions().generatePassword();
                        newPasswordHash = new PasswordFunctions().IntoHash(newPassword);
                        newPasswordText.setText(newPassword);

                        updatePassword.setString(1, newPasswordHash);
                        updatePassword.setString(2, userID);
                        updatePassword.executeQuery();

                        passwordResetSuccessLabel.setText("Password has been reset");
                    } else {
                        passwordResetSuccessLabel.setText("Please find a valid userID");
                    }
                }

                rs.close();
                checkUserIDExist.close();
                removeUser.close();
                updatePassword.close();
                getUsersDetails.close();
                getOrganisationName.close();

            } catch (SQLException | NoSuchAlgorithmException throwable) {
                throwable.printStackTrace();
            }
        }

    }
}