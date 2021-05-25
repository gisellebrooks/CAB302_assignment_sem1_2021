package marketplace.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


public class ModifyUserGUI extends JPanel implements ActionListener {

    private static JButton createOrganisationButton;
    private static JButton toSettingsButton;
    private static JButton findUserButton;
    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JLabel organisationLabel;
    private static JComboBox organisationComboBox;
    private static JLabel userTypeLabel;
    private static JComboBox userTypeComboBox;

    private static JButton resetPasswordButton;
    private static JTextField newPasswordText;

    private static JLabel valid;
    private static JLabel invalid;

    public ModifyUserGUI() {

        setLayout(null);
        setBounds(0, 0, 600, 600);



        resetPasswordButton = new JButton("Reset User's Password");
        resetPasswordButton.setBounds(300, 40, 180, 25);
        resetPasswordButton.addActionListener(this);
        add(resetPasswordButton);

        newPasswordText = new JTextField(30);
        newPasswordText.setBounds(300, 80, 180, 25);
        add(newPasswordText);


        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 160, 25);
        add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        add(userIDText);

        findUserButton = new JButton("Find");
        findUserButton.setBounds(10, 70, 80, 25);
        findUserButton.addActionListener(this);
        add(findUserButton);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 100, 260, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 100, 340, 25);
        add(invalid);


        organisationLabel = new JLabel("Select your organisation:");
        organisationLabel.setBounds(10, 120, 180, 25);
        add(organisationLabel);

        organisationComboBox = new JComboBox(MainGUIHandler.organisationHandler.getAllOrganisationsNames().toArray());
        organisationComboBox.setBounds(10, 150, 165, 25);
        add(organisationComboBox);

        userTypeLabel = new JLabel("Select your user type:");
        userTypeLabel.setBounds(10, 190, 180, 25);
        add(userTypeLabel);

        ArrayList<String> userTypes = new ArrayList<>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 220, 165, 25);
        add(userTypeComboBox);

        createOrganisationButton = new JButton("Modify");
        createOrganisationButton.setBounds(10, 260, 80, 25);
        createOrganisationButton.addActionListener(this);
        add(createOrganisationButton);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(200, 400, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            add(new SettingsNavigationAdminGUI());
            updateUI();
        });
        add(toSettingsButton);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String userID = null;
        String newPassword;
        String userOrganisation;
        String userType;

//        valid.setText("");
//        invalid.setText("");
//
//        if (e.getSource() == findUserButton) {
//
//        }
//
//        if (e.getSource() == resetPasswordButton) {
//            if (userID ) {
//
//            }
//        }
//
//        String organisationName = nameText.getText();
//        int credits = 0;

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