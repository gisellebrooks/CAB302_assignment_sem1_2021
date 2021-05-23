package marketplace.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ModifyUserGUI extends JPanel implements ActionListener {

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;
    private static JButton createOrganisationButton;
    private static JTextField givenIDLabel;
    private static JLabel valid;
    private static JLabel invalid;

    public ModifyUserGUI() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        namePromptLabel = new JLabel("Organisation Name:");
        namePromptLabel.setBounds(10, 20, 80, 25);
        add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        add(nameText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 80, 180, 25);
        add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 100, 165, 25);
        add(creditsText);

        createOrganisationButton = new JButton("Create Organisation");
        createOrganisationButton.setBounds(10, 200, 80, 25);
        createOrganisationButton.addActionListener(this);
        add(createOrganisationButton);

        // where the given password goes
        givenIDLabel = new JTextField(20);
        givenIDLabel.setBounds(10, 280, 220, 25);
        add(givenIDLabel);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 360, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 260, 340, 25);
        add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("Invalid inputs");

        String organisationName = nameText.getText();
        int credits = 0;

//        try  {
//            credits = Integer.parseInt(creditsText.getText());
//
//            if (userHandler.userIDExists(organisationName)) {
//                invalid.setText("That organisation name is taken");
//            } else {
//                userHandler.userIDExists(userIDExists.newOrganisationID(), organisationName, credits);
//                valid.setText("Organisation was successfully created");
//            }
//        } catch (NumberFormatException NumberFormatError) {
//            NumberFormatError.printStackTrace();
//        }



    }
}