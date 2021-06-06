package marketplace.Client;

import marketplace.TableObject;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Client{
    private static String rootDir;
    private static Properties props;
    private Socket socket;
    private String address;
    private int port;
    private PrintWriter output;
    private ObjectInputStream input;


    public Client() {

    }

    private static Properties loadServerConfig() {
        rootDir = System.getProperty("user.dir");
        props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("MarketPlace2/src/marketplace/Util/server.props");
            props.load(in);
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }

    public void connect() throws IOException {

        try {
            Properties props = loadServerConfig();
            address = props.getProperty("app.hostname");
            port = Integer.parseInt(props.getProperty("app.port"));
            socket = new Socket(address, port);

            try {
                input = new ObjectInputStream(socket.getInputStream());

            } catch (IOException e) {
                e.printStackTrace();
            }
            output = getOutputStream();

        } catch (IOException e) {
            this.input = null;
            this.output = null;
            this.socket = null;
            System.err.println(e);
            e.printStackTrace();
        }
    }

    private PrintWriter getOutputStream() throws IOException {
        return new PrintWriter(this.socket.getOutputStream(), true);
    }

    private BufferedReader getInputStream() throws IOException {
        return new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
    }

    /**
     * Attempt to close the connection, including input/output streams.
     */
    public void disconnect() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch (IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Write to the connection socket
     */
    public void writeToServer(String query, TableObject type) throws IOException {

        output.println(type);
        output.println(query);
        output.flush();
    }


    public List readListFromServer() throws IOException, ClassNotFoundException {
        List objectList = (List) input.readObject();
        return objectList;
    }

}