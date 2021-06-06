package marketplace.Test;

import marketplace.Client.Client;
import marketplace.Handlers.InventoryHandler;
import marketplace.Server.ServerHandler;
import marketplace.TableObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class InventoryHandlerTest {
    private static Client client;
    private static InventoryHandler inventoryHandler;

    // reference: https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
    private static double round (double value, int precision) {
        int scale = (int) Math.pow(10, precision);
        return (double) Math.round(value * scale) / scale;
    }

    @BeforeAll
    static void startClientServer() {

        Thread thread = new ServerHandler();
        thread.start();
        // We sleep this thread so that the server handler has time to finish setting up before we continue
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @BeforeAll
    public static void startClientConnection(){
        client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        inventoryHandler = new InventoryHandler(client);
    }

    @BeforeEach
    public void removeTestInventory(){
        try {
            client.writeToServer("DELETE FROM INVENTORY WHERE assetName = 'test asset';", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    @Test
    public void testAddInventoryValid() throws Exception {

        String newAssetID = inventoryHandler.newAssetID();
        String assetName = "test asset";
        String orgID = "org1";
        int quantity = 100;

        inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);
    }

    @Test
    public void testAddInventoryInvalidNameEmpty() {

        String newAssetID = inventoryHandler.newAssetID();
        String assetName = "";
        String orgID = "org1";
        int quantity = 100;

        Assertions.assertThrows(Exception.class, () -> {
            inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);
        });
    }

    @Test
    public void testAddInventoryInvalidNameLong() {

        String newAssetID = inventoryHandler.newAssetID();
        String assetName = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String orgID = "org1";
        int quantity = 100;

        Assertions.assertThrows(Exception.class, () -> {
            inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);
        });
    }

    @Test
    public void testAddInventoryInvalidNameExists() throws Exception {

        String newAssetID = inventoryHandler.newAssetID();
        String assetName = "test asset";
        String orgID = "org1";
        int quantity = 100;

        inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);

        Assertions.assertThrows(Exception.class, () -> {
            inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);
        });
    }

    @Test
    public void testAddInventoryInvalidQuantityNegative() {

        String newAssetID = inventoryHandler.newAssetID();
        String assetName = "test asset";
        String orgID = "org1";
        int quantity = -200;

        Assertions.assertThrows(Exception.class, () -> {
            inventoryHandler.addAsset(newAssetID, assetName, orgID, quantity);
        });
    }


}