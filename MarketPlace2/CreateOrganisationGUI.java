import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class CreateOrganisationGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel organisationPromptLabel;
    private static JTextField organisationText;
    private static JLabel creditsPromptLabel;
    private static JTextField creditsText;

    private static JLabel givenOrgananisationID;

    private static JButton createButton;
    private static JLabel valid;
    private static JLabel invalid;

    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        new InitDatabase().initDb(pool);


        JFrame frame = new JFrame();
        frame.setDefaultLookAndFeelDecorated(true);
        JPanel panel = new JPanel();
        frame.setSize(550,200);
        frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);

        frame.add(panel);
        panel.setLayout(null);

        frame.add(panel);
        panel.setLayout(null);


        // get all organisations for use in combo box
        ArrayList<String> organisationsList = new ArrayList<String>();
        try {
            ResultSet rs;

            PreparedStatement getAllOrganisations = pool.getConnection().prepareStatement("SELECT distinct orgName FROM ORGANISATIONAL_UNIT_INFORMATION");

            rs = getAllOrganisations.executeQuery();

            while (rs.next()) {
                organisationsList.add(rs.getString("orgName"));
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        organisationPromptLabel = new JLabel("Organisation Name:");
        organisationPromptLabel.setBounds(10, 20, 160, 25);
        panel.add(organisationPromptLabel);

        organisationText = new JTextField(20);
        organisationText.setBounds(10, 40, 165, 25);
        panel.add(organisationText);

        creditsPromptLabel = new JLabel("Credits:");
        creditsPromptLabel.setBounds(10, 80, 180, 25);
        panel.add(creditsPromptLabel);

        creditsText = new JTextField(20);
        creditsText.setBounds(10, 100, 165, 25);
        panel.add(creditsText);

        createButton = new JButton("Create");
        createButton.setBounds(10, 150, 80, 25);
        createButton.addActionListener(new CreateOrganisationGUI());
        panel.add(createButton);

        givenOrgananisationID = new JLabel("Your organisations ID is");
        givenOrgananisationID.setBounds(10, 180, 165, 25);
        panel.add(givenOrgananisationID);
        givenOrgananisationID.setText("");

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 220, 340, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 220, 340, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String orgName = null;
        double orgCredits = 0;
        String maxorgID = null;
        String neworgID = null;

        if (organisationText.isValid() && organisationText.getText().length() > 2 && organisationText.getText().length() < 250) {
            orgName = organisationText.getText();
            orgCredits = Double.parseDouble(creditsText.getText());

            try {
                MariaDBDataSource pool = MariaDBDataSource.getInstance();
                invalid.setText("can't create new organisation");
                ResultSet rs;

                PreparedStatement getAllOrganisationID = pool.getConnection().prepareStatement("SELECT orgID FROM ORGANISATIONAL_UNIT_INFORMATION");
                PreparedStatement addNewOrganisation = pool.getConnection().prepareStatement("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES (?, ?, ?)");

                // getting the next organisation ID
                ArrayList<String> orgIDs = new ArrayList<String>();
                rs = getAllOrganisationID.executeQuery();
                while (rs.next()) {
                    orgIDs.add(rs.getString("orgID"));
                }
                orgIDs.sort(String::compareToIgnoreCase);

                maxorgID = orgIDs.get(orgIDs.size() - 1);
                maxorgID = maxorgID.replace("org", "");
                neworgID = (String.valueOf(Integer.parseInt(maxorgID) + 1));
                neworgID = "org" + neworgID;

                addNewOrganisation.setString(1, neworgID);
                addNewOrganisation.setString(2, orgName);
                addNewOrganisation.setDouble(3, orgCredits);

                addNewOrganisation.executeQuery();

                valid.setText("creation of " + orgName + " successful!");
                invalid.setText("");
                givenOrgananisationID.setText("Your organisation ID: " + neworgID);

                rs.close();
                addNewOrganisation.close();
                getAllOrganisationID.close();
                addNewOrganisation.close();

            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        }
    }

    @Override
    public void run() {

    }
}