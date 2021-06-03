package marketplace.Server;

import marketplace.Objects.*;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReconcileOrders {
    private final MariaDBDataSource pool;

    public ReconcileOrders(MariaDBDataSource pool) {
        this.pool = pool;
    }

    public List<Order> getAllActiveBuyOrders() throws SQLException {
        ResultSet result;
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
        ResultSet result;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM ACTIVE_SELL_ORDERS;")) {
                result = statement.executeQuery();
            }
        }
        List<SellOrder> sellList = new ArrayList<>();
        while (result.next()) {
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

    public User getUserInformation(String orderID, String type) {

        ResultSet result = null;
        if (type.equals("buy")) {
            try (Connection conn = pool.getConnection()) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "SELECT u.* FROM active_buy_orders a RIGHT JOIN user_information u ON a.userID=u.userID WHERE a." + type + "ID= '" + orderID + "';")) {
                    result = statement.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (type.equals("sell")) {
            try (Connection conn = pool.getConnection()) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "SELECT u.* FROM active_sell_orders a RIGHT JOIN user_information u ON a.userID=u.userID WHERE a." + type + "ID= '" + orderID + "';")) {
                    result = statement.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        List<User> userList = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    User user = new User();
                    user.setUserID(result.getString("userID"));
                    user.setPasswordHash(result.getString("passwordHash"));
                    user.setAccountType(result.getString("accountType"));
                    user.setOrganisationID(result.getString("orgID"));
                    user.setName(result.getString("name"));
                    userList.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList.get(0);
    }

    public String newAssetID() {
        String lastID = null;
        ResultSet result = null;

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT assetID FROM INVENTORY ORDER BY cast(assetID as SIGNED) DESC LIMIT 1;")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (result != null) {
                if (result.next()) {
                    lastID = result.getString("assetID");
                } else {
                    lastID = "asset0";
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        String[] part = new String[0];
        if (lastID != null) {
            part = lastID.split("(?<=\\D)(?=\\d)");
        }
        int IDNumber = Integer.parseInt(part[1]) + 1;
        return ("asset" + IDNumber);
    }

    public String newOrderHistoryID(String orderType) {
        String lastID = null;
        ResultSet result = null;
        String[] part;
        int IDNumber;

        if (orderType.equals("oldBuy")) {
            try (Connection conn = pool.getConnection()) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "SELECT oldBuyID FROM BUY_ORDER_HISTORY ORDER BY cast(oldBuyID as SIGNED) DESC LIMIT 1;")) {
                    result = statement.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (result == null) {
                    lastID = "oldBuy0";
                }
                else {
                    while (result.next()) {
                        lastID = result.getString("oldBuyID");
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (orderType.equals("oldSell")) {
            try (Connection conn = pool.getConnection()) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "SELECT oldSellID FROM SELL_ORDER_HISTORY ORDER BY cast(oldSellID as SIGNED) DESC LIMIT 1;")) {
                    result = statement.executeQuery();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (result == null) {
                    lastID = "oldSell0";
                }
                else {
                    while (result.next()) {
                        lastID = result.getString("oldSellID");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        part = new String[0];
        if (lastID != null) {
            part = lastID.split("(?<=\\D)(?=\\d)");
            IDNumber = Integer.parseInt(part[1]) + 1;
        }
        else {
            IDNumber = 1;
        }

        return (orderType + IDNumber);
    }

    public List<Organisation> getOrgInformation(String orderID, String orderType) {
        String orgID;
        ResultSet result = null;
        orgID = getUserInformation(orderID, orderType).getOrganisationID();
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement(
                    "SELECT * FROM organisational_unit_information WHERE orgID='" + orgID + "';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Organisation> organisationList = new ArrayList<>();
        try {
            if (result != null) {
                while (result.next()) {
                    Organisation organisation = new Organisation();
                    organisation.setOrgID(result.getString("orgID"));
                    organisation.setOrgName(result.getString("orgName"));
                    organisation.setCredits(result.getBigDecimal("credits"));
                    organisationList.add(organisation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return organisationList;
    }

    public Inventory getInventoryInformation(String assetID) throws SQLException {
        ResultSet result = null;
        Inventory inventory = new Inventory();

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM INVENTORY WHERE assetID= '" + assetID + "';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (result != null && result.next()) {
            inventory.setAssetID(result.getString("assetID"));
            inventory.setAssetName(result.getString("assetName"));
            inventory.setOrgID(result.getString("orgID"));
            inventory.setQuantity(result.getInt("quantity"));
        }
        return inventory;
    }

    public boolean checkIfAssetExists(String assetName, String orgID){
        ResultSet result = null;
        boolean assetExists = false;

        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM INVENTORY WHERE assetName= '" +
                    assetName + "' AND orgID= '" + orgID + "';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (result != null && result.next()) {
                 if (result.getInt("quantity") > 0){
                     assetExists = true;
                 }
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return assetExists;
    }

    public void updateBuyOrder(String buyID, int remainderBuyQuantity){
        try (Connection conn = pool.getConnection()) {
            if (remainderBuyQuantity == 0) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "DELETE FROM active_buy_orders WHERE buyID='" + buyID + "';")) {
                    statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = conn.prepareStatement(
                        "UPDATE active_buy_orders SET quantity= '" + remainderBuyQuantity + "' WHERE buyID='"
                                + buyID + "';")) {
                    statement.executeUpdate();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateSellOrder(String sellID, int remainderSellQuantity){
        try (Connection conn = pool.getConnection()) {
            if (remainderSellQuantity == 0) {
                try (PreparedStatement statement = conn.prepareStatement(
                        "DELETE FROM active_sell_orders WHERE sellID='" + sellID + "';")) {
                    statement.executeUpdate();
                }
            } else {
                try (PreparedStatement statement = conn.prepareStatement(
                        "UPDATE active_sell_orders SET quantity= '" + remainderSellQuantity +
                                "' WHERE sellID='" + sellID + "';")) {
                    statement.executeUpdate();
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToBuyHistory(String buyID, String buyUserID, String sellAssetID, int quantitySold,
                                BigDecimal sellPrice, Timestamp reconcileDate) {

        try (Connection conn = pool.getConnection()) {
            String oldBuyID = newOrderHistoryID("oldBuy");
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO buy_order_history VALUES('" +
                    oldBuyID + "', '" + buyID + "', '" + buyUserID + "', '" + sellAssetID + "', '" + quantitySold +
                    "', '" + sellPrice + "', '" + reconcileDate + "') ;")) {

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addToSellHistory(String sellID, String sellUserID, String sellAssetID, int quantitySold,
                                  BigDecimal sellPrice, Timestamp reconcileDate) {

        try (Connection conn = pool.getConnection()) {
            String oldSellID = newOrderHistoryID("oldSell");
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO sell_order_history VALUES('" +
                    oldSellID + "', '" + sellID + "', '" + sellUserID + "', '" + sellAssetID + "', '" + quantitySold +
                    "', '" + sellPrice + "', '" + reconcileDate + "') ;"))
            {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeAssetFromInventory(String sellAssetID){
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("DELETE FROM INVENTORY  WHERE assetID= '" +
                    sellAssetID + "';")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addNewAssetToInventory(String assetName, String orgID, int quantitySold) {
        String assetID = newAssetID();
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("INSERT INTO INVENTORY VALUES('" + assetID + "', " + assetName + "', " + orgID + "', " + quantitySold + "';")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void updateBuyInventory(String assetName, String orgID, int quantitySold){
        ResultSet result = null;
        int assetQuantity = 0;
        int newBuyOrgQuantity;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("SELECT * FROM INVENTORY WHERE assetName= '" +
                    assetName + "' AND orgID= '" + orgID + "';")) {
                result = statement.executeQuery();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (result != null && result.next()) {
                assetQuantity = result.getInt("quantity");

            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        newBuyOrgQuantity = assetQuantity + quantitySold;
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE INVENTORY SET quantity= '" +
                    newBuyOrgQuantity + "' WHERE assetName= '" + assetName + "' AND orgID= '" + orgID + "';")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateSellInventory (String sellAssetID, int newSellOrgQuantity){
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE INVENTORY SET quantity= '" +
                    newSellOrgQuantity + "' WHERE assetID= '" + sellAssetID + "';")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateOrgCredits(BigDecimal updatedCredits, String orgID) {
        try (Connection conn = pool.getConnection()) {
            try (PreparedStatement statement = conn.prepareStatement("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET credits= '" + updatedCredits + "' WHERE orgID= '" + orgID + "';")) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void run() throws SQLException {

        List<Order> buyOrders = null;
        String buyID;
        String buyUserID;
        String buyOrgID;
        String buyAssetName;
        int buyQuantity;
        int remainderBuyQuantity = 0;
        BigDecimal buyOrgCredits;
        BigDecimal buyPrice;
        Organisation buyOrgInformation;

        List<SellOrder> sellOrders = null;
        String sellID;
        String sellUserID;
        String sellOrgID;
        String sellAssetID;
        String sellAssetName;
        int sellQuantity;
        int remainderSellQuantity = 0;
        BigDecimal sellOrgCredits;
        BigDecimal sellPrice;
        BigDecimal sellPriceUpper;
        Organisation sellOrgInformation;

        BigDecimal totalPurchasePrice;
        int quantitySold;

        try {
            buyOrders = getAllActiveBuyOrders();
            sellOrders = getAllActiveSellOrders();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (buyOrders != null) {
            for (Order buyOrder : buyOrders) {
                buyID = buyOrder.getOrderID();
                buyUserID = buyOrder.getUserID();
                buyAssetName = buyOrder.getAssetName();
                buyPrice = buyOrder.getPrice();

                buyOrgInformation = getOrgInformation(buyID, "buy").get(0);
                buyOrgCredits = buyOrgInformation.getCredits();
                buyOrgID = buyOrgInformation.getOrgID();

                if (sellOrders != null) {
                    for (SellOrder sellOrder : sellOrders) {
                        sellID = sellOrder.getOrderID();
                        sellUserID = sellOrder.getUserID();
                        sellAssetID = sellOrder.getAssetID();
                        sellAssetName = sellOrder.getAssetName();

                        sellOrgInformation = getOrgInformation(sellID, "sell").get(0);
                        sellOrgCredits = sellOrgInformation.getCredits();
                        sellOrgID = sellOrgInformation.getOrgID();

                        if (buyAssetName.equals(sellAssetName) && !buyOrgID.equals(sellOrgID)) {

                            // calculate range for sell price
                            sellPrice = sellOrder.getPrice();
                            sellPriceUpper = sellPrice.multiply(BigDecimal.valueOf(1.1));

                            // check if the buy price is within the range of the sell price
                            if (sellPrice.compareTo(buyPrice) <= 0 && sellPriceUpper.compareTo(buyPrice) >= 0) {
                                buyQuantity = buyOrder.getQuantity();
                                sellQuantity = getInventoryInformation(sellAssetID).getQuantity();

                                if (buyQuantity < sellQuantity) {
                                    quantitySold = buyQuantity;
                                    remainderSellQuantity = sellQuantity - buyQuantity;

                                } else if (buyQuantity > sellQuantity) {
                                    quantitySold = sellQuantity;
                                    remainderBuyQuantity = buyQuantity - sellQuantity;

                                } else {
                                    quantitySold = buyQuantity;
                                    remainderBuyQuantity = 0;
                                }

                                totalPurchasePrice = sellPrice.multiply(new BigDecimal(quantitySold));

                                // check buy organisation has enough credits
                                if (buyOrgCredits.compareTo(totalPurchasePrice) == -1) {
                                    //if not enough, continue to next iteration
                                    continue;
                                }
                                BigDecimal updatedBuyOrgCredits = buyOrgCredits.subtract(totalPurchasePrice);
                                BigDecimal updatedSellOrgCredits = sellOrgCredits.add(totalPurchasePrice);

                                //get time of purchase
                                Timestamp reconcileDate = new Timestamp(System.currentTimeMillis());

                                // UPDATE BUY ORDER
                                updateBuyOrder(buyID, remainderBuyQuantity);

                                // UPDATE SELL ORDER
                                updateSellOrder(sellID, remainderSellQuantity);

                                // ADD BUY ORDER TO HISTORY
                                addToBuyHistory(buyID, buyUserID, sellAssetID, quantitySold, sellPrice, reconcileDate);

                                // ADD SELL ORDER TO HISTORY
                                addToSellHistory(sellID, sellUserID, sellAssetID, quantitySold, sellPrice, reconcileDate);

                                // UPDATE BUY ORG INVENTORY INFORMATION
                                boolean buyOrgHasAsset = checkIfAssetExists(buyAssetName, buyOrgID);
                                if (buyOrgHasAsset) {
                                    addNewAssetToInventory(buyAssetName, buyOrgID, quantitySold);

                                } else {
                                    updateBuyInventory(buyAssetName, buyOrgID, quantitySold);
                                }

                                // UPDATE SELL ORG INVENTORY INFORMATION
                                int newSellOrgQuantity = sellQuantity - quantitySold;

                                if (newSellOrgQuantity > 0) {
                                    updateSellInventory(sellAssetID, newSellOrgQuantity);
                                } else {
                                    removeAssetFromInventory(sellAssetID);
                                }

                                // UPDATE BUY ORG ORGANISATION CREDITS
                                updateOrgCredits(updatedBuyOrgCredits, buyOrgID);

                                // UPDATE SELL ORG ORGANISATION CREDITS
                                updateOrgCredits(updatedSellOrgCredits, sellOrgID);

                            }
                        }
                    }
                }
            }
        }
    }
}
