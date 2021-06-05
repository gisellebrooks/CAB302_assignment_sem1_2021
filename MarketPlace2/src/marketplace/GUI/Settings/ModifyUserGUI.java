package marketplace.GUI.Settings;

import marketplace.GUI.MainGUI;
import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.MainGUIHandler;
import marketplace.Objects.User;
import marketplace.Handlers.PasswordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ModifyUserGUI extends FullSizeJPanel implements ActionListener {

    private static JButton modifyUserButton;
    private static JButton toSettingsButton;
    private static JButton findUserButton;
    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
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
            if (MainGUI.user.getAccountType().equals("ADMIN")) {
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

        PasswordHandler passwordHandler = new PasswordHandler();

        valid.setText("");
        invalid.setText("");

        if (e.getSource() == findUserButton) {

            newPasswordText.setText("");

            try {
                userValid = true;
                userID = userIDText.getText();
                user = MainGUIHandler.userHandler.searchUser(userID);

                invalid.setText("");
                valid.setText(user.getUserID() + " selected");

            } catch (Exception exception) {
                user = null;
                userValid = false;
                valid.setText("");
                invalid.setText(exception.getMessage());
            }
        }

        if (e.getSource() == resetPasswordButton) {
            if (userValid) {
                try {
                    newPassword = passwordHandler.generatePassword();
                    user.setPasswordHash(passwordHandler.IntoHash(newPassword));
                    MainGUI.userHandler.updateUserPassword(user);
                    newPasswordText.setText(newPassword);
                    invalid.setText("");
                } catch (Exception exception) {
                    invalid.setText(exception.getMessage());
                }
            } else {
                invalid.setText("Find a valid user");
            }
        }

        if (e.getSource() == modifyUserButton) {
            if (userValid) {
                try {
                    MainGUI.userHandler.updateUser(user);
                    invalid.setText("");
                    valid.setText(user.getUserID() + " information updated");
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            } else {
                invalid.setText("Find a valid user");
            }

            try {
                MainGUIHandler.user = MainGUIHandler.userHandler.searchUser(MainGUIHandler.user.getUserID());
            } catch (Exception exception) {
                invalid.setText("Error");
            }
        }
    }
}