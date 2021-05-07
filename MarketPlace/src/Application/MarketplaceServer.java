package Application;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class MarketplaceServer {

    private Properties props;

    private DataOutputStream output;
    private DataInputStream input;
    private AtomicBoolean running = new AtomicBoolean(true);

    public MarketplaceServer(Properties props){
        this.props = props;

    }

    public void startServer() {

        int port = Integer.parseInt(props.getProperty("app.port"));

        try (ServerSocket server = new ServerSocket(port)) {

            try {
                Socket socket = server.accept();

                // Obtain input and output streams
                input = new DataInputStream(socket.getInputStream());
                output = new DataOutputStream(socket.getOutputStream());

                // Create new thread object
                Thread thread = new ThreadSocket(socket, input, output);
                thread.start();

            } catch (SocketTimeoutException ignored) {
            }


            // Close down the server
            //shutdown();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Requests the server to shut down
     */
    public void shutdown() {
        // Shut the server down
        running.set(false);
    }
}

class ThreadSocket extends Thread {
    final DataInputStream input;
    final DataOutputStream output;
    final Socket socket;


    // Constructor
    public ThreadSocket(Socket socket, DataInputStream input, DataOutputStream output) {
        this.socket = socket;
        this.input = input;
        this.output = output;
    }

    @Override
    public void run() {
        String received;
        String send;

        // do stuff idk what to put here yet

        try {
            // closing resources
            this.input.close();
            this.output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


