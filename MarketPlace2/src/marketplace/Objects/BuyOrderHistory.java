package marketplace.Objects;

public class BuyOrderHistory extends Order {
    String oldBuyID;

    public BuyOrderHistory(){}

    public void setOldBuyID(String oldBuyID){
        this.oldBuyID = oldBuyID;
    }

    public String getOldBuyID(){
        return oldBuyID;
    }
}
