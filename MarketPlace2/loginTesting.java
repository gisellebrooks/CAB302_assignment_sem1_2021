import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class loginTesting {

    @BeforeEach
    /*
     * Create a fresh database
     */
    public void create() throws Exception {
        // statement.executeUpdate("DROP DATABASE marketplace_server");
        // addUser("name1", "password12#A", "user", "org1");
        // addOrganisation(1, "org1", 200, 10);
        // addOrganisation(1, "org2", 200, 10);
        LoginGUI gui = new LoginGUI();
    }



    @Test
    /* typical case - test if user was removed */
    public void checkinvalidLogin() {
        // assertEquals(1, SELECT * FROM [Users] WHERE Username = "name1");
        // removeUser("name1", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "name1");
    }

    @Test
    /* typical case - test if user was removed */
    public void checkvalidLogin() {
        // assertEquals(1, SELECT * FROM [Users] WHERE Username = "name1");
        // removeUser("name1", "password12#A", "user", "org1");
        // assertEquals(0, SELECT * FROM [Users] WHERE Username = "name1");
    }
}
