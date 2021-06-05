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
    public static User user;
    public static String assetName;

    /**
     * This method runs when the MainGUI is initialised and it starts the gui and program for the client.
     */
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
        setSize(1181, 718);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(1181, 718));
        panel.setBounds(0, 0, 1181, 718);
        panel.add(new LoginGUI());

        add(panel);
    }
}