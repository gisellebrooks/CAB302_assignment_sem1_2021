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


public class RemoveOrganisationGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel organisationLabel;
    private static JComboBox organisationComboBox;
    private static JButton removeOrganisationButton;
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
        SwingUtilities.invokeLater(new RemoveOrganisationGUI());
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


        organisationLabel = new JLabel("Select organisation:");
        organisationLabel.setBounds(10, 40, 180, 25);
        panel.add(organisationLabel);

        organisationComboBox = new JComboBox(organisationsList.toArray());
        organisationComboBox.setBounds(10, 60, 165, 25);
        panel.add(organisationComboBox);

        removeOrganisationButton = new JButton("Remove");
        removeOrganisationButton.setBounds(10, 100, 80, 25);
        removeOrganisationButton.addActionListener(new RemoveOrganisationGUI());
        panel.add(removeOrganisationButton);

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
        invalid.setText("Can't remove organisation");



//        if (nameText.isValid() && nameText.getText().length() > 2 && nameText.getText().length() < 250) {
//            System.out.println(nameText.getText());
//            name = nameText.getText();


            try {
                MariaDBDataSource pool = MariaDBDataSource.getInstance();
                invalid.setText("can't signup");
                ResultSet rs;
                String orgID = null;
                String organisationName = organisationComboBox.getSelectedItem().toString();

                PreparedStatement getOrganisationsID = pool.getConnection().prepareStatement("SELECT orgID FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = ?");
                PreparedStatement removeOrganisation = pool.getConnection().prepareStatement("DELETE FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = ?");

                // get organisation ID with org name
                getOrganisationsID.setString(1, organisationName);

                rs = getOrganisationsID.executeQuery();

                if (rs.next()) {
                    orgID = rs.getString(1);
                }

                removeOrganisation.setString(1, orgID);

                removeOrganisation.executeQuery();

                getOrganisationsID.close();
                removeOrganisation.close();
                rs.close();

                invalid.setText("");
                valid.setText(organisationName + " was removed from the server");

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }
