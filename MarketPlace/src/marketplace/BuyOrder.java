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


    public BuyOrder (MariaDBDataSource pool){
        this.pool = pool;
//        this.assetPrice = assetPrice;
//        this.assetQuantity = assetQuantity;
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
