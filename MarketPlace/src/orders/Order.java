package orders;

import java.sql.ResultSet;

/**
 * Provides functionality needed by any data source for the Address Book
 * application.
 */
public interface Order {

    /**
     * Gets the Historical transaction data of a given asset.
     *
     * @param order_id The ID of the asset order.
     *
     * @param unit_id the ID of the organisational unit that made the order.
     *
     * @return ResultSet of order history.
     */
    ResultSet getOrderHistory(String order_id, String unit_id);


    ResultSet getActiveOrders(String assetName);

    double getAssetQuantity(String organisationID, String assetID);

    int getAssetId();

    double getAssetPrice(String assetID);



    void processOrder(String order_id);


}