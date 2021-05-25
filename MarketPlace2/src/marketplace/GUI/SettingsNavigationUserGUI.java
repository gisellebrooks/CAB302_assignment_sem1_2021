package marketplace.GUI;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class SettingsNavigationUserGUI extends JPanel implements ActionListener {

    private static JButton changePasswordButton;

    public SettingsNavigationUserGUI() {
        setLayout(null);
        setBounds(0, 0, 600, 600);

        changePasswordButton = new JButton("Change Password");
        changePasswordButton.setBounds(50, 20, 160, 25);
        changePasswordButton.addActionListener(e -> {
            removeAll();
            add(new SignUpUserGUI());
            updateUI();
        });
        add(changePasswordButton);
    }

    public void actionPerformed(ActionEvent e) {

    }
}