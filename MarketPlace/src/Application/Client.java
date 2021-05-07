package Application;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client {

    /**
     * Create a new client socket connection for the user
     * setup input and output streams
     *
     * Call functionality for login and register screen? or make it here and call it in GUI login/ register
     *
     *
     **/
    private Properties props;
    private Socket socket;
    DataInputStream input;
    DataOutputStream output;
    private AtomicBoolean openConn = new AtomicBoolean(true);

    public Client (Properties props){
        this.props = props;
    }

    public void createConnection() {

        String hostname = props.getProperty("app.hostname");
        int port = Integer.parseInt(props.getProperty("app.port"));

        try {
            socket = new Socket(hostname, port);

            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());

            do {
                // do stuff while connected
            }while(!openConn.get());


        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            closeConnection();
        }

    }
    public void closeConnection(){
        try{
            socket.close();
            input.close();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
