package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrganisationHandler;
import marketplace.Objects.Organisation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class ModifyOrganisationGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton findOrganisationButton;

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton modifyOrganisationButton;
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
        SwingUtilities.invokeLater(new ModifyOrganisationGUI());
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



        organisationIDPromptLabel = new JLabel("Organisation ID:");
        organisationIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(organisationIDPromptLabel);

        organisationIDText = new JTextField(20);
        organisationIDText.setBounds(10, 40, 165, 25);
        panel.add(organisationIDText);

        findOrganisationButton = new JButton("Find Organisation");
        findOrganisationButton.setBounds(10, 60, 140, 25);
        findOrganisationButton.addActionListener(new ModifyOrganisationGUI());
        panel.add(findOrganisationButton);


        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 80, 80, 25);
        panel.add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 100, 165, 25);
        panel.add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 140, 180, 25);
        panel.add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 160, 165, 25);
        panel.add(creditsText);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(10, 200, 80, 25);
        modifyOrganisationButton.addActionListener(new ModifyOrganisationGUI());
        panel.add(modifyOrganisationButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 360, 260, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 260, 340, 25);
        panel.add(invalid);
    }

    boolean foundOrganisation = false;
    Organisation organisation = null;

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("Invalid inputs");
        String organisationID = null;
        Double credits;

        if (e.getSource() == findOrganisationButton) {
            organisationID = organisationIDText.getText();

            if (organisationHandler.organisationIDExists(organisationID)) {
                foundOrganisation = true;
                organisation = organisationHandler.getOrganisationInformation(organisationID);

                nameText.setText(organisation.getOrgName());
                creditsText.setText(Double.toString((organisation.getCredits())));

            } else {
                invalid.setText("Organisation can't be found");
            }

        }

        if (e.getSource() == modifyOrganisationButton) {
            if (foundOrganisation) {
                try {
                    credits = Double.parseDouble(creditsText.getText());
                    creditsText.setText(credits.toString());

                } catch (NumberFormatException NumberFormatError) {
                    NumberFormatError.printStackTrace();
                }
            }
        }

    }
}