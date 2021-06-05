package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUI;
import marketplace.Objects.Inventory;
import marketplace.TableObject;
import java.io.IOException;
import java.util.List;

/**
 * Handler class for Inventory object type. Handles methods that main methods for interacting with Inventory type.
 * Interacts with INVENTORY table in database.
 *
 * @see Inventory
 *
 */
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

    public void addAsset(String assetID, String assetName, String orgID, int quantity) throws Exception {

        if (assetName == null || assetName.length() < 2) {
            throw new Exception("Please enter an asset name");
        }

        if (quantity < 1) {
            throw new Exception("Please enter a quantity more than 0");
        }

        if (assetNameExists(assetName, orgID)) {
            throw new Exception("Organisation already has that asset");
        }

        try {
            client.writeToServer("INSERT INTO INVENTORY VALUES('" + assetID + "', '" + assetName + "', '" + orgID +
                    "', '" + quantity + "' );", TableObject.INVENTORY);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public List<Inventory> getAllInventory(){
        List<Inventory> inventory = null;

        try {
            client.writeToServer("SELECT * FROM INVENTORY;", TableObject.INVENTORY);
            inventory = (List<Inventory>) client.readObjectFromServer();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
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
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public void updateOrganisationAssetQuantity(String assetID, String orgID, int quantity) throws Exception {

        if (quantity < 0) {
            throw new Exception("Please enter a valid number");
        }

        if (quantity < (MainGUI.organisationHandler.getOrganisationSellingQuantity(orgID, assetID))) {
            throw new Exception("Current sell orders don't allow that");
        }

        if (quantity == 0) {
            try {
                client.writeToServer("DELETE FROM INVENTORY WHERE assetID = '" + assetID + "' orgID = '" + orgID + "';", TableObject.INVENTORY);
                client.readListFromServer();
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        } else {
            try {
                client.writeToServer("UPDATE INVENTORY SET quantity = '"+ quantity +"' WHERE assetID= '" + assetID
                        + "' AND orgID = '" + orgID + "';", TableObject.INVENTORY);
                client.readListFromServer();
            } catch (IOException | ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }
    }

    public void updateAssetQuantity(String assetID, int quantity) {

        try {
            client.writeToServer("UPDATE INVENTORY SET quantity= '"+ quantity +"' WHERE assetID= '"+ assetID +"';", TableObject.INVENTORY);
        } catch (IOException exception) {
            exception.printStackTrace();
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

    public Inventory getOrganisationsAsset(String organisationID, String assetName) throws Exception {
        List<Inventory> inventory = null;

        if (assetName == null) {
            throw new Exception("Please enter an asset name");
        }

        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE orgID= '"+ organisationID +
                    "' AND assetName = '" + assetName + "';", TableObject.INVENTORY);
            inventory = (List) client.readListFromServer();
            return inventory.get(0);

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (IndexOutOfBoundsException exception) {
            throw new Exception("Asset couldn't be found");
        }

        if (inventory == null) {
            throw new Exception("Asset couldn't be found");
        }

        return inventory.get(0);
    }

    public List<Inventory> getOrganisationsAssets(String organisationID) throws Exception {
        List<Inventory> inventories = null;

        if (organisationID == null) {
            throw new Exception("Please enter an organisation ID");
        }

        try {
            client.writeToServer("SELECT * FROM INVENTORY WHERE orgID= '"+ organisationID +"';", TableObject.INVENTORY);
            inventories = (List<Inventory>) client.readObjectFromServer();

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        return inventories;
    }

    public boolean assetNameExists(String assetName, String orgID) throws Exception {
        Inventory asset = null;

        try {
            List<Inventory> inventories = getOrganisationsAssets(orgID);

            for (Inventory inventory : inventories) {
                if (inventory.getAssetName().equals(assetName)) {
                    return true;
                }
            }
        } catch (IOException | ClassNotFoundException exception) {
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