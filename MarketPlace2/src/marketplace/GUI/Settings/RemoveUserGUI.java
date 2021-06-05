package marketplace.GUI.Settings;

import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.MainGUIHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveUserGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JButton removeUserButton;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public RemoveUserGUI() {

        setLayout(null);

        userIDPromptLabel = new JLabel("ID of user to remove");
        userIDPromptLabel.setBounds(10, 20, 180, 25);
        add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        add(userIDText);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(10, 70, 120, 25);
        removeUserButton.addActionListener(this);
        add(removeUserButton);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(200, 150, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            add(new SettingsNavigationAdminGUI());
            updateUI();
        });
        add(toSettingsButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 100, 340, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 100, 340, 25);
        add(invalid);
    }

    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        String userID = userIDText.getText();
        userIDText.setText("");

        if (userID.length() < 249 && userID != null) {
            try {
                if (MainGUIHandler.userHandler.userIDExists(userID)) {
                    MainGUIHandler.userHandler.removeUser(userID);
                    valid.setText("User was successfully removed");
                } else {
                    invalid.setText("User can't be found or can't be removed");
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            invalid.setText("Provided userID is invalid");
        }
    }
}