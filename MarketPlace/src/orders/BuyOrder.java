package orders;

import Server.QueryTemplate;
import Server.MariaDBDataSource;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;


public class BuyOrder implements Order {

    private double assetQuantity;
    private double assetPrice;
    private long sellID;

    private ResultSet orderHistory;
    private final MariaDBDataSource pool;
    private final QueryTemplate query;


    //private static final String INSERT_PERSON = "INSERT INTO address (name, street, suburb, phone, email) VALUES (?, ?, ?, ?, ?);";

    private static final String GET_ACTIVE_BUY_ORDERS = "SELECT * FROM ACTIVE_BUY_ORDERS WHERE assetName=?";

    private static final String EDIT_ACTIVE_BUY_ORDER = "UPDATE ACTIVE_BUY_ORDERS SET quantity = ?, priceUpper = ? WHERE buy_id=?";

    private static final String GET_ASSET = "SELECT * FROM address WHERE name=?";

    private static final String GET_ASSET_QUANTITY = "SELECT quantity FROM ASSETS WHERE unit_id=? AND asset_id=?";

    private static final String GET_ORDER_HISTORY = "SELECT * FROM ASSETS WHERE assetName=?";

    private static final String GET_ASSET_PRICE = "SELECT priceUpper FROM ACTIVE_SELL_ORDERS WHERE asset_id=?";


    private static final String GET_CREDITS = "SELECT credits FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID=?;";
    private static final String INSERT_NEW_BUY_ORDER ="INSERT INTO ACTIVE_BUY_ORDERS (buyID, assetName, orgID)" +
            "VALUES ('buy5', ?, ?)";

    private static final String HAS_CREDITS =  "SELECT credits" +
            " FROM ORGANISATIONAL_UNIT_INFORMATION" +
            " WHERE orgID=? and credits >= ?";

    private static final String UPDATE_ORDER = "INSERT INTO buy_order_history * SELECT * FROM active_buy_orders WHERE buyID=?;";
    private static final String DELETE_ACTIVE_ORDER = "DELETE FROM active_buy_orders WHERE buyID=?;";

    public BuyOrder (MariaDBDataSource pool){
        this.pool = pool;
        this.query = new QueryTemplate(pool);
//        this.assetPrice = assetPrice;
//        this.assetQuantity = assetQuantity;
    }
        /////////// fix this so I can set column as column name instead of index in the prepared statement to string method
    public void createBuyOrder(String assetName, double priceUpper, String userID){

        ResultSet hasCredits;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Map<String, Object> credit_params = new HashMap<>();

        credit_params.put("userID", userID);
        credit_params.put("priceUpper", priceUpper);
        hasCredits = query.get(HAS_CREDITS, credit_params);

        try {
            if (hasCredits.next()) {
                Map<String, Object> order_params = new HashMap<>();
                order_params.put("assetName", assetName);
                order_params.put("priceUpper", priceUpper);
                order_params.put("userID", userID);
                query.add(INSERT_NEW_BUY_ORDER, order_params);


            } else {
                System.out.println("Sorry, you don't have enough credits to place this order");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public ResultSet getOrderHistory(String buyID, String orgID){

        ResultSet orderHistory;
        Map<String, Object> params = new HashMap<>();

        params.put("buyID", buyID);
        params.put("orgID", orgID);

        orderHistory = query.get(GET_ORDER_HISTORY, params);

        try{
            // Handle if query returns empty
            if (orderHistory.next()) {
                assetQuantity = orderHistory.getLong("quantity");
            } else{
                // If not found then quantity is 0
                assetQuantity = 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistory;
    }

    public ResultSet getActiveOrders(String assetName){

        ResultSet activeOrders;

        Map<String, Object> params = new HashMap<>();
        params.put("assetName", assetName);

        activeOrders = query.get(GET_ACTIVE_BUY_ORDERS, params);

        return activeOrders;
    }


    public int getAssetId(){return 1;}


    public void processOrder(String buyID){
        Map<String, Object> params = new HashMap<>();
        params.put("buyID", buyID);

        query.update(UPDATE_ORDER, params);
        query.update(DELETE_ACTIVE_ORDER, params);

    }


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

    public double getAssetPrice(String assetID) {

        ResultSet result;

        Map<String, Object> params = new HashMap<>();
        params.put("assetID", assetID);

        result = query.get(GET_ASSET_PRICE, params);

        // Handle if query returns empty
        try {
            if (result.next()) {
                while (result.next()) {
                    assetPrice = result.getLong("priceUpper");
                }
            } else{
                // If not found then quantity is 0
                assetPrice = 0.0;
                sellID = 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return assetPrice;
    }

}
