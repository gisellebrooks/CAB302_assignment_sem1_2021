package marketplace.GUI;

import marketplace.Objects.*;
import marketplace.Util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HomeGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel assetNamePromptLabel;
    private static JComboBox assetBox;
    private static CustomButton sellButton;
    private static CustomButton buyButton;
    private static CustomButton toSettingButton;
    private static JLabel valid;
    private static JLabel invalid;
    List<Order> activeBuyOrders;
    List<SellOrder> activeSellOrders;
    Fonts fonts;

    public void refreshPage() {
        this.removeAll();
        this.add(new HomeGUI());
        this.updateUI();
    }

    public HomeGUI() {
        this.fonts = new Fonts();
        setLayout(null);
        setBounds(0, 0, 1181, 718);
        LogoPanel logo = new LogoPanel();
        logo.setBounds(10, 10, 200, 50);
        add(logo);

        List<Order> buy = MainGUI.orderHandler.getAllActiveBuyOrders();
        List<String> assets = new ArrayList<String>();

        toSettingButton = new CustomButton("Settings");
        toSettingButton.setBounds(1000, 20, 120, 25);
        toSettingButton.addActionListener(e -> {
            removeAll();
            if (MainGUI.userType.equals("admin")) {
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

        List<String> assetNames =  MainGUI.orderHandler.getAllActiveAssetNames();
        assetBox= new JComboBox(MainGUI.orderHandler.getAllActiveAssetNames().toArray(new String[0]));
        

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
                add(new BuySellOrderGUI( MainGUI.assetName,null, false));
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
                try {
                    MainGUI.assetName = assetBox.getSelectedItem().toString();
                    removeAll();
                    Inventory asset = MainGUI.inventoryHandler.getOrganisationsAsset(MainGUI.orgID, MainGUI.assetName);
                    add(new BuySellOrderGUI(MainGUI.assetName, asset, true));
                    updateUI();
                } catch (Exception exception) {
                    invalid.setText("You do not have any of this asset to sell");
                }
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

        activeBuyOrders = MainGUI.orderHandler.getAllOrganisationBuyOrders(MainGUI.orgID);
        Collections.reverse(activeBuyOrders);

        activeSellOrders = MainGUI.orderHandler.getAllOrganisationSellOrders(MainGUI.orgID);
        Collections.reverse(activeSellOrders);

        JLabel buyOrdersLabel = new CustomLabel("Your Buy Orders", fonts.smallHeading, false);
        buyOrdersLabel.setBounds(10, 200, 340, 25);

        add(buyOrdersLabel);

        add(new History(false));

        JLabel sellOrdersLabel = new CustomLabel("Your Sell Orders", fonts.smallHeading, false);
        sellOrdersLabel.setBounds(10, 400, 340, 25);
        add(sellOrdersLabel);
        add(new History(true));

    }

    class History extends DefaultJPanel {

        public History(boolean isSell){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            if (!isSell) {
                setBounds(10, 240, 500, 400);
            } else {
                setBounds(610, 240, 500, 400);
            }
            JLabel buyHistoryLabel = new CustomLabel(String.format("Your Recent %s Orders", isSell ? "sell" : "buy"), fonts.smallHeading, true);
            add(buyHistoryLabel);
            add(new TableRow(
                    "Asset",
                    "Quantity",
                    "Price per unit",
                    "Order Date",
                    null,
                    false
            ));
            for (Order order: isSell ? activeSellOrders : activeBuyOrders) {
                add(new OrderRow(order, isSell));
            }
            Dimension minSize = new Dimension(600, Short.MAX_VALUE);
            Dimension prefSize = new Dimension(600, Short.MAX_VALUE);
            Dimension maxSize = new Dimension(600, Short.MAX_VALUE);
            add(new Box.Filler(minSize, prefSize, maxSize));
        }
    }
    class OrderRow extends DefaultJPanel {

        public OrderRow(Order order, boolean isSell){
            
            
            

            add(new TableRow(
                    order.getAssetName(),
                    String.format("%d", order.getQuantity()),
                    NumberFormat.getCurrencyInstance().format(order.getPrice().divide(new BigDecimal(order.getQuantity()), 2, RoundingMode.HALF_UP)),
                    new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(order.getOrderDate()),
                    order,
                    isSell
            ));
        }
    }

    class TableRow extends DefaultJPanel {
        public TableRow(String assetName, String quantity, String pricePerUnit, String orderDate, Order order, boolean isSell){
            JPanel container = new DefaultJPanel();
            JLabel assetLabel =  new CustomLabel(assetName, fonts.small, true);
            assetLabel.setPreferredSize(new Dimension(70, 20));
            JLabel quantityLabel =  new CustomLabel(quantity, fonts.small, true);
            quantityLabel.setPreferredSize(new Dimension(70, 20));
            JLabel pricePerUnitLabel =  new CustomLabel(pricePerUnit, fonts.small, true);
            pricePerUnitLabel.setPreferredSize(new Dimension(100, 20));
            JLabel orderDateLabel = new CustomLabel(orderDate, fonts.small, true);
            orderDateLabel.setPreferredSize(new Dimension(140, 20));
            container.add(assetLabel);
            container.add(quantityLabel);
            container.add(pricePerUnitLabel);
            container.add(orderDateLabel);
            if (order != null) {
                JButton delete = new CustomButton("Delete");
                delete.setPreferredSize(new Dimension(70, 30));
                delete.addActionListener(e -> {
                    MainGUI.orderHandler.deleteOrder(isSell ? "sell" : "buy", order.getOrderID());
                    refreshPage();
                });
                container.add(delete);
            } else {
                JLabel emptyLabel = new CustomLabel("", fonts.small, true);
                emptyLabel.setPreferredSize(new Dimension(70, 20));
                container.add(emptyLabel);
            }
            add(container);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}