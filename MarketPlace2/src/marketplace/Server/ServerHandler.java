package marketplace.Server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class ServerHandler {

    private static String rootDir;
    private static Properties props;
    private static MariaDBDataSource pooledDataSource;
    private ServerSocket listener;
    int port;
    int backlog;
    InetAddress address;
    Socket clientSocket;

    public ServerHandler(Properties props){
        try {
            listener = newServerSocket(props);

            while (true){
                clientSocket = newClientConnection();
                try {
                    System.out.println("Waiting for client connection...");

                    clientSocket = listener.accept();
                    System.out.println("A new client is connected : " + clientSocket);

                    Thread thread = new ClientHandler(clientSocket, pooledDataSource);
                    thread.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ServerSocket newServerSocket(Properties props) throws IOException {
        port = Integer.parseInt(props.getProperty("app.port"));
        backlog = 10;
        address = InetAddress.getByName(props.getProperty("app.hostname"));
        return new ServerSocket(port, backlog, address);
    }

    public Socket newClientConnection(){
        return new Socket();
    }

    public static Properties loadServerConfig() {
        rootDir = System.getProperty("user.dir");
        props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream(rootDir + "/MarketPlace2/src/marketplace/util/server.props");
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
            BufferedReader reader = new BufferedReader(new FileReader("MarketPlace2/src/marketplace/util/setupDB.sql"));
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
    public static void loadMockData(MariaDBDataSource pool) throws SQLException {
        String string;
        StringBuffer buffer = new StringBuffer();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("MarketPlace2/src/marketplace/util/mockupData.sql"));
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
        pooledDataSource = MariaDBDataSource.getInstance();

        initDb(pooledDataSource);

        Properties props = loadServerConfig();

        ServerHandler server = new ServerHandler(props);

    }
}
