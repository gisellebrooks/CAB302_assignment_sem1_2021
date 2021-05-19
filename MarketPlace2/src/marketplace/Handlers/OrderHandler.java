package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.BuyOrder;
import marketplace.Objects.SellOrder;
import marketplace.TableObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class OrderHandler implements Serializable {
    private final Client client;

    public OrderHandler(Client client){
        this.client = client;
    }

    public List<BuyOrder> getAllActiveBuyOrders(){
        List<BuyOrder> result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS;", TableObject.BUY_ORDER);
            result = (List) client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public BuyOrder getBuyOrder(String buyID){
        BuyOrder result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS WHERE buyID = '" +buyID+ "' ;", TableObject.BUY_ORDER);
            result = (BuyOrder) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addBuyOrder(String buyID, String userID, String assetName, int quantity, BigDecimal priceUpper){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());

        try {
            client.writeToServer("INSERT INTO ACTIVE_BUY_ORDERS VALUES('"+buyID+"', '"+userID+"', '"+ assetName +
                    "', '"+ quantity + "', '" + priceUpper +"', '" +orderDate+ "' );", TableObject.BUY_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SellOrder getAllActiveSellOrders(){
        SellOrder result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS;", TableObject.SELL_ORDER);
            result = (SellOrder) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public SellOrder getSellOrder(String sellID){
        SellOrder result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS WHERE sellID = '" +sellID+ "' ;", TableObject.SELL_ORDER);
            result = (SellOrder) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addSellOrder(String sellID, String userID, String assetName, int quantity, BigDecimal priceLower){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());

        try {
            client.writeToServer("INSERT INTO ACTIVE_SELL_ORDERS VALUES('"+sellID+"', '"+userID+"', '"+ assetName +
                    "', '"+ quantity + "', '" + priceLower +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
