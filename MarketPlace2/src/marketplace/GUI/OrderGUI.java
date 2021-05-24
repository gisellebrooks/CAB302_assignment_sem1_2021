package marketplace.GUI;

import marketplace.Objects.Order;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class OrderGUI extends JPanel implements ActionListener, Runnable {

    private static JLabel Title;
    private static JComboBox assetBox;
    private static JButton sellButton;
    private static JButton buyButton;

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel valid;
    private static JLabel invalid;

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        Title = new JLabel("Orders");
        Title.setBounds(210, 20, 80, 50);
        add(Title);
        /// THIS NEEDS TO CHANGE TO GRAB ASSETS FROM INVENTORY INSTEAD OF BUY ORDERS
        // List<Order> buy = orderHandler.getAllActiveBuyOrders();
        List<String> assets = new ArrayList<String>();
        //System.out.println("got buy orders");
        // for (Order buyOrder : buy) {
            // assets.add(buyOrder.getAssetName());
            //System.out.println(buyOrder.getAssetName());

//        }
//        List<Order> sell = orderHandler.getAllActiveSellOrders();
//        System.out.println("got sell orders");
//        for (Order sellOrder : sell) {
//            assets.add(sellOrder.getAssetName());
//            System.out.println(sellOrder.getAssetName());
//
//        }
//        try {
//            orderHandler.reconcileOrder();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        assetBox = new JComboBox(assets.toArray(new String[0]));
//        assetBox.setBounds(210, 100, 80, 25);
//        add(assetBox);
////        assetBox.setSelectedIndex(buy.size());
//        assetBox.addActionListener(this);
//
//        buyButton = new JButton("Create Buy Order");
//        buyButton.setBounds(90, 250, 150, 25);
//        add(buyButton);
//
//        sellButton = new JButton("Create Sell Order");
//        sellButton.setBounds(260, 250, 150, 25);
//        add(sellButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String userID = userText.getText();
        String password = passwordText.getText();
        valid.setText("");
        invalid.setText("");
//        User user = userHandler.getUserInformation(userID);
//        userHandler.addUser("user10", "12345", "user", "org10", "bob bob");
//
//        if (user.getUserID().equals(userID)){
//            System.out.println("successful bitch");
//            if (user.getPasswordHash().equals(password)) {
//                invalid.setText("");
//                valid.setText("Login successful!");
//            }
//            else{
//                invalid.setText("wrong password!");
//            }
//
//        }

    }
}