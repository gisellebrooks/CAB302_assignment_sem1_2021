package marketplace.GUI;

import marketplace.GUI.MainGUI;
import marketplace.GUI.FullSizeJPanel;
import marketplace.Objects.User;
import marketplace.Handlers.PasswordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <h1>Page to update user password</h1>
 * * Shows a GUI to update the users password
 * *
 * * @author Ali
 */
public class ChangeUsersPasswordGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel oldPasswordPromptLabel;
    private static JTextField oldPasswordText;
    private static JLabel newPasswordPromptLabel;
    private static JTextField newPasswordText;
    private static JLabel confirmPasswordPromptLabel;
    private static JTextField confirmPasswordText;
    private static JButton changePasswordButton;
    private static JButton toSettingsButton;

    private static JLabel valid;
    private static JLabel invalid;

    User user = null;

    public ChangeUsersPasswordGUI() {

        setLayout(null);

        oldPasswordPromptLabel = new JLabel("Old Password:");
        oldPasswordPromptLabel.setBounds(20, 30, 160, 25);
        add(oldPasswordPromptLabel);

        oldPasswordText = new CustomTextField(30);
        oldPasswordText.setBounds(20, 50, 180, 25);
        add(oldPasswordText);

        newPasswordPromptLabel = new JLabel("New Password:");
        newPasswordPromptLabel.setBounds(20, 90, 160, 25);
        add(newPasswordPromptLabel);

        newPasswordText = new CustomTextField(30);
        newPasswordText.setBounds(20, 110, 180, 25);
        add(newPasswordText);

        confirmPasswordPromptLabel = new JLabel("Confirm Password:");
        confirmPasswordPromptLabel.setBounds(20, 140, 160, 25);
        add(confirmPasswordPromptLabel);

        confirmPasswordText = new CustomTextField(30);
        confirmPasswordText.setBounds(20, 160, 180, 25);
        add(confirmPasswordText);

        changePasswordButton = new CustomButton("Change");
        changePasswordButton.setBounds(20, 200, 80, 25);
        changePasswordButton.addActionListener(this);
        add(changePasswordButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(20, 240, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(20, 240, 340, 25);
        add(invalid);

        toSettingsButton = new CustomButton("SETTINGS");
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
        String oldPassword;
        String newPassword;
        String confirmedPassword;

        valid.setText("");

        PasswordHandler passwordHandler = new PasswordHandler();

        user = MainGUI.user;

        oldPassword = oldPasswordText.getText();
        newPassword = newPasswordText.getText();
        confirmedPassword = confirmPasswordText.getText();

        try {
            user.setPasswordHash(passwordHandler.IntoHash(newPassword));

            MainGUI.userHandler.updateUserPassword(
                    MainGUI.user, oldPassword, newPassword, confirmedPassword);

            valid.setText("Password has been changed");
            invalid.setText("");
        } catch (Exception exception) {
            invalid.setText(exception.getMessage());
        }
    }
}