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


public class RemoveUserGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userIDPromptLabel;
    private static JTextField userIDText;
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
        SwingUtilities.invokeLater(new RemoveUserGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(550,400);
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

        userIDPromptLabel = new JLabel("User ID:");
        userIDPromptLabel.setBounds(10, 20, 80, 25);
        panel.add(userIDPromptLabel);

        userIDText = new JTextField(20);
        userIDText.setBounds(10, 40, 165, 25);
        panel.add(userIDText);

        button = new JButton("Remove User");
        button.setBounds(10, 80, 120, 25);
        button.addActionListener(new RemoveUserGUI());
        panel.add(button);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String userID = userIDText.getText();
        valid.setText("");
        invalid.setText("User doesn't exist or can't be removed");

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


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }
}