package marketplace.Objects;

public class SellOrderHistory extends SellOrder{
    String oldSellID;
    public SellOrderHistory(){}

    public void setOldSellID(String oldSellID){
        this.oldSellID = oldSellID;
    }

    public String getOldSellID(){
        return oldSellID;
    }
}
