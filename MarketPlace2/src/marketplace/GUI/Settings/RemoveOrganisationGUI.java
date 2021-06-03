package marketplace.GUI.Settings;

import marketplace.GUI.MainGUIHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RemoveOrganisationGUI extends JPanel implements ActionListener {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton removeOrganisationButton;
    private static JComboBox organisationComboBox;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public RemoveOrganisationGUI() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        organisationIDPromptLabel = new JLabel("ID of organisation to remove");
        organisationIDPromptLabel.setBounds(10, 20, 160, 25);
        add(organisationIDPromptLabel);

        organisationComboBox = new JComboBox(MainGUIHandler.organisationHandler.getAllOrganisationsNames().toArray());
        organisationComboBox.setBounds(10, 50, 200, 25);
        add(organisationComboBox);

        removeOrganisationButton = new JButton("Remove");
        removeOrganisationButton.setBounds(10, 90, 80, 25);
        removeOrganisationButton.addActionListener(this);
        add(removeOrganisationButton);

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
        valid.setBounds(10, 120, 340, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 120, 340, 25);
        add(invalid);
    }

    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        String organisationID = null;
        String organisationName;

        // get organisation ID with org name
        organisationName = organisationComboBox.getSelectedItem().toString();
        try {
            organisationID = MainGUIHandler.organisationHandler.getOrganisationID(organisationName);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        try {
            if (MainGUIHandler.organisationHandler.organisationIDExists(organisationID)) {
                try {
                    MainGUIHandler.organisationHandler.removeOrganisation(organisationID);
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
                valid.setText("Organisation was successfully removed");
            } else {
                invalid.setText("Organisation can't be found or can't be removed");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}