package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;

public class UserHandler {
    private final Client client;

    public UserHandler(Client client){
        this.client = client;
    }

    public User getUserInformation(String userID){
        User result = null;
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER);
            result = (User) client.readFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addUser(String username, String passwordHash, String accountType, String organisationID, String name) {

        try {
            client.writeToServer("INSERT INTO USER_INFORMATION VALUES('" + username + "', '" + passwordHash + "', '" + accountType +
                    "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String username, String passwordHash, String accountType, String organisationID, String name) {

        try {
            client.writeToServer("D INTO USER_INFORMATION VALUES('" + username + "', '" + passwordHash + "', '" + accountType +
                    "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String username, String passwordHash, String accountType, String organisationID, String name) {

        try {
            client.writeToServer("INSERT INTO USER_INFORMATION VALUES('" + username + "', '" + passwordHash + "', '" + accountType +
                    "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userIDExists(String userID) {
        User user = null;
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER);
            user = (User) client.readFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (user.getUserID() != null) {
            return true;
        }
        return false;
    }
}