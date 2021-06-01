package marketplace.Server;

import marketplace.Objects.*;
import marketplace.TableObject;
import marketplace.Client.Client;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

public class ReconcileOrders extends TimerTask{
    private MariaDBDataSource pool;

    public ReconcileOrders(MariaDBDataSource pool){
        this.pool = pool;
    }

    public List<Order> getAllActiveBuyOrders() throws SQLException {
        ResultSet result = null;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACTIVE_BUY_ORDERS;")) {
                result = statement.executeQuery();
            }
        }
        List<Order> objectList = new ArrayList<>();
        while (result.next()) {
            Order buy = new Order();
            buy.setOrderID(result.getString("buyID"));
            buy.setUserID(result.getString("userID"));
            buy.setAssetName(result.getString("assetName"));
            buy.setQuantity(result.getInt("quantity"));
            buy.setPrice(result.getBigDecimal("priceUpper"));
            buy.setOrderDate(result.getTimestamp("orderDate"));
            objectList.add(buy);
        }
        return objectList;
    }

    public List<SellOrder> getAllActiveSellOrders() throws SQLException {
        ResultSet result = null;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACTIVE_SELL_ORDERS;")) {
                result = statement.executeQuery();
            }
        }
        List<SellOrder> sellList = new ArrayList<>();
        while (result.next()){
            SellOrder sell = new SellOrder();
            sell.setOrderID(result.getString("sellID"));
            sell.setUserID(result.getString("userID"));
            sell.setAssetID(result.getString("assetID"));
            sell.setAssetName(result.getString("assetName"));
            sell.setQuantity(result.getInt("quantity"));
            sell.setPrice(result.getBigDecimal("priceLower"));
            sell.setOrderDate(result.getTimestamp("orderDate"));
            sellList.add(sell);
        }
        return sellList;
    }

    public List<User> getUserInformation(String buyID, String type)  {

        ResultSet result = null;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT u.* FROM active_buy_orders a RIGHT JOIN user_information u ON a.userID=u.userID WHERE a."+type+"ID='"+buyID+"';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<User> userList = new ArrayList<>();
        try {
            while (result.next()) {
                User user = new User();
                user.setUserID(result.getString("userID"));
                user.setPasswordHash(result.getString("passwordHash"));
                user.setAccountType(result.getString("accountType"));
                user.setOrganisationID(result.getString("orgID"));
                user.setName(result.getString("name"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }
    public List<Organisation> getOrgInformation(String buyOrgID) {
        ResultSet result = null;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM organisational_unit_information WHERE orgID='"+buyOrgID+"';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<Organisation> organisationList = new ArrayList<>();
        try {
            while (result.next()) {
                Organisation organisation = new Organisation();
                organisation.setOrgID(result.getString("orgID"));
                organisation.setOrgName(result.getString("orgName"));
                organisation.setCredits(result.getBigDecimal("credits"));
                organisationList.add(organisation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organisationList;
    }

    public List<Inventory> getInventoryInformation(ResultSet result) throws SQLException {
        List<Inventory> inventoryList = new ArrayList<>();
        while (result.next()) {
            Inventory inventory = new Inventory();
            inventory.setAssetID(result.getString("assetID"));
            inventory.setAssetName(result.getString("assetName"));
            inventory.setOrgID(result.getString("orgID"));
            inventory.setQuantity(result.getInt("quantity"));
            inventoryList.add(inventory);
        }
        return inventoryList;
    }

        public void run(){
            List<Order> buy = null;
            List<SellOrder> sell = null;
            try {
            buy = getAllActiveBuyOrders();
            sell = getAllActiveSellOrders();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            for (Order buyOrder : buy){
            String buyID = buyOrder.getOrderID();
            String buyUserID = buyOrder.getUserID();
            String buyAssetName = buyOrder.getAssetName();
            int buyQuantity = buyOrder.getQuantity();
            BigDecimal buyPrice = buyOrder.getPrice();

            User buyUser = getUserInformation(buyID, "buy").get(0);
            String buyOrgID = buyUser.getOrganisationID();

            Organisation buyOrg = getOrgInformation(buyOrgID).get(0);
            BigDecimal buyOrgCredits = buyOrg.getCredits();

            for (SellOrder sellOrder : sell){
                String sellID = sellOrder.getOrderID();
                String sellUserID = sellOrder.getUserID();
                String sellAssetID = sellOrder.getAssetID();
                String sellAssetName = sellOrder.getAssetName();
                int sellQuantity = sellOrder.getQuantity();
                BigDecimal sellPrice = sellOrder.getPrice();

                User sellUser = getUserInformation(sellID, "sell").get(0);
                String sellOrgID = sellUser.getOrganisationID();


                // compare buy and sell price
                int comparePrice = buyPrice.compareTo(sellPrice);

//                if (buyAssetName.equals(sellAssetName) && buyQuantity == sellQuantity && !buyOrgID.equals(sellOrgID) && (comparePrice == -1 || comparePrice == 0)){
//
//                    // calculate purchase price
//                    BigDecimal pricePerUnit = buyPrice;
//                    BigDecimal totalPurchasePrice = pricePerUnit.multiply(new BigDecimal(buyQuantity));
//
//                    // check buy organisation has enough credits
//                    BigDecimal updatedBuyOrgCredits = buyOrgCredits.subtract(totalPurchasePrice);
//                    if(updatedBuyOrgCredits.compareTo(new BigDecimal(0)) < 0){
//                        //if not enough, continue to next iteration
//                        continue;
//                    }
//
//                    // check sell organisation has enough quantity
//                    if(inventoryHandler.getAssetQuantity(sellAssetID) < 0){
//                        //not enough assets
//                        continue;
//                    }
//
//                    // if we get to here, all conditions are met and we can process order
//
//                    //get time of purchase
//                    Timestamp reconcileDate = new Timestamp(System.currentTimeMillis());
//
//                    client.writeToServer("DELETE FROM active_buy_orders WHERE buyID='"+ buyID +"';", TableObject.DELETE);
//                    client.writeToServer("INSERT INTO buy_order_history VALUES('"+ buyID +"', '"+ buyUserID +"', '"+ sellAssetID +"', '"+ buyQuantity +"', '"+ pricePerUnit +"', '"+ reconcileDate +"') ;", TableObject.BUY_HISTORY);
//                    inventoryHandler.updateAssetQuantity(sellAssetID, buyQuantity);
//                    organisationHandler.updateOrganisationCredits(buyOrgID, updatedBuyOrgCredits);
//
//                    client.writeToServer("DELETE FROM active_sell_orders WHERE sellID='"+ sellID +"';", TableObject.DELETE);
//                    client.writeToServer("INSERT INTO sell_order_history VALUES('"+ sellID +"', '"+ sellUserID +"', '"+ sellAssetID +"', '"+ sellQuantity +"', '"+ pricePerUnit +"', '"+ reconcileDate +"') ;", TableObject.SELL_HISTORY);
//                    inventoryHandler.updateAssetQuantity(sellAssetID, sellQuantity);
//                    organisationHandler.updateOrganisationCredits(buyOrgID, updatedBuyOrgCredits);
//                }
            }
        }
    }

}
