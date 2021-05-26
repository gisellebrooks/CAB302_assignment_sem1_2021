package marketplace.GUI;

import marketplace.Objects.User;
import marketplace.PasswordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class LoginGUI extends JPanel implements ActionListener {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel valid;
    private static JLabel invalid;

    public LoginGUI() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

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

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 300, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        add(invalid);
    }

    public void actionPerformed(ActionEvent e) {
        String userID = userText.getText();
        String password = passwordText.getText();
        String passwordHash = null;
        User user;

        valid.setText("");
        invalid.setText("Invalid details!");

        // if user found then test password matches
        if (userID.length() < 249 && userID != null  && password. length() < 249 && password != null &&
                MainGUIHandler.userHandler.userIDExists(userID)) {

            user = MainGUIHandler.userHandler.getUser(userID);

            try {
                passwordHash = PasswordHandler.intoHash(password);

                if (user.getPasswordHash().equals(passwordHash) && !passwordHash.equals(null)) {
                    removeAll();
                    MainGUIHandler.userType = user.getAccountType();
                    MainGUIHandler.user = MainGUIHandler.userHandler.getUser(userID);



                    if (MainGUIHandler.userType.equals("ADMIN")) {
                        add(new SettingsNavigationAdminGUI());
                    } else {
                        add(new SettingsNavigationUserGUI());
                    }

                    updateUI();
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                invalid.setText("Invalid details!");
            }
        }
    }
}