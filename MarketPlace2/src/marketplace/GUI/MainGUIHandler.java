package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.OrganisationHandler;
import marketplace.Handlers.UserHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class MainGUIHandler extends JFrame implements ActionListener, Runnable {

    public static Client client;
    public static UserHandler userHandler;
    public static OrganisationHandler organisationHandler;
    public static OrderHandler orderHandler;
    public static JPanel panel;

    public static void main(String[] args){

        client = new Client();

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        userHandler = new UserHandler(client);
        organisationHandler = new OrganisationHandler(client);
        orderHandler = new OrderHandler(client);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new MainGUIHandler());
    }

    @Override
    public void run() {
        startProgram();
        this.setVisible(true);
    }

    public void startProgram() {

        setTitle("Market");
        setSize(600,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        panel = new JPanel();
        panel.setLayout(null);
        panel.setBounds(0, 0, 600, 600);

        panel.add(new LoginGUI());
        add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}