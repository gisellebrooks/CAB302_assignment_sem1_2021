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

    public User getUserInformation(String userID){
        User result = null;
        try {
            System.out.println(userID);
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER);
            result = (User) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
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
//        try {
//            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER);
//            user = (User) client.readObjectFromServer();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
        User user = null;
        user = getUserInformation(userID);
        if (user.getUserID() != null) {
            return true;
        }
        return false;
    }

    public String newUserID() {
        User user = null;
        String returnID;
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION ORDER BY cast(userID as SIGNED) ASC;", TableObject.USER);

            user = (User) client.readObjectFromServer(); // this is the issue
        } catch (Exception exception) {

            exception.printStackTrace();
        }

        returnID = user.getUserID();
        returnID = returnID.replace("user", "");
        returnID = (String.valueOf(Integer.parseInt(returnID) + 1));
        returnID = "user" + returnID;
        return (returnID);
    }
}