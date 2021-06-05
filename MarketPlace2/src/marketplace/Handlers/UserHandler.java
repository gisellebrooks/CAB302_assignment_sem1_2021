package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUI;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.util.List;


/**
 * Handler class for User object type. Handles main methods for interacting with User type.
 * Interacts with User_Information table in database.
 *
 * @see User
 *
 */
public class UserHandler {

    private final Client client;

    /**
     * Constructor for each UserHandler, just creates an instance and sets the client connection.
     *
     * @param client sets the connection to the server so that queries for the database can be sent.
     *
     */
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

        // will return empty if no users exist in the database
        return result;
    }

    public User searchUser(String userID) throws Exception {
        List<User> users = getAllUsers();
        User result = null;

        if (users != null) {
            for (User user : users) {

                // check all users for a matching user ID and return that user if found
                if (user.getUserID().equals(userID)) {
                    result = user;
                }
            }
        }

        // if no matching user ID is found then throw an exception
        if (result == null) {
            throw new Exception("User can't be found");
        }

        return result;
    }

    /**
     * Runs checks on the params and if valid, tries to add the user details to the database as a new user.
     *
     * @return a list containing just the user that has a matching userID to the parameter.
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @throws Exception when the param name is too short or too long.
     *
     * @param userID is the unique user identification that is used in the database, USER_INFORMATION table.
     * @param passwordHash is the salted, SHA256 hash of the user's password.
     * @param accountType is an enum, USER or ADMIN, and decides the users access level.
     * @param organisationID is the unique organisation identification that is used to find that user's organisation.
     * @param name is the name of the user.
     *
     * @see User
     */
    public void addUser(String userID, String passwordHash, String accountType, String organisationID, String name)
            throws Exception{

        if (name.length() < 2) {
            throw new Exception("Name is too short");
        }

        if (name.length() > 250) {
            throw new Exception("That name is too long");
        }

        // try and add the new user to the database, USER_INFORMATION table
        try {
            client.writeToServer("INSERT INTO USER_INFORMATION VALUES('" + userID + "', '" + passwordHash +
                    "', '" + accountType + "', '" + organisationID + "', '" + name + "' );", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User can't be added");
        }
    }

    /**
     * Tries to update a user using a full User object which contains the target user's userID and the desired User values.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @param user is the updated User values along with the target user's ID in order to find them in the database.
     *
     */
    public void updateUser(User user) throws Exception {

        // try to update the user with their current user ID, but with new accompanying values
        try {
            client.writeToServer("UPDATE USER_INFORMATION SET accountType = '" +
                    user.getAccountType() + "', orgID = '" + user.getOrganisationID() + "' WHERE userID = '" +
                    user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User can't be updated");
        }
    }

    /**
     * Tries to save a new password for a user if they input the correct current password and give a strong password.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @throws Exception when the new password is not strong enough.
     * @throws Exception when the new password is not the same as the confirmed password.
     * @throws Exception when the new password is the same as the old password.
     * @throws Exception when the input current password is not correct.
     *
     * @param user is the target user with the current userID and passwordHash.
     * @param currentPassword is the password to be tested against the saved user passwordHash.
     * @param newPassword is the desired new password to be used.
     * @param passwordConfirmed is a confirmation of the new password.
     *
     */
    public void updateUserPassword(User user, String currentPassword, String newPassword,
                                   String passwordConfirmed) throws Exception
    {

        // test if desired password meets requirements to be considered
        if (!PasswordHandler.IsPasswordStrong(currentPassword)) {
            throw new Exception("That password is not strong enough");
        }

        // if the new password isn't correctly confirmed the second time throw an exception
        if (!newPassword.equals(passwordConfirmed)) {
            throw new Exception("Please confirm the password");
        }

        // if the new password is the same as the old one throw an exception
        if (currentPassword.equals(newPassword)) {
            throw new Exception("New password must be different to old password");
        }

        // if the input current password is not correct then throw an exception
        if (!PasswordHandler.IntoHash(newPassword).equals(user.getPasswordHash())) {
            throw new Exception("Incorrect current password");
        }

        // try and update the user's passwordHash with the password hash of the new desired password
        try {
            client.writeToServer("UPDATE USER_INFORMATION SET passwordHash = '" +
                    user.getPasswordHash() + "' WHERE userID = '" + user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User's password can't be updated");
        }
    }

    /**
     * Tries to save a new user with a new passwordHash that's from the passwordHandler generatePassword method.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @param user is the target user with the current userID and the updated passwordHash.
     *
     */
    public void updateUserPassword(User user) throws Exception
    {

        // try to update a user in the database with an updated password hash
        try {
            client.writeToServer("UPDATE USER_INFORMATION SET passwordHash = '" +
                    user.getPasswordHash() + "' WHERE userID = '" + user.getUserID() + "';", TableObject.USER);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            throw new Exception("User's password can't be updated");
        }
    }

    /**
     * Tries to get a user using the input userID, returns true or false depending of if the user can be found.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @param userID is the userID of the user that is being looked for.
     * @return true if the user can be found and false if they can't.
     */
    public boolean userIDExists(String userID) throws Exception {
        User user = null;
        user = searchUser(userID);
        if (user.getUserID() != null) {
            return true;
        }
        return false;
    }

    /**
     * Searches the USER_INFORMATION for userID and gets the highest number following the "user", then adds one
     * and returns the new userID string.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @return the new userID, and if no users are found then the id is "user1".
     */
    public String newUserID() throws Exception {

        int currentID;
        int maxID = 0;
        String holder;
        String newID;
        List<User> users = getAllUsers();

        // for all users, compare the numbers in their user ID to get the max ID
        for (User user : users) {
            holder = (user.getUserID());
            holder = holder.replace("user", "");
            currentID = (Integer.parseInt((holder)));

            if (currentID > maxID) {
                maxID = currentID;
            }
        }

        // add the starting string for user ID to the start of the new number
        newID = "user" + (maxID + 1);

        return newID;
    }

    /**
     * Checks whether the userID exists and if the provided password, when hashed, matches the password saved.
     *
     * @throws Exception when an error occurs in the database process and returns an error message.
     * @throws Exception when the userID does not exist in the database.
     * @throws Exception when the passwordHash of the provided password does not match the saved passwordHash.
     *
     * @return true if the login details are valid and false if they are not.
     */
    public Boolean loginUser(String userID, String passwordString) throws Exception {

        User user = MainGUI.userHandler.searchUser(userID);
        String passwordHash = PasswordHandler.IntoHash(passwordString);

        // check if userID exists
        if (!userIDExists(userID) || user == null) {
            throw new Exception("Invalid details");
        }

        // check if provided password's hash matches the saved passwordHash of the user
        if (!user.getPasswordHash().equals(passwordHash) ) {
            throw new Exception("Invalid details");
        }

        return true;
    }
}