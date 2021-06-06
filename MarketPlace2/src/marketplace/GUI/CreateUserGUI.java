package marketplace.GUI;

import marketplace.Handlers.PasswordHandler;
import marketplace.Util.Fonts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CreateUserGUI extends FullSizeJPanel implements ActionListener {

    private static CustomLabel namePromptLabel;
    private static CustomTextField nameText;
    private static CustomLabel passwordPromptLabel;
    private static CustomLabel organisationLabel;
    private static JComboBox organisationComboBox;
    private static CustomLabel userTypeLabel;
    private static JComboBox userTypeComboBox;
    private static CustomTextField givenPasswordText;
    private static CustomLabel IDPromptLabel;
    private static CustomLabel givenIDLabel;
    private static CustomButton signUpButton;
    private static CustomButton toSettingsButton;
    private static JLabel valid;
    private static JLabel invalid;
    public JPanel logo;
    public Fonts fonts;

    public CreateUserGUI() {
        setLayout(null);
        setBounds(0, 0, 1181, 718);
        logo = new LogoPanel();
        logo.setBounds(10, 10, 200, 50);
        add(logo);

        this.fonts = new Fonts();

        namePromptLabel = new CustomLabel("Full Name:" , fonts.inputLabel, true);
        namePromptLabel.setBounds(10, 20, 80, 25);
        add(namePromptLabel);

        nameText = new CustomTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        add(nameText);

        organisationLabel = new CustomLabel("Select your organisation:", fonts.inputLabel, true);
        organisationLabel.setBounds(10, 80, 180, 25);
        add(organisationLabel);

        organisationComboBox = new JComboBox(MainGUI.organisationHandler.getAllOrganisationsNames().toArray());
        organisationComboBox.setBounds(10, 100, 165, 25);
        add(organisationComboBox);

        userTypeLabel = new CustomLabel("Select your user type:", fonts.inputLabel, true);
        userTypeLabel.setBounds(10, 140, 180, 25);
        add(userTypeLabel);

        ArrayList<String> userTypes = new ArrayList<>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 160, 165, 25);
        add(userTypeComboBox);

        signUpButton = new CustomButton("Signup");
        signUpButton.setBounds(10, 200, 80, 25);
        signUpButton.addActionListener(this);
        add(signUpButton);

        IDPromptLabel = new CustomLabel("Your userID is:", fonts.inputLabel, true);
        IDPromptLabel.setBounds(10, 260, 180, 25);
        add(IDPromptLabel);

        // where the given password goes
        givenIDLabel = new CustomLabel("", fonts.inputLabel, true);
        givenIDLabel.setBounds(10, 280, 180, 25);
        add(givenIDLabel);

        passwordPromptLabel = new CustomLabel("Your password is:", fonts.inputLabel, true);
        passwordPromptLabel.setBounds(10, 320, 180, 25);
        add(passwordPromptLabel);

        // where the given password goes
        givenPasswordText = new CustomTextField(30);
        givenPasswordText.setBounds(10, 340, 180, 25);
        add(givenPasswordText);

        JButton toSettingButton = new CustomButton("Settings");
        toSettingButton.setBounds(1020, 20, 120, 25);
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
        String passwordHash;
        String name;
        String password;
        String accountType;
        String organisationID;
        String organisationName;

        valid.setText("");
        invalid.setText("Can't signup");
        givenIDLabel.setText("");
        givenPasswordText.setText("");

        try {
            organisationName = organisationComboBox.getSelectedItem().toString();
            accountType = userTypeComboBox.getSelectedItem().toString();
            userID = MainGUI.userHandler.newUserID();
            name = nameText.getText();

            password = new PasswordHandler().generatePassword();
            organisationID = MainGUI.organisationHandler.getOrganisationID(organisationName);
            passwordHash = new PasswordHandler().IntoHash(password);

            MainGUI.userHandler.addUser(userID, passwordHash, accountType, organisationID, name);

            nameText.setText("");
            valid.setText("signup successful!");
            invalid.setText("");
            givenPasswordText.setText(password);
            givenIDLabel.setText(userID);

        } catch (Exception exception) {
            invalid.setText(exception.getMessage());
        }
    }
}