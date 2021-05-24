package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.BuyOrder;
import marketplace.Objects.Order;
// import marketplace.Objects.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class BuyOrderGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel Title;
    private static JLabel graphTitle;
    private static JLabel placeOrderTitle;

    private static JLabel buyQuantityTitle;
    private static JLabel buyPriceTitle;

    private static JTextField quantityText;
    private static JTextField priceText;

    private static Client client;
    private static UserHandler userHandler;
    private static OrderHandler orderHandler;

    private static JButton buyButton;

    private static JTextField userText;


    public static void main(String[] args){

        client = new Client();
        userHandler= new UserHandler(client);
        orderHandler = new OrderHandler(client);

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new BuyOrderGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(800,650);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);


        Title = new JLabel("Buy {asset name}");  /////// NEED LOGIC TO GET SELECTION FROM COMBO BOX FROM ORDERGUI AND ADD THE ASSET NAME HERE
        Title.setBounds(300, 20, 200, 25);
        Title.setFont (Title.getFont ().deriveFont (20.0f));
        panel.add(Title);

        graphTitle = new JLabel("Price History for {asset name} (graph goes here)");
        graphTitle.setBounds(30, 120, 300, 25);
        panel.add(graphTitle);

        List<Order> buyHistory = orderHandler.getAllActiveBuyOrders();
        List<String> timestamp = new ArrayList<String>();
        List<String> price = new ArrayList<String>();

        // need to write code to handle order history


        placeOrderTitle = new JLabel("Place Order");
        placeOrderTitle.setBounds(500, 120, 150, 25);
        placeOrderTitle.setFont (placeOrderTitle.getFont ().deriveFont (16.0f));
        panel.add(placeOrderTitle);


        buyQuantityTitle = new JLabel("Buy Quantity");
        buyQuantityTitle.setBounds(450, 160, 100, 25);
        panel.add(buyQuantityTitle);

        buyPriceTitle = new JLabel("Buy Price");
        buyPriceTitle.setBounds(560, 160, 100, 25);
        panel.add(buyPriceTitle);


        quantityText = new JTextField(20);
        quantityText.setBounds(450, 200, 100, 25);
        panel.add(quantityText);

        priceText = new JTextField(20);
        priceText.setBounds(550, 200, 100, 25);
        panel.add(priceText);

        buyButton = new JButton("Calculate");
        buyButton.setBounds(660, 200, 100, 25);
        buyButton.addActionListener(new LoginGUI());
        panel.add(buyButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userID = userText.getText();

    }
}