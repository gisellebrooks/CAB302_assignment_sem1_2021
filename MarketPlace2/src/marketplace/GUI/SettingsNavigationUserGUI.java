package marketplace.GUI;

import marketplace.GUI.CustomLabel;
import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.HomeGUI;
import marketplace.Util.Fonts;

import javax.swing.*;

public class SettingsNavigationUserGUI extends FullSizeJPanel {

    private static JButton changePasswordButton;
    private static JButton toHomeButton;
//    private static CustomLabel settingsHeading;
    public Fonts fonts;

    public SettingsNavigationUserGUI() {
        setLayout(null);

//        JPanel settingsHeading = new CustomLabel("Settings", fonts.smallHeading, false);

        toHomeButton = new CustomButton("Home");
        toHomeButton.setBounds(450, 20, 120, 25);
        toHomeButton.addActionListener(e -> {
            removeAll();
            add(new HomeGUI());
            updateUI();
        });
        add(toHomeButton);

        changePasswordButton = new CustomButton("Change Password");
        changePasswordButton.setBounds(50, 20, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new ChangeUsersPasswordGUI());
            updateUI();
        });
        add(changePasswordButton);

//        logOutButton = new CustomButton("Logout");
//        logOutButton.setBounds(50, 60, 160, 25);
//        logOutButton.addActionListener(e -> {
//            removeAll();
//            MainGUIHandler.user = null;
//            MainGUIHandler.userType = "USER";
//            add(new LoginGUI());
//            updateUI();
//        });
//        add(logOutButton);
    }
}