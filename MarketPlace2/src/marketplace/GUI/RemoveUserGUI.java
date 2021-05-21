package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.UserHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class RemoveUserGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JButton removeUserButton;
    private static JLabel valid;
    private static JLabel invalid;

    private static Client client;
    private static UserHandler userHandler;


    public static void main(String[] args){

        client = new Client();
        userHandler= new UserHandler(client);

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new RemoveUserGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(550,450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        userIDPromptLabel = new JLabel("ID of user to remove");
        userIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        panel.add(userIDText);

        removeUserButton = new JButton("Remove User");
        removeUserButton.setBounds(10, 200, 120, 25);
        removeUserButton.addActionListener(new RemoveUserGUI());
        panel.add(removeUserButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 360, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 360, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        String userID = userIDText.getText();

        client = new Client();
        userHandler= new UserHandler(client);

        try {
            client.connect();

        } catch (IOException er) {
            er.printStackTrace();
        }

        if (userHandler.userIDExists(userID)) {
            userHandler.removeUser(userID);
            valid.setText("User was successfully removed");

        } else {
            invalid.setText("User can't be found or can't be removed");
        }

    }
}