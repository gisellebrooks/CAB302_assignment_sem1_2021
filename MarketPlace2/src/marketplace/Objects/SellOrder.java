package marketplace.Objects;

import java.math.BigDecimal;
import java.sql.Timestamp;
/**
 * Sell Order class for orders that involve selling an asset. A user makes an order using their organisation's credits.
 * Extends order class.
 *
 * @see marketplace.Objects.Order
 *
 */
public class SellOrder extends Order{
    String assetID;

    /**
     * Constructor for SellOrder object, used in database for ACTIVE_SELL_ORDERS and SELL_ORDER_HISTORY table.
     *
     * @param orderID is the unique identifier for each order, so it can be found in the database.
     * @param userID is the userID of the user who placed the sell order.
     * @param assetID is the ID of the asset being sold
     * @param assetName is the name of the asset being either sold.
     * @param quantity is the quantity of the asset being sold in whole numbers.
     * @param price is the price that the orders are active for.
     * @param orderDate is the data time of the order.
     *
     */
    public SellOrder(String orderID, String userID, String assetID, String assetName, int quantity, BigDecimal price, Timestamp orderDate){
        super(orderID, userID, assetName, quantity, price, orderDate);
        this.assetID = assetID;
    }

    /**
     * Constructor for SellOrder object, used in database for ACTIVE_SELL_ORDERS and SELL_ORDER_HISTORY table.
     */
    public SellOrder(){}

    /**
     * sets a new assetID
     */
    public void setAssetID(String assetID){
        this.assetID = assetID;
    }

    /**
     * gets the assetID
     */
    public String getAssetID(){
        return assetID;
    }
}
