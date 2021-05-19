package marketplace.GUI;

import marketplace.Client.Client;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.User;
import marketplace.PasswordFunctions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;


public class UserSignUpGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel nameLabel;
    private static JTextField nameText;
    private static JLabel passwordPromptLabel;
    private static JLabel organisationLabel;
    private static JComboBox organisationComboBox;
    private static JLabel userTypeLabel;
    private static JComboBox userTypeComboBox;
    private static JLabel givenPasswordLabel;
    private static JLabel IDPromptLabel;
    private static JLabel givenIDLabel;
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
        SwingUtilities.invokeLater(new UserSignUpGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(550,450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        nameLabel = new JLabel("Full Name:");
        nameLabel.setBounds(10, 20, 80, 25);
        panel.add(nameLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 40, 165, 25);
        panel.add(nameText);

        organisationLabel = new JLabel("Select your organisation:");
        organisationLabel.setBounds(10, 80, 180, 25);
        panel.add(organisationLabel);

        organisationComboBox = new JComboBox(organisationsList.toArray());
        organisationComboBox.setBounds(10, 100, 165, 25);
        panel.add(organisationComboBox);

        userTypeLabel = new JLabel("Select your user type:");
        userTypeLabel.setBounds(10, 140, 180, 25);
        panel.add(userTypeLabel);

        ArrayList<String> userTypes = new ArrayList<String>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 160, 165, 25);
        panel.add(userTypeComboBox);


        button = new JButton("Signup");
        button.setBounds(10, 200, 80, 25);
        button.addActionListener(new UserSignUpGUI());
        panel.add(button);


        IDPromptLabel = new JLabel("Your userID is:");
        IDPromptLabel.setBounds(10, 260, 180, 25);
        panel.add(IDPromptLabel);

        // where the given password goes
        givenIDLabel = new JLabel("");
        givenIDLabel.setBounds(10, 280, 180, 25);
        panel.add(givenIDLabel);


        passwordPromptLabel = new JLabel("Your password is:");
        passwordPromptLabel.setBounds(10, 300, 180, 25);
        panel.add(passwordPromptLabel);

        // where the given password goes
        givenPasswordLabel = new JLabel("");
        givenPasswordLabel.setBounds(10, 320, 180, 25);
        panel.add(givenPasswordLabel);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 360, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 360, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("Can't signup");

        String userID;
        String passwordHash = null;
        String password;
        String accountType = null;
        String organisationID = null;
        User user = null;

        password = new PasswordFunctions().generatePassword();
        try {
            passwordHash = new PasswordFunctions().intoHash(password);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        if (nameText.isValid() && nameText.getText().length() > 2 && nameText.getText().length() < 250) {
            System.out.println(nameText.getText());
            String name = nameText.getText();

            invalid.setText("can't signup");

            // get organisation ID with org name
            String userOrganisation = organisationComboBox.getSelectedItem().toString();

            accountType = userTypeComboBox.getSelectedItem().toString();

            userID = userHandler.newUserID();

            // do all checks hear before adding new user values

            userHandler.addUser(userID, passwordHash, accountType, organisationID, name);

            valid.setText("signup successful!");
            invalid.setText("");
            givenPasswordLabel.setText(password);
            givenIDLabel.setText(userID);

        }
    }
}