package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Inventory;
import marketplace.Objects.TableObject;

import java.io.IOException;
import java.util.List;

public class InventoryHandler {
    private final Client client;

    public InventoryHandler(Client client){
        this.client = client;
    }

    public Inventory getAssetInformation(String assetID){
        Inventory result = null;
        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE assetID = '" + assetID + "';", TableObject.INVENTORY);
            result = (Inventory) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addAsset(String assetID, String assetName, String orgID, int quantity) {

        try {
            client.writeToServer("INSERT INTO INVENTORY VALUES('" + assetID + "', '" + assetName + "', '" + orgID +
                    "', '" + quantity + "' );", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeAsset(String assetID, String assetName, String orgID, int quantity) {

        try {
            client.writeToServer("D INTO INVENTORY VALUES('" + assetID + "', '" + assetName + "', '" + orgID +
                    "', '" + quantity + "' );", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateAssetQuantity(String assetID, int quantity) {

        try {
            client.writeToServer("UPDATE INVENTORY SET quantity= '"+ quantity +"' WHERE assetID= '"+ assetID +"';", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getAssetQuantity(String assetID){
        List<Inventory> inventory = null;
        int assetQuantity = 0;
        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE assetID= '"+ assetID +"';", TableObject.INVENTORY);
            inventory = (List<Inventory>) client.readObjectFromServer();

            for (Inventory inv: inventory){
                assetQuantity = inv.getQuantity();
            }


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return assetQuantity;
    }

    public boolean userIDExists(String assetID) {
        Inventory asset = null;
        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE assetID = '" + assetID + "';", TableObject.INVENTORY);
            asset = (Inventory) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (asset.getAssetID() != null) {
            return true;
        }
        return false;
    }
}