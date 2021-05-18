package marketplace.Client;

import marketplace.User;

import java.io.*;
import java.net.Socket;
import java.util.Properties;

/**
 * Client
 * Will connect to the server using a Socket. Provides an interface to
 * to that socket to read and write to the socket.
 *
 * @author Cory Gross
 * @version October 22, 2012
 */
public class Client {
    private Socket socket;
    private String address;
    private int port;
    private PrintWriter output;
    private BufferedReader input;
    private ObjectInputStream inp;

    public Client(){

    }

    private static Properties loadServerConfig() {
        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("server.props");
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
            address = props.getProperty("app.address");
            port = Integer.parseInt(props.getProperty("app.port"));

            socket = new Socket(address, port);

            //this.input = getInputStream();
            inp = new ObjectInputStream(socket.getInputStream());
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

    /** Attempt to close the connection, including input/output streams. */
    public void disconnect() {
        try {
            socket.close();
            input.close();
            output.close();
        } catch(IOException e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /** Write to the connection socket */
    public void writeToServer(String query) {
        output.println(query);
        output.flush();
    }

    /** Attempt to read from the connection socket. */
    public User readFromServer() throws IOException, ClassNotFoundException {
        User user = (User)inp.readObject();
        return user;
    }



//    public void sendChatMessage(String msg) {
//        write(username + ": " + msg);
//    }
//
//    public void sendQuitMessage() {
//        write("QUIT");
//    }

}