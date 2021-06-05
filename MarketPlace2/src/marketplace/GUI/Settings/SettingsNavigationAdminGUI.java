package marketplace.GUI.Settings;

import marketplace.GUI.OrderGUI;
import javax.swing.*;

public class SettingsNavigationAdminGUI extends JPanel {

    private static JButton createUserButton;
    private static JButton modifyUserButton;
    private static JButton changePasswordButton;
    private static JButton createOrganisationButton;
    private static JButton modifyOrganisationButton;
    private static JButton toHomeButton;

    public SettingsNavigationAdminGUI() {
        setLayout(null);
        setBounds(0, 0, 1181, 718);

        toHomeButton = new JButton("Home");
        toHomeButton.setBounds(450, 20, 120, 25);
        toHomeButton.addActionListener(e -> {
            removeAll();
            add(new OrderGUI());
            updateUI();
        });
        add(toHomeButton);

        createUserButton = new JButton("Create User");
        createUserButton.setBounds(50, 20, 160, 25);
        createUserButton.addActionListener(e -> {
            removeAll();
            add(new SignUpUserGUI());
            updateUI();
        });
        add(createUserButton);

        modifyUserButton = new JButton("Modify User");
        modifyUserButton.setBounds(50, 100, 160, 25);
        modifyUserButton.addActionListener(e -> {
            removeAll();
            add(new ModifyUserGUI());
            updateUI();
        });
        add(modifyUserButton);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(50, 140, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new ChangeUsersPasswordGUI());
            updateUI();
        });
        add(changePasswordButton);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(250, 20, 160, 25);
        createOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new SignUpOrganisationGUI());
            updateUI();
        });
        add(createOrganisationButton);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(250, 100, 160, 25);
        modifyOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new ModifyOrganisationGUI());
            updateUI();
        });
        add(modifyOrganisationButton);
    }
}