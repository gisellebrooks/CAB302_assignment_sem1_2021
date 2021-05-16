import Server.MariaDBDataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class SignUpGUI extends JFrame implements ActionListener, Runnable {

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


    private static void initDb(MariaDBDataSource pool) throws SQLException {
        String string;
        StringBuffer buffer = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./setupDB.sql"));
            while ((string = reader.readLine()) != null) {
                buffer.append(string + "\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] queries = buffer.toString().split(";");

        for (String query : queries) {
            if (query.isBlank()) continue;
            try (Connection conn = pool.getConnection();
                 PreparedStatement statement = conn.prepareStatement(query)) {
                statement.execute();
            }
        }
    }

    private static void loadMockData(MariaDBDataSource pool) throws SQLException {
        String string;
        StringBuffer buffer = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("./mockupData.sql"));
            while ((string = reader.readLine()) != null) {
                buffer.append(string + "\n");
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String[] queries = buffer.toString().split(";");

        for (String query : queries) {
            if (query.isBlank()) continue;
            try (Connection conn = pool.getConnection();
                 PreparedStatement statement = conn.prepareStatement(query)) {
                statement.execute();
            }
        }

    }

    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        initDb(pool);

//        loadMockData(pool);

        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new SignUpGUI());
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


        // get all organisations for use in combo box
        ArrayList<String> organisationsList = new ArrayList<String>();
        try {
            MariaDBDataSource pool = MariaDBDataSource.getInstance();
            ResultSet rs;

            PreparedStatement getAllOrganisations = pool.getConnection().prepareStatement("SELECT distinct orgName FROM ORGANISATIONAL_UNIT_INFORMATION");

            rs = getAllOrganisations.executeQuery();

            while (rs.next()) {
                organisationsList.add(rs.getString("orgName"));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }



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
        button.addActionListener(new SignUpGUI());
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
        String user = nameText.getText();
        // String organisation = passwordText.getText();
        valid.setText("");
        invalid.setText("Can't signup");

        String newUserID;
        String newPasswordHash = null;
        String newPassword;
        String accountType;
        String orgID = null;
        String name = null;
        String latestUserID;

        newPassword = new PasswordFunctions().generatePassword();
        try {
            newPasswordHash = new PasswordFunctions().intoHash(newPassword);
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            noSuchAlgorithmException.printStackTrace();
        }

        if (nameText.isValid() && nameText.getText().length() > 4) {
            System.out.println(nameText.getText());
            name = nameText.getText();

            try {
                MariaDBDataSource pool = MariaDBDataSource.getInstance();
                invalid.setText("can't signup");
                ResultSet rs;

                PreparedStatement getNameExists = pool.getConnection().prepareStatement("SELECT * FROM USER_INFORMATION WHERE name = ?");
                PreparedStatement getAllUserID = pool.getConnection().prepareStatement("SELECT userID FROM USER_INFORMATION");
                PreparedStatement getOrganisationsID = pool.getConnection().prepareStatement("SELECT orgID FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = ?");
                PreparedStatement addNewUser = pool.getConnection().prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?, ?)");

                // get organisation ID with org name
                String userOrganisation = organisationComboBox.getSelectedItem().toString();
                getOrganisationsID.setString(1, userOrganisation);

                rs = getOrganisationsID.executeQuery();

                if (rs.next()) {
                    orgID = rs.getString(1);
                }

                accountType = userTypeComboBox.getSelectedItem().toString();

                // getting the next user ID
                ArrayList<String> userIDs = new ArrayList<String>();
                rs = getAllUserID.executeQuery();
                while (rs.next()) {
                    userIDs.add(rs.getString("userID"));
                }
                userIDs.sort(String::compareToIgnoreCase);

                latestUserID = userIDs.get(userIDs.size() - 1);
                latestUserID = latestUserID.replace("user", "");
                newUserID = (String.valueOf(Integer.parseInt(latestUserID) + 1));
                newUserID = "user" + newUserID;

                addNewUser.setString(1, newUserID);
                addNewUser.setString(2, newPasswordHash);
                addNewUser.setString(3, accountType);
                addNewUser.setString(4, orgID);
                addNewUser.setString(5, name);

                addNewUser.executeQuery();

                valid.setText("signup successful!");
                invalid.setText("");
                givenPasswordLabel.setText(newPassword);
                givenIDLabel.setText(newUserID);

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
}