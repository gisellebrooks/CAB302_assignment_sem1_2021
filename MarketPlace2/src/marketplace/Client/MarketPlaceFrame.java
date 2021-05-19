package marketplace.Client;



import marketplace.GUI.LoginGUI;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

//create class and extend with JFrame
public class MarketPlaceFrame extends JFrame
{
    //declare variable
    private JPanel contentPane;

    private Client client;

    private static Properties loadServerConfig() {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("MarketPlace/server.props");
            props.load(in);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    /**
     * Launch the application.
     */
    //main method
    public static void main(String[] args)
    {
        Properties props = loadServerConfig();
		/* It posts an event (Runnable)at the end of Swings event list and is
		processed after all other GUI events are processed.*/
        EventQueue.invokeLater(new Runnable()
        {
            public void run()
            {
                //try - catch block
                try
                {
//                    Client client = new Client();
//
//                    try {
//                        client.connect(props.getProperty("app.address"), Integer.parseInt(props.getProperty("app.port")));
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    } catch (NumberFormatException e) {
//                        e.printStackTrace();
//                    }
                    //Create object of OldWindow
//                    MarketPlaceFrame frame = new MarketPlaceFrame(client);
//                    //set frame visible true
//                    frame.setVisible(true);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MarketPlaceFrame(Client client){
        this.client = client;

        //set frame title
        setTitle("Bit Trade");
        //set default close operation
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //set bounds of the frame
        setBounds(100, 100, 450, 300);
        //create object of JPanel
        contentPane = new JPanel();
        //set border
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        //set ContentPane
        setContentPane(contentPane);
        //set null
        contentPane.setLayout(null);

        //create object of JButton and set label on it
        JButton btnNewFrame = new JButton("Login");
        //add actionListener
        btnNewFrame.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                //call the object of NewWindow and set visible true
                LoginGUI frame = new LoginGUI();
                frame.setVisible(true);
                //set default close operation
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        });
        //set font of the Button
        btnNewFrame.setFont(new Font("Times New Roman", Font.BOLD, 12));
        //set bounds of the Button
        btnNewFrame.setBounds(180, 195, 78, 25);
        //add Button into contentPane
        contentPane.add(btnNewFrame);

        //set Label in the frame
        JLabel lblThisIsOld = new JLabel("Welcome To Bit Trade");
        //set foreground color to the label
        lblThisIsOld.setForeground(Color.BLUE);
        //set font of that label
        lblThisIsOld.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 24));
        //set bound of the label
        lblThisIsOld.setBounds(127, 82, 239, 39);
        //add label to the contentPane
        contentPane.add(lblThisIsOld);
    }
}
