package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class Order implements Serializable {
    String orderID;
    String userID;
    String assetName;
    int quantity;
    BigDecimal price;
    Timestamp orderDate;

    public Order(String orderID, String userID, String assetName, int quantity, BigDecimal price, Timestamp orderDate){
        this.orderID = orderID;
        this.userID = userID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.price = price;
        this.orderDate = orderDate;
    }

    public Order(){

    }

    public void setOrderID(String orderID){
        this.orderID = orderID;
    }

    public void setUserID(String userID){
        this.userID = userID;
    }

    public void setAssetName(String assetName){
        this.assetName = assetName;
    }

    public void setQuantity(int quantity){
            this.quantity = quantity;
    }

    public void setPrice(BigDecimal price){
        this.price = price;
    }

    public void setOrderDate(Timestamp orderDate){
        this.orderDate = orderDate;
    }

    public String getOrderID(){
        return orderID;
    }

    public String getUserID() {
        return userID;
    }

    public String getAssetName(){
        return assetName;
    }

    public int getQuantity(){
        return quantity;
    }

    public BigDecimal getPrice(){
        return price;
    }

    public Timestamp getOrderDate(){
        return orderDate;
    }
}
