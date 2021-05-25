package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.util.List;

public class UserHandler {
    private final Client client;

    public UserHandler(Client client){
        this.client = client;
    }

    public List<User> getAllUsers(){
        List<User> result = null;
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION;", TableObject.USER);
            result = (List) client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public User getUser(String userID){
        List<User> users = getAllUsers();
        User result = null;

        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserID().equals(userID)) {
                    result =  users.get(i);
                }
            }
        }

        return result;
    }

    public void addUser(String userID, String passwordHash, String accountType, String organisationID, String name) {
        try {
            client.writeToServer("INSERT INTO USER_INFORMATION VALUES('" + userID + "', '" + passwordHash + "', '" + accountType +
                    "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeUser(String userID) {
        try {
            client.writeToServer("DELETE FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER) ;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String userID, String passwordHash, String accountType, String organisationID, String name) {

        try {
            client.writeToServer("UPDATE USER_INFORMATION SET passwordHash = '" + passwordHash + "', accountType = '" +
                    accountType + "', or orgID = '" + organisationID + "', name = '" + name + "' WHERE userID = '" +
                    userID + "';", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean userIDExists(String userID) {
        User user = null;
        user = getUser(userID);
        if (user != null && user.getUserID() != null) {
            return true;
        }
        return false;
    }

    public String newUserID() {
        List<User> users = getAllUsers();
        int currentID = 0;
        int maxID = 0;
        String holder;
        String newID;

        for (User user : users) {
            holder = (user.getUserID());
            holder = holder.replace("user", "");
            currentID = (Integer.parseInt((holder)));

            if (currentID > maxID) {
                maxID = currentID;
            }
        }

        newID = "user" + (maxID + 1);

        return newID;
    }
}