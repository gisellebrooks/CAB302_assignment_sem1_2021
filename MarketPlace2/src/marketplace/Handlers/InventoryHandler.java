package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Inventory;
import marketplace.TableObject;

import java.io.IOException;

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
            client.writeToServer("DELETE FROM INVENTORY WHERE assetID = '" + assetID + "' AND orgID = " + orgID + "';", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String assetID, String assetName, String orgID, int quantity) {

        try {
            client.writeToServer("UPDATE INVENTORY SET assetName = '" + assetName
                    + "', orgID = '" + orgID + "', quantity = '" + quantity + "' WHERE assetID = '" + assetID + "');", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean assetExists(String assetID) {
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