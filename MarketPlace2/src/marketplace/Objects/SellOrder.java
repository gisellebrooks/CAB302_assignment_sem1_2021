package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class SellOrder implements Serializable {

    String sellId;
    String userID;
    String assetName;
    int quantity;
    BigDecimal priceLower;
    Timestamp orderDate;

    public SellOrder(String sellId, String userID, String assetName, int quantity, BigDecimal priceLower, Timestamp orderDate){
        this.sellId = sellId;
        this.userID = userID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.priceLower = priceLower;
        this.orderDate = orderDate;
    }

    public SellOrder(){

    }

    public void setSellId(String sellId){
        this.sellId = sellId;
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

    public void setPriceLower(BigDecimal priceLower){
        this.priceLower = priceLower;
    }

    public void setOrderDate(Timestamp orderDate){
        this.orderDate = orderDate;
    }

    public String getBuyId(){
        return sellId;
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

    public BigDecimal getPriceLower(){
        return priceLower;
    }

    public Timestamp getOrderDate(){
        return orderDate;
    }
}
