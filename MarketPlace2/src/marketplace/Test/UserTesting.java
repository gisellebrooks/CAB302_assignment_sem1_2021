package marketplace.Test;

import marketplace.Client.Client;
import marketplace.Handlers.InventoryHandler;
import marketplace.Handlers.OrderHandler;
import marketplace.Handlers.OrganisationHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Server.MariaDBDataSource;
import marketplace.Server.ServerHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class UserTesting {
    private static MariaDBDataSource newInstance;

    @BeforeAll
    static void createDBInstance() throws SQLException {
        newInstance = MariaDBDataSource.getInstance();
        Properties props = ServerHandler.loadServerConfig();


        ServerHandler server = new ServerHandler();
//        MainGUIHandler mainGUIHandler = new MainGUIHandler();

//        System.out.println("good");

        Client client = new Client();

        try {
            client.connect();

        } catch (IOException e) {
            e.printStackTrace();
        }

        UserHandler userHandler = new UserHandler(client);
        OrderHandler orderHandler = new OrderHandler(client);
        OrganisationHandler organisationHandler = new OrganisationHandler(client);
        InventoryHandler inventoryHandler = new InventoryHandler(client);


    }

    @Test
    public void createValidUser() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // terry as admin

        // Alice as user
    }

    @Test
    public void createUserInvalidNames() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // empty input
    }

    @Test
    public void checkUserExist() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // add a new user and then try seeing if they exist
        // see if a user doesn't exist correctly
    }

    @Test
    public void changeUserPasswordValid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // strong password
        // correct previous password and confirms
    }

    @Test
    public void changeUserPasswordInvalid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // weak password
        // invalid previous password
        // not repeated password for confirm
    }

    @Test
    public void getNewUserID() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // check if correct with users and without
    }

    @Test
    public void loginValid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // valid username and password
    }

    @Test
    public void logininValid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // incorrect username, correct username wrong password, empty details
    }

    @Test
    public void createUserThenLoginValid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // valid details and login
    }

    @Test
    public void createUserThenLoginInvalid() throws SQLException {
        //assertNotNull(newInstance.getConnection());

        // valid sign up then invalid login, etc
    }
}
