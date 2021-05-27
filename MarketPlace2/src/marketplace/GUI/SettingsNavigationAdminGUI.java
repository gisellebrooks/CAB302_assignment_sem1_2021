package marketplace.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsNavigationAdminGUI extends JPanel implements ActionListener {

    private static JButton createUserButton;
    private static JButton removeUserButton;
    private static JButton modifyUserButton;
    private static JButton createOrganisationButton;
    private static JButton removeOrganisationButton;
    private static JButton modifyOrganisationButton;

    public SettingsNavigationAdminGUI() {
        createGui();
    }

    public void createGui() {
        setLayout(null);
        setBounds(0, 0, 600, 600);

        createUserButton = new JButton("Create User");
        createUserButton.setBounds(50, 20, 150, 25);
        createUserButton.addActionListener(e -> {
            removeAll();
            add(new SignUpUserGUI());
            updateUI();
        });
        add(createUserButton);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(50, 60, 150, 25);
        removeUserButton.addActionListener(e -> {
            removeAll();
            add(new RemoveUserGUI());
            updateUI();
        });
        add(removeUserButton);

        modifyUserButton = new JButton("Modify User");
        modifyUserButton.setBounds(50, 100, 150, 25);
        modifyUserButton.addActionListener(e -> {
            removeAll();
            add(new ModifyUserGUI());
            updateUI();
        });
        add(modifyUserButton);




        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(250, 20, 150, 25);
        createOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new SignUpOrganisationGUI());
            updateUI();
        });
        add(createOrganisationButton);

        removeOrganisationButton = new JButton("Remove Organisation");
        removeOrganisationButton.setBounds(250, 60, 150, 25);
        removeOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new RemoveOrganisationGUI());
            updateUI();
        });
        add(removeOrganisationButton);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(250, 100, 150, 25);
        modifyOrganisationButton.addActionListener(e -> {
            removeAll();
            add(new ModifyOrganisationGUI());
            updateUI();
        });
        add(modifyOrganisationButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}