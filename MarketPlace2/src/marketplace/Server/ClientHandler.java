package marketplace.Server;

import marketplace.Objects.*;
import marketplace.TableObject;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler extends Thread {

    private Socket socket;
    private ObjectInputStream inputFromClient;
    private ObjectOutputStream outputToClient;
    private MariaDBDataSource pool;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler (Socket socket, MariaDBDataSource pool) throws IOException, SQLException {
        this.socket = socket;
        this.pool = pool;
        // ServerHandler.loadMockData(this.pool);
    }

    public void run(){
        try {

            ResultSet result;
            //inputFromClient = new ObjectInputStream(socket.getInputStream());
            outputToClient = new ObjectOutputStream(socket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // = new PrintWriter(socket.getOutputStream(), true);

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
                                organisation.setCredits(result.getDouble("credits"));
                            }
                            outputToClient.writeObject(organisation);
                            break;

                        case BUY_ORDER:
                            System.out.println("gut to buy switch statement");

                            List<BuyOrder> objectList = new ArrayList<>();
                            while (result.next()){
                                BuyOrder buy = new BuyOrder();
                                buy.setBuyId(result.getString("buyID"));
                                buy.setUserID(result.getString("userID"));
                                buy.setAssetName(result.getString("assetName"));
                                buy.setQuantity(result.getInt("quantity"));
                                buy.setPriceUpper(result.getBigDecimal("priceUpper"));
                                buy.setOrderDate(result.getTimestamp("orderDate"));
                                objectList.add(buy);
                            }
                            outputToClient.writeObject(objectList);
                            break;

                        case SELL_ORDER:
                            SellOrder sell = new SellOrder();
                            while (result.next()){
                                sell.setSellId(result.getString("sellID"));
                                sell.setUserID(result.getString("userID"));
                                sell.setAssetName(result.getString("assetName"));
                                sell.setQuantity(result.getInt("quantity"));
                                sell.setPriceLower(result.getBigDecimal("priceLower"));
                                sell.setOrderDate(result.getTimestamp("orderDate"));
                            }
                            break;

                        case BUY_HISTORY:
                            break;

                        case SELL_HISTORY:
                            break;

                        case INVENTORY:
                            Inventory inventory = new Inventory();
                            while (result.next()) {
                                inventory.setAssetID(result.getString("assetID"));
                                inventory.setAssetName(result.getString("assetName"));
                                inventory.setOrgID(result.getString("orgID"));
                                inventory.setQuantity(result.getInt("quantity"));
                            }
                            outputToClient.writeObject(inventory);
                            break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}