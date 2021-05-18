package marketplace.Server;

import marketplace.TableObject;
import marketplace.User;
import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;

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
                            break;

                        case SELL_ORDER:
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