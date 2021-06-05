package marketplace.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.*;
import marketplace.Server.*;
import marketplace.Client.Client;

import java.sql.SQLException;
import java.util.Properties;

public class ServerTest {
    private static MariaDBDataSource newInstance;

    @BeforeAll
    static void createDBInstance() throws SQLException {
        newInstance = MariaDBDataSource.getInstance();
    }

    @Test
    public void connectToDataBase() throws SQLException {
        assertNotNull(newInstance.getConnection());
    }

    @Test
    public void getConfigInformation(){
        Properties props = ServerHandler.loadServerConfig();
        assertNotNull(props);
    }

    @Test
    public void acceptSingleClient() {

        ServerHandler server = new ServerHandler();
        Client client = new Client();

        /// NEED TO MAKE THREADED
        assertEquals("Socket[addr=/172.19.6.157,port=64477,localport=6000]", server.newClientConnection());
    }

    @Test
    public void closeDataBaseConnection(){
        assertTrue(newInstance.Close());
    }

}
