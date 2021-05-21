package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrganisationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class RemoveOrganisationGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton removeOrganisationButton;
    private static JLabel valid;
    private static JLabel invalid;

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
        SwingUtilities.invokeLater(new RemoveOrganisationGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(550,450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        organisationIDPromptLabel = new JLabel("ID of organisation to remove");
        organisationIDPromptLabel.setBounds(10, 20, 160, 25);
        panel.add(organisationIDPromptLabel);

        organisationIDText = new JTextField(20);
        organisationIDText.setBounds(10, 50, 160, 25);
        panel.add(organisationIDText);

        removeOrganisationButton = new JButton("Remove");
        removeOrganisationButton.setBounds(10, 80, 80, 25);
        removeOrganisationButton.addActionListener(new RemoveOrganisationGUI());
        panel.add(removeOrganisationButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 120, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 120, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        String organisationID = organisationIDText.getText();

        client = new Client();
        organisationHandler= new OrganisationHandler(client);

        try {
            client.connect();

        } catch (IOException er) {
            er.printStackTrace();
        }

        if (organisationHandler.organisationIDExists(organisationID)) {
            organisationHandler.removeOrganisation(organisationID);
            valid.setText("Organisation was successfully removed");
        } else {
            invalid.setText("Organisation can't be found or can't be removed");
        }
    }
}