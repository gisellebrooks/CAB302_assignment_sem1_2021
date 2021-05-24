package marketplace.GUI;

import marketplace.Objects.Organisation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyOrganisationGUI extends JPanel implements ActionListener {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton findOrganisationButton;

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton modifyOrganisationButton;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public ModifyOrganisationGUI() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        organisationIDPromptLabel = new JLabel("Organisation ID:");
        organisationIDPromptLabel.setBounds(10, 20, 80, 25);
        add(organisationIDPromptLabel);

        organisationIDText = new JTextField(20);
        organisationIDText.setBounds(10, 40, 165, 25);
        add(organisationIDText);

        findOrganisationButton = new JButton("Find Organisation");
        findOrganisationButton.setBounds(10, 60, 140, 25);
        findOrganisationButton.addActionListener(this);
        add(findOrganisationButton);


        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 80, 80, 25);
        add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 100, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 140, 180, 25);
        add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 160, 165, 25);
        add(creditsText);

        modifyOrganisationButton = new JButton("Modify Organisation");
        modifyOrganisationButton.setBounds(10, 200, 80, 25);
        modifyOrganisationButton.addActionListener(this);
        add(modifyOrganisationButton);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(300, 50, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            add(new SettingsNavigationAdminGUI());
            updateUI();
        });
        add(toSettingsButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 360, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 260, 340, 25);
        add(invalid);
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

            if (MainGUIHandler.organisationHandler.organisationIDExists(organisationID)) {
                foundOrganisation = true;
                organisation = MainGUIHandler.organisationHandler.getOrganisationInformation(organisationID);

                nameText.setText(organisation.getOrgName());
                // creditsText.setText(Double.toString((organisation.getCredits())));

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