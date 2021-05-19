package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

public class BuyOrder implements Serializable {
    String buyId;
    String userID;
    String assetName;
    int quantity;
    BigDecimal priceUpper;
    Timestamp orderDate;

    public BuyOrder(String buyId, String userID, String assetName, int quantity, BigDecimal priceUpper, Timestamp orderDate){
        this.buyId = buyId;
        this.userID = userID;
        this.assetName = assetName;
        this.quantity = quantity;
        this.priceUpper = priceUpper;
        this.orderDate = orderDate;
    }

    public BuyOrder(){

    }

    public void setBuyId(String buyId){
        this.buyId = buyId;
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

    public void setPriceUpper(BigDecimal priceUpper){
        this.priceUpper = priceUpper;
    }

    public void setOrderDate(Timestamp orderDate){
        this.orderDate = orderDate;
    }

    public String getBuyId(){
        return buyId;
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

    public BigDecimal getPriceUpper(){
        return priceUpper;
    }

    public Timestamp getOrderDate(){
        return orderDate;
    }
}
