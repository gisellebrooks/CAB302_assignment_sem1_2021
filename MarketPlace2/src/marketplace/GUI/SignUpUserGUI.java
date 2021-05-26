package marketplace.GUI;

import marketplace.PasswordHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class SignUpUserGUI extends JPanel implements ActionListener {

    private static JLabel namePromptLabel;
    private static JTextField nameText;
    private static JLabel passwordPromptLabel;
    private static JLabel organisationLabel;
    private static JComboBox organisationComboBox;
    private static JLabel userTypeLabel;
    private static JComboBox userTypeComboBox;
    private static JTextField givenPasswordText;
    private static JLabel IDPromptLabel;
    private static JLabel givenIDLabel;
    private static JButton signUpButton;
    private static JButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;

    public SignUpUserGUI() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        namePromptLabel = new JLabel("Full Name:");
        namePromptLabel.setBounds(10, 20, 80, 25);
        add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        add(nameText);

        organisationLabel = new JLabel("Select your organisation:");
        organisationLabel.setBounds(10, 80, 180, 25);
        add(organisationLabel);

        organisationComboBox = new JComboBox(MainGUIHandler.organisationHandler.getAllOrganisationsNames().toArray());
        organisationComboBox.setBounds(10, 100, 165, 25);
        add(organisationComboBox);

        userTypeLabel = new JLabel("Select your user type:");
        userTypeLabel.setBounds(10, 140, 180, 25);
        add(userTypeLabel);

        ArrayList<String> userTypes = new ArrayList<>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 160, 165, 25);
        add(userTypeComboBox);

        signUpButton = new JButton("Signup");
        signUpButton.setBounds(10, 200, 80, 25);
        signUpButton.addActionListener(this);
        add(signUpButton);

        IDPromptLabel = new JLabel("Your userID is:");
        IDPromptLabel.setBounds(10, 260, 180, 25);
        add(IDPromptLabel);

        // where the given password goes
        givenIDLabel = new JLabel("");
        givenIDLabel.setBounds(10, 280, 180, 25);
        add(givenIDLabel);

        passwordPromptLabel = new JLabel("Your password is:");
        passwordPromptLabel.setBounds(10, 320, 180, 25);
        add(passwordPromptLabel);

        // where the given password goes
        givenPasswordText = new JTextField(30);
        givenPasswordText.setBounds(10, 340, 180, 25);
        add(givenPasswordText);

        toSettingsButton = new JButton("SETTINGS");
        toSettingsButton.setBounds(200, 400, 120, 25);
        toSettingsButton.addActionListener(e -> {
            removeAll();
            add(new SettingsNavigationAdminGUI());
            updateUI();
        });
        add(toSettingsButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 225, 340, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 225, 340, 25);
        add(invalid);
    }

    public void actionPerformed(ActionEvent e) {
        String userID;
        String passwordHash = null;
        String name;
        String password;
        String accountType;
        String organisationID;
        String organisationName;

        valid.setText("");
        invalid.setText("Can't signup");
        givenIDLabel.setText("");
        givenPasswordText.setText("");

        password = new PasswordHandler().generatePassword();
        try {
            passwordHash = new PasswordHandler().intoHash(password);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        // get organisation ID with org name
        organisationName = organisationComboBox.getSelectedItem().toString();
        organisationID = MainGUIHandler.organisationHandler.getOrganisationID(organisationName);

        if (nameText.isValid() && nameText.getText().length() > 2 ) {
            if (nameText.getText().length() < 250) {

                name = nameText.getText();
                accountType = userTypeComboBox.getSelectedItem().toString();
                userID = MainGUIHandler.userHandler.newUserID();

                MainGUIHandler.userHandler.addUser(userID, passwordHash, accountType, organisationID, name);

                nameText.setText("");
                valid.setText("signup successful!");
                invalid.setText("");
                givenPasswordText.setText(password);
                givenIDLabel.setText(userID);

            } else {
                invalid.setText("The name is too long");
            }
        } else {
            invalid.setText("The name is too short");
        }
    }
}