import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class BuyOrderGUI extends JPanel {
    Font heading;

    static class MainPanel extends JPanel
    {
        public MainPanel(){
            setPreferredSize(new Dimension(1181, 718));
            setLayout(new FlowLayout(FlowLayout.CENTER, 0, 100));
            add(new DataPanel());
//            add(new TextPanel());
            add(new PlaceOrderPanel());


        }
    }

    static class DefaultPanel extends JPanel {
        Font body;
        Font bodyFont;
    }


    static class PlaceOrderPanel extends DefaultPanel {
        CustomTextField buyQuantityText;
        CustomTextField buyPriceText;
        float quantity;
        float price;
        OrderSummaryPanel orderSummaryPanel;

        public void setQuantity(float quantity) {
            this.quantity = quantity;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public void calculateOrder() {
            setQuantity(Float.parseFloat(buyQuantityText.getText()));
            setPrice(Float.parseFloat(buyPriceText.getText()));
            orderSummaryPanel.updateSummary(this.quantity, this.price);
        }

        public PlaceOrderPanel(){
            Fonts fonts = new Fonts();
            setPreferredSize(new Dimension(590, 580));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

            JLabel buyQuantityLabel;

            JLabel buyPriceLabel;

            CustomButton calculateButton;
            JLabel valid;
            JLabel invalid;
            JPanel quantityPanel;
            JPanel inputsPanel;
            JPanel pricePanel;

            quantityPanel = new JPanel();
            quantityPanel.setLayout(new BoxLayout(quantityPanel, BoxLayout.Y_AXIS));

            pricePanel = new JPanel();
            pricePanel.setLayout(new BoxLayout(pricePanel, BoxLayout.Y_AXIS));

            inputsPanel = new JPanel();
            JLabel placeOrder = new JLabel("Place Order");
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setBounds(0, 20, 165, 25);
            placeOrder.setFont(fonts.smallHeading);
            add(placeOrder);

            buyQuantityLabel = new JLabel("Buy quantity");
            buyQuantityLabel.setBounds(0, 20, 165, 25);
            buyQuantityLabel.setFont(fonts.inputLabel);
            buyQuantityLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityLabel);

            buyQuantityText = new CustomTextField(10);
            buyQuantityText.setPlaceholder("17");
            buyQuantityText.setBounds(0, 20, 165, 25);
            buyQuantityText.setAlignmentX( Component.LEFT_ALIGNMENT );
            quantityPanel.add(buyQuantityText);

            inputsPanel.add(quantityPanel);

            JPanel order;

            buyPriceLabel = new JLabel("Buy price");
            buyPriceLabel.setBounds(10, 80, 180, 25);
            buyPriceLabel.setFont(fonts.inputLabel);
            buyPriceLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            pricePanel.add(buyPriceLabel);

            buyPriceText = new CustomTextField(20);
            buyPriceText.setPlaceholder("$5");
            buyPriceText.setBounds(10, 100, 165, 25);
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

            valid = new JLabel("");
            valid.setForeground(Color.green);
            valid.setBounds(10, 220, 340, 25);
            add(valid);

            invalid = new JLabel("");
            invalid.setForeground(Color.red);
            invalid.setBounds(10, 220, 340, 25);
            add(invalid);
            orderSummaryPanel = new OrderSummaryPanel();
            add(orderSummaryPanel);
        }
    }
    static class OrderSummaryPanel extends JPanel {
        JLabel priceLabel;
        JLabel quantityLabel;
        JLabel totalLabel;

        public void updateSummary(float quantity, float price) {
            priceLabel.setText(String.format("at $%.2f per unit", price));
            quantityLabel.setText(String.format("%.1f units", quantity));
            totalLabel.setText(String.format("Total: $%.2f", price * quantity));
        }

        public OrderSummaryPanel() {
            setPreferredSize(new Dimension(200, 200));
            setBackground(new Color(255,246,246));
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBorder(BorderFactory.createMatteBorder(2,2,2,2, new Color(255,185,175)));
            JButton placeOrderButton;
            Fonts fonts = new Fonts();

            JLabel placeOrder = new JLabel("Order Summary");
            placeOrder.setAlignmentX( Component.LEFT_ALIGNMENT );
            placeOrder.setBounds(0, 20, 165, 25);
            placeOrder.setFont(fonts.smallHeading);
            add(placeOrder);

            quantityLabel = new JLabel("x units");
            quantityLabel.setFont(fonts.body);
            quantityLabel.setBounds(50, 300, 180, 25);
            add(quantityLabel);

            priceLabel = new JLabel("at $x per unit");
            priceLabel.setFont(fonts.body);
            priceLabel.setBounds(50, 300, 180, 25);
            add(priceLabel);

            totalLabel = new JLabel("Total: $x");
            totalLabel.setAlignmentX( Component.LEFT_ALIGNMENT );
            totalLabel.setBounds(0, 20, 165, 25);
            totalLabel.setFont(fonts.smallHeading);
            add(totalLabel);

            placeOrderButton = new CustomButton("Place order");
            placeOrderButton.setBounds(50, 300, 80, 25);
            add(placeOrderButton);
        }
    }
    static class DataPanel extends JPanel
    {
        public DataPanel(){
            JPanel container = new JPanel();
            container.setPreferredSize(new Dimension(480, 580));
//            container.setBackground(Color.YELLOW);
            JScrollPane scroll = new JScrollPane(container);

            scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

            add(scroll);

            container.add(new GraphView());
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
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);

        SwingUtilities.invokeLater(new Runnable(){
            @Override
            public void run(){
                JFrame frame = new JFrame("Buy Orders");
                frame.setDefaultLookAndFeelDecorated(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new MainPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });

    }
}
