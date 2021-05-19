package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;


public class LoginGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
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
        SwingUtilities.invokeLater(new LoginGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(350,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 160, 25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new LoginGUI());
        panel.add(button);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userID = userText.getText();
        String password = passwordText.getText();
        String passwordHash = null;

        valid.setText("");
        invalid.setText("");

        User user = null;

        // try and get user from server
        try {
            user = userHandler.getUserInformation(userID);
            passwordHash = PasswordFunctions.intoHash(password);
        } catch (Exception exception) {
            exception.printStackTrace();
            invalid.setText("Invalid details!");
        }

        // if user found then test password matches
        if (userHandler.userIDExists(userID)) {

            if (user.getPasswordHash().equals(passwordHash) && !passwordHash.equals(null)) {
                valid.setText("user found");
            } else {
                invalid.setText("Invalid details!");
            }

        } else {
            invalid.setText("Invalid details!");
        }
    }
}