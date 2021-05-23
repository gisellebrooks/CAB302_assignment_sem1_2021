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

    static class PlaceOrderPanel extends JPanel
    {
        Font heading;


        public PlaceOrderPanel(){
            try {
                // load custom font in project folder
                heading = Font.createFont(Font.TRUETYPE_FONT, new File("DelaGothicOne-Regular.ttf")).deriveFont(30f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(heading);
            } catch (IOException | FontFormatException e) {

            }
            setPreferredSize(new Dimension(590, 580));
//            setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            setBackground(Color.ORANGE);
            add(new OrderSummaryPanel());

            JLabel buyQuantityLabel;
            JTextField buyQuantityText;
            JLabel buyPriceLabel;
            JTextField buyPriceText;
            CustomButton calculateButton;
            JLabel valid;
            JLabel invalid;

            buyQuantityLabel = new JLabel("Buy quantity");
            buyQuantityLabel.setBounds(10, 20, 160, 25);
            buyQuantityLabel.setFont(heading);
            add(buyQuantityLabel);

            buyQuantityText = new JTextField(20);
            buyQuantityText.setBounds(10, 40, 165, 25);
            add(buyQuantityText);

            buyPriceLabel = new JLabel("Buy price");
            buyPriceLabel.setBounds(10, 80, 180, 25);
            add(buyPriceLabel);

            buyPriceText = new JTextField(20);
            buyPriceText.setBounds(10, 100, 165, 25);
            add(buyPriceText);

            calculateButton = new CustomButton("Calculate");
            calculateButton.setBounds(10, 150, 80, 25);
//            calculateButton.addActionListener(new CreateOrganisationGUI());
            add(calculateButton);

            valid = new JLabel("");
            valid.setForeground(Color.green);
            valid.setBounds(10, 220, 340, 25);
            add(valid);

            invalid = new JLabel("");
            invalid.setForeground(Color.red);
            invalid.setBounds(10, 220, 340, 25);
            add(invalid);
        }
    }
    static class OrderSummaryPanel extends JPanel {
        public OrderSummaryPanel() {
            setPreferredSize(new Dimension(200, 200));
            setBackground(Color.WHITE);

            JButton placeOrderButton;
            JLabel price;
            JLabel quantity;
            JLabel total;

            quantity = new JLabel("quantity");
            quantity.setBounds(50, 300, 180, 25);
            add(quantity);

            price = new JLabel("price");
            price.setBounds(50, 300, 180, 25);
            add(price);

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
