package users;

import java.sql.ResultSet;

public class UserOrders {
    private String userID;

    public UserOrders(String userID){
        this.userID = userID;
    }

    public ResultSet getActiveBuyOrders(){
        ResultSet buyOrders = null; // remove null later

        // query to grab the user's active buy orders

        return buyOrders;

    }

    public ResultSet getActiveSellOrders(){
        ResultSet sellOrders = null;

        // query to grab the user's active sell orders

        return sellOrders;

    }

}
