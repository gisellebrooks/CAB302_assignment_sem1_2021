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

import com.sun.tools.javac.Main;
import marketplace.Objects.*;
import marketplace.Util.Fonts;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.SeriesException;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.time.Second;
import org.jfree.data.time.TimeSeries;
import org.jfree.chart.ChartPanel;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
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
 * * @author  Ali
 * * @version 1.0
 * * @since   2021-06-01
 */

public class BuySellOrderGUI extends FullSizeJPanel {
    public JPanel mainPanel;
    public String assetName;
    public Inventory inventory;
    public Fonts fonts;
    public List<Order> activeBuyOrders;
    public List<SellOrder> activeSellOrders;
    public Boolean isSellOrder;

    /**
     *  Gets the user readable text for this GUI
     */
    public String getActionText() {
        if (isSellOrder) {
            return "Sell";
        } else {
            return "Buy";
        }
    }

    /**
     *  Takes you back to the home page
     */
    public void backToHome() {
        this.removeAll();
        this.add(new HomeGUI());
        this.updateUI();
    }

    /**
     *
     * @param assetName The name of the asset you want to buy / sell
     * @param inventory The existing inventory for your asset (only needed for sell orders)
     * @param isSellOrder If true, this GUI is used to sell the asset, if false, you are buying more
     */
    public BuySellOrderGUI(String assetName, Inventory inventory, Boolean isSellOrder) {
        List<String> timestamp = new ArrayList<String>();

        this.isSellOrder = isSellOrder;
        this.inventory = inventory;
        this.assetName = assetName;
        java.sql.Timestamp.valueOf("2007-09-23 10:10:10.0");
        this.activeBuyOrders = MainGUI.orderHandler.getAllActiveBuyOrdersForAsset(assetName);
        this.activeSellOrders = MainGUI.orderHandler.getAllActiveSellOrdersForAsset(assetName);

        this.assetName = assetName;
        this.fonts = new Fonts();
        mainPanel = new MainPanel();
        setPreferredSize(new Dimension(1181, 718));
        setBounds(0, 0, 1181, 718);
        setLayout(null);
        JButton backToAssets = new CustomButton("Back to Assets");
        backToAssets.setBounds(300, 50, 120, 25);
        backToAssets.addActionListener(e -> {
            backToHome();
        });
        add(backToAssets);

        JButton toSettingButton = new CustomButton("SETTINGS");
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

    /**
     *  The main panel contains everything aside from the navigation
     */
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
            main.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 100));
            main.add(new DataPanel());
            main.add(Box.createRigidArea(new Dimension(10, 0)));
            main.add(new PlaceOrderPanel());
            add(main);
        }
    }


    /**
     *  This is the right hand side panel used to make an order
     */
    class PlaceOrderPanel extends JPanel {
        CustomTextField buyQuantityText;
        CustomTextField buyPriceText;
        int quantity;
        float price;
        JLabel invalidOrderLabel;
        JLabel invalidCreditLabel;
        OrderSummaryPanel orderSummaryPanel;

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void calculateOrder() {
            try {
                    try {
                        setQuantity(Integer.parseInt(buyQuantityText.getText()));
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
                    if (isSellOrder || MainGUI.organisationHandler.organisationHasCredits(MainGUI.orgID, BigDecimal.valueOf(price))){
                    } else {
                        
                        invalidCreditLabel.setText("You don't have enough credits!");
                        return;
                    }
                if (isSellOrder && inventory.getQuantity() <= quantity) {
                    invalidCreditLabel.setText("You don't have enough to sell!");
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            invalidCreditLabel.setText("");
            invalidOrderLabel.setText("");
            orderSummaryPanel.updateSummary(this.quantity, new BigDecimal(this.price));
            
        }

        public PlaceOrderPanel(){
            setBackground(Color.WHITE);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(450, 500));

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
            invalidCreditLabel = new JLabel("");
            invalidCreditLabel.setForeground(Color.red);
            invalidCreditLabel.setBounds(10, 220, 340, 25);
            add(invalidCreditLabel);

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

    /**
     *  This is the Red box that shows your order summary after you calculate
     */
    class OrderSummaryPanel extends JPanel {
        JLabel priceLabel;
        JLabel quantityLabel;
        JLabel totalLabel;
        boolean toggleOrdered;
        int quantity;
        BigDecimal price;

        public void updateSummary(int quantity, BigDecimal price) {
            this.quantity = quantity;
            this.price = price;
            priceLabel.setText(String.format("at $%.2f per unit", price));
            quantityLabel.setText(String.format("%d %s units", quantity, assetName));
            totalLabel.setText(String.format("Total: $%.2f",  price.multiply(new BigDecimal(quantity))));
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
                if (isSellOrder) {
                    MainGUI.orderHandler.addNewSellOrder(
                            MainGUI.user.getUserID(),
                            inventory.getAssetID(),
                            quantity,
                            price
                    );
                } else {
                    MainGUI.orderHandler.addNewBuyOrder(
                            MainGUI.user.getUserID(),
                            assetName,
                            quantity,
                            price
                    );
                }
                backToHome();
            });

            updateSummary(0, new BigDecimal(0));
        }
    }

    /**
     * This is the left hand side panel, showing contextual data about the asset you're transacting
     */
    class DataPanel extends DefaultJPanel {

        public DataPanel(){
            JPanel container = new DefaultJPanel();
            setPreferredSize(new Dimension(650, 500));
            container.setPreferredSize(new Dimension(650, 1580));
            JScrollPane scroll = new JScrollPane(container);
            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            scroll.setPreferredSize(new Dimension(650, 500));

            JLabel buyHistoryLabel = new CustomLabel(String.format("Price History for %s", assetName), fonts.smallHeading, false);
            buyHistoryLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            container.add(buyHistoryLabel);

            add(scroll);
            final XYDataset dataset = createDataset( );
            final JFreeChart chart = createChart( dataset );
            final ChartPanel chartPanel = new ChartPanel( chart );
            chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 370 ) );
            chartPanel.setMouseZoomable( true , false );
            container.add( chartPanel );
            container.add(new History(false));
            container.add(new History(true));
        }

        /**
         * This is used to generate the price history data for your asset
         *
         * For more info, look into the docs for JFreeChart
         * @return
         */
        private XYDataset createDataset( ) {
            final TimeSeries series = new TimeSeries( "Price History" );
            List<SellOrderHistory> orderHistory = MainGUI.orderHandler.getAllSellOrderHistoryForAsset(assetName);

            for (SellOrderHistory order: orderHistory) {
                try {
                    series.addOrUpdate(new Second(order.getOrderDate()), order.getPrice().doubleValue() );
                } catch ( SeriesException e ) {
                    System.err.println("Error adding to series");
                }
            }

            return new TimeSeriesCollection(series);
        }

        /**
         * For more info, look into the docs for JFreeChart
         * @return
         */
        private JFreeChart createChart(final XYDataset dataset ) {
            return ChartFactory.createTimeSeriesChart(
                    "Price History",
                    "Date Time",
                    "Price",
                    dataset,
                    false,
                    false,
                    false);
        }
    }

    /**
    * This is a table showing historical orders for the asset
    */
    class History extends DefaultJPanel {

        public History(boolean isSell){
            this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            JLabel buyHistoryLabel = new CustomLabel(String.format("Recent %s Orders", isSell ? "sell" : "buy"), fonts.smallHeading, true);
            add(buyHistoryLabel);
            // The Header Row
            add(new TableRow(
                    "Organisational Unit",
                    "Quantity",
                    "Price per unit",
                    "Order Date"
            ));
            for (Order order: isSell ? activeSellOrders : activeBuyOrders) {
                add(new OrderRow(order));
            }
        }
    }

    /**
    * This is a row in the table for an order
    */
    class OrderRow extends DefaultJPanel {

        public OrderRow(Order order){
            String organisationUnit;
            try {
                User user = MainGUI.userHandler.searchUser(order.getUserID());
                Organisation organisation = MainGUI.organisationHandler.getOrganisation(user.getOrganisationID());
                organisationUnit = organisation.getOrgName();
            } catch (Exception exception) {
                exception.printStackTrace();
                organisationUnit = "Not Found";
            }

            add(new TableRow(
                    organisationUnit,
                    String.format("%d", order.getQuantity()),
                    NumberFormat.getCurrencyInstance().format(order.getPrice().divide(new BigDecimal(order.getQuantity()), 2, RoundingMode.HALF_UP)),
                    new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(order.getOrderDate())
            ));
        }
    }

    /**
    * This is a row in the table for both the header and body
    */
    class TableRow extends DefaultJPanel {
        public TableRow(String organisationalUnit, String quantity, String pricePerUnit, String orderDate){
            JPanel container = new DefaultJPanel();
            JLabel organisationalUnitLabel = new CustomLabel(organisationalUnit, fonts.small, true);
            organisationalUnitLabel.setPreferredSize(new Dimension(150, 20));
            JLabel quantityLabel =  new CustomLabel(quantity, fonts.small, true);
            quantityLabel.setPreferredSize(new Dimension(70, 20));
            JLabel pricePerUnitLabel =  new CustomLabel(pricePerUnit, fonts.small, true);
            pricePerUnitLabel.setPreferredSize(new Dimension(100, 20));
            JLabel orderDateLabel = new CustomLabel(orderDate, fonts.small, true);
            orderDateLabel.setPreferredSize(new Dimension(140, 20));
            container.add(organisationalUnitLabel);
            container.add(quantityLabel);
            container.add(pricePerUnitLabel);
            container.add(orderDateLabel);
            add(container);
        }
    }
}
