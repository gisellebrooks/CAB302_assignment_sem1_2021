package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.*;
import marketplace.TableObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Handler class for Order object type. Handles methods that main methods for interacting with Order type.
 * Interacts with ACTIVE_BUY_ORDERS, ACTIVE_SELL_ORDERS tables in database.
 *
 * @see Order
 *
 */
public class OrderHandler implements Serializable {
    private final Client client;
    private InventoryHandler inventoryHandler;
    private OrganisationHandler organisationHandler;

    public OrderHandler(Client client){
        this.client = client;
         inventoryHandler = new InventoryHandler(client);
         organisationHandler = new OrganisationHandler(client);
    }

    public List<Order> getAllActiveBuyOrdersForAsset(String assetName){
        List<Order> allBuyOrders;
        List<Order> allBuyOrdersForAsset = new ArrayList<>();
        allBuyOrders = getAllActiveBuyOrders();

        for (Order buyOrder: allBuyOrders){
            if(buyOrder.getAssetName().equals(assetName)){
                allBuyOrdersForAsset.add(buyOrder);
            }
        }
        return allBuyOrdersForAsset;
    }

    public List<SellOrder> getAllActiveSellOrdersForAsset(String assetName){
        List<SellOrder> allSellOrders;
        List<SellOrder> allSellOrdersForAsset = new ArrayList<>();
        allSellOrders = getAllActiveSellOrders();

        for (SellOrder sellOrder: allSellOrders){
            if(sellOrder.getAssetName().equals(assetName)){
                allSellOrdersForAsset.add(sellOrder);
            }
        }
        return allSellOrdersForAsset;
    }

    public List<Order> getAllActiveBuyOrders(){
        List<Order> result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS;", TableObject.BUY_ORDER);
            result = client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public List<SellOrder> getAllActiveSellOrders(){
        List<SellOrder> result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS;", TableObject.SELL_ORDER);
            result =  (List<SellOrder>) client.readListFromServer();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public List<SellOrderHistory> getAllSellOrderHistory(){
        List<SellOrderHistory> result = null;
        try {
            client.writeToServer("SELECT * FROM SELL_ORDER_HISTORY;", TableObject.SELL_HISTORY);
            result =  client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public List<SellOrderHistory> getAllSellOrderHistoryForAsset(String assetName){
        List<SellOrderHistory> result = null;
        try {
            client.writeToServer(" SELECT sell_order_history.* FROM sell_order_history LEFT JOIN " +
                            "inventory ON sell_order_history.assetID = inventory.assetID WHERE inventory.assetName = " +
                            "'" + assetName + "';", TableObject.SELL_HISTORY);

            result =  client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return result;
    }


    public String newOrderID(String orderType) {
        Order buyOrderID = null;
        SellOrder sellOrderID = null;
        String lastID = null;
        if (orderType.equals("buy")){
            List<Order> buyList = null;
            try {
                client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS ORDER BY buyID DESC LIMIT 1;", TableObject.BUY_ORDER);

                buyList = (List<Order>) client.readListFromServer();
                if (buyList != null){
                    buyOrderID = buyList.get(0);
                    lastID = buyOrderID.getOrderID();
                }
                else {
                    lastID = "buy0";
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            }

        } else if (orderType.equals("sell")) {
            List<SellOrder> sellList = null;
            try {
                client.writeToServer(
                        "SELECT * FROM ACTIVE_SELL_ORDERS ORDER BY sellID DESC LIMIT 1;", TableObject.SELL_ORDER);

                sellList = (List<SellOrder>) client.readListFromServer();
                if (sellList != null){
                    sellOrderID = sellList.get(0);
                    lastID = sellOrderID.getOrderID();
                }
                else {
                    lastID = "sell0";
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

        String[] part = lastID.split("(?<=\\D)(?=\\d)");
        int IDNumber = Integer.parseInt(part[1]) + 1;
        return (orderType + IDNumber);
    }

    public void addNewBuyOrder(String userID, String assetName, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String id = newOrderID("buy");

        try {
            client.writeToServer("INSERT INTO ACTIVE_BUY_ORDERS VALUES('"+id+"', '"+userID+"', '"+assetName+"', '"+ quantity +
                    "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewSellOrder(String userID, String assetID, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String sellID = newOrderID("sell");

        try {
            client.writeToServer("INSERT INTO ACTIVE_SELL_ORDERS VALUES('"+sellID+"', '"+userID+"', '"+ assetID +
                    "', '"+ quantity + "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ammendOrder(String orderType, String orderID, int quantity, BigDecimal price){
        if (orderType == "buy"){
            try {
                client.writeToServer("UPDATE ACTIVE_BUY_ORDERS SET quantity= '" + quantity + "', priceUpper= '"+ price +"' WHERE buyID= '"+ orderID +"' ;", TableObject.UPDATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (orderType == "sell"){
            try {
                client.writeToServer("UPDATE ACTIVE_SELL_ORDERS SET quantity= '" + quantity + "', priceLower= '"+ price +"' WHERE sellID= '"+ orderID +"' ;", TableObject.UPDATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public List<String> getAllActiveAssetNames() {
        List<Order> allBuyOrders = getAllActiveBuyOrders();
        List<SellOrder> allSellOrders = getAllActiveSellOrders();
        List<String> allAssetNames = new ArrayList<>();

        if (allBuyOrders != null || allSellOrders != null ) {
            for (Order assetName : allBuyOrders) {
                if (!allAssetNames.contains(assetName.getAssetName())) {
                    allAssetNames.add(assetName.getAssetName());
                }
            }

            for (SellOrder assetName : allSellOrders) {
                if (!allAssetNames.contains(assetName.getAssetName())) {
                    allAssetNames.add(assetName.getAssetName());
                }
            }
        }

        return allAssetNames;
    }

    public List<String> getAllOrganisationsAssets(String orgID) {
        List<Order> allBuyOrders = getAllActiveBuyOrders();
        List<SellOrder> allSellOrders = getAllActiveSellOrders();
        List<String> allAssetNames = new ArrayList<>();

        if (allBuyOrders != null || allSellOrders != null ) {
            for (Order assetName : allBuyOrders) {
                if (!allAssetNames.contains(assetName.getAssetName())) {
                    allAssetNames.add(assetName.getAssetName());
                }
            }
            for (SellOrder assetName : allSellOrders) {
                if (!allAssetNames.contains(assetName.getAssetName())) {
                    allAssetNames.add(assetName.getAssetName());
                }
            }

            return allAssetNames;
        }

        return new ArrayList<>();
    }
}