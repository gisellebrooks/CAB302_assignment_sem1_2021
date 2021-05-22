package marketplace.GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class RemoveOrganisationGUI extends JPanel implements ActionListener, Runnable {

    private static JLabel organisationIDPromptLabel;
    private static JTextField organisationIDText;
    private static JButton removeOrganisationButton;
    private static JLabel valid;
    private static JLabel invalid;

    @Override
    public void run() {
        createGui();
    }

    public void createGui() {

        setLayout(null);
        setBounds(0, 0, 600, 600);

        organisationIDPromptLabel = new JLabel("ID of organisation to remove");
        organisationIDPromptLabel.setBounds(10, 20, 160, 25);
        add(organisationIDPromptLabel);

        organisationIDText = new JTextField(20);
        organisationIDText.setBounds(10, 50, 160, 25);
        add(organisationIDText);

        removeOrganisationButton = new JButton("Remove");
        removeOrganisationButton.setBounds(10, 80, 80, 25);
        removeOrganisationButton.addActionListener(new RemoveOrganisationGUI());
        add(removeOrganisationButton);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 120, 340, 25);
        add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 120, 340, 25);
        add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        valid.setText("");
        invalid.setText("");

        String organisationID = organisationIDText.getText();
//
//        organisationHandler= new OrganisationHandler(client);

        if (MainGUIHandler.organisationHandler.organisationIDExists(organisationID)) {
            MainGUIHandler.organisationHandler.removeOrganisation(organisationID);
            valid.setText("Organisation was successfully removed");
        } else {
            invalid.setText("Organisation can't be found or can't be removed");
        }
    }
}