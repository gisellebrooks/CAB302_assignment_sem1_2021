package marketplace.GUI;

import marketplace.GUI.MainGUI;
import marketplace.GUI.FullSizeJPanel;
import marketplace.Objects.Inventory;
import marketplace.Objects.Organisation;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ModifyOrganisationGUI extends FullSizeJPanel implements ActionListener {

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

        organisationIDPromptLabel = new JLabel("Organisation ID:");
        organisationIDPromptLabel.setBounds(10, 20, 160, 25);
        add(organisationIDPromptLabel);

        organisationIDText = new CustomTextField(20);
        organisationIDText.setBounds(10, 40, 165, 25);
        add(organisationIDText);

        findOrganisationButton = new CustomButton("Find Organisation");
        findOrganisationButton.setBounds(10, 80, 140, 25);
        findOrganisationButton.addActionListener(this);
        add(findOrganisationButton);

        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 130, 160, 25);
        add(namePromptLabel);

        nameText = new CustomTextField(20);
        nameText.setBounds(10, 150, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 180, 180, 25);
        add(creditsPromptLabel);

        creditsText = new CustomTextField(20);
        creditsText.setBounds(10, 200, 165, 25);
        add(creditsText);

        modifyCreditsButton = new CustomButton("Change Credits");
        modifyCreditsButton.setBounds(10, 240, 160, 25);
        modifyCreditsButton.addActionListener(this);
        add(modifyCreditsButton);

        assetNamePromptLabel = new JLabel("Asset Name:");
        assetNamePromptLabel.setBounds(250, 20, 180, 25);
        add(assetNamePromptLabel);

        assetNameText = new CustomTextField();
        assetNameText.setBounds(250, 40, 165, 25);
        add(assetNameText);

        findAssetButton = new CustomButton("Find Asset");
        findAssetButton.setBounds(250, 80, 160, 25);
        findAssetButton.addActionListener(this);
        add(findAssetButton);

        assetQuantityPromptLabel = new JLabel("Quantity:");
        assetQuantityPromptLabel.setBounds(250, 130, 180, 25);
        add(assetQuantityPromptLabel);

        assetQuantityText = new CustomTextField();
        assetQuantityText.setBounds(250, 150, 165, 25);
        add(assetQuantityText);

        modifyAssetQuantityButton = new CustomButton("Change");
        modifyAssetQuantityButton.setBounds(250, 190, 160, 25);
        modifyAssetQuantityButton.addActionListener(this);
        add(modifyAssetQuantityButton);

        newAssetPromptLabel = new JLabel("Add new asset:");
        newAssetPromptLabel.setBounds(250, 240, 180, 25);
        add(newAssetPromptLabel);

        newAssetNameText = new CustomTextField();
        newAssetNameText.setBounds(250, 260, 165, 25);
        add(newAssetNameText);

        newAssetQuantityPromptLabel = new JLabel("Quantity:");
        newAssetQuantityPromptLabel.setBounds(250, 290, 180, 25);
        add(newAssetQuantityPromptLabel);

        newAssetQuantityText = new CustomTextField();
        newAssetQuantityText.setBounds(250, 310, 165, 25);
        add(newAssetQuantityText);

        addNewAssetButton = new CustomButton("Add");
        addNewAssetButton.setBounds(250, 350, 160, 25);
        addNewAssetButton.addActionListener(this);
        add(addNewAssetButton);

        toSettingsButton = new CustomButton("SETTINGS");
        toSettingsButton.setBounds(460, 40, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            if (MainGUI.userType.equals("ADMIN")) {
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
    Inventory organisationsInventory = null;
    Inventory currentAsset;

    public void actionPerformed(ActionEvent e) {

        BigDecimal credits;
        int newQuantityForAsset;
        int newAssetQuantity;
        String newAssetName;
        String selectedAsset;

        valid.setText("");
        invalid.setText("");

        if (e.getSource() == findOrganisationButton) {
            try {
                organisationID = organisationIDText.getText();
                organisation = MainGUI.organisationHandler.getOrganisation(organisationID);
                foundOrganisation = true;
                valid.setText("Organisation found");
                nameText.setText(organisation.getOrgName());
                creditsText.setText(organisation.getCredits().toString());
            } catch (Exception exception) {
                foundOrganisation = false;
                organisation = null;
                nameText.setText("");
                creditsText.setText("");
                valid.setText("");
                invalid.setText(exception.getMessage());
            }
        }

        if (e.getSource() == modifyCreditsButton) {
            if (foundOrganisation) {
                try {
                    credits = new BigDecimal(creditsText.getText());
                    MainGUI.organisationHandler.updateOrganisationCredits(organisationID, credits);
                    valid.setText("credits changed");
                    invalid.setText("");
                } catch (NumberFormatException NumberFormatError) {
                    invalid.setText("Please enter a valid number");
                } catch (Exception exception) {
                    invalid.setText(exception.getMessage());
                }
            } else {
                invalid.setText("Please select an organisation");
            }
        }

        if (e.getSource() == findAssetButton) {
            if (foundOrganisation) {
                try {
                    assetQuantityText.setText("");
                    selectedAsset = assetNameText.getText();
                    currentAsset = MainGUI.inventoryHandler.getOrganisationsAsset(organisationID, selectedAsset);
                    foundAsset = true;
                    assetQuantityText.setText(String.valueOf(currentAsset.getQuantity()));

                } catch (Exception exception) {
                    invalid.setText(exception.getMessage());
                }

            } else {
                invalid.setText("Please find an organisation");
            }
        }

        if (e.getSource() == modifyAssetQuantityButton) {
            if (foundOrganisation && foundAsset) {
                try {
                    newQuantityForAsset = Integer.parseInt(assetQuantityText.getText());
                    MainGUI.inventoryHandler.updateOrganisationAssetQuantity(currentAsset.getAssetID(), organisationID, newQuantityForAsset);

                    valid.setText("asset quantity changed");
                    invalid.setText("");
                    assetQuantityText.setText("");
                } catch (NumberFormatException NumberFormatError) {
                    invalid.setText("Please enter a valid number");
                } catch (Exception exception) {
                    invalid.setText(exception.getMessage());
                }
            } else {
                invalid.setText("Please select an organisation and asset");
            }
        }

        if (e.getSource() == addNewAssetButton) {
            if (foundOrganisation) {
                try {
                    newAssetName = newAssetNameText.getText();
                    newAssetQuantity = Integer.parseInt(newAssetQuantityText.getText());
                    MainGUI.inventoryHandler.addAsset(MainGUI.inventoryHandler.newAssetID(), newAssetName, organisationID, newAssetQuantity);
                    valid.setText("asset added");
                    invalid.setText("");
                    newAssetNameText.setText("");
                    newAssetQuantityText.setText("");
                } catch (NumberFormatException NumberFormatError) {
                    invalid.setText("Please enter a valid number");
                } catch (Exception exception) {
                    invalid.setText(exception.getMessage());
                }
            } else {
                invalid.setText("Please select an organisation");
            }
        }
    }
}