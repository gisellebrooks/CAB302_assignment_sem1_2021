package marketplace.Server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

public class ServerHandler {

    private ServerSocket listener;

    public ServerHandler(int port, String address){
        try {
            MariaDBDataSource pool = MariaDBDataSource.getInstance();
            initDb(pool);
            //loadMockData(pool);
            InetAddress addr = InetAddress.getByName(address);
            listener = new ServerSocket(port, 10, addr);
            while (true){
                Socket socket = null;
                try {
                    System.out.println("inb4 client connects");
                    // socket object to receive incoming client requests
                    socket = listener.accept();
                    System.out.println("A new client is connected : " + socket);

                    Thread thread = new ClientHandler(socket, pool);
                    thread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }

    }

    private static Properties loadServerConfig() {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("server.props");
            props.load(in);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

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


    public static void main(String[] args) throws IOException, SQLException {
        Properties props = loadServerConfig();

        ServerHandler server = new ServerHandler(Integer.parseInt(props.getProperty("app.port")), props.getProperty("app.hostname"));


    }
}
