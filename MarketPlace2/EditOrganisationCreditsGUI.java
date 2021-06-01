import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EditOrganisationCreditsGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel organisationLabel;
    private static JComboBox organisationComboBox;

    private static JLabel currentCreditsPromptLabel;
    private static JTextField currentCreditsText;

    private static JLabel addCreditsPromptLabel;
    private static JTextField addCreditsText;
    private static JButton addCreditsButton;

    private static JLabel removeCreditsPromptLabel;
    private static JTextField removeCreditsText;
    private static JButton removeCreditsButton;

    private static JLabel valid;
    private static JLabel invalid;


    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new EditOrganisationCreditsGUI());
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


        organisationLabel = new JLabel("Organisation:");
        organisationLabel.setBounds(10, 40, 180, 25);
        panel.add(organisationLabel);

        organisationComboBox = new JComboBox(organisationsList.toArray());
        organisationComboBox.setBounds(10, 60, 165, 25);
        panel.add(organisationComboBox);

        currentCreditsPromptLabel = new JLabel("Organisation's current credits:");
        currentCreditsPromptLabel.setBounds(10, 100, 180, 25);
        panel.add(currentCreditsPromptLabel);

        currentCreditsText = new JTextField(20);
        currentCreditsText.setBounds(10, 120, 165, 25);
        panel.add(currentCreditsText);


        addCreditsPromptLabel = new JLabel("Add credits:");
        addCreditsPromptLabel.setBounds(10, 150, 180, 25);
        panel.add(addCreditsPromptLabel);

        addCreditsText = new JTextField(20);
        addCreditsText.setBounds(10, 170, 165, 25);
        panel.add(addCreditsText);

        addCreditsButton = new JButton("+");
        addCreditsButton.setBounds(10, 190, 20, 25);
        addCreditsButton.addActionListener(new EditOrganisationCreditsGUI());
        panel.add(addCreditsButton);


        removeCreditsPromptLabel = new JLabel("Remove credits:");
        removeCreditsPromptLabel.setBounds(10, 220, 180, 25);
        panel.add(removeCreditsPromptLabel);

        removeCreditsText = new JTextField(20);
        removeCreditsText.setBounds(10, 240, 165, 25);
        panel.add(removeCreditsText);

        removeCreditsButton = new JButton("-");
        removeCreditsButton.setBounds(10, 260, 20, 25);
        removeCreditsButton.addActionListener(new EditOrganisationCreditsGUI());
        panel.add(removeCreditsButton);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 300, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 300, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String newUserID;
        String newPasswordHash = null;
        String newPassword;
        String accountType;
        String orgID = null;
        String name = null;
        String latestUserID;

//        newPassword = new PasswordFunctions().generatePassword();
//        try {
//            newPasswordHash = new PasswordFunctions().intoHash(newPassword);
//        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
//            noSuchAlgorithmException.printStackTrace();
//        }
//
//        if (nameText.isValid() && nameText.getText().length() > 2 && nameText.getText().length() < 250) {
//            System.out.println(nameText.getText());
//            name = nameText.getText();
//
//            try {
//                MariaDBDataSource pool = MariaDBDataSource.getInstance();
//                invalid.setText("can't signup");
//                ResultSet rs;
//
//                PreparedStatement getAllUserID = pool.getConnection().prepareStatement("SELECT userID FROM USER_INFORMATION");
//                PreparedStatement getOrganisationsID = pool.getConnection().prepareStatement("SELECT orgID FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = ?");
//                PreparedStatement addNewUser = pool.getConnection().prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?, ?)");
//
//                // get organisation ID with org name
//                String userOrganisation = organisationComboBox.getSelectedItem().toString();
//                getOrganisationsID.setString(1, userOrganisation);
//
//                rs = getOrganisationsID.executeQuery();
//
//                if (rs.next()) {
//                    orgID = rs.getString(1);
//                }
//
//                accountType = userTypeComboBox.getSelectedItem().toString();
//
//                // getting the next user ID
//                ArrayList<String> userIDs = new ArrayList<String>();
//                rs = getAllUserID.executeQuery();
//                while (rs.next()) {
//                    userIDs.add(rs.getString("userID"));
//                }
//                userIDs.sort(String::compareToIgnoreCase);
//
//                latestUserID = userIDs.get(userIDs.size() - 1);
//                latestUserID = latestUserID.replace("user", "");
//                newUserID = (String.valueOf(Integer.parseInt(latestUserID) + 1));
//                newUserID = "user" + newUserID;
//
//                addNewUser.setString(1, newUserID);
//                addNewUser.setString(2, newPasswordHash);
//                addNewUser.setString(3, accountType);
//                addNewUser.setString(4, orgID);
//                addNewUser.setString(5, name);
//
//                addNewUser.executeQuery();
//
//                valid.setText("signup successful!");
//                invalid.setText("");
//                givenPasswordLabel.setText(newPassword);
//                givenIDLabel.setText(newUserID);
//
//                rs.close();
//                addNewUser.close();
//                getAllUserID.close();
//                getOrganisationsID.close();
//
//            } catch (SQLException throwable) {
//                throwable.printStackTrace();
//            }
//        }
    }
}