package marketplace.Test;

import marketplace.Client.Client;
import marketplace.Handlers.PasswordHandler;
import marketplace.Server.ServerHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordHandlerTest {
    private static Client client;
    private static PasswordHandler passwordHandler;

    @BeforeAll
    static void startClientServer() {

        Thread thread = new ServerHandler();
        thread.start();
        // We sleep this thread so that the server handler has time to finish setting up before we continue
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void startClientConnection(){
        client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        passwordHandler = new PasswordHandler();
    }

    @Test
    public void testPasswordHandlerIsStrong() {

        String strongPassword = "password123!@#ABCzxc";

        assertTrue(passwordHandler.IsPasswordStrong(strongPassword));
    }

    @Test
    public void testPasswordHandlerIsWeak() {

        String weakPassword = "password123";

        assertFalse(passwordHandler.IsPasswordStrong(weakPassword));
    }

    @Test
    public void testPasswordHandlerGeneratePassword() {

        String newPassword = passwordHandler.generatePassword();

        assertTrue(passwordHandler.IsPasswordStrong(newPassword));
    }

    @Test
    public void testPasswordHandlerIntoHashSame() throws Exception {

        String firstPassword = passwordHandler.generatePassword();
        String passwordHash = passwordHandler.IntoHash(firstPassword);

        assertEquals(passwordHash, passwordHandler.IntoHash(firstPassword));
    }

    @Test
    public void testPasswordHandlerIntoHashDifferent() throws Exception {

        String firstPassword = passwordHandler.generatePassword();
        String passwordHash = passwordHandler.IntoHash(firstPassword);

        assertNotEquals(passwordHash, passwordHandler.IntoHash("231132dsdsadsa!@##!@DAS"));
    }
}
