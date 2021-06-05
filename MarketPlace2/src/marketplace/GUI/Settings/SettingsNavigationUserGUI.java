package marketplace.GUI.Settings;

import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.OrderGUI;
import javax.swing.*;

public class SettingsNavigationUserGUI extends FullSizeJPanel {

    private static JButton changePasswordButton;
    private static JButton toHomeButton;

    public SettingsNavigationUserGUI() {
        setLayout(null);

        toHomeButton = new JButton("Home");
        toHomeButton.setBounds(450, 20, 120, 25);
        toHomeButton.addActionListener(e -> {
            removeAll();
            add(new OrderGUI());
            updateUI();
        });
        add(toHomeButton);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(50, 20, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new ChangeUsersPasswordGUI());
            updateUI();
        });
        add(changePasswordButton);

//        logOutButton = new JButton("Logout");
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