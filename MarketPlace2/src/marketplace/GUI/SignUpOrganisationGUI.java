package marketplace.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpOrganisationGUI extends JPanel implements ActionListener {

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton createOrganisationButton;
    private static JLabel givenIDLabel;
    private static JLabel valid;
    private static JLabel invalid;

    public SignUpOrganisationGUI() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 20, 160, 25);
        add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 80, 180, 25);
        add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 100, 165, 25);
        add(creditsText);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(10, 140, 160, 25);
        createOrganisationButton.addActionListener(this);
        add(createOrganisationButton);

        // where the given password goes
        givenIDLabel = new JLabel("");
        givenIDLabel.setBounds(10, 200, 220, 25);
        add(givenIDLabel);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 180, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 180, 340, 25);
        add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String newID = null;
        double credits = 0;
        String organisationName = null;

        valid.setText("");
        invalid.setText("Invalid inputs");

        try  {
            credits = Double.parseDouble(creditsText.getText());
            newID = MainGUIHandler.organisationHandler.newOrganisationID();
            organisationName = nameText.getText();

            if (MainGUIHandler.organisationHandler.organisationNameExists(organisationName)) {
                invalid.setText("That organisation name is taken");
            } else {
                MainGUIHandler.organisationHandler.addOrganisation(newID, organisationName, credits);
                valid.setText("Organisation was successfully created");
                invalid.setText("");
                givenIDLabel.setText("OrganisationID: " + newID);


            }
        } catch (NumberFormatException NumberFormatError) {
            invalid.setText("Credits must be a number");
        }
    }
}