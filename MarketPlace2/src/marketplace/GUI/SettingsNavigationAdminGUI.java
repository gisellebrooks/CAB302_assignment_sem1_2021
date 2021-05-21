package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrganisationHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class SettingsNavigationAdminGUI extends JFrame implements ActionListener, Runnable {

    private static JButton createUserButton;
    private static JButton removeUserButton;
    private static JButton modifyUserButton;
    private static JButton createOrganisationButton;
    private static JButton removeOrganisationButton;
    private static JButton modifyOrganisationButton;

    private static Client client;
    private static OrganisationHandler organisationHandler;


    public static void main(String[] args){

        client = new Client();
        organisationHandler= new OrganisationHandler(client);

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new SettingsNavigationAdminGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(600,600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        createUserButton = new JButton("Create User");
        createUserButton.setBounds(50, 20, 150, 25);
        createUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignUpUserGUI().run();
            }
        });
        panel.add(createUserButton);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(50, 60, 150, 25);
        removeUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RemoveUserGUI().run();
            }
        });
        panel.add(removeUserButton);

        modifyUserButton = new JButton("Modify User");
        modifyUserButton.setBounds(50, 100, 150, 25);
        modifyUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ModifyUserGUI().run();
            }
        });
        panel.add(modifyUserButton);




        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(250, 20, 150, 25);
        createOrganisationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new SignUpOrganisationGUI().run();
            }
        });
        panel.add(createOrganisationButton);

        removeOrganisationButton = new JButton("Remove Organisation");
        removeOrganisationButton.setBounds(250, 60, 150, 25);
        removeOrganisationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new RemoveOrganisationGUI().run();
            }
        });
        panel.add(removeOrganisationButton);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(250, 100, 150, 25);
        modifyOrganisationButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ModifyOrganisationGUI().run();
            }
        });
        panel.add(modifyOrganisationButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        client = new Client();
        organisationHandler= new OrganisationHandler(client);

        try {
            client.connect();

        } catch (IOException er) {
            er.printStackTrace();
        }
    }
}