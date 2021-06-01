package marketplace.GUI;

import marketplace.GUI.Settings.SettingsNavigationAdminGUI;
import marketplace.GUI.Settings.SettingsNavigationUserGUI;
import marketplace.Objects.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class OrderGUI extends JPanel implements ActionListener {

    private static JLabel assetNamePromptLabel;
    private static JComboBox assetBox;
    private static JButton sellButton;
    private static JButton buyButton;
    private static JButton toSettingButton;
    private static JLabel valid;
    private static JLabel invalid;

    public OrderGUI() {

        setLayout(null);
        setBounds(0, 0, 1181, 718);

        List<Order> buy = MainGUIHandler.orderHandler.getAllActiveBuyOrders();
        List<String> assets = new ArrayList<String>();

        toSettingButton = new JButton("SETTINGS");
        toSettingButton.setBounds(430, 20, 120, 25);
        toSettingButton.addActionListener(e -> {
            removeAll();
            if (MainGUIHandler.userType.equals("ADMIN")) {
                add(new SettingsNavigationAdminGUI());
            } else {
                add(new SettingsNavigationUserGUI());
            }
            updateUI();
        });
        add(toSettingButton);

        assetNamePromptLabel = new JLabel("Select Asset");
        assetNamePromptLabel.setBounds(250, 20, 80, 50);
        add(assetNamePromptLabel);

        assetBox = new JComboBox(MainGUIHandler.orderHandler.getAllActiveAssetNames().toArray());
        assetBox.setBounds(200, 60, 160, 25);
        add(assetBox);
        assetBox.addActionListener(this);



        sellButton = new JButton("Sell");
        sellButton.setBounds(150, 150, 120, 25);
        sellButton.addActionListener(e -> {

            if (assetBox.getSelectedItem() != null) {
                MainGUIHandler.assetName = assetBox.getSelectedItem().toString();
                removeAll();
                add(new BuySellOrderGUI( MainGUIHandler.assetName, true));
                updateUI();
            }
            invalid.setText("Select an asset");

        });
        add(sellButton);

        buyButton = new JButton("Buy");
        buyButton.setBounds(300, 150, 120, 25);
        buyButton.addActionListener(e -> {

            if (assetBox.getSelectedItem() != null) {
                MainGUIHandler.assetName = assetBox.getSelectedItem().toString();
                removeAll();
                add(new BuySellOrderGUI( MainGUIHandler.assetName, true));
                updateUI();
            }
            invalid.setText("Select an asset");

        });
        add(buyButton);

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