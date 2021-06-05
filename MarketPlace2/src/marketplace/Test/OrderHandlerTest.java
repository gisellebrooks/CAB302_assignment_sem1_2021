package marketplace.Test;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import marketplace.Objects.Order;
import marketplace.Handlers.OrderHandler;
import marketplace.Objects.SellOrder;
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
        pooledDataSource = MariaDBDataSource.getInstance();
        ServerHandler.initDb(pooledDataSource);
        props = ServerHandler.loadServerConfig();
        Thread thread = new ServerHandler();
        thread.start();
        client = new Client();
        try {
            client.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        orderHandler = new OrderHandler(client);
    }

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
        expectedFirstBuyOrder.add("2021-03-24 16:34:26.0");

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
    public void testNewBuyOrderID(){
        String expectedNewBuyOrderID;
        String actualNewBuyOrderID;

        expectedNewBuyOrderID = "buy7";
        actualNewBuyOrderID = orderHandler.newOrderID("buy");

        assertEquals(expectedNewBuyOrderID, actualNewBuyOrderID);
    }

    @Test
    public void testNewSellOrderID(){
        String expectedNewSellOrderID;
        String actualNewSellOrderID;

        expectedNewSellOrderID = "sell6";
        actualNewSellOrderID = orderHandler.newOrderID("sell");


        assertEquals(expectedNewSellOrderID, actualNewSellOrderID);
    }

//    @Test
//    public void

    // test new order ID with empty database
    // test getting active orders with empty database
    // test adding a new buy order
    // test adding a new sell order
    // test ammending an order
    // test deleting an order


}
