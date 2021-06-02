package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Inventory;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.util.ArrayList;
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

    public List<Inventory> getAllInventory(){
        List<Inventory> inventory = null;

        try {
            client.writeToServer("SELECT * FROM INVENTORY;", TableObject.INVENTORY);
            inventory = (List<Inventory>) client.readObjectFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public String newAssetID() {
        List<Inventory> inventories = getAllInventory();
        int currentID = 0;
        int maxID = 0;
        String holder;
        String newID;

        for (Inventory inventory : inventories) {
            holder = (inventory.getAssetID());
            holder = holder.replace("asset", "");
            currentID = (Integer.parseInt((holder)));

            if (currentID > maxID) {
                maxID = currentID;
            }
        }

        newID = "asset" + (maxID + 1);

        return newID;
    }

    public void removeAsset(String assetID, String assetName, String orgID, int quantity) {

        try {
            client.writeToServer("D INTO INVENTORY VALUES('" + assetID + "', '" + assetName + "', '" + orgID +
                    "', '" + quantity + "' );", TableObject.INVENTORY);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOrganisationAssetQuantity(String assetID, String orgID, int quantity) {
        try {
            client.writeToServer("UPDATE INVENTORY SET quantity = '"+ quantity +"' WHERE assetID= '" + assetID
                    + "' AND orgID = '" + orgID + "';", TableObject.INVENTORY);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
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

    public List<Inventory> getOrganisationsAssets(String organisationID){
        List<Inventory> inventory = null;

        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE orgID= '"+ organisationID +"';", TableObject.INVENTORY);
            inventory = (List<Inventory>) client.readObjectFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return inventory;
    }

    public boolean assetNameExists(String assetName, String orgID) {
        Inventory asset = null;

        try {
            List<Inventory> inventories = getOrganisationsAssets(orgID);

            for (Inventory inventory : inventories) {
                if (inventory.getAssetName().equals(assetName)) {
                    return true;
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public boolean assetIDExists(String assetID) {
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

    public void deleteOrganisationAsset(String assetID, String organisationID) {
        try {
            client.writeToServer("DELETE FROM INVENTORY WHERE assetID = '" + assetID + "' orgID = '" + organisationID + "';", TableObject.INVENTORY);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}