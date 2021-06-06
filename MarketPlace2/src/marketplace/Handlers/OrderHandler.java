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
import java.util.UUID;

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
            System.out.println("GOT RESULT");
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        System.out.println("GETTING ALL SELL ORDERS");
        System.out.println(result);
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


    public String newOrderID() {
        UUID uuid = UUID.randomUUID();
        String uuidAsString = uuid.toString();
        return uuidAsString;
    }

    public void addNewBuyOrder(String userID, String assetName, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String id = newOrderID();

        try {
            client.writeToServer("INSERT INTO ACTIVE_BUY_ORDERS VALUES('"+id+"', '"+userID+"', '"+assetName+"', '"+ quantity +
                    "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewSellOrder(String userID, String assetID, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String sellID = newOrderID();

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

    public void deleteOrder(String orderType, String orderID){
        if (orderType == "buy"){
            try {
                client.writeToServer("DELETE FROM ACTIVE_BUY_ORDERS WHERE buyID= '"+ orderID +"' ;", TableObject.DELETE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (orderType == "sell"){
            try {
                client.writeToServer("DELETE FROM ACTIVE_SELL_ORDERS WHERE sellID= '"+ orderID +"' ;", TableObject.DELETE);
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
            for (Order order : allBuyOrders) {
                if (!allAssetNames.contains(order.getAssetName())) {
                    allAssetNames.add(order.getAssetName());
                }
            }
            System.out.println(allBuyOrders);
            System.out.println(allSellOrders);

            for (Order order : allSellOrders) {
                if (!allAssetNames.contains(order.getAssetName())) {
                    allAssetNames.add(order.getAssetName());
                }
            }
        }

        return allAssetNames;
    }

    public List<Order> getAllOrganisationBuyOrders(String orgID){
        List<Order> allBuyOrdersFromOrg = new ArrayList<>();
        try {
            client.writeToServer("SELECT active_buy_orders.* FROM active_buy_orders LEFT JOIN user_information" +
                    " ON active_buy_orders.userID = user_information.userID WHERE user_information.orgID" +
                    " = '"+ orgID +"';", TableObject.BUY_ORDER);

            allBuyOrdersFromOrg = (List<Order>) client.readListFromServer();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return allBuyOrdersFromOrg;
    }

    public List<SellOrder> getAllOrganisationSellOrders(String orgID){
        List<SellOrder> allSellOrdersFromOrg = new ArrayList<>();
        try {
            client.writeToServer("SELECT active_sell_orders.* FROM active_sell_orders LEFT JOIN user_information" +
                    " ON active_sell_orders.userID = user_information.userID WHERE user_information.orgID" +
                    " = '"+ orgID +"';", TableObject.SELL_ORDER);

            allSellOrdersFromOrg = (List<SellOrder>) client.readListFromServer();

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return allSellOrdersFromOrg;
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