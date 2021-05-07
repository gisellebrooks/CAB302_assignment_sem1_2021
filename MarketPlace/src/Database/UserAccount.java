package Database;

public class UserAccount {
    private String username;
    private String password;
    private String accountType;
    private String organisation;
    private String name;
    private UserOrders orders;

    public UserAccount(String username, String password, String accountType, String organisation, String name){
        this.username = username;
        this.password = password;
        this.accountType = accountType;
        this.organisation = organisation;
        this.name = name;
        orders = new UserOrders(username);

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
