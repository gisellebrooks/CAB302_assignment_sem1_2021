package marketplace.GUI;

import marketplace.GUI.Settings.SettingsNavigationAdminGUI;
import marketplace.GUI.Settings.SettingsNavigationUserGUI;
import marketplace.Objects.Order;
import marketplace.Util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class OrderGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel assetNamePromptLabel;
    private static JComboBox assetBox;
    private static CustomButton sellButton;
    private static CustomButton buyButton;
    private static CustomButton toSettingButton;
    private static JLabel valid;
    private static JLabel invalid;
    Fonts fonts;

    public OrderGUI() {
        this.fonts = new Fonts();
        setLayout(null);
        setBounds(0, 0, 1181, 718);

        List<Order> buy = MainGUI.orderHandler.getAllActiveBuyOrders();
        List<String> assets = new ArrayList<String>();

        toSettingButton = new CustomButton("Settings");
        toSettingButton.setBounds(1000, 20, 120, 25);
        toSettingButton.addActionListener(e -> {
            removeAll();
            if (MainGUI.userType.equals("ADMIN")) {
                add(new SettingsNavigationAdminGUI());
            } else {
                add(new SettingsNavigationUserGUI());
            }
            updateUI();
        });
        add(toSettingButton);

        assetNamePromptLabel = new CustomLabel(String.format("Select Asset"), fonts.smallHeading, true);
        assetNamePromptLabel.setBounds(550, 60, 80, 50);
        assetNamePromptLabel.setFont(fonts.inputLabel);
        add(assetNamePromptLabel);

        List<String> assetNames =  MainGUIHandler.orderHandler.getAllActiveAssetNames();
        assetBox= new JComboBox(MainGUIHandler.orderHandler.getAllActiveAssetNames().toArray(new String[0]));
        System.out.println(assetNames);

//        assetNames = assetBox.getSelectedItem().toString();

//        assetBox = new JComboBox(assetNames.toArray());
        assetBox.setBounds(510, 100, 160, 25);
        add(assetBox);
        assetBox.addActionListener(this::actionPerformed);
        assetBox.setForeground(Color.BLACK);
        assetBox.setBackground(Color.WHITE);
        assetBox.setOpaque(true);
        assetBox.setFont(fonts.small);

        buyButton = new CustomButton("Buy");
        buyButton.setBounds(300, 150, 120, 25);
        buyButton.addActionListener(e -> {

            if (assetBox.getSelectedItem() != null) {
                MainGUI.assetName = assetBox.getSelectedItem().toString();
                removeAll();
                add(new BuySellOrderGUI( MainGUI.assetName, false));
                updateUI();
            } else {
                invalid.setText("Select an asset");
            }
        });
        add(buyButton);

        sellButton = new CustomButton("Sell");
        sellButton.setBounds(150, 150, 120, 25);
        sellButton.addActionListener(e -> {

            if (assetBox.getSelectedItem() != null) {
                MainGUI.assetName = assetBox.getSelectedItem().toString();
                removeAll();
                add(new BuySellOrderGUI( MainGUI.assetName, true));
                updateUI();
            }
            else {
                invalid.setText("Select an asset");
            }
        });
        add(sellButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 120, 340, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 120, 340, 25);
        add(invalid);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}