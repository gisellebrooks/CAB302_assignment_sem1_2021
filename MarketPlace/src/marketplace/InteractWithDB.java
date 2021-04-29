package marketplace;


import java.sql.Statement;


public interface InteractWithDB {

    // Assets
    public double getAssetQuantity();

    public int getAssetId();

    public double getAssetPrice();

    // Orders
    public int getOrderId();

    public default double orderValue(){
        return getAssetQuantity() * getAssetPrice();
    }

    // Organisations
    public boolean hasEnoughCredits();

    public void displayAllUsers();


}


//    public void processOrder() {
//
//        if (hasCredits()){
//            if (type == OrderType.BUY){
//                String buyQuery = "SELECT unitName, assetName, quantity, priceUpper FROM active_buy WHERE buy_id == " + buy_id;
//                quantity =
//            }
//
//        }
//
//    }
//    public void add() {}
//
//    public final void cancel() {
//        // universal code for order cancellations
//}

