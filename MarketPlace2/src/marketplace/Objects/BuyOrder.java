package marketplace.Objects;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BuyOrder extends Order{
    String assetID;

    public BuyOrder(String orderID, String userID, String assetID, String assetName, int quantity, BigDecimal price, Timestamp orderDate){
        super(orderID, userID, assetName, quantity, price, orderDate);
        this.assetID = assetID;
    }

    public BuyOrder(){

    }

    public void setAssetID(String assetID){
        this.assetID = assetID;
    }

    public String getAssetID(){

        return assetID;
    }
}
