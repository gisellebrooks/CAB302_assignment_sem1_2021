package marketplace.Server;

import marketplace.User;

import java.io.*;
import java.net.Socket;
import java.sql.ResultSet;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private Socket socket;
    private DataInputStream inputFromClient;
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
            inputFromClient = new DataInputStream(socket.getInputStream());
            outputToClient = new ObjectOutputStream(socket.getOutputStream());

            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);


            while (true)
            {
                if (!socket.isClosed())
                {
                    String line = in.readLine();

                    result = pool.getResult(line);

                    User user = new User();
                    while (result.next()) {

                        user.setUsername(result.getString("userID"));
                        user.setPasswordHash(result.getString("password"));
                        user.setAccountType(result.getString("accountType"));
                        user.setOrganisation(result.getString("orgID"));
                        user.setName(result.getString("name"));

                    }
                    outputToClient.writeObject(user);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}