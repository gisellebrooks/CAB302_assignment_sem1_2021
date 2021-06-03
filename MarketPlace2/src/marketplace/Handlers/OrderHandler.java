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
            result =  client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }


    public String newOrderID(String orderType) {
        Object orderID = null;
        String lastID = null;
        if (orderType.equals("buy")){
            List<Order> buyList = null;
            try {
                client.writeToServer("SELECT buyID FROM ACTIVE_BUY_ORDERS ORDER BY cast(buyID as SIGNED) DESC LIMIT 1;", TableObject.USER);

                buyList = (List<Order>) client.readListFromServer();
                if (buyList != null){
                    orderID = buyList.get(0);
                    lastID = ((Order) orderID).getOrderID();
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            }

        } else if (orderType.equals("sell")) {
            List<SellOrder> sellList = null;
            try {
                client.writeToServer("SELECT sellID FROM ACTIVE_BUY_ORDERS ORDER BY cast(sellID as SIGNED) DESC LIMIT 1;", TableObject.USER);

                sellList = (List<SellOrder>) client.readListFromServer();
                if (sellList != null){
                    orderID = sellList.get(0);
                    lastID = ((SellOrder) orderID).getOrderID();
                }

            } catch (Exception exception) {

                exception.printStackTrace();
            }
        }

        String[] part = lastID.split("(?<=\\D)(?=\\d)");
        int IDNumber = Integer.parseInt(part[1]) + 1;
        return (orderType + IDNumber);
    }

    public void addNewBuyOrder(String userID, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String id = newOrderID("buy");

        try {
            client.writeToServer("INSERT INTO ACTIVE_BUY_ORDERS VALUES('"+id+"', '"+userID+"', '"+ quantity +
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

            return allAssetNames;
        }

        return new ArrayList<>();
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