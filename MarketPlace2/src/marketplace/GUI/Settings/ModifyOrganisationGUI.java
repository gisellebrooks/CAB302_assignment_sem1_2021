package marketplace.GUI.Settings;

import marketplace.GUI.MainGUIHandler;
import marketplace.Objects.Organisation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;


public class ModifyOrganisationGUI extends JPanel implements ActionListener {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton findOrganisationButton;

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton modifyCreditsButton;

    private static JLabel assetNamePromptLabel;
    private static JComboBox assetNameComboBox;
    private static JLabel assetQuantityPromptLabel;
    private static JTextField assetQuantityText;
    private static JButton modifyAssetQuantityButton;

    private static JLabel newAssetPromptLabel;
    private static JTextField newAssetNameText;
    private static JLabel newAssetQuantityPromptLabel;
    private static JTextField newAssetQuantityText;
    private static JButton addNewAssetButton;

    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public ModifyOrganisationGUI() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        organisationIDPromptLabel = new JLabel("Organisation ID:");
        organisationIDPromptLabel.setBounds(10, 20, 160, 25);
        add(organisationIDPromptLabel);

        organisationIDText = new JTextField(20);
        organisationIDText.setBounds(10, 40, 165, 25);
        add(organisationIDText);

        findOrganisationButton = new JButton("Find Organisation");
        findOrganisationButton.setBounds(10, 80, 140, 25);
        findOrganisationButton.addActionListener(this);
        add(findOrganisationButton);


        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 130, 160, 25);
        add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 150, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 180, 180, 25);
        add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 200, 165, 25);
        add(creditsText);

        modifyCreditsButton = new JButton("Change Credits");
        modifyCreditsButton.setBounds(10, 240, 160, 25);
        modifyCreditsButton.addActionListener(this);
        add(modifyCreditsButton);





        assetNamePromptLabel = new JLabel("Asset Name:");
        assetNamePromptLabel.setBounds(250, 40, 180, 25);
        add(assetNamePromptLabel);

        assetNameComboBox = new JComboBox();
        assetNameComboBox.setBounds(250, 60, 165, 25);
        add(assetNameComboBox);

        assetQuantityPromptLabel = new JLabel("Quantity:");
        assetQuantityPromptLabel.setBounds(250, 100, 180, 25);
        add(assetQuantityPromptLabel);

        assetQuantityText = new JTextField();
        assetQuantityText.setBounds(250, 120, 165, 25);
        add(assetQuantityText);

        modifyAssetQuantityButton = new JButton("Change");
        modifyAssetQuantityButton.setBounds(250, 160, 160, 25);
        modifyAssetQuantityButton.addActionListener(this);
        add(modifyAssetQuantityButton);



        newAssetPromptLabel = new JLabel("Add new asset:");
        newAssetPromptLabel.setBounds(250, 220, 180, 25);
        add(newAssetPromptLabel);

        newAssetNameText = new JTextField();
        newAssetNameText.setBounds(250, 240, 165, 25);
        add(newAssetNameText);

        newAssetQuantityPromptLabel = new JLabel("Quantity:");
        newAssetQuantityPromptLabel.setBounds(250, 280, 180, 25);
        add(newAssetQuantityPromptLabel);

        newAssetQuantityText = new JTextField();
        newAssetQuantityText.setBounds(250, 300, 165, 25);
        add(newAssetQuantityText);

        addNewAssetButton = new JButton("Add");
        addNewAssetButton.setBounds(250, 340, 160, 25);
        addNewAssetButton.addActionListener(this);
        add(addNewAssetButton);



        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(600, 50, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            if (MainGUIHandler.userType.equals("ADMIN")) {
                add(new SettingsNavigationAdminGUI());
            } else {
                add(new SettingsNavigationUserGUI());
            }
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

    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");
        String organisationID = null;
        Double credits;

        if (e.getSource() == findOrganisationButton) {
            organisationID = organisationIDText.getText();

            if (MainGUIHandler.organisationHandler.organisationIDExists(organisationID)) {
                foundOrganisation = true;
                organisation = MainGUIHandler.organisationHandler.getOrganisation(organisationID);
                assetNameComboBox = new JComboBox(MainGUIHandler.inventoryHandler.getOrganisationsAssets(organisationID).toArray());
                assetNameComboBox.setBounds(250, 60, 165, 25);
                add(assetNameComboBox);
//                assetQuantityPromptLabel.setText();
                nameText.setText(organisation.getOrgName());
                creditsText.setText(organisation.getCredits().toString());
            } else {
                foundOrganisation = false;
                organisation = null;
                assetNameComboBox.setModel(new DefaultComboBoxModel());
                nameText.setText("");
                creditsText.setText("");
                invalid.setText("Organisation can't be found");
            }
        }

        if (e.getSource() == modifyCreditsButton) {
            if (foundOrganisation) {
                if (creditsText.getText().isEmpty() || creditsText.getText().equals(null)) {
                    invalid.setText("Please enter a valid number");
                } else {
                    if (1 == new BigDecimal(creditsText.getText()).compareTo(MainGUIHandler.organisationHandler.getOrganisationTotalOwing(organisationID))) {
                        try {
                            credits = Double.parseDouble(creditsText.getText());
                            creditsText.setText(credits.toString());
                            valid.setText("credits changed");
                        } catch (NumberFormatException NumberFormatError) {
                            invalid.setText("Please enter a valid number");
                        }
                    } else {
                        invalid.setText("The organisation has buy orders with a total value of more than that number");
                    }

                }
            } else {
                invalid.setText("Please select an organisation");
            }
        }
    }
}