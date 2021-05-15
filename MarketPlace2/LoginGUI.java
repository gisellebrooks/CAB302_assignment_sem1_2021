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


public class LoginGUI extends JFrame implements ActionListener, Runnable {

    private static JLabel userLabel;
    private static JTextField userText;
    private static JLabel passwordLabel;
    private static JTextField passwordText;
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


//         loadMockData(pool);

//        ImplementUser manualTestUser = new ImplementUser(pool);
//
//        try (Connection conn = pool.getConnection()) {
//            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?, ?)")) {
//                if (params != null){
//                    conn.executeQuery();
//
//                } else{
//                    statement.executeQuery();
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }

//        manualTestUser.addUser("1", "14e3885dc3a6764f84023badcdaa54b9f3f6121ff28c68174636f533ce97e3a5",
//                "USER", "CPU HQ", "Jack Gate-Leven");

        QueryTemplate query = new QueryTemplate(pool);

        //query.add("INSERT INTO USER_INFORMATION VALUES ('adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas', 'adsadsdsadas')");

//        Map<String, Object> params = new HashMap<>();
//        params.put("userID", username);
//        params.put("password", password);
//        params.put("accountType", accountType);
//        params.put("orgID", organisation);
//        params.put("name", name);

        ResultSet rs;
        rs = query.get("SELECT * FROM USER_INFORMATION", null);

        while (rs.next()){
            System.out.println(rs.getString("userID"));
        }



//        while (rs.next()){
//            System.out.println(rs.getString(1));
//        }




        JFrame.setDefaultLookAndFeelDecorated(true);
        SwingUtilities.invokeLater(new LoginGUI());
    }

    @Override
    public void run() {
        createGui();
        this.setVisible(true);
    }

    public void createGui() {
        JPanel panel = new JPanel();
        this.setSize(350,200);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.add(panel);
        panel.setLayout(null);

        userLabel = new JLabel("User");
        userLabel.setBounds(10, 20, 80, 25);
        panel.add(userLabel);

        userText = new JTextField(20);
        userText.setBounds(100, 20, 165, 25);
        panel.add(userText);

        passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 50, 80, 25);
        panel.add(passwordLabel);

        passwordText = new JPasswordField();
        passwordText.setBounds(100, 50, 160, 25);
        panel.add(passwordText);

        button = new JButton("Login");
        button.setBounds(10, 80, 80, 25);
        button.addActionListener(new LoginGUI());
        panel.add(button);

        valid = new JLabel("");
        valid.setForeground(Color.green);
        valid.setBounds(10, 110, 300, 25);
        panel.add(valid);

        invalid = new JLabel("");
        invalid.setForeground(Color.red);
        invalid.setBounds(10, 110, 300, 25);
        panel.add(invalid);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String user = userText.getText();
        String password = passwordText.getText();
        valid.setText("");
        invalid.setText("");



        PasswordFunctions passwordFunctions = new PasswordFunctions();
        MariaDBDataSource pool = null;

        try {
            pool = MariaDBDataSource.getInstance();
            invalid.setText("invalid");
            ResultSet rs;
            QueryTemplate query = new QueryTemplate(pool);

            PreparedStatement getUserExists = pool.getConnection().prepareStatement("SELECT * FROM USER_INFORMATION WHERE userID = ?");
            PreparedStatement getPasswordHash = pool.getConnection().prepareStatement("SELECT passwordHash FROM USER_INFORMATION WHERE userID = ?");

            String dbPasswordHash = "";
            int rowsReturned = 0;

            getUserExists.setString(1,user);
            rs = getUserExists.executeQuery();
            System.out.println("got to 1");
            if (rs.next()) { // if user found

                getPasswordHash.setString(1, user);
                rs = getPasswordHash.executeQuery();
                System.out.println("got to 2");
                while (rs.next()) { // if password hash found
                    dbPasswordHash = rs.getString(1);
                    System.out.println("got to 3");

                    if (dbPasswordHash.equals(passwordFunctions.intoHash(password))) { // if given password matches users saved password
                        invalid.setText("");
                        valid.setText("Login successful!");
                    }
                }
            }

        } catch (SQLException | NoSuchAlgorithmException throwable) {
            throwable.printStackTrace();
        }


    }
}