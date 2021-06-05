package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUIHandler;
import marketplace.Objects.User;
import marketplace.PasswordHandler;
import marketplace.TableObject;

import java.io.IOException;
import java.util.List;

public class UserHandler {
    private final Client client;

    public UserHandler(Client client){
        this.client = client;
    }

    public List<User> getAllUsers() {
        List<User> result = null;
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION;", TableObject.USER);
            result = (List) client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public User searchUser(String userID) throws Exception {
        List<User> users = getAllUsers();
        User result = null;

        if (users != null) {
            for (int i = 0; i < users.size(); i++) {
                if (users.get(i).getUserID().equals(userID)) {
                    result =  users.get(i);
                }
            }
        }

        if (result == null) {
            throw new Exception("User can't be found");
        }

        return result;
    }

    public void addUser(String userID, String passwordHash, String accountType, String organisationID, String name) throws Exception{

        if (name.length() < 2) {
            throw new Exception("Name is too short");
        }

        if (name.length() > 250) {
            throw new Exception("That name is too long");
        }

        try {
            client.writeToServer("INSERT INTO USER_INFORMATION VALUES('" + userID + "', '" + passwordHash + "', '" + accountType +
                    "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User can't be added");
        }
    }

    public void removeUser(String userID) throws Exception {
        try {
            client.writeToServer("DELETE FROM USER_INFORMATION WHERE userID = '" + userID + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User can't be removed");
        }
    }

    public void updateUser(User user) throws Exception {
        try {
            client.writeToServer("UPDATE USER_INFORMATION SET accountType = '" +
                    user.getAccountType() + "', orgID = '" + user.getOrganisationID() + "' WHERE userID = '" +
                    user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User can't be updated");
        }
    }

    public void updateUserPassword(User user, String currentPassword, String newPassword,
                                   String passwordConfirmed) throws Exception
    {

        if (PasswordHandler.IsPasswordStrong(currentPassword)) {
            throw new Exception("That password is not strong enough");
        }

        if (!newPassword.equals(passwordConfirmed)) {
            throw new Exception("Please confirm the password");
        }

        if (currentPassword.equals(newPassword)) {
            throw new Exception("New password must be different to old password");
        }

        PasswordHandler passwordHandler = null;
        if (!passwordHandler.IntoHash(newPassword).equals(user.getPasswordHash())) {
            throw new Exception("Incorrect current password");
        }

        try {
            client.writeToServer("UPDATE USER_INFORMATION SET passwordHash = '" +
                    user.getPasswordHash() + "' WHERE userID = '" + user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User's password can't be updated");
        }
    }

    public void updateUserPassword(User user) throws Exception
    {
        try {
            client.writeToServer("UPDATE USER_INFORMATION SET passwordHash = '" +
                    user.getPasswordHash() + "' WHERE userID = '" + user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User's password can't be updated");
        }
    }

    public boolean userIDExists(String userID) throws Exception {
        User user = null;
        user = searchUser(userID);
        if (user.getUserID() != null) {
            return true;
        }
        return false;
    }

    public String newUserID() throws Exception {
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

    public Boolean loginUser(String userID, String passwordString) throws Exception {

        User user = MainGUIHandler.userHandler.searchUser(userID);
        String passwordHash = PasswordHandler.IntoHash(passwordString);

        if (!userIDExists(userID) || user == null) {
            throw new Exception("Invalid details");
        }

        if (!user.getPasswordHash().equals(passwordHash) ) {
            throw new Exception("Invalid details");
        }

        return true;
    }
}