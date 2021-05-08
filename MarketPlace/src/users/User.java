package users;

public class User {
    private String username;
    private String password;
    private String accountType;
    private String organisation;
    private String name;
    private UserOrders orders;

    public User(String username, String password, String accountType, String organisation, String name){
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.organisation = organisation;
        this.name = name;
        orders = new UserOrders(username);

    }
    public void setUsername(String username){
        this.username = username;
    }

    public void setPassword(String password){
        this.password = password;
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

    public String getPassword(){
        return password;
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

    public UserOrders getOrders(){
        return orders;
    }

}
