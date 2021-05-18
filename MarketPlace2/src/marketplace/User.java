package marketplace;

import java.io.Serializable;

public class User implements Serializable{
    private String userID;
    private String passwordHash;
    private String accountType;
    private String orgID;
    private String name;

    public User(String userID, String passwordHash, String accountType, String orgID, String name){
        this.userID = userID;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.orgID = orgID;
        this.name = name;
    }
    public User(){

    }

    public void setUsername(String userID){

        this.userID = userID;
    }

    public void setPasswordHash(String passwordHash){

        this.passwordHash = passwordHash;
    }

    public void setAccountType(String accountType){

        this.accountType = accountType;
    }

    public void setOrganisation(String orgID){

        this.orgID = orgID;
    }

    public void setName(String name){

        this.name = name;
    }

    public String getUsername(){

        return userID;
    }

    public String getPasswordHash(){

        return passwordHash;
    }

    public String getAccountType(){

        return accountType;
    }

    public String getOrganisation(){

        return orgID;
    }

    public String getName(){

        return name;
    }

}