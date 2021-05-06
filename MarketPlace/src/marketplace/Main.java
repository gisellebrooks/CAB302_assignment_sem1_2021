package marketplace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.sql.*;

public class Main {

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

    public static void main(String[] args) throws SQLException {

        MariaDBDataSource pool = MariaDBDataSource.getInstance();
        initDb(pool);



//        ResultSet rs;
//
        BuyOrder order = new BuyOrder(pool);
        order.createBuyOrder("CPU", 30000, "org1");
//        double quantity = order.getAssetQuantity("246", "123");
//        System.out.println(quantity);
//        double price = order.getAssetPrice("246", "123");



        // Testing the connection pool -- testing purposes only
//        for (int i = 0; i < 7; i++ )
//        {
//            try (Connection conn1 = pool.getConnection(); Connection conn2 = pool.getConnection(); Connection conn3 = pool.getConnection()) {
//
//                Statement stmt1 = conn1.createStatement();
//                Statement stmt2 = conn2.createStatement();
//                Statement stmt3 = conn3.createStatement();
//
//                rs = stmt1.executeQuery("SELECT CONNECTION_ID()");
//                rs.next();
//                System.out.println(rs.getLong(1));
//                //
//                rs = stmt2.executeQuery("SELECT CONNECTION_ID()");
//                rs.next();
//                System.out.println(rs.getLong(1));
//
//                rs = stmt3.executeQuery("SELECT CONNECTION_ID()");
//                rs.next();
//                System.out.println(rs.getLong(1));
//            }
//        }
    }
}


