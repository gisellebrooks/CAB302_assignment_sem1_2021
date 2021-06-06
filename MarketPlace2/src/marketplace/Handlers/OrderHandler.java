package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUI;
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
            System.out.println("GETTING SELL ORDERS");
            System.out.println(result);
            for (SellOrder sellOrder: result){
                System.out.println(sellOrder);
            }
            
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
        System.out.println("GEtting getAllSellOrderHistoryForAsset");
        System.out.println(assetName);
        System.out.println(result);
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
                    "', '" + price +"', '" +orderDate+ "' );", TableObject.BUY_ORDER);
            client.readListFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addNewSellOrder(String userID, String assetID, String assetName, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String sellID = newOrderID();

        try {
            client.writeToServer("INSERT INTO ACTIVE_SELL_ORDERS VALUES('"+sellID+"', '"+userID+"', '"+ assetID +
                    "', '" + assetName + "', '" + quantity + "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
            client.readListFromServer();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(String orderType, String orderID){
        System.out.println("DELETEING ORDER");
        System.out.println(orderType);
        System.out.println(orderID);
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
        List<Inventory> allInventory = MainGUI.inventoryHandler.getAllInventory();
        List<String> allAssetNames = new ArrayList<>();

        if (allBuyOrders != null || allSellOrders != null || allInventory != null ) {
            for (Order order : allBuyOrders) {
                if (!allAssetNames.contains(order.getAssetName())) {
                    allAssetNames.add(order.getAssetName());
                }
            }
            for (Order order : allSellOrders) {
                if (!allAssetNames.contains(order.getAssetName())) {
                    allAssetNames.add(order.getAssetName());
                }
            }
            for (Inventory inv : allInventory) {
                if (!allAssetNames.contains(inv.getAssetName())) {
                    allAssetNames.add(inv.getAssetName());
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