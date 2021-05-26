package marketplace.GUI;

import com.sun.tools.javac.Main;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsNavigationUserGUI extends JPanel implements ActionListener {

    private static JButton changePasswordButton;
    private static JButton logOutButton;

    public SettingsNavigationUserGUI() {
        setLayout(null);
        setBounds(0, 0, 600, 600);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(50, 20, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new ChangeUsersPasswordGUI());
            updateUI();
        });
        add(changePasswordButton);

        logOutButton = new JButton("Logout");
        logOutButton.setBounds(50, 60, 160, 25);
        logOutButton.addActionListener(e -> {
            removeAll();
            MainGUIHandler.user = null;
            MainGUIHandler.userType = "USER";
            add(new LoginGUI());
            updateUI();
        });
        add(logOutButton);
    }

    public void actionPerformed(ActionEvent e) {

    }
}