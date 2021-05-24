package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Order;
import marketplace.Objects.Organisation;
import marketplace.Objects.SellOrder;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
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

    public List<Order> getAllActiveBuyOrders(){
        List<Order> result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS;", TableObject.BUY_ORDER);
            result = (List) client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public Order getBuyOrder(String buyID){
        Order result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS WHERE buyID = '" +buyID+ "' ;", TableObject.BUY_ORDER);
            result = (Order) client.readObjectFromServer();
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

    public List<SellOrder> getAllActiveSellOrders(){
        List<SellOrder> result = null;
        try {
            client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS;", TableObject.SELL_ORDER);
            result = (List<SellOrder>) client.readObjectFromServer();
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

    public String newOrderID(String orderType) {
        Object orderID = null;
        String lastID = null;
        if (orderType.equals("Buy")){
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

        } else if (orderType.equals("Sell")) {
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
        return ("user" + IDNumber);
    }

    public void addNewBuyOrder(String userID, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String id = newOrderID("Buy");

        try {
            client.writeToServer("INSERT INTO ACTIVE_BUY_ORDERS VALUES('"+id+"', '"+userID+"', '"+ quantity +
                    "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addNewSellOrder(String userID, String assetID, int quantity, BigDecimal price){
        Timestamp orderDate = new Timestamp(System.currentTimeMillis());
        String sellID = newOrderID("Sell");

        try {
            client.writeToServer("INSERT INTO ACTIVE_SELL_ORDERS VALUES('"+sellID+"', '"+userID+"', '"+ assetID +
                    "', '"+ quantity + "', '" + price +"', '" +orderDate+ "' );", TableObject.SELL_ORDER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reconcileOrder() throws IOException, ClassNotFoundException {
        List<Order> buy = getAllActiveBuyOrders();
        List<SellOrder> sell = getAllActiveSellOrders();

        for (Order buyOrder : buy){
            String buyID = buyOrder.getOrderID();
            String buyUserID = buyOrder.getUserID();
            String buyAssetName = buyOrder.getAssetName();
            int buyQuantity = buyOrder.getQuantity();
            BigDecimal buyPrice = buyOrder.getPrice();

            client.writeToServer("SELECT u.* FROM active_buy_orders a RIGHT JOIN user_information u ON a.userID=u.userID WHERE a.buyID='"+buyID+"';", TableObject.USER);
            User buyUser = (User) client.readObjectFromServer();
            String buyOrgID = buyUser.getOrganisationID();

            client.writeToServer("SELECT * FROM organisational_unit_information WHERE orgID='"+buyOrgID+"';", TableObject.ORGANISATION);
            Organisation buyOrg = (Organisation) client.readObjectFromServer();
            BigDecimal buyOrgCredits = buyOrg.getCredits();

            for (SellOrder sellOrder : sell){
                String sellID = sellOrder.getOrderID();
                String sellUserID = sellOrder.getUserID();
                String sellAssetID = sellOrder.getAssetID();
                String sellAssetName = sellOrder.getAssetName();
                int sellQuantity = sellOrder.getQuantity();
                BigDecimal sellPrice = sellOrder.getPrice();
                client.writeToServer("SELECT u.* FROM active_sell_orders a RIGHT JOIN user_information u ON a.userID=u.userID WHERE a.sellID='"+ sellID +"';", TableObject.USER);
                User sellUser = (User) client.readObjectFromServer();
                String sellOrgID = sellUser.getOrganisationID();


                // compare buy and sell price
                int comparePrice = buyPrice.compareTo(sellPrice);

                if (buyAssetName.equals(sellAssetName) && buyQuantity == sellQuantity && !buyOrgID.equals(sellOrgID) && (comparePrice == -1 || comparePrice == 0)){

                    // calculate purchase price
                    BigDecimal pricePerUnit = buyPrice;
                    BigDecimal totalPurchasePrice = pricePerUnit.multiply(new BigDecimal(buyQuantity));

                    // check buy organisation has enough credits
                    BigDecimal updatedBuyOrgCredits = buyOrgCredits.subtract(totalPurchasePrice);
                    if(updatedBuyOrgCredits.compareTo(new BigDecimal(0)) < 0){
                        //if not enough, continue to next iteration
                        continue;
                    }

                    // check sell organisation has enough quantity
                    if(inventoryHandler.getAssetQuantity(sellAssetID) < 0){
                        //not enough assets
                        continue;
                    }

                    // if we get to here, all conditions are met and we can process order

                    //get time of purchase
                    Timestamp reconcileDate = new Timestamp(System.currentTimeMillis());

                    client.writeToServer("DELETE FROM active_buy_orders WHERE buyID='"+ buyID +"';", TableObject.DELETE);
                    client.writeToServer("INSERT INTO buy_order_history VALUES('"+ buyID +"', '"+ buyUserID +"', '"+ sellAssetID +"', '"+ buyQuantity +"', '"+ pricePerUnit +"', '"+ reconcileDate +"') ;", TableObject.BUY_HISTORY);
                    inventoryHandler.updateAssetQuantity(sellAssetID, buyQuantity);
                    organisationHandler.updateOrganisationCredits(buyOrgID, updatedBuyOrgCredits);

                    client.writeToServer("DELETE FROM active_sell_orders WHERE sellID='"+ sellID +"';", TableObject.DELETE);
                    client.writeToServer("INSERT INTO sell_order_history VALUES('"+ sellID +"', '"+ sellUserID +"', '"+ sellAssetID +"', '"+ sellQuantity +"', '"+ pricePerUnit +"', '"+ reconcileDate +"') ;", TableObject.SELL_HISTORY);
                    inventoryHandler.updateAssetQuantity(sellAssetID, sellQuantity);
                    organisationHandler.updateOrganisationCredits(buyOrgID, updatedBuyOrgCredits);
                }
            }
        }
    }

}


