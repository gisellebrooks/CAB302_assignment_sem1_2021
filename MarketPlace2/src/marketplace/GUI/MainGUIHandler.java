package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.GUI.Settings.*;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.OrganisationHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainGUIHandler extends JFrame implements ActionListener, Runnable {

    public static Client client;
    public static UserHandler userHandler;
    public static OrganisationHandler organisationHandler;
    public static OrderHandler orderHandler;
    public static JPanel panel;
    public static String userType;
    public static User user;
    public static String assetName;


    public static void main(String[] args){

        client = new Client();

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        userHandler = new UserHandler(client);
        orderHandler = new OrderHandler(client);
        organisationHandler = new OrganisationHandler(client);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new MainGUIHandler());
    }

    @Override
    public void run() {

        this.setVisible(true);

        setTitle("Market");
        setSize(1181, 718);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1181, 718));
        panel.setBounds(0, 0, 1181, 718);

//        panel.add(new LoginGUI());
//        panel.add(new SettingsNavigationAdminGUI());
//        panel.add(new BuyOrderGUI());
        panel.add(new OrderGUI());

//        panel.add(new SignUpUserGUI());
//        panel.add(new SignUpUserGUI());
//        BuySellOrderGUI gui = new BuySellOrderGUI("Doge Coin", true);
//        panel.add(gui.getMainPanel());
//        panel.add(new JLabel("HAHHAHAHAHA"));
        panel.add(new OrderGUI());
//        panel.add(new SignUpOrganisationGUI());
//        panel.add(new ModifyOrganisationGUI());
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}