/**
 * TODO: wherever we go to place a buy/sell order we need to add the methods addNewBuyOrder and addNewSellOrder. These methods
 *  each take userID, assetID, quantity of assets, and price per asset
 *
 * TODO: when calculating the order price, you need to add a check if the organisation has enough credits.
 *  Use the method in organisationHandler called organisationHasCredits which returns either true or false. This method
 *  takes orgID and total price
 *
 *
 */


package marketplace.GUI;

import marketplace.GUI.Settings.SettingsNavigationAdminGUI;
import marketplace.GUI.Settings.SettingsNavigationUserGUI;
import marketplace.Objects.Order;
import marketplace.Util.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <h1>Create the Buy and sell order GUIs!</h1>
 * * Displays a 'Place order panel' and to calculate buy and sell orders
 * * based on price and quantity and place them.
 * *
 * * <p>
 * * <b>Note:</b> Giving proper comments in your program makes it more
 * * user friendly and it is assumed as a high quality code.
 * *
 * * @author  Zara Ali
 * * @version 1.0
 * * @since   2014-03-31
 */

public class BuySellOrderGUI extends JPanel {
    JPanel mainPanel;
    String assetName;
    Fonts fonts;
    List<Order> buyHistory;
    List<Order> sellHistory;
    Boolean isSellOrder;

    public String getActionText() {
        if (isSellOrder) {
            return "Sell";
        } else {
            return "Buy";
        }
    }

    public BuySellOrderGUI(String assetName, Boolean isSellOrder) {
        /*
        List<Order> buyHistory = orderHandler.getAllActiveBuyOrders();

        System.out.println(buyHistory);
        List<String> timestamp = new ArrayList<String>();
        List<String> price = new ArrayList<String>();
        */
        this.isSellOrder = isSellOrder;
        java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
        this.buyHistory =  new ArrayList<>();
        // this.buyHistory = MainGUIHandler.orderHandler.getAllActiveBuyOrdersForAsset(assetName).toArray()
        // MOCKED DATA, Use above call ^
        this.buyHistory.add(new Order(
                "123", "456", assetName, 100, new BigDecimal(100), java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0")
        ));
        this.buyHistory.add(new Order(
                "123", "456", assetName, 25, new BigDecimal(200), java.sql.Timestamp.valueOf("2017-09-23 10:10:10.0")
        ));
        this.sellHistory =  new ArrayList<>();
        // this.sellHistory = MainGUIHandler.orderHandler.getAllActiveSellOrdersForAsset(assetName).toArray()
        // MOCKED DATA, Use above call ^
        this.sellHistory.add(new Order(
                "123", "456", assetName, 100, new BigDecimal(100), java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0")
        ));
        this.sellHistory.add(new Order(
                "123", "456", assetName, 25, new BigDecimal(200), java.sql.Timestamp.valueOf("2017-09-23 10:10:10.0")
        ));
        this.assetName = assetName;
        this.fonts = new Fonts();
        mainPanel = new MainPanel();
        System.out.println("WE DID IT");
        setPreferredSize(new Dimension(1181, 718));
        setBounds(0, 0, 1181, 718);
        setLayout(null);
        JButton backToAssets = new JButton("Back to Assets");
        backToAssets.setBounds(300, 50, 120, 25);
        backToAssets.addActionListener(e -> {
            removeAll();
            add(new OrderGUI());
            updateUI();
        });
        add(backToAssets);

        JButton toSettingButton = new JButton("SETTINGS");
        toSettingButton.setBounds(800, 50, 120, 25);
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
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    class MainPanel extends DefaultJPanel {

        public MainPanel(){
            setBackground(Color.WHITE);
            setBounds(0, 0, 1181, 718);
            setPreferredSize(new Dimension(1181, 718));
            JLabel placeOrder = new JLabel(
                    String.format("%s %s", getActionText(), assetName));
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setBorder(new EmptyBorder(0,0,16,0));
            placeOrder.setFont(fonts.heading);
            add(placeOrder);

            JPanel main = new DefaultJPanel();
            main.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
            main.add(new DataPanel());
//            add(new TextPanel());
            main.add(Box.createRigidArea(new Dimension(10, 0)));
            main.add(new PlaceOrderPanel());
            add(main);
        }
    }


    class PlaceOrderPanel extends JPanel {
        CustomTextField buyQuantityText;
        CustomTextField buyPriceText;
        float quantity;
        float price;
        JLabel invalidOrderLabel;
        OrderSummaryPanel orderSummaryPanel;

        public void setQuantity(float quantity) {
            this.quantity = quantity;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void calculateOrder() {
            //TODO: add giselle's method to check if user has enough credits to place order.
            try {
                setQuantity(Float.parseFloat(buyQuantityText.getText()));
            } catch (NumberFormatException ex) {
                invalidOrderLabel.setText("Invalid Quantity");
                return;
            }
            try {
                setPrice(Float.parseFloat(buyPriceText.getText()));
            } catch (NumberFormatException ex) {
                invalidOrderLabel.setText("Invalid Price");
                return;
            }
            invalidOrderLabel.setText("");
            orderSummaryPanel.updateSummary(this.quantity, this.price);
        }

        public PlaceOrderPanel(){
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel buyQuantityLabel;

            JLabel buyPriceLabel;

            CustomButton calculateButton;
            JLabel valid;
            JPanel quantityPanel;
            JPanel inputsPanel;
            JPanel pricePanel;

            quantityPanel = new DefaultJPanel();
            quantityPanel.setBackground(Color.WHITE);
            quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));

            pricePanel = new DefaultJPanel();
            pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));

