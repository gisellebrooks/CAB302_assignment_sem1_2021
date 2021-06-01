package marketplace.GUI.Settings;

import marketplace.GUI.MainGUIHandler;
import marketplace.Objects.Organisation;
import marketplace.Objects.User;
import marketplace.PasswordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class ModifyUserGUI extends JPanel implements ActionListener {

    private static JButton modifyUserButton;
    private static JButton toSettingsButton;
    private static JButton findUserButton;
    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
//    private static JLabel organisationLabel;
//    private static JComboBox userOrganisationComboBox;
    private static JLabel userTypeLabel;
    private static JComboBox userTypeComboBox;

    private static JButton resetPasswordButton;
    private static JLabel newPasswordPromptLabel;
    private static JTextField newPasswordText;

    boolean userValid;
    User user = null;

    private static JLabel valid;
    private static JLabel invalid;

    public ModifyUserGUI() {

        setLayout(null);
        setBounds(0, 0, 600, 600);



        resetPasswordButton = new JButton("Reset User's Password");
        resetPasswordButton.setBounds(300, 40, 180, 25);
        resetPasswordButton.addActionListener(this);
        add(resetPasswordButton);

        newPasswordPromptLabel = new JLabel("New password:");
        newPasswordPromptLabel.setBounds(300, 70, 160, 25);
        add(newPasswordPromptLabel);

        newPasswordText = new JTextField(30);
        newPasswordText.setBounds(300, 100, 180, 25);
        add(newPasswordText);


        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 160, 25);
        add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        add(userIDText);

        findUserButton = new JButton("Find");
        findUserButton.setBounds(10, 70, 80, 25);
        findUserButton.addActionListener(this);
        add(findUserButton);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 100, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 100, 340, 25);
        add(invalid);


//        organisationLabel = new JLabel("Select your organisation:");
//        organisationLabel.setBounds(10, 120, 180, 25);
//        add(organisationLabel);

//        userOrganisationComboBox = new JComboBox(MainGUIHandler.organisationHandler.getAllOrganisationsNames().toArray());
//        userOrganisationComboBox.setBounds(10, 150, 165, 25);
//        add(userOrganisationComboBox);

        userTypeLabel = new JLabel("Select your user type:");
        userTypeLabel.setBounds(10, 190, 180, 25);
        add(userTypeLabel);

        ArrayList<String> userTypes = new ArrayList<>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 220, 165, 25);
        add(userTypeComboBox);

        modifyUserButton = new JButton("Modify");
        modifyUserButton.setBounds(10, 260, 80, 25);
        modifyUserButton.addActionListener(this);
        add(modifyUserButton);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(200, 400, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            if (MainGUIHandler.user.getAccountType().equals("ADMIN")) {
                add(new SettingsNavigationAdminGUI());
            } else {
                add(new SettingsNavigationUserGUI());
            }

            updateUI();
        });
        add(toSettingsButton);

    }

    public void actionPerformed(ActionEvent e) {

        String userID;
        String newPassword;
        String userOrganisationName;
        String userOrganisationID;
        Organisation userOrganisation;
        String userType;

        PasswordHandler passwordHandler = new PasswordHandler();

        valid.setText("");
        invalid.setText("invalid");

        if (e.getSource() == findUserButton) {

            newPasswordText.setText("");
            userID = userIDText.getText();

            if (!userID.isEmpty() && userID.length() < 250 && MainGUIHandler.userHandler.userIDExists(userID)) {
                userValid = true;
                userID = userIDText.getText();
                user = MainGUIHandler.userHandler.getUser(userID);

                userOrganisationID = user.getOrganisationID();

//                userOrganisation = MainGUIHandler.organisationHandler.getOrganisation(userOrganisationID);
//                userOrganisationName = userOrganisation.getOrgName();
//                userType = user.getAccountType();
//                userTypeComboBox.setSelectedItem(userType);
//                userOrganisationComboBox.setSelectedItem(userOrganisationName);

                invalid.setText("");
                valid.setText(user.getUserID() + " selected");

            } else {
                user = null;
                userValid = false;
                valid.setText("");
                invalid.setText("That userID is invalid");
            }
        }

        if (e.getSource() == resetPasswordButton) {
            if (userValid) {
                try {
                    newPassword = passwordHandler.generatePassword();
                    user.setPasswordHash(passwordHandler.IntoHash(newPassword));
                    MainGUIHandler.userHandler.updateUserPassword(user);
                    newPasswordText.setText(newPassword);
                    invalid.setText("");
                } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    noSuchAlgorithmException.printStackTrace();
                }
            } else {
                invalid.setText("invalid user");
            }
        }

        if (e.getSource() == modifyUserButton) {
            if (userValid) {

//                userOrganisationName = userOrganisationComboBox.getSelectedItem().toString();
//                userOrganisationID = MainGUIHandler.organisationHandler.getOrganisationID(userOrganisationName);
//                user.setOrganisationID(userOrganisationID);
//                user.setAccountType(userTypeComboBox.getSelectedItem().toString());

                MainGUIHandler.userHandler.updateUser(user);

                invalid.setText("");
                valid.setText("user information updated");
            } else {
                invalid.setText("invalid user");
            }

            MainGUIHandler.user = MainGUIHandler.userHandler.getUser(MainGUIHandler.user.getUserID());
        }
    }
}