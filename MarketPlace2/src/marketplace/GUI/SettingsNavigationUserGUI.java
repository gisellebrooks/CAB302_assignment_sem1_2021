package marketplace.GUI;

import marketplace.GUI.CustomLabel;
import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.HomeGUI;
import marketplace.Util.Fonts;

import javax.swing.*;

/**
 * <h1>Settings page for the user level</h1>
 * *
 * *
 * * @author Ali
 */
public class SettingsNavigationUserGUI extends FullSizeJPanel {

    private static JButton changePasswordButton;
    private static JButton toHomeButton;
    public Fonts fonts;

    public SettingsNavigationUserGUI() {
        setLayout(null);

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
    }
}