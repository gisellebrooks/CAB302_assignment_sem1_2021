package marketplace.GUI;

import marketplace.Objects.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.border.EmptyBorder;

public class LoginGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel invalid;
    public JPanel ImagePanel;

    public LoginGUI() {

        setLayout(null);
        setBounds(0, 0, 1181, 718);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 160, 25);
        add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 160, 25);
        add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(this);
        add(button);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        add(invalid);

        ImagePanel = new ImagePanel();
        add(ImagePanel);
    }

    public void actionPerformed(ActionEvent e) {
        String userID = userText.getText();
        String password = passwordText.getText();
        User user;

        try {
            MainGUIHandler.userHandler.loginUser(userID, password);

            user = MainGUIHandler.userHandler.searchUser(userID);
            MainGUIHandler.userType = user.getAccountType();
            MainGUIHandler.orgID = user.getOrganisationID();
            MainGUIHandler.setUser(user);
            System.out.println(MainGUIHandler.user.getUserID());
            removeAll();
            add(new OrderGUI());
            updateUI();

        } catch (Exception exception) {
            invalid.setText(exception.getMessage());
        }
    }

    public class ImagePanel extends JPanel{

        private BufferedImage image;

        public ImagePanel() {
            try {
                image = ImageIO.read(new File("MarketPlace2/src/marketplace/Home.png"));
                System.out.println("oo");
            } catch (IOException ex) {
                System.out.println("oops");
            }
        }
        @Override
        public void paintComponent(Graphics g) {
            System.out.println("and a oop");
            super.paintComponent(g);
            g.drawImage(image, 50, 50, this); // see javadoc for more info on the parameters
        }

    }
}

