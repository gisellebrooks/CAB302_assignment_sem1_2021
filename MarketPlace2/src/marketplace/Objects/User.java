package marketplace.Objects;

import java.io.Serializable;

/**
 * User class, each client that logs in is a user.
 * User's belong to an organisation and are able to make orders for that unit.
 * There are admin user's that have higher privileges than regular users.
 *
 * @see marketplace.Handlers.UserHandler
 *
 */
public class User implements Serializable{
    private String userID;
    private String passwordHash;
    private String accountType;
    private String orgID;
    private String name;

    /**
     * Constructor for User object, parameters match column titles in database for USER_INFORMATION table.
     *
     * @param userID is the unique identifier for each user, matches an ID in the USER_INFORMATION table in the database.
     *               "user34" for example.
     * @param passwordHash is the salted SHA-256 hash of their current password.
     * @param accountType is an enum, either USER or ADMIN, and this describes the access level of the user.
     * @param orgID is the unique identifier for the organisation that this user is connected to. "org12" for example.
     * @param name is the full name of the user and is used to show who owns what account.
     *
     */
    public User(String userID, String passwordHash, String accountType, String orgID, String name){
        this.userID = userID;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.orgID = orgID;
        this.name = name;
    }

    public User(){
    }

    /**
     * @param userID sets the user's ID
     */
    public void setUserID(String userID){
        this.userID = userID;
    }

    /**
     * @param passwordHash sets the user's password hash
     */
    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    /**
     * @param accountType sets the user's account type
     */
    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    /**
     * @param orgID sets the user's organisation ID
     */
    public void setOrganisationID(String orgID){
        this.orgID = orgID;
    }

    /**
     * @param name sets the user's name
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * @return the user's ID
     */
    public String getUserID(){
        return userID;
    }

    /**
     * @return the user's password hash
     */
    public String getPasswordHash(){
        return passwordHash;
    }

    /**
     * @return the user's account type
     */
    public String getAccountType(){
        return accountType;
    }

    /**
     * @return the user's organisation ID
     */
    public String getOrganisationID(){
        return orgID;
    }

    /**
     * @return the user's name
     */
    public String getName(){
        return name;
    }
}