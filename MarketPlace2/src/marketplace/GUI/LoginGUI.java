package marketplace.GUI;

import marketplace.Objects.User;
import marketplace.Util.Fonts;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginGUI extends FullSizeJPanel implements ActionListener {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
    private static JButton button;
    private static JLabel invalid;
    public LogoPanel logo;
    Fonts fonts;
    public JPanel image;

    public LoginGUI() {

        setLayout(null);
        setBounds(0, 0, 1181, 718);

        logo = new LogoPanel();
        logo.setBounds(10, 10, 200, 50);
        add(logo);

        image = new ImagePanel();
        image.setBounds(110, 135, 500, 500);
        add(image);

        int loginOffsetx = 750;
        int loginOffsety = 300;

        JPanel login = new DefaultJPanel();
        login.setBounds(loginOffsetx + 10, loginOffsety + 20, 500, 200);
        login.setLayout(new BoxLayout(login, BoxLayout.Y_AXIS));

        this.fonts = new Fonts();
        JPanel userInput = new DefaultJPanel();
        userInput.setBackground(Color.WHITE);
        userInput.setLayout(new BoxLayout(userInput, BoxLayout.Y_AXIS));

        userLabel = new CustomLabel("User", fonts.inputLabel, true);
        userInput.add(userLabel);

        userText = new CustomTextField(20);
        userInput.add(userText);
        login.add(userInput);

        JPanel passwordInput = new DefaultJPanel();
        passwordInput.setBackground(Color.WHITE);
        passwordInput.setLayout(new BoxLayout(passwordInput, BoxLayout.Y_AXIS));

        passwordLabel = new CustomLabel("Password", fonts.inputLabel, true);
        passwordInput.add(passwordLabel);

        passwordText = new CustomPasswordField();
        passwordInput.add(passwordText);
        login.add(passwordInput);

        button = new CustomButton("Login");
        button.addActionListener(this);
        login.add(button);

        invalid = new CustomLabel("", fonts.inputLabel, true);
        invalid.setForeground(Color.red);
        login.add(invalid);
        add(login);
    }

    public void actionPerformed(ActionEvent e) {
        String userID = userText.getText();
        String password = passwordText.getText();
        User user;

        try {
            MainGUI.userHandler.loginUser(userID, password);

            user = MainGUI.userHandler.searchUser(userID);
            MainGUI.userType = user.getAccountType();
            MainGUI.orgID = user.getOrganisationID();
            MainGUI.setUser(user);
            
            removeAll();
            add(new HomeGUI());
            updateUI();

        } catch (Exception exception) {
            invalid.setText(exception.getMessage());
        }
    }

    public class ImagePanel extends JPanel {

        public ImagePanel() {
            try {
                setBackground(Color.WHITE);
                BufferedImage myPicture = ImageIO.read(new File("MarketPlace2/src/images/Home.png"));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture));
                add(picLabel);
            } catch (IOException ex) {
                
            }
        }
    }
}

