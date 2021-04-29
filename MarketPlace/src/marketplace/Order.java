package marketplace;


import java.sql.Statement;

public interface Order {

    public enum OrderType {
        BUY, SELL;
    }

//    private OrderType type;
//    private long quantity;
//    private double price; //placeholder type
//    private int asset_id;
//    private int order_id;
//    private long availableCredits;

//    public Order(int asset_id, int order_id, double price, long quantity) {
//        this.asset_id = asset_id;
//        this.price = price;
//        this.quantity = quantity;
//
//    }

    public long getQuantity();

    public int getAssetId();

    public double getPrice();

    public int getOrderId();

    public long orderValue();

    public boolean hasCredits();
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

