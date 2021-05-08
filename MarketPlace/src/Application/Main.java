package Application;

import Database.BuyOrder;
import Database.MariaDBDataSource;

import java.io.*;

import java.sql.*;
import java.util.Properties;

public class Main {

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
        //Properties serverProps = loadServerConfig();

//        MarketplaceServer server = new MarketplaceServer(serverProps);
//        server.startServer();

        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        initDb(pool);
        loadMockData(pool);

        // Not sure where to put this client connection, leaving it here for now lol
//        Client client = new Client(serverProps);
//        client.createConnection();

//        ResultSet rs;
//
        BuyOrder order = new BuyOrder(pool);
        order.createBuyOrder("CPU", 30000, "org1");
//        double quantity = order.getAssetQuantity("246", "123");
//        System.out.println(quantity);
//        double price = order.getAssetPrice("246", "123");



    }
}


