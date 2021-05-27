package marketplace.GUI;

import marketplace.Util.Fonts;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Collection;

public class BuyOrderGUI extends JPanel {
    JPanel mainPanel;
    String assetName;
    Fonts fonts;

    public BuyOrderGUI (String assetName) {
        this.assetName = assetName;
        this.fonts = new Fonts();
        mainPanel = new MainPanel();
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    class MainPanel extends DefaultJPanel {

        public MainPanel(){
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(1181, 718));
            JLabel placeOrder = new JLabel(String.format("Buy %s", assetName));
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setBorder(new EmptyBorder(0,0,16,0));
            placeOrder.setFont(fonts.heading);
            add(placeOrder);

            JPanel main = new DefaultJPanel();
            main.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
            main.add(new DataPanel());
//            add(new TextPanel());
            main.add(Box.createRigidArea(new Dimension(10, 0)));;
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

            buyQuantityLabel = new JLabel("Buy quantity");
            buyQuantityLabel.setFont(fonts.inputLabel);
            buyQuantityLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityLabel);

            buyQuantityText = new CustomTextField(10);
            buyQuantityText.setPlaceholder("17");
            buyQuantityText.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityText);

            inputsPanel.add(quantityPanel);

            JPanel order;

            buyPriceLabel = new JLabel("Buy price");
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

        public void updateSummary(float quantity, float price) {
            priceLabel.setText(String.format("at $%.2f per unit", price));
            quantityLabel.setText(String.format("%.1f %s units", quantity, assetName));
            totalLabel.setText(String.format("Total: $%.2f", price * quantity));
        }

        public OrderSummaryPanel() {
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
            updateSummary(0,0);
        }
    }
    class DataPanel extends JPanel {
        GraphView graph;

        public DataPanel(){
            JPanel container = new DefaultJPanel();
            container.setPreferredSize(new Dimension(480, 580));
//            container.setBackground(Color.YELLOW);
            JScrollPane scroll = new JScrollPane(container);

            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            add(scroll);
            graph = new GraphView();
            container.add(graph);
            Integer[] data = {1,4,7,3,4,5,6,7,8,3,4,6};
            Collection<Integer> intList = new ArrayList<Integer>(data.length);
            for (int i : data)
            {
                intList.add(i);
            }
            graph.setValues(intList);

//            add(new BuyOrderTable());
        }
    }

//    static class TextPanel extends JPanel
//    {
//        public TextPanel(){
//            setPreferredSize(new Dimension(100, 600));
//            setBackground(Color.CYAN);
//        }
//    }

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
                BuyOrderGUI gui = new BuyOrderGUI("Doge Coin");
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
