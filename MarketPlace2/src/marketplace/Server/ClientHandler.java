package marketplace.Server;

import marketplace.Objects.*;
import marketplace.TableObject;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream inputFromClient;
    private ObjectOutputStream outputToClient;
    private MariaDBDataSource pool;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler (Socket socket, MariaDBDataSource pool) throws IOException {
        this.socket = socket;
        this.pool = pool;
    }

    public void run(){
        try {
            ResultSet result;
            //inputFromClient = new ObjectInputStream(socket.getInputStream());
            outputToClient = new ObjectOutputStream(socket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true)
            {
                if (!socket.isClosed())
                {
                    String line = in.readLine();
                    String[] parts = line.split("-");
                    String query = parts[0];
                    TableObject type = TableObject.valueOf(parts[1]);

                    System.out.println(query);
                    System.out.println(type);

                    result = pool.getResult( query);

                    switch(type){
                        case USER:
                            User user = new User();
                            while (result.next()) {
                                user.setUserID(result.getString("userID"));
                                user.setPasswordHash(result.getString("passwordHash"));
                                user.setAccountType(result.getString("accountType"));
                                user.setOrganisationID(result.getString("orgID"));
                                user.setName(result.getString("name"));
                            }
                            outputToClient.writeObject(user);

                            break;

                        case ORGANISATION:

                            Organisation organisation = new Organisation();
                            while (result.next()) {
                                organisation.setOrgID(result.getString("orgID"));
                                organisation.setOrgName(result.getString("orgName"));
                                organisation.setCredits(result.getBigDecimal("credits"));
                            }
                            outputToClient.writeObject(organisation);
                            break;

                        case BUY_ORDER:

                            List<Order> objectList = new ArrayList<>();
                            while (result.next()){
                                Order buy = new Order();
                                buy.setOrderID(result.getString("buyID"));
                                buy.setUserID(result.getString("userID"));
                                buy.setAssetName(result.getString("assetName"));
                                buy.setQuantity(result.getInt("quantity"));
                                buy.setPrice(result.getBigDecimal("priceUpper"));
                                buy.setOrderDate(result.getTimestamp("orderDate"));
                                objectList.add(buy);
                            }
                            outputToClient.writeObject(objectList);
                            outputToClient.flush();
                            break;

                        case SELL_ORDER:
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
                            outputToClient.writeObject(sellList);
                            outputToClient.flush();
                            break;

                        case BUY_HISTORY:
                            List<Order> buyHistory = new ArrayList<>();
                            while (result.next()){
                                SellOrder oldBuy = new SellOrder();
                                oldBuy.setOrderID(result.getString("buyID"));
                                oldBuy.setUserID(result.getString("userID"));
                                oldBuy.setAssetID(result.getString("assetID"));
                                oldBuy.setQuantity(result.getInt("quantity"));
                                oldBuy.setPrice(result.getBigDecimal("reconcilePrice"));
                                oldBuy.setOrderDate(result.getTimestamp("reconcileDate"));
                                buyHistory.add(oldBuy);
                            }
                            outputToClient.writeObject(buyHistory);
                            outputToClient.flush();
                            break;

                        case SELL_HISTORY:
                            List<SellOrder> sellHistory = new ArrayList<>();
                            while (result.next()){
                                SellOrder oldSell = new SellOrder();
                                oldSell.setOrderID(result.getString("sellID"));
                                oldSell.setUserID(result.getString("userID"));
                                oldSell.setAssetID(result.getString("assetID"));
                                oldSell.setQuantity(result.getInt("quantity"));
                                oldSell.setPrice(result.getBigDecimal("reconcilePrice"));
                                oldSell.setOrderDate(result.getTimestamp("reconcileDate"));
                                sellHistory.add(oldSell);
                            }
                            outputToClient.writeObject(sellHistory);
                            outputToClient.flush();
                            break;

                        case INVENTORY:
                            List<Inventory> inventoryList = new ArrayList<>();

                            while (result.next()) {
                                Inventory inventory = new Inventory();
                                inventory.setAssetID(result.getString("assetID"));
                                inventory.setAssetName(result.getString("assetName"));
                                inventory.setOrgID(result.getString("orgID"));
                                inventory.setQuantity(result.getInt("quantity"));
                                inventoryList.add(inventory);
                            }
                            outputToClient.writeObject(inventoryList);
                            break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}