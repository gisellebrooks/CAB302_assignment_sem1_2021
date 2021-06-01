package marketplace.GUI.Settings;

import marketplace.GUI.LoginGUI;
import marketplace.GUI.MainGUIHandler;
import marketplace.GUI.OrderGUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsNavigationAdminGUI extends JPanel implements ActionListener {

    private static JButton createUserButton;
    private static JButton removeUserButton;
    private static JButton modifyUserButton;
    private static JButton changePasswordButton;
    private static JButton createOrganisationButton;
    private static JButton removeOrganisationButton;
    private static JButton modifyOrganisationButton;
    private static JButton logOutButton;
    private static JButton toHomeButton;

    public SettingsNavigationAdminGUI() {
        setLayout(null);
        setBounds(0, 0, 600, 600);

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

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(50, 60, 160, 25);
        removeUserButton.addActionListener(e -> {
            removeAll();
            add(new RemoveUserGUI());
            updateUI();
        });
        add(removeUserButton);

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

        logOutButton = new JButton("Logout");
        logOutButton.setBounds(150, 200, 160, 25);
        logOutButton.addActionListener(e -> {
            removeAll();
            MainGUIHandler.user = null;
            MainGUIHandler.userType = "USER";
            add(new LoginGUI());
            updateUI();
        });
        add(logOutButton);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(250, 20, 160, 25);
        createOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new SignUpOrganisationGUI());
            updateUI();
        });
        add(createOrganisationButton);

        removeOrganisationButton = new JButton("Remove Organisation");
        removeOrganisationButton.setBounds(250, 60, 160, 25);
        removeOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new RemoveOrganisationGUI());
            updateUI();
        });
        add(removeOrganisationButton);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(250, 100, 160, 25);
        modifyOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new ModifyOrganisationGUI());
            updateUI();
        });
        add(modifyOrganisationButton);
    }

    public void actionPerformed(ActionEvent e) {

    }
}