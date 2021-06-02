package marketplace.GUI.Settings;

import marketplace.GUI.MainGUIHandler;
import marketplace.Objects.Inventory;
import marketplace.Objects.Organisation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.List;


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
    private static JTextField assetNameText;
    private static JButton findAssetButton;
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
        assetNamePromptLabel.setBounds(250, 20, 180, 25);
        add(assetNamePromptLabel);

        assetNameText = new JTextField();
        assetNameText.setBounds(250, 40, 165, 25);
        add(assetNameText);

        findAssetButton = new JButton("Find Asset");
        findAssetButton.setBounds(250, 80, 160, 25);
        findAssetButton.addActionListener(this);
        add(findAssetButton);

        assetQuantityPromptLabel = new JLabel("Quantity:");
        assetQuantityPromptLabel.setBounds(250, 130, 180, 25);
        add(assetQuantityPromptLabel);

        assetQuantityText = new JTextField();
        assetQuantityText.setBounds(250, 150, 165, 25);
        add(assetQuantityText);

        modifyAssetQuantityButton = new JButton("Change");
        modifyAssetQuantityButton.setBounds(250, 190, 160, 25);
        modifyAssetQuantityButton.addActionListener(this);
        add(modifyAssetQuantityButton);



        newAssetPromptLabel = new JLabel("Add new asset:");
        newAssetPromptLabel.setBounds(250, 240, 180, 25);
        add(newAssetPromptLabel);

        newAssetNameText = new JTextField();
        newAssetNameText.setBounds(250, 260, 165, 25);
        add(newAssetNameText);

        newAssetQuantityPromptLabel = new JLabel("Quantity:");
        newAssetQuantityPromptLabel.setBounds(250, 290, 180, 25);
        add(newAssetQuantityPromptLabel);

        newAssetQuantityText = new JTextField();
        newAssetQuantityText.setBounds(250, 310, 165, 25);
        add(newAssetQuantityText);

        addNewAssetButton = new JButton("Add");
        addNewAssetButton.setBounds(250, 350, 160, 25);
        addNewAssetButton.addActionListener(this);
        add(addNewAssetButton);



        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(460, 40, 120, 25);
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
        valid.setBounds(10, 280, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 280, 340, 25);
        add(invalid);
    }

    boolean foundOrganisation = false;
    Organisation organisation = null;
    boolean foundAsset = false;
    String organisationID = null;
    List<Inventory> organisationsInventory = null;
    Inventory currentAsset;

    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        BigDecimal credits;
        int newQuantityForAsset;
        int newAssetQuantity;
        String newAssetName;

        if (e.getSource() == findOrganisationButton) {
            organisationID = organisationIDText.getText();

            if (MainGUIHandler.organisationHandler.organisationIDExists(organisationID)) {
                foundOrganisation = true;
                organisation = MainGUIHandler.organisationHandler.getOrganisation(organisationID);
                nameText.setText(organisation.getOrgName());
                creditsText.setText(organisation.getCredits().toString());
            } else {
                foundOrganisation = false;
                organisation = null;
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
                            credits = new BigDecimal(creditsText.getText());
                            MainGUIHandler.organisationHandler.updateOrganisationCredits(organisationID, credits);
                            valid.setText("credits changed");
                        } catch (NumberFormatException NumberFormatError) {
                            invalid.setText("Please enter a valid number");
                        }
                    } else {
                        invalid.setText("Current buy orders don't allow that");
                    }
                }
            } else {
                invalid.setText("Please select an organisation");
            }
        }

        if (e.getSource() == findAssetButton) {
            assetQuantityText.setText("");
            if (foundOrganisation) {
                if (assetNameText.getText().isEmpty() || assetNameText.getText() != null) {
                    organisationsInventory = MainGUIHandler.inventoryHandler.getOrganisationsAssets(organisationID);
                    for (Inventory inventory : organisationsInventory) {
                        if (inventory.getAssetName().equals(assetNameText.getText())) {
                            foundAsset = true;
                            currentAsset = inventory;
                            assetQuantityText.setText(String.valueOf(currentAsset.getQuantity()));
                        }
                    }
                    if (foundAsset) {
                        assetQuantityText.setText(String.valueOf(currentAsset.getQuantity()));
                    } else {
                        invalid.setText("Asset not found");
                    }
                } else {
                    invalid.setText("Please enter an asset name");
                }
            } else {
                invalid.setText("Please find an organisation");
            }
        }

        if (e.getSource() == modifyAssetQuantityButton) {
            if (foundOrganisation && foundAsset) {
                if (assetQuantityText.getText().isEmpty() || assetQuantityText.getText().equals(null)) {
                    invalid.setText("Please enter a valid number");
                } else {
                    newQuantityForAsset = Integer.parseInt(assetQuantityText.getText());
                    if (newQuantityForAsset > (MainGUIHandler.organisationHandler.getOrganisationSellingQuantity(organisationID))) {
                        try {
                            if (newQuantityForAsset == 0) {
                                MainGUIHandler.inventoryHandler.deleteOrganisationAsset(currentAsset.getAssetID(), organisationID);
                            } else{
                                MainGUIHandler.inventoryHandler.updateOrganisationAssetQuantity(currentAsset.getAssetID(), organisationID, newQuantityForAsset);
                            }
                            valid.setText("asset quantity changed");
                            invalid.setText("");
                            assetQuantityText.setText("");
                        } catch (NumberFormatException NumberFormatError) {
                            invalid.setText("Please enter a valid number");
                        }
                    } else {
                        invalid.setText("Current sell orders don't allow that");
                    }
                }
            } else {
                invalid.setText("Please select an organisation and asset");
            }
        }


        if (e.getSource() == addNewAssetButton) {
            if (foundOrganisation) {
                if (newAssetNameText.getText().isEmpty() || newAssetNameText.getText().equals(null)) {
                    invalid.setText("Please enter an asset name");
                } else {
                    newAssetName = newAssetNameText.getText();
                    if (!newAssetQuantityText.getText().isEmpty()) {
                        newAssetQuantity = Integer.parseInt(newAssetQuantityText.getText());
                        try {
                            if (!MainGUIHandler.inventoryHandler.assetNameExists(newAssetName, organisationID)) {
                                MainGUIHandler.inventoryHandler.addAsset(MainGUIHandler.inventoryHandler.newAssetID(), newAssetName, organisationID, newAssetQuantity);
                                valid.setText("asset added");
                                invalid.setText("");
                                newAssetNameText.setText("");
                                newAssetQuantityText.setText("");
                            } else{
                                invalid.setText("Organisation already has that asset");
                            }

                        } catch (NumberFormatException NumberFormatError) {
                            invalid.setText("Please enter a valid number");
                        }
                    } else {
                        invalid.setText("Please enter a valid asset quantity");
                    }
                }
            } else {
                invalid.setText("Please select an organisation");
            }
        }
    }
}