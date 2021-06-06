package marketplace.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import marketplace.Objects.Order;
import marketplace.Handlers.OrderHandler;
import marketplace.Objects.SellOrder;
import marketplace.TableObject;
import org.junit.jupiter.api.*;
import marketplace.Server.*;
import marketplace.Client.Client;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class OrderHandlerTest {
    private static Client client;
    private String rootDir;
    private static Properties props;
    private static MariaDBDataSource pooledDataSource;
    private static OrderHandler orderHandler;

    @BeforeAll
    static void startClientServer() throws SQLException {

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
        orderHandler = new OrderHandler(client);
    }

//    @AfterAll
//    public static void closeClientConnection() {
//        client.disconnect();
//    }

    @Test
    public void testFirstBuyOrder() throws SQLException {

        List<Order> buyOrders = orderHandler.getAllActiveBuyOrders();

        // Lists to hold values from the expected and actual first buy order
        List<Object> expectedFirstBuyOrder = new ArrayList<>();
        List<Object> actualFirstBuyOrder = new ArrayList<>();

        Order actualFirstBuyOrderObject = buyOrders.get(0);
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getOrderID());
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getUserID());
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getAssetName());
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getQuantity());
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getPrice().toString());
        actualFirstBuyOrder.add(actualFirstBuyOrderObject.getOrderDate().toString());

        expectedFirstBuyOrder.add("buy1");
        expectedFirstBuyOrder.add("user1");
        expectedFirstBuyOrder.add("RAM");
        expectedFirstBuyOrder.add(15);
        expectedFirstBuyOrder.add("10.00");
        expectedFirstBuyOrder.add("2021-03-24 16:34:27.0");

        // Assert both lists are equal
        assertEquals(expectedFirstBuyOrder, actualFirstBuyOrder);

    }

    @Test
    public void testFirstSellOrder() {

        List<SellOrder> sellOrders = orderHandler.getAllActiveSellOrders();

        // Lists to hold values from the expected and actual first buy order
        List<Object> expectedFirstSellOrder = new ArrayList<>();
        List<Object> actualFirstSellOrder = new ArrayList<>();

        SellOrder actualFirstSellOrderObject = sellOrders.get(0);
        actualFirstSellOrder.add(actualFirstSellOrderObject.getOrderID());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getUserID());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getAssetID());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getAssetName());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getQuantity());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getPrice().toString());
        actualFirstSellOrder.add(actualFirstSellOrderObject.getOrderDate().toString());

        expectedFirstSellOrder.add("sell1");
        expectedFirstSellOrder.add("user3");
        expectedFirstSellOrder.add("asset3");
        expectedFirstSellOrder.add("ARDUINOS");
        expectedFirstSellOrder.add(15);
        expectedFirstSellOrder.add("10.00");
        expectedFirstSellOrder.add("2021-03-24 16:34:26.0");

        // Assert both lists are equal
        assertEquals(expectedFirstSellOrder, actualFirstSellOrder);

    }

    @Test
    public void testGetAllActiveBuyOrdersForAsset(){
        String expectedAssetName = "CPU";
        String actualAssetName;

        List<Order> buyOrders = orderHandler.getAllActiveBuyOrdersForAsset(expectedAssetName);
        Order actualFirstBuyOrderObject = buyOrders.get(0);
        actualAssetName = actualFirstBuyOrderObject.getAssetName();

        // Assert both lists are equal
        assertEquals(expectedAssetName, actualAssetName);

    }

    @Test
    public void testGetAllActiveSellOrdersForAsset(){
        String expectedAssetName = "ARDUINOS";
        String actualAssetName;

        List<SellOrder> sellOrders = orderHandler.getAllActiveSellOrdersForAsset(expectedAssetName);
        SellOrder actualFirstSellOrderObject = sellOrders.get(0);
        actualAssetName = actualFirstSellOrderObject.getAssetName();

        // Assert both lists are equal
        assertEquals(expectedAssetName, actualAssetName);

    }

//    @Test
//    public void testAddNewBuyOrder(){
//        String expectedUserID = "user1";
//        String expectedAssetName = "CPU";
//        int expectedQuantity = 5;
//        BigDecimal expectedPrice = BigDecimal.valueOf(32);
//
//        List<Order> actualBuyOrderList = new ArrayList<>();
//        Order actualBuyOrderObject;
//
//
//        // Lists to hold values from the expected and actual new buy order
//        List<Object> expectedNewBuyOrder = new ArrayList<>();
//        List<Object> actualNewBuyOrder = new ArrayList<>();
//
//        actualBuyOrderList = orderHandler.getAllActiveBuyOrders();
//        for (Order ord: actualBuyOrderList){
//            System.out.println(ord.getOrderID());
//        }
//
//        expectedNewBuyOrder.add(expectedUserID);
//        expectedNewBuyOrder.add(expectedAssetName);
//        expectedNewBuyOrder.add(expectedQuantity);
//        expectedNewBuyOrder.add(expectedPrice);
//
//        orderHandler.addNewBuyOrder(expectedUserID, expectedAssetName, expectedQuantity, expectedPrice);
//
//        // Fetch the most recent buy order added to the database
//
//
////        try {
////            client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS ORDER BY buyID DESC LIMIT 1;",
////                    TableObject.BUY_ORDER);
////            System.out.println(client.readListFromServer().get(0));
////            actualBuyOrderList = (List<Order>) client.readListFromServer();
////            actualBuyOrderObject = actualBuyOrderList.get(0);
////            actualNewBuyOrder.add(actualBuyOrderObject.getUserID());
////            actualNewBuyOrder.add(actualBuyOrderObject.getAssetName());
////            actualNewBuyOrder.add(actualBuyOrderObject.getQuantity());
////            actualNewBuyOrder.add(actualBuyOrderObject.getPrice());
////
////        } catch (IOException | ClassNotFoundException e) {
////            e.printStackTrace();
////        }
//
//        assertEquals(expectedNewBuyOrder, actualNewBuyOrder);
//
//    }

    // test new order ID with empty database
    // test getting active orders with empty database
    // test adding a new buy order
    // test adding a new sell order
    // test ammending an order
    // test deleting an order

        // only put in a sell order for what they have!


}
