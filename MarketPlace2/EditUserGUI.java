import Server.MariaDBDataSource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class EditUserGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
    private static JButton find;

    private static JTextField newPasswordText;
    private static JButton resetPassword;

    private static JLabel namePromptLabel;
    private static JTextField nameText;

    private static JLabel organisationPromptLabel;
    private static JComboBox organisationComboBox;

    private static JLabel userTypePromptLabel;
    private static JComboBox userTypeComboBox;

    private static JButton save;
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
        SwingUtilities.invokeLater(new EditUserGUI());
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

        resetPassword = new JButton("Reset Password");
        resetPassword.setBounds(300, 40, 160, 25);
        resetPassword.addActionListener(new EditUserGUI());
        panel.add(resetPassword);

        newPasswordText = new JTextField(20);
        newPasswordText.setBounds(300, 80, 165, 25);
        panel.add(newPasswordText);



        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 50, 150, 25);
        panel.add(userIDText);
        userIDText.setText("user id go here");

        find = new JButton("Find user");
        find.setBounds(10, 90, 100, 25);
        find.addActionListener(new EditUserGUI());
        panel.add(find);


        namePromptLabel = new JLabel("Full Name:");
        namePromptLabel.setBounds(10, 150, 165, 25);
        panel.add(namePromptLabel);

        nameText = new JTextField(20);
        nameText.setBounds(10, 170, 165, 25);
        panel.add(nameText);


        organisationPromptLabel = new JLabel("Organisation:");
        organisationPromptLabel.setBounds(10, 210, 165, 25);
        panel.add(organisationPromptLabel);

        organisationComboBox = new JComboBox(organisationsList.toArray());
        organisationComboBox.setBounds(10, 230, 165, 25);
        panel.add(organisationComboBox);


        userTypePromptLabel = new JLabel("User Type:");
        userTypePromptLabel.setBounds(10, 270, 165, 25);
        panel.add(userTypePromptLabel);

        ArrayList<String> userTypes = new ArrayList<String>();
        userTypes.add("USER");
        userTypes.add("ADMIN");

        userTypeComboBox = new JComboBox(userTypes.toArray());
        userTypeComboBox.setBounds(10, 290, 165, 25);
        panel.add(userTypeComboBox);


        save = new JButton("Save");
        save.setBounds(10, 330, 100, 25);
        save.addActionListener(new EditUserGUI());
        panel.add(save);


        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 370, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 370, 340, 25);
        panel.add(invalid);
    }

    // when find button is pressed
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }

    // when resetPassword button is pressed
//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userID = userIDText.getText();
        valid.setText("");
        invalid.setText("User doesn't exist or can't be edited");

        String newUserID;

        try {
            MariaDBDataSource pool = MariaDBDataSource.getInstance();
            invalid.setText("can't remove user");
            ResultSet rs;

            PreparedStatement checkUserIDExist = pool.getConnection().prepareStatement("SELECT name FROM USER_INFORMATION WHERE userID = ?");
            PreparedStatement removeUser = pool.getConnection().prepareStatement("DELETE FROM USER_INFORMATION WHERE userID = ?");

            // check userID exists
            checkUserIDExist.setString(1, userID);

            rs = checkUserIDExist.executeQuery();

            if (rs.next()) {
                removeUser.setString(1, userID);
                removeUser.executeUpdate();
                valid.setText("user was removed");
                invalid.setText("");
                userIDText.setText("");
            }

            rs.close();
            checkUserIDExist.close();
            removeUser.close();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}