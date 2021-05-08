import java.security.NoSuchAlgorithmException;
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
    private static Connection connection;
    private static String displayLine = "_".repeat(124);

    public static void createTables(Statement statement) throws SQLException {

        String user_info = "CREATE TABLE IF NOT EXISTS USER_INFORMATION" +
                "(username VARCHAR(255) not NULL, " +
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

    private static int addUser(String username, String passwordHash, String accountType, String organisation) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO USER_INFORMATION VALUES (?, ?, ?, ?)");
        statement.clearParameters();
        statement.setString(1, username);
        statement.setString(2, passwordHash);
        statement.setString(3, accountType);
        statement.setString(4, organisation);
        return statement.executeUpdate();
    }

    private static int addOrganisation(int unit_id, String unitName, double unitCredits, int assets) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES (?, ?, ?, ?)");
        statement.clearParameters();
        statement.setInt(1, unit_id);
        statement.setString(2, unitName);
        statement.setDouble(3, unitCredits);
        statement.setInt(4, assets);
        return statement.executeUpdate();
    }

    private static void displayAllUsers() throws SQLException {
        Statement statement = connection.createStatement();
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet columns = metadata.getColumns(null, null, "USER_INFORMATION", null);

        System.out.println();
        System.out.println();
        System.out.println(displayLine);

        // display all tables and data from database
        while (columns.next()){
            String str2 = String.format("%30s|", columns.getString("COLUMN_NAME"));
            System.out.print(str2);
        }

        System.out.println();
        System.out.print(displayLine);
        System.out.println();

        columns = statement.executeQuery("SELECT * FROM USER_INFORMATION");
        while (columns.next()){
            String str1 = String.format("%30s|%30s|%30s|%30s|", columns.getObject(1), columns.getObject(2),
                    columns.getObject(3), columns.getObject(4));
            System.out.println(str1);
        }
        System.out.println(displayLine);
    }

    private static void displayAllOrganisations() throws SQLException {
        Statement viewOrganisations = connection.createStatement();
        DatabaseMetaData metadata = connection.getMetaData();
        ResultSet columns = metadata.getColumns(null, null, "ORGANISATIONAL_UNIT_INFORMATION", null);

        System.out.println();
        System.out.println();
        System.out.println(displayLine);

        // display all tables and data from database
        while (columns.next()){
            String str2 = String.format("%30s|", columns.getString("COLUMN_NAME"));
            System.out.print(str2);
        }

        System.out.println();
        System.out.print(displayLine);
        System.out.println();

        columns = viewOrganisations.executeQuery("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION");
        while (columns.next()){
            String str1 = String.format("%30s|%30s|%30s|%30s|", columns.getObject(1), columns.getObject(2),
                    columns.getObject(3), columns.getObject(4));
            System.out.println(str1);
        }

        System.out.println(displayLine);
    }

    public static void main(String [] args) throws SQLException, NoSuchAlgorithmException {

        // clear the local database so things don't get messy - remove before release
        connection = DriverManager.getConnection(url, user, password);
        Statement statement = connection.createStatement();
        statement.executeUpdate("DROP DATABASE marketplace_server");
        connection.close();



        // create database
        connection = DriverManager.getConnection(url, user, password);
        statement = connection.createStatement();
        createTables(statement);


        // insert new organisations
        addOrganisation(1, "Admin", 0, 0);
        addOrganisation(2, "Computational Research", 100, 5);
        addOrganisation(3, "Web Design", 400, 65);


        // insert new users
        // addUser("Bob Le", "k1234!2#@!dsaA", "user", "Computational Research");
        addUser("Steve Deno", "ASDn213k21!@#", "user", "Web Design");
        addUser("Sandra Meago", "ASDn213k21!@#", "Admin", "Admin");

        // User testUser = new User("Bob Le", "k1234!2#@!dsaA", "user", "Computational Research", dataSource);


        // insert new assets
//        addAsset();
//        addAsset();
//        addAsset();

        // display all data
        displayAllUsers();
        displayAllOrganisations();
        // displayAllAssets();
        // displayAllActiveBuy();
        // displayAllActiveSell();
        // displayAllClosedBuy();
        // displayAllClosedSell();
        // displayAllActiveBuy();

        connection.close();

        System.out.println();
        System.out.println();

//        String password = "Password1234";
//        ToHash getHash = new ToHash();
//        System.out.println(getHash.intoHash(password));


    }
}
