package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * Order class, parent for buy and sell orders. Holds information about an asset, it's connected organisation and other
 * details.
 *
 * @see marketplace.Handlers.OrderHandler
 *
 */
public class Order implements Serializable {
    String orderID;
    String userID;
    String assetName;
    int quantity;
    BigDecimal price;
    Timestamp orderDate;

    /**
     * Constructor for Order object.
     *
     * @param orderID is the unique identifier for each order, so it can be found in the database.
     * @param userID is the userID of the user who placed the sell or buy order.
     * @param assetName is the name of the asset being either sold or bought.
     * @param quantity is the quantity of the asset being sold or bought in whole numbers.
     * @param price is the price that the orders are active for or have been fullfilled with.
     * @param orderDate is the data time of the order.
     *
     */
    public Order(String orderID, String userID, String assetName, int quantity, BigDecimal price, Timestamp orderDate){
        this.orderID = orderID;
        this.userID = userID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
    }

    public Order(){}

    /**
     * @param orderID sets the order's ID
     */
    public void setOrderID(String orderID){
        this.orderID = orderID;
    }

    /**
     * @param userID sets the order's userID with who made the order
     */
    public void setUserID(String userID){
        this.userID = userID;
    }

    /**
     * @param assetName sets the order's asset name that is being interacted with
     */
    public void setAssetName(String assetName){
        this.assetName = assetName;
    }

    /**
     * @param quantity sets the order's asset quantity
     */
    public void setQuantity(int quantity){
            this.quantity = quantity;
    }

    /**
     * @param price sets the orders value per asset being sold or bought
     */
    public void setPrice(BigDecimal price){
        this.price = price;
    }

    /**
     * @param orderDate sets the date and time that the order was made or completed on
     */
    public void setOrderDate(Timestamp orderDate){
        this.orderDate = orderDate;
    }

    /**
     * @return the orderID of the order
     */
    public String getOrderID(){
        return orderID;
    }


    /**
     * @return the userID for the user who made the order
     */
    public String getUserID() {
        return userID;
    }

    /**
     * @return the assetName for the asset being sold or bought in the order
     */
    public String getAssetName(){
        return assetName;
    }

    /**
     * @return the quantity of the asset being sold or bought in the order
     */
    public int getQuantity(){
        return quantity;
    }

    /**
     * @return the price of the asset being sold or bought in the order
     */
    public BigDecimal getPrice(){
        return price;
    }

    /**
     * @return the date and time of the order
     */
    public Timestamp getOrderDate(){
        return orderDate;
    }
}
