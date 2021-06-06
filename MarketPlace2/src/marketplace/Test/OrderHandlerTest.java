package marketplace.Test;

import marketplace.Client.Client;
import marketplace.Handlers.OrderHandler;
import marketplace.Objects.Order;
import marketplace.Objects.SellOrder;
import marketplace.Server.MariaDBDataSource;
import marketplace.Server.ServerHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testFirstBuyOrder() throws SQLException {

        List<Order> buyOrders = orderHandler.getAllActiveBuyOrders();

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

        assertEquals(expectedFirstBuyOrder, actualFirstBuyOrder);
    }

    @Test
    public void testFirstSellOrder() {

        List<SellOrder> sellOrders = orderHandler.getAllActiveSellOrders();

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

        assertEquals(expectedFirstSellOrder, actualFirstSellOrder);

    }

    @Test
    public void testGetAllActiveBuyOrdersForAsset(){
        String expectedAssetName = "CPU";
        String actualAssetName;

        List<Order> buyOrders = orderHandler.getAllActiveBuyOrdersForAsset(expectedAssetName);
        Order actualFirstBuyOrderObject = buyOrders.get(0);
        actualAssetName = actualFirstBuyOrderObject.getAssetName();

        assertEquals(expectedAssetName, actualAssetName);

    }

    @Test
    public void testGetAllActiveSellOrdersForAsset(){
        String expectedAssetName = "ARDUINOS";
        String actualAssetName;

        List<SellOrder> sellOrders = orderHandler.getAllActiveSellOrdersForAsset(expectedAssetName);
        SellOrder actualFirstSellOrderObject = sellOrders.get(0);
        actualAssetName = actualFirstSellOrderObject.getAssetName();

        assertEquals(expectedAssetName, actualAssetName);

    }
}
