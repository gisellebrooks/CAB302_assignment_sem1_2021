package marketplace;

import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Main {

    public static void createTables(Statement statement) throws SQLException {

        String user_info = "CREATE TABLE IF NOT EXISTS USER_INFORMATION" +
                "(username VARCHAR(255) NOT NULL, " +
                " firstName VARCHAR(255), " +
                " lastName VARCHAR(255), " +
                " password CHAR(64), " +
                " accountType VARCHAR(255), " +
                " organisationalUnit VARCHAR(255), " +
                " PRIMARY KEY ( username ))";

        String org_unit_info = "CREATE TABLE IF NOT EXISTS ORGANISATIONAL_UNIT_INFORMATION" +
                "(unit_id INTEGER NOT NULL AUTO_INCREMENT, " +
                " unitName VARCHAR(255), " +
                " credits NUMERIC(19,2), " +
                " assets INTEGER, " +
                " PRIMARY KEY ( unit_id ))";

        String active_buy = "CREATE TABLE IF NOT EXISTS ACTIVE_BUY_ORDERS" +
                "(buy_id INTEGER NOT NULL AUTO_INCREMENT, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP, " +
                " PRIMARY KEY ( buy_id ))";

        String active_sell = "CREATE TABLE IF NOT EXISTS ACTIVE_SELL_ORDERS" +
                "(sell_id INTEGER NOT NULL AUTO_INCREMENT, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP, " +
                " PRIMARY KEY ( sell_id )) ";

        String buy_history = "CREATE TABLE IF NOT EXISTS BUY_ORDER_HISTORY" +
                "(buy_id INTEGER NOT NULL, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String sell_history = "CREATE TABLE IF NOT EXISTS SELL_ORDER_HISTORY" +
                "(sell_id INTEGER NOT NULL AUTO_INCREMENT, " +
                " unitName VARCHAR(255), " +
                " assetName VARCHAR(255), " +
                " quantity INTEGER, " +
                " priceUpper NUMERIC(19,2), " +
                " orderDate TIMESTAMP)";

        String assets = "CREATE TABLE IF NOT EXISTS ASSETS" +
                "(asset_id INTEGER NOT NULL AUTO_INCREMENT, " +
                " assetName VARCHAR(255)," +
                " unit_id INTEGER, " +
                " unitName VARCHAR(255), " +
                " quantity INTEGER, " +
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


    public static void main(String[] args) throws SQLException {
        MariaDBDataSource pool = MariaDBDataSource.getInstance();

        try(Connection conn = pool.getConnection()) {
            try (Statement statement = conn.createStatement()) {
//                ResultSet rs = statement.executeQuery("SELECT CONNECTION_ID()");
//                rs.next();
//                System.out.println(rs.getLong(1));
                createTables(statement);
            }
        }


    }
}
