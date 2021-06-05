package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.InventoryHandler;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.OrganisationHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * The MainGUI file is the initialiser for the gui for clients. It starts the GUI at the login page. Sets handlers
 * and starts the main frame that all other gui panels are held in. This is the window that holds the program's
 * GUI.
 *
 * @see marketplace.GUI for GUI files
 *
 */
public class MainGUI extends JFrame implements Runnable {

    public static Client client;
    public static UserHandler userHandler;
    public static OrganisationHandler organisationHandler;
    public static OrderHandler orderHandler;
    public static InventoryHandler inventoryHandler;
    public static JPanel panel;
    public static String userType;
    public static String orgID;
    public static User user;
    public static String assetName;
    public static Boolean isSellOrder;

    /**
     * This method runs when the MainGUI is initialised and it starts the gui and program for the client.
     */
    public static void setUser(User user) {
        MainGUI.user = user;
    }

    public static void main(String[] args){

        client = new Client();

        // try to connect the client to the server
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userHandler = new UserHandler(client);
        orderHandler = new OrderHandler(client);
        organisationHandler = new OrganisationHandler(client);
        inventoryHandler = new InventoryHandler(client);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new MainGUI());
    }

    /**
     * Lays out the frame for the GUI to sit in and starts the LoginGUI for clients who start the program.
     */
    public void run() {

        this.setVisible(true);

        setTitle("Market");
        setSize(FullSizeJPanel.fullWidth, FullSizeJPanel.fullHeight);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, FullSizeJPanel.fullWidth, FullSizeJPanel.fullHeight);

        panel.add(new LoginGUI());
//        panel.add(new SettingsNavigationAdminGUI());
//        panel.add(new SettingsNavigationUserGUI());

//        panel.add(new BuySellOrderGUI(assetName, isSellOrder));
//        panel.add(new OrderGUI());
//        panel.add(new BuyOrderGUI());
//        panel.add(new OrderGUI());
        System.out.println(user);
//        panel.add(new SignUpUserGUI());
//        panel.add(new SignUpOrganisationGUI());
//        panel.add(new ModifyUserGUI());
//        panel.add(new ModifyOrganisationGUI());
//        panel.add(new ChangeUsersPasswordGUI());

        add(panel);
    }
}