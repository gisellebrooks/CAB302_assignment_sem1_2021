package marketplace.Objects;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class SellOrder extends Order{
    String assetID;

    public SellOrder(String orderID, String userID, String assetID, String assetName, int quantity, BigDecimal price, Timestamp orderDate){
        super(orderID, userID, assetName, quantity, price, orderDate);
        this.assetID = assetID;
    }

    public SellOrder(){

    }

    public void setAssetID(String assetID){
        this.assetID = assetID;
    }

    public String getAssetID(){

        return assetID;
    }
}
