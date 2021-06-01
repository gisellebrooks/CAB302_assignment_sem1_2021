package marketplace.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.Before;
import org.junit.jupiter.api.*;
import marketplace.Server.*;
import marketplace.Client.Client;

import java.net.Socket;
import java.sql.SQLException;
import java.util.Properties;

public class ServerTest {
    private static MariaDBDataSource newInstance;
    private static Properties props;

    @BeforeAll
    static void createDBInstance() throws SQLException {
        newInstance = MariaDBDataSource.getInstance();
    }

    @Test
    @BeforeAll
    static void getConfigInformation(){
        props = ServerHandler.loadServerConfig();
        assertNotNull(props);
    }

    @Test
    public void connectToDataBase() throws SQLException {
        assertNotNull(newInstance.getConnection());
    }


//    @BeforeAll
//    public void initServerSocket(){
//        Thread thread = new ServerHandler(props);
//        thread.start();
//
//    }

//    @Test
//    public void acceptSingleClient() {
//        Client client = new Client();
//        assertNotNull(client);
//    }
//
//    @Test
//    public void acceptMultipleClients() {
//
//        Boolean clientsAccepted = false;
//        Client client1 = new Client();
//        System.out.println(ServerHandler.newClientConnection());
//        Client client2 = new Client();
//        System.out.println(ServerHandler.newClientConnection());
//        Client client3 = new Client();
//        System.out.println(ServerHandler.newClientConnection());
//
//        //assertTrue(clientsAccepted);
//
//    }


    @Test
    public void closeDataBaseConnection(){
        assertTrue(newInstance.Close());
    }

}
