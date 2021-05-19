package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.BuyOrder;
import marketplace.Objects.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class OrderGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel Title;
    private static JComboBox assetBox;
    private static JButton sellButton;
    private static JButton buyButton;

    private static Client client;
    private static UserHandler userHandler;
    private static OrderHandler orderHandler;


    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel valid;
    private static JLabel invalid;


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
        SwingUtilities.invokeLater(new OrderGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(500,450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);



        Title = new JLabel("Orders");
        Title.setBounds(210, 20, 80, 50);
        panel.add(Title);
        /// THIS NEEDS TO CHANGE TO GRAB ASSETS FROM INVENTORY INSTEAD OF BUY ORDERS
        List<BuyOrder> buy = orderHandler.getAllActiveBuyOrders();
        List<String> assets = new ArrayList<String>();

        for (BuyOrder buyOrder : buy) {
            assets.add(buyOrder.getAssetName());

        }

        assetBox = new JComboBox(assets.toArray(new String[0]));
        assetBox.setBounds(210, 100, 80, 25);
        panel.add(assetBox);
//        assetBox.setSelectedIndex(buy.size());
        assetBox.addActionListener(this);

        buyButton = new JButton("Create Buy Order");
        buyButton.setBounds(90, 250, 150, 25);
        panel.add(buyButton);

        sellButton = new JButton("Create Sell Order");
        sellButton.setBounds(260, 250, 150, 25);
        panel.add(sellButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {


        String userID = userText.getText();
        String password = passwordText.getText();
        valid.setText("");
        invalid.setText("");
        User user = userHandler.getUserInformation(userID);
        userHandler.addUser("user10", "12345", "user", "org10", "bob bob");

        if (user.getUserID().equals(userID)){
            System.out.println("successful bitch");
            if (user.getPasswordHash().equals(password)) {
                invalid.setText("");
                valid.setText("Login successful!");
            }
            else{
                invalid.setText("wrong password!");
            }

        }

    }
}