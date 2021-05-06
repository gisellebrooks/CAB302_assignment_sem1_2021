package marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyOrder implements Order {

    private double assetQuantity;
    private double assetPrice;
    private long sellID;

    private ResultSet orderHistory;
    private final MariaDBDataSource pool;


    //private static final String INSERT_PERSON = "INSERT INTO address (name, street, suburb, phone, email) VALUES (?, ?, ?, ?, ?);";

    private static final String GET_ACTIVE_BUY_ORDERS = "SELECT * FROM ACTIVE_BUY_ORDERS WHERE buy_id=?";

    private static final String EDIT_ACTIVE_BUY_ORDER = "UPDATE ACTIVE_BUY_ORDERS SET quantity = ?, priceUpper = ? WHERE buy_id=?";

    private static final String GET_ASSET = "SELECT * FROM address WHERE name=?";

    private static final String GET_ASSET_QUANTITY = "SELECT quantity FROM ASSETS WHERE unit_id=? AND asset_id=?";

    private static final String GET_ORDER_HISTORY = "SELECT * FROM ASSETS WHERE assetName=?";

    private static final String GET_ASSET_PRICE = "SELECT priceUpper, sell_id FROM ACTIVE_SELL_ORDERS WHERE unit_id=? AND asset_id=?";

   // private static final String GET_ACTIVE_BUY_ORDERS = " SELECT ACTIVE_BUY_ORDERS.buyID, ORGANISATIONAL_UNIT_INFORMATION.orgName, INVENTORY.assetID, INVENTORY.assetName, ACTIVE_BUY_ORDERS.quantity, ACTIVE_BUY_ORDERS.priceUpper, ACTIVE_BUY_ORDERS.orderDate
    // FROM ACTIVE_BUY_ORDERS
    // JOIN ORGANISATIONAL_UNIT_INFORMATION ON ACTIVE_BUY_ORDERS.orgID=ORGANISATIONAL_UNIT_INFORMATION.orgID
    // LEFT JOIN INVENTORY ON ACTIVE_BUY_ORDERS.assetID=INVENTORY.assetID
    // INNER JOIN INVENTORY ON ACTIVE_BUY_ORDERS.assetID = INVENTORY.assetID ORDER BY INVENTORY

    private static final String GET_CREDITS = "SELECT credits FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID=?;";
    private static final String INSERT_NEW_BUY_ORDER ="INSERT INTO ACTIVE_BUY_ORDERS (buyID, assetName, orgID)" +
            "VALUES ('buy5', ?, ?)";

    private static final String HAS_CREDITS =  "SELECT credits" +
            " FROM ORGANISATIONAL_UNIT_INFORMATION" +
            " WHERE orgID=? and credits >= ?";

    // INSERT INTO INVENTORY orgID
    // SELECT org.orgID
    // FROM ORGANISATIONAL_UNIT_INFORMATION org

    public BuyOrder (MariaDBDataSource pool){
        this.pool = pool;
//        this.assetPrice = assetPrice;
//        this.assetQuantity = assetQuantity;
    }

    public void createBuyOrder(String assetName, double price, String orgID){

        ResultSet hasCredits;
        try(Connection conn = pool.getConnection()) {
            try(PreparedStatement statement = conn.prepareStatement(HAS_CREDITS)){
                statement.setString(1, orgID);
                statement.setDouble(2, price);
                hasCredits = statement.executeQuery();


            }
            if (hasCredits.next()) {
                System.out.println(hasCredits.getLong("credits"));
                try (PreparedStatement statement = conn.prepareStatement(INSERT_NEW_BUY_ORDER)) {
                    statement.setString(1, assetName);
                    statement.setString(2, orgID);
                    statement.executeQuery();
                }
            } else {
                System.out.println("Sorry, you don't have enough credits to place this order");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public ResultSet getOrderHistory(String order_id, String unit_id){

        try(Connection conn = pool.getConnection()) {

            ResultSet orderHistory;
            try(PreparedStatement statement = conn.prepareStatement(GET_ORDER_HISTORY)) {

                statement.setString(1, order_id);
                statement.setString(2, unit_id);
                orderHistory = statement.executeQuery();

                // Handle if query returns empty
                if (orderHistory.next()) {
                    assetQuantity = orderHistory.getLong("quantity");
                } else{
                    // If not found then quantity is 0
                    assetQuantity = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistory;
    }

    public ResultSet getActiveOrders(){return null;}


    public int getAssetId(){return 1;}


    public void processOrder(){}


    public double getAssetQuantity(String organisationID, String assetID){
        try(Connection conn = pool.getConnection()) {

            ResultSet queriedResult;
            try(PreparedStatement statement = conn.prepareStatement(GET_ASSET_QUANTITY)) {

                statement.setString(1, organisationID);
                statement.setString(2, assetID);
                queriedResult = statement.executeQuery();

                // Handle if query returns empty
                if (queriedResult.next()) {
                    assetQuantity = queriedResult.getLong("quantity");
                } else{
                    // If not found then quantity is 0
                    assetQuantity = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assetQuantity;
    }

    public double getAssetPrice(String organisationID, String assetID) {
        try(Connection conn = pool.getConnection()) {

            ResultSet queriedResult;
            try(PreparedStatement statement = conn.prepareStatement(GET_ASSET_PRICE)) {

                statement.setString(1, organisationID);
                statement.setString(2, assetID);
                queriedResult = statement.executeQuery();

                // Handle if query returns empty
                if (queriedResult.next()) {
                    while (queriedResult.next()) {
                        assetPrice = queriedResult.getLong("priceUpper");
                        sellID = queriedResult.getLong("sell_id");
                    }
                } else{
                    // If not found then quantity is 0
                    assetPrice = 0.0;
                    sellID = 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assetPrice;
    }

}