            inputsPanel = new DefaultJPanel();

            JLabel placeOrder = new JLabel("Place Order");
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setFont(fonts.smallHeading);
            JPanel placeOrderContainer = new DefaultJPanel();
            placeOrderContainer.add(placeOrder);
            add(placeOrderContainer);

            buyQuantityLabel = new JLabel(String.format("%s quantity", getActionText()));
            buyQuantityLabel.setFont(fonts.inputLabel);
            buyQuantityLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityLabel);

            buyQuantityText = new CustomTextField(10);
            buyQuantityText.setPlaceholder("17");
            buyQuantityText.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityText);

            inputsPanel.add(quantityPanel);

            JPanel order;

            buyPriceLabel = new JLabel(String.format("%s price", getActionText()));
            buyPriceLabel.setFont(fonts.inputLabel);
            buyPriceLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            pricePanel.add(buyPriceLabel);

            buyPriceText = new CustomTextField(20);
            buyPriceText.setPlaceholder("$5");
            buyPriceText.setAlignmentX( Component.LEFT_ALIGNMENT );
            pricePanel.add(buyPriceText);

            inputsPanel.add(pricePanel);

            add(inputsPanel);
            calculateButton = new CustomButton("Calculate");
            calculateButton.setBounds(10, 150, 80, 25);
            calculateButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    calculateOrder();
                }
            });
            inputsPanel.add(calculateButton);

            invalidOrderLabel = new JLabel("");
            invalidOrderLabel.setForeground(Color.red);
            invalidOrderLabel.setBounds(10, 220, 340, 25);
            add(invalidOrderLabel);
            add(Box.createRigidArea(new Dimension(0, 150)));
            orderSummaryPanel = new OrderSummaryPanel();
            add(orderSummaryPanel);
            add(Box.createRigidArea(new Dimension(0, 180)));
        }
    }
    class OrderSummaryPanel extends JPanel {
        JLabel priceLabel;
        JLabel quantityLabel;
        JLabel totalLabel;
        boolean toggleOrdered;

        public void updateSummary(float quantity, float price) {
            priceLabel.setText(String.format("at $%.2f per unit", price));
            quantityLabel.setText(String.format("%.1f %s units", quantity, assetName));
            totalLabel.setText(String.format("Total: $%.2f", price * quantity));
        }

        public void colourBox(boolean green) {
            if (green) {
                setBackground(new Color(246,255,246));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2,2,2,2, new Color(175,255,185)),
                        BorderFactory.createLineBorder(new Color(246,255,246), 16)
                ));

            } else {
                setBackground(new Color(255,246,246));
                setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createMatteBorder(2,2,2,2, new Color(255,185,175)),
                        BorderFactory.createLineBorder(new Color(255,246,246), 16)
                ));
            }
        }

        public void toggleOrdered() {
            this.toggleOrdered = !this.toggleOrdered;
            if (this.toggleOrdered) {
                colourBox(true);
            } else {
                colourBox(false);
            }
        }

        public OrderSummaryPanel() {
            this.toggleOrdered = false;
            setBackground(new Color(255,246,246));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(2,2,2,2, new Color(255,185,175)),
                    BorderFactory.createLineBorder(new Color(255,246,246), 16)
            ));

            JButton placeOrderButton;

            JLabel placeOrder = new JLabel("Order Summary");
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setBorder(new EmptyBorder(0,0,16,0));
            placeOrder.setFont(fonts.smallHeading);
            add(placeOrder);

            quantityLabel = new JLabel("x units");
            quantityLabel.setFont(fonts.body);
            add(quantityLabel);

            priceLabel = new JLabel("at $x per unit");
            priceLabel.setFont(fonts.body);
            priceLabel.setBorder(new EmptyBorder(0,0,16,0));
            add(priceLabel);

            totalLabel = new JLabel("Total: $x");
            totalLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            totalLabel.setBorder(new EmptyBorder(0,0,16,0));
            totalLabel.setFont(fonts.smallHeading);
            add(totalLabel);

            placeOrderButton = new CustomButton("Place order");
            placeOrderButton.setBounds(50, 300, 80, 25);
            add(placeOrderButton);

            placeOrderButton.addActionListener(e -> {
                toggleOrdered();
            });

            updateSummary(0,0);
        }
    }
    class DataPanel extends DefaultJPanel {
        GraphView graph;

        public DataPanel(){
            JPanel container = new DefaultJPanel();
            container.setPreferredSize(new Dimension(600, 580));
//            container.setBackground(Color.YELLOW);
            JScrollPane scroll = new JScrollPane(container);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setPreferredSize(new Dimension(600, 580));

            JLabel buyHistoryLabel = new CustomLabel(String.format("Price History for %s", assetName), fonts.smallHeading, true);
            container.add(buyHistoryLabel);

            add(scroll);
            graph = new GraphView();
            container.add(graph);
            // MOCKED DATA, THIS NEEDS TO READ ALL RECENT PRICES FOR THIS "this.assetName" from the DB
            Integer[] data = {1,4,7,3,4,5,6,7,8,3,4,6};
            Collection<Integer> intList = new ArrayList<Integer>(data.length);
            for (int i : data)
            {
                intList.add(i);
            }
            graph.setValues(intList);
            container.add(new History(false));
            container.add(new History(true));

//            add(new BuyOrderTable());
        }
    }

    class History extends DefaultJPanel {

        public History(boolean isSell){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//            setPreferredSize(new Dimension(600, 200));
//            container.setBackground(Color.YELLOW);
            JLabel buyHistoryLabel = new CustomLabel(String.format("Recent %s Orders", isSell ? "sell" : "buy"), fonts.smallHeading, true);
            add(buyHistoryLabel);
            add(new TableRow(
                    "Status",
                    "Organisational Unit",
                    "Quantity",
                    "Price per unit",
                    "Order Date"
            ));
            for (Order order: isSell ? sellHistory : buyHistory ) {
                add(new OrderRow(order));
            }
//            add(new BuyOrderTable());
        }
    }
    class OrderRow extends DefaultJPanel {

        public OrderRow(Order order){
            add(new TableRow(
                "Pending",
                "Computer Hardware",
                String.format("%d", order.getQuantity()),
                String.format("$%d", order.getPrice().divide(new BigDecimal(order.getQuantity())).intValue()),
                new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(order.getOrderDate())
            ));
        }
    }

    class TableRow extends DefaultJPanel {
        public TableRow(String status, String organisationalUnit, String quantity, String pricePerUnit, String orderDate){
            JPanel container = new DefaultJPanel();
            JLabel statusLabel = new CustomLabel(status, fonts.small, true);
            statusLabel.setPreferredSize(new Dimension(65, 20));
            JLabel organisationalUnitLabel = new CustomLabel(organisationalUnit, fonts.small, true);
            organisationalUnitLabel.setPreferredSize(new Dimension(150, 20));
            JLabel quantityLabel =  new CustomLabel(quantity, fonts.small, true);
            quantityLabel.setPreferredSize(new Dimension(70, 20));
            JLabel pricePerUnitLabel =  new CustomLabel(pricePerUnit, fonts.small, true);
            pricePerUnitLabel.setPreferredSize(new Dimension(100, 20));
            JLabel orderDateLabel = new CustomLabel(orderDate, fonts.small, true);
            orderDateLabel.setPreferredSize(new Dimension(140, 20));
            container.add(statusLabel);
            container.add(organisationalUnitLabel);
            container.add(quantityLabel);
            container.add(pricePerUnitLabel);
            container.add(orderDateLabel);
            add(container);
        }
    }

    public static void main(String[] args) throws SQLException {
        /*
        List<Order> buyHistory = orderHandler.getAllActiveBuyOrders();

        System.out.println(buyHistory);
        List<String> timestamp = new ArrayList<String>();
        List<String> price = new ArrayList<String>();
         */
        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame frame = new JFrame("Buy Orders");
                BuySellOrderGUI gui = new BuySellOrderGUI("Doge Coin", true);
                frame.setDefaultLookAndFeelDecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(gui.getMainPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }
}
