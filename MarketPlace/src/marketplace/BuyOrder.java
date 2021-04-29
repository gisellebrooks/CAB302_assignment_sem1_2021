package marketplace;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BuyOrder {

    private double assetQuantity;
    private double assetPrice;
    private long sellID;
    private final MariaDBDataSource pool;
//
    public BuyOrder (MariaDBDataSource pool){
        this.pool = pool;
//        this.assetPrice = assetPrice;
//        this.assetQuantity = assetQuantity;
    }


    public double getAssetQuantity(String organisationID, String assetID) throws SQLException {
        try(Connection conn = pool.getConnection()) {

            ResultSet queriedResult;
            try(PreparedStatement statement = conn.prepareStatement(" SELECT quantity FROM ASSETS " +
                    " WHERE unit_id=? AND asset_id=? ")) {

                statement.setString(1, organisationID);
                statement.setString(2, assetID);
                queriedResult = statement.executeQuery();

                // Handle if query returns empty
                if (queriedResult.next()) {
                    assetQuantity = queriedResult.getLong("quantity");
                } else{
                    // If not found then quantity is 0
                    assetQuantity = 0;
                }
            }
        }
        return assetQuantity;
    }

    public double getAssetPrice(String organisationID, String assetID) throws SQLException {
        try(Connection conn = pool.getConnection()) {

            ResultSet queriedResult;
            try(PreparedStatement statement = conn.prepareStatement(" SELECT priceUpper, sell_id FROM ACTIVE_SELL_ORDERS " +
                    " WHERE unit_id=? AND asset_id=? ")) {

                statement.setString(1, organisationID);
                statement.setString(2, assetID);
                queriedResult = statement.executeQuery();

                // Handle if query returns empty
                if (queriedResult.next()) {
                    while (queriedResult.next()) {
                        assetPrice = queriedResult.getLong("priceUpper");
                        sellID = queriedResult.getLong("sell_id");
                    }
                } else{
                    // If not found then quantity is 0
                    assetPrice = 0.0;
                    sellID = 0;
                }
            }
        }
        return assetPrice;
    }

}
