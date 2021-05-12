

public class User {
    private String username;
    private String passwordHash;
    private String accountType;
    private String organisation;
    private String name;

    public User(String username, String passwordHash, String accountType, String organisation, String name){
        this.username = username;
        this.passwordHash = passwordHash;
        this.accountType = accountType;
        this.organisation = organisation;
        this.name = name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setPasswordHash(String passwordHash){
        this.passwordHash = passwordHash;
    }

    public void setAccountType(String accountType){
        this.accountType = accountType;
    }

    public void setOrganisation(String organisation){
        this.organisation = organisation;
    }
    public void setName(String name){
        this.name = name;
    }

    public String getUsername(){
        return username;
    }

    public String getPasswordHash(){
        return passwordHash;
    }

    public String getAccountType(){
        return accountType;
    }

    public String getOrganisation(){
        return organisation;
    }

    public String getName(){
        return name;
    }

}
