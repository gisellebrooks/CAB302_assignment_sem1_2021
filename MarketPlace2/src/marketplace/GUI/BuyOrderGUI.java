package marketplace.GUI;

import marketplace.GUI.Settings.SettingsNavigationAdminGUI;
import marketplace.Objects.Order;
// import marketplace.Objects.Order;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class BuyOrderGUI extends JPanel implements ActionListener {

    private static JLabel Title;
    private static JLabel graphTitle;
    private static JLabel placeOrderTitle;

    private static JLabel buyQuantityTitle;
    private static JLabel buyPriceTitle;

    private static JTextField quantityText;
    private static JTextField priceText;
    private static JTextField userText;
    private static JButton buyButton;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;


    public BuyOrderGUI() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);


        Title = new JLabel("Buy {asset name}");  /////// NEED LOGIC TO GET SELECTION FROM COMBO BOX FROM ORDERGUI AND ADD THE ASSET NAME HERE
        Title.setBounds(300, 20, 200, 25);
        Title.setFont (Title.getFont ().deriveFont (20.0f));
        add(Title);

        graphTitle = new JLabel("Price History for {asset name} (graph goes here)");
        graphTitle.setBounds(30, 120, 300, 25);
        add(graphTitle);

        List<Order> buyHistory = MainGUIHandler.orderHandler.getAllActiveBuyOrders();
        System.out.println(buyHistory);
        List<String> timestamp = new ArrayList<String>();
        List<String> price = new ArrayList<String>();

        // need to write code to handle order history


        placeOrderTitle = new JLabel("Place Order");
        placeOrderTitle.setBounds(500, 120, 150, 25);
        placeOrderTitle.setFont (placeOrderTitle.getFont ().deriveFont (16.0f));
        add(placeOrderTitle);


        buyQuantityTitle = new JLabel("Buy Quantity");
        buyQuantityTitle.setBounds(450, 160, 100, 25);
        add(buyQuantityTitle);

        buyPriceTitle = new JLabel("Buy Price");
        buyPriceTitle.setBounds(560, 160, 100, 25);
        add(buyPriceTitle);


        quantityText = new JTextField(20);
        quantityText.setBounds(450, 200, 100, 25);
        add(quantityText);

        priceText = new JTextField(20);
        priceText.setBounds(550, 200, 100, 25);
        add(priceText);

        buyButton = new JButton("Calculate");
        buyButton.setBounds(660, 200, 100, 25);
        buyButton.addActionListener(this);
        add(buyButton);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(300, 50, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            add(new SettingsNavigationAdminGUI());
            updateUI();
        });
        add(toSettingsButton);

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

        String userID = userText.getText();

    }
}