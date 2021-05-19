package marketplace.Server;

import marketplace.Objects.BuyOrder;
import marketplace.Objects.SellOrder;
import marketplace.TableObject;
import marketplace.Objects.User;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;

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
                                user.setUsername(result.getString("userID"));
                                user.setPasswordHash(result.getString("password"));
                                user.setAccountType(result.getString("accountType"));
                                user.setOrganisation(result.getString("orgID"));
                                user.setName(result.getString("name"));
                            }
                            outputToClient.writeObject(user);
                            break;
                        case ORGANISATION:
                            break;

                        case BUY_ORDER:
                            BuyOrder buy = new BuyOrder();
                            while (result.next()){
                                buy.setBuyId(result.getString("buyID"));
                                buy.setUserID(result.getString("userID"));
                                buy.setAssetName(result.getString("assetName"));
                                buy.setQuantity(result.getInt("quantity"));
                                buy.setPriceUpper(result.getBigDecimal("priceUpper"));
                                buy.setOrderDate(result.getTimestamp("orderDate"));
                            }
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
                            break;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}