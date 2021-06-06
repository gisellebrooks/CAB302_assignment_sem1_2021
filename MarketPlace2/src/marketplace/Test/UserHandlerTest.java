package marketplace.Test;

import marketplace.Client.Client;
import marketplace.Handlers.PasswordHandler;
import marketplace.Handlers.UserHandler;
import marketplace.Objects.User;
import marketplace.Server.ServerHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserHandlerTest {
    private static Client client;
    private static UserHandler userHandler;
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
        userHandler = new UserHandler(client);

        passwordHandler = new PasswordHandler();
    }

    @Test
    public void testSearchUserInvalid() throws Exception {

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.searchUser("user32113232113");
        });
    }

    @Test
    public void testSearchUserValid() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        User user = userHandler.searchUser(newUserID);

        // Lists to hold values from the expected and actual user
        List<Object> expectedUser = new ArrayList<>();
        List<Object> actualUser = new ArrayList<>();

        expectedUser.add(newUserID);
        expectedUser.add(passwordHash);
        expectedUser.add(userType);
        expectedUser.add(orgID);
        expectedUser.add(name);

        actualUser.add(user.getUserID());
        actualUser.add(user.getPasswordHash());
        actualUser.add(user.getAccountType());
        actualUser.add(user.getOrganisationID());
        actualUser.add(user.getName());

        // Assert both lists are equal
        assertEquals(actualUser, expectedUser);
    }

    @Test
    public void testAddUserValid() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
    }

    @Test
    public void testAddUserInvalidPassword() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = "password123!@#asd";
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        });
    }

    @Test
    public void testAddUserInvalidUserType() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "user";
        String orgID = "org1";
        String name = "John Doe";

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        });
    }

    @Test
    public void testAddUserInvalidOrgID() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "user";
        String orgID = "org1231";
        String name = "John Doe";

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        });
    }

    @Test
    public void testAddUserInvalidNameShort() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "1";

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        });
    }

    @Test
    public void testAddUserInvalidNameLong() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        });
    }

    @Test
    public void testUpdateUserValid() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "ADMIN";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        User user = userHandler.searchUser(newUserID);
        user.setAccountType(changedValue);
        userHandler.updateUser(user);

        assertEquals(user.getAccountType(), changedValue);
    }

    @Test
    public void testUpdateUserInvalidUserID() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "user1";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);
        user.setUserID(changedValue);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserInvalidAccountType() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "special user";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);
        user.setAccountType(changedValue);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserInvalidOrgID() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "organisation 231";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);
        user.setOrganisationID(changedValue);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserInvalidNameShort() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "a";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);
        user.setName(changedValue);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUser(user);
        });
    }

    @Test
    public void testUpdateUserInvalidNameLong() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        String changedValue = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);
        user.setName(changedValue);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUser(user);
        });
    }

    @Test
    public void testChangeUserPasswordValid() throws Exception {

        String newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        String passwordHash = PasswordHandler.IntoHash(currentPassword);

        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);

        String newPassword = passwordHandler.generatePassword();

        userHandler.updateUserPassword(user, currentPassword, newPassword, newPassword);

        user = userHandler.searchUser(newUserID);

        assertEquals(user.getPasswordHash(), PasswordHandler.IntoHash(newPassword));
    }

    @Test
    public void testChangeUserPasswordInvalidWrongCurrentPassword() throws Exception {

        String newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        String passwordHash = PasswordHandler.IntoHash(currentPassword);

        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);

        String newPassword = passwordHandler.generatePassword();

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUserPassword(user, "123", newPassword, newPassword);
        });
    }

    @Test
    public void testChangeUserPasswordInvalidNotStrong() throws Exception {

        String newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        String passwordHash = PasswordHandler.IntoHash(currentPassword);

        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUserPassword(user, currentPassword, "123", "123");
        });
    }

    @Test
    public void testChangeUserPasswordInvalidSamePassword() throws Exception {

        String newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        String passwordHash = PasswordHandler.IntoHash(currentPassword);

        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUserPassword(user, currentPassword, currentPassword, currentPassword);
        });
    }

    @Test
    public void testChangeUserPasswordInvalidNotConfirmed() throws Exception {

        String newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        String passwordHash = PasswordHandler.IntoHash(currentPassword);

        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        User user = userHandler.searchUser(newUserID);

        String newPassword = passwordHandler.generatePassword();

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.updateUserPassword(user, currentPassword, newPassword, currentPassword);
        });
    }

    @Test
    public void testUserIDExistsValid() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        User user = userHandler.searchUser(newUserID);

        assertTrue(userHandler.userIDExists(newUserID));
    }

    @Test
    public void testUserIDExistsInvalid() throws Exception {

        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        User user = userHandler.searchUser(newUserID);

        Assertions.assertThrows(Exception.class, () -> {
            assertFalse(userHandler.userIDExists("user123312123312"));
        });
    }

    @Test
    public void testNewUserID() throws Exception {

        String newUserID = userHandler.newUserID();

        Assertions.assertThrows(Exception.class, () -> {
            userHandler.userIDExists(newUserID);
        });
    }

    @Test
    public void testAllValidUserTests() throws Exception {
        String newUserID = userHandler.newUserID();
        String passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        String userType = "USER";
        String orgID = "org1";
        String name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        User user;

        assertTrue(userHandler.userIDExists(newUserID));

        newUserID = userHandler.newUserID();

        String currentPassword = passwordHandler.generatePassword();
        passwordHash = PasswordHandler.IntoHash(currentPassword);

        userType = "USER";
        orgID = "org1";
        name = "John Doe";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);
        user = userHandler.searchUser(newUserID);

        String newPassword = passwordHandler.generatePassword();

        userHandler.updateUserPassword(user, currentPassword, newPassword, newPassword);

        user = userHandler.searchUser(newUserID);

        assertEquals(user.getPasswordHash(), PasswordHandler.IntoHash(newPassword));


        newUserID = userHandler.newUserID();
        passwordHash = PasswordHandler.IntoHash(passwordHandler.generatePassword());
        userType = "USER";
        orgID = "org1";
        name = "John Doe";

        String changedValue = "ADMIN";

        userHandler.addUser(newUserID, passwordHash,userType, orgID, name);

        user = userHandler.searchUser(newUserID);
        user.setAccountType(changedValue);
        userHandler.updateUser(user);

        assertEquals(user.getAccountType(), changedValue);
    }
}
