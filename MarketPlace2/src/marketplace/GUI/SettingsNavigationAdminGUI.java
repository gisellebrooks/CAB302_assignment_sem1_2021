package marketplace.GUI;

import marketplace.GUI.FullSizeJPanel;
import marketplace.GUI.HomeGUI;
import javax.swing.*;

public class SettingsNavigationAdminGUI extends FullSizeJPanel {

    private static CustomButton createUserButton;
    private static CustomButton modifyUserButton;
    private static CustomButton changePasswordButton;
    private static CustomButton createOrganisationButton;
    private static CustomButton modifyOrganisationButton;
    private static CustomButton toHomeButton;

    public SettingsNavigationAdminGUI() {
        
        setLayout(null);

        toHomeButton = new CustomButton("Home");
        toHomeButton.setBounds(450, 20, 120, 25);
        toHomeButton.addActionListener(e -> {
            removeAll();
            add(new HomeGUI());
            updateUI();
        });
        add(toHomeButton);

        createUserButton = new CustomButton("Create User");
        createUserButton.setBounds(50, 20, 160, 25);
        createUserButton.addActionListener(e -> {
            removeAll();
            add(new CreateUserGUI());
            updateUI();
        });
        add(createUserButton);

        modifyUserButton = new CustomButton("Modify User");
        modifyUserButton.setBounds(50, 100, 160, 25);
        modifyUserButton.addActionListener(e -> {
            removeAll();
            add(new ModifyUserGUI());
            updateUI();
        });
        add(modifyUserButton);

        changePasswordButton = new CustomButton("Change Password");
        changePasswordButton.setBounds(50, 140, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new ChangeUsersPasswordGUI());
            updateUI();
        });
        add(changePasswordButton);

        createOrganisationButton = new CustomButton("Create Organisation");
        createOrganisationButton.setBounds(250, 20, 160, 25);
        createOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new SignUpOrganisationGUI());
            updateUI();
        });
        add(createOrganisationButton);

        modifyOrganisationButton = new CustomButton("Modify Organisation");
        modifyOrganisationButton.setBounds(250, 100, 160, 25);
        modifyOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new ModifyOrganisationGUI());
            updateUI();
        });
        add(modifyOrganisationButton);
    }
}