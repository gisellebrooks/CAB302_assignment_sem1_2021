//import Server.Server.MariaDBDataSource;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.io.BufferedReader;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.security.NoSuchAlgorithmException;
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//
//
//public class SignUpGUI extends JFrame implements ActionListener, Runnable {
//
//    private static JLabel nameLabel;
//    private static JTextField nameText;
//    private static JLabel passwordLabel;
//    private static JLabel organisationLabel;
//    private static JComboBox organisationComboBox;
//    private static JLabel givenpasswordLabel;
//    private static JButton button;
//    private static JLabel valid;
//    private static JLabel invalid;
//
//
//    private static void initDb(Server.MariaDBDataSource pool) throws SQLException {
//        String string;
//        StringBuffer buffer = new StringBuffer();
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("./setupDB.sql"));
//            while ((string = reader.readLine()) != null) {
//                buffer.append(string + "\n");
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String[] queries = buffer.toString().split(";");
//
//        for (String query : queries) {
//            if (query.isBlank()) continue;
//            try (Connection conn = pool.getConnection();
//                 PreparedStatement statement = conn.prepareStatement(query)) {
//                statement.execute();
//            }
//        }
//    }
//
//    private static void loadMockData(Server.MariaDBDataSource pool) throws SQLException {
//        String string;
//        StringBuffer buffer = new StringBuffer();
//
//        try {
//            BufferedReader reader = new BufferedReader(new FileReader("./mockupData.sql"));
//            while ((string = reader.readLine()) != null) {
//                buffer.append(string + "\n");
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        String[] queries = buffer.toString().split(";");
//
//        for (String query : queries) {
//            if (query.isBlank()) continue;
//            try (Connection conn = pool.getConnection();
//                 PreparedStatement statement = conn.prepareStatement(query)) {
//                statement.execute();
//            }
//        }
//
//    }
//
//    public static void main(String[] args) throws SQLException {
//        Server.MariaDBDataSource pool = Server.MariaDBDataSource.getInstance();
//        initDb(pool);
//
//
////         loadMockData(pool);
//
//        //query.add("INSERT INTO USER_INFORMATION VALUES ('adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas')");
//
//
//
//
//        JFrame.setDefaultLookAndFeelDecorated(true);
//        SwingUtilities.invokeLater(new SignUpGUI());
//    }
//
//    @Override
//    public void run() {
//        createGui();
//        this.setVisible(true);
//    }
//
//    public void createGui() {
//        JPanel panel = new JPanel();
//        this.setSize(550,300);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        this.add(panel);
//        panel.setLayout(null);
//
//
//        // get all organisations for use in combo box
//        ArrayList<String> organisationsList = new ArrayList<String>();
//        try {
//            Server.MariaDBDataSource pool = Server.MariaDBDataSource.getInstance();
//            ResultSet rs;
//
//            PreparedStatement getAllOrganisations = pool.getConnection().prepareStatement("SELECT distinct orgName FROM ORGANISATIONAL_UNIT_INFORMATION");
//
//            rs = getAllOrganisations.executeQuery();
//
//            while (rs.next()) {
//                organisationsList.add(rs.getString("orgName"));
//            }
//
//        } catch (SQLException throwable) {
//            throwable.printStackTrace();
//        }
//
//
//
//        nameLabel = new JLabel("Full Name:");
//        nameLabel.setBounds(10, 20, 80, 25);
//        panel.add(nameLabel);
//
//        nameText = new JTextField(20);
//        nameText.setBounds(10, 40, 165, 25);
//        panel.add(nameText);
//
//        organisationLabel = new JLabel("Select your organisation:");
//        organisationLabel.setBounds(10, 80, 180, 25);
//        panel.add(organisationLabel);
//
//        organisationComboBox = new JComboBox(organisationsList.toArray());
//        organisationComboBox.setBounds(10, 100, 165, 25);
//        panel.add(organisationComboBox);
//
//        button = new JButton("Signup");
//        button.setBounds(10, 140, 80, 25);
//        button.addActionListener(new SignUpGUI());
//        panel.add(button);
//
//        passwordLabel = new JLabel("Your password is:");
//        passwordLabel.setBounds(10, 180, 180, 25);
//        panel.add(passwordLabel);
//
//        // where the given password goes
//        givenpasswordLabel = new JLabel("");
//        givenpasswordLabel.setBounds(10, 200, 180, 25);
//        panel.add(givenpasswordLabel);
//
//        valid = new JLabel("");
//        valid.setForeground(Color.green);
//        valid.setBounds(10, 110, 300, 25);
//        panel.add(valid);
//
//        invalid = new JLabel("");
//        invalid.setForeground(Color.red);
//        invalid.setBounds(10, 110, 300, 25);
//        panel.add(invalid);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String user = nameText.getText();
//        // String organisation = passwordText.getText();
//        valid.setText("");
//        invalid.setText("");
//
//        int newUserID;
//        String newPasswordHash = null;
//        String newPassword;
//        String accountType;
//        int orgID;
//        String name = null;
//
//        newPassword = new PasswordFunctions().generatePassword();
//        try {
//            newPasswordHash = new PasswordFunctions().intoHash(newPassword);
//        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
//            noSuchAlgorithmException.printStackTrace();
//        }
//
//        if (nameText.isValid()) {
//            name = nameText.getText();
//        }
//
//        try {
//            Server.MariaDBDataSource pool = Server.MariaDBDataSource.getInstance();
//            invalid.setText("can't signup");
//            ResultSet rs;
//
//            PreparedStatement getNameExists = pool.getConnection().prepareStatement("SELECT * FROM USER_INFORMATION WHERE name = ?");
//            PreparedStatement getOrganisationsID = pool.getConnection().prepareStatement("SELECT orgID FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = ?");
//            PreparedStatement addNewUser = pool.getConnection().prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?, ?)");
//
//            // get organisation ID with org name
//            //getOrganisationsID.setString(organisationComboBox.);
//
//            addNewUser.setInt(1, newUserID);
//            addNewUser.setString(2, newPasswordHash);
//            addNewUser.setString(3, accountType);
//            addNewUser.setInt(4, orgID);
//            addNewUser.setString(5, name);
//
//            rs = addNewUser.executeQuery();
//
//
//            // if signup successful
//            if (rs.next()) {
//                valid.setText("signup successful!");
//                givenpasswordLabel.setText(newPassword);
//
//            }
//
//        } catch (SQLException | NoSuchAlgorithmException throwable) {
//            throwable.printStackTrace();
//        }
//    }
//}