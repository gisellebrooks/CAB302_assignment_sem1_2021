package marketplace;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

public class DBConnection {

    public static final String db_name = "marketplace_server";

    public static final String url = "jdbc:mariadb://localhost:3306/" + db_name + "?createDatabaseIfNotExist=true";
    public static final String user = "root";
    public static final String password = "302";

    public static void createTables(Statement statement) throws SQLException {

        String user_info = "CREATE TABLE IF NOT EXISTS USER_INFORMATION" +
                "(username VARCHAR(255) not NULL, " +
                " firstName VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " password CHAR(64), " +
                " accountType VARCHAR(255), " +
                " organisationalUnit VARCHAR(255), " +
                " PRIMARY KEY ( username ))";

        String org_unit_info = "CREATE TABLE IF NOT EXISTS ORGANISATIONAL_UNIT_INFORMATION" +
                "(unit_id INTEGER not NULL, " +
                " unitName VARCHAR(255), " +
                " credits NUMERIC(19,2), " +
                " assets INTEGER, " +
                " PRIMARY KEY ( unit_id ))";

        String active_buy = "CREATE TABLE IF NOT EXISTS ACTIVE_BUY_ORDERS" +
                "(buy_id INTEGER not NULL PRIMARY KEY, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String active_sell = "CREATE TABLE IF NOT EXISTS ACTIVE_SELL_ORDERS" +
                "(sell_id INTEGER not NULL PRIMARY KEY, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String buy_history = "CREATE TABLE IF NOT EXISTS BUY_ORDER_HISTORY" +
                "(buy_id INTEGER NOT NULL, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String sell_history = "CREATE TABLE IF NOT EXISTS SELL_ORDER_HISTORY" +
                "(sell_id INTEGER not NULL, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String assets = "CREATE TABLE IF NOT EXISTS ASSETS" +
                "(asset_id VARCHAR(255) not NULL, " +
                " unit_id INTEGER, " +
                " unitName VARCHAR(255), " +
                " quantity CHAR(64), " +
                " PRIMARY KEY ( asset_id ))";

        statement.addBatch(user_info);
        statement.addBatch(org_unit_info);
        statement.addBatch(active_buy);
        statement.addBatch(active_sell);
        statement.addBatch(buy_history);
        statement.addBatch(sell_history);
        statement.addBatch(assets);
        statement.executeBatch();
    }

    public static void main(String [] args) throws SQLException {

        Connection conn = DriverManager.getConnection(url, user, password);
        Statement statement = conn.createStatement();
        createTables(statement);



        String sql = "INSERT INTO user_information " + "VALUES ('abc123', 'Steve', 'Jobes', '1234', 'apple', 'also apple')";
        statement.executeUpdate(sql);


        DatabaseMetaData md = conn.getMetaData();
        ResultSet rs = md.getTables(null, null, "%", null);
        while (rs.next()) {
            System.out.println(rs.getString(3));
        }



        conn.close();

    }
}
