package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrganisationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class SignUpOrganisationGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton createOrganisationButton;
    private static JLabel givenIDLabel;
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
        SwingUtilities.invokeLater(new SignUpOrganisationGUI());
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

        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 20, 160, 25);
        panel.add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        panel.add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 80, 180, 25);
        panel.add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 100, 165, 25);
        panel.add(creditsText);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(10, 140, 160, 25);
        createOrganisationButton.addActionListener(new SignUpOrganisationGUI());
        panel.add(createOrganisationButton);

        // where the given password goes
        givenIDLabel = new JLabel("");
        givenIDLabel.setBounds(10, 200, 220, 25);
        panel.add(givenIDLabel);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 180, 260, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 180, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newID = null;
        double credits = 0;
        String organisationName = null;

        valid.setText("");
        invalid.setText("Invalid inputs");

        client = new Client();
        organisationHandler= new OrganisationHandler(client);

        try {
            client.connect();

        } catch (IOException er) {
            er.printStackTrace();
        }

        try  {
            credits = Double.parseDouble(creditsText.getText());
            newID = organisationHandler.newOrganisationID();
            organisationName = nameText.getText();

            if (organisationHandler.organisationNameExists(organisationName)) {
                invalid.setText("That organisation name is taken");
            } else {
                organisationHandler.addOrganisation(newID, organisationName, credits);
                valid.setText("Organisation was successfully created");
                invalid.setText("");
                givenIDLabel.setText("OrganisationID: " + newID);


            }
        } catch (NumberFormatException NumberFormatError) {
            invalid.setText("Credits must be a number");
        }
    }
}