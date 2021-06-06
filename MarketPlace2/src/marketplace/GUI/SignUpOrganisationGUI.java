package marketplace.GUI;

import marketplace.GUI.MainGUI;
import marketplace.GUI.FullSizeJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpOrganisationGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton createOrganisationButton;
    private static JLabel givenIDLabel;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public SignUpOrganisationGUI() {

        setLayout(null);

        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 20, 160, 25);
        add(namePromptLabel);

        nameText = new CustomTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 80, 180, 25);
        add(creditsPromptLabel);

        creditsText = new CustomTextField(20);
        creditsText.setBounds(10, 100, 165, 25);
        add(creditsText);

        createOrganisationButton = new CustomButton("Create Organisation");
        createOrganisationButton.setBounds(10, 140, 160, 25);
        createOrganisationButton.addActionListener(this);
        add(createOrganisationButton);

        // where the given password goes
        givenIDLabel = new JLabel("");
        givenIDLabel.setBounds(10, 200, 220, 25);
        add(givenIDLabel);

        toSettingsButton = new CustomButton("SETTINGS");
        toSettingsButton.setBounds(300, 50, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            if (MainGUI.userType.equals("admin")) {
                add(new SettingsNavigationAdminGUI());
            } else {
                add(new SettingsNavigationUserGUI());
            }
            updateUI();
        });
        add(toSettingsButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 180, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 180, 340, 25);
        add(invalid);
    }

    public void actionPerformed(ActionEvent e) {
        String newID;
        double credits;
        String organisationName;

        valid.setText("");
        invalid.setText("");

        try  {
            credits = Double.parseDouble(creditsText.getText());
            newID = MainGUI.organisationHandler.newOrganisationID();
            organisationName = nameText.getText();

            MainGUI.organisationHandler.addOrganisation(newID, organisationName, credits);
            nameText.setText("");
            creditsText.setText("");
            valid.setText("Organisation was successfully created");
            invalid.setText("");
            givenIDLabel.setText("OrganisationID: " + newID);

        } catch (Exception exception) {
            invalid.setText(exception.getMessage());
        }
    }
}