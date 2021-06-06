//package marketplace.Test;
//
//import marketplace.Client.Client;
//import marketplace.Handlers.InventoryHandler;
//import marketplace.Objects.Organisation;
//import marketplace.Server.ServerHandler;
//import marketplace.TableObject;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class InventoryHandlerTest {
//    private static Client client;
//    private static InventoryHandler inventoryHandler;
//
//    // reference: https://stackoverflow.com/questions/22186778/using-math-round-to-round-to-one-decimal-place
//    private static double round (double value, int precision) {
//        int scale = (int) Math.pow(10, precision);
//        return (double) Math.round(value * scale) / scale;
//    }
//
//    @BeforeAll
//    static void startClientServer() {
//
//        Thread thread = new ServerHandler();
//        thread.start();
//        // We sleep this thread so that the server handler has time to finish setting up before we continue
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @BeforeAll
//    public static void startClientConnection(){
//        client = new Client();
//        try {
//            client.connect();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        inventoryHandler = new InventoryHandler(client);
//    }
//
//    @BeforeEach
//    public void removeTestOrganisation(){
//        try {
//            client.writeToServer("DELETE FROM INVENTORY WHERE assetName = 'test asset';", TableObject.ORGANISATION);
//            client.readListFromServer();
//        } catch (IOException | ClassNotFoundException exception) {
//            exception.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testUpdateOrganisationCreditsValid() throws Exception {
//
//        String newOrgID = organisationHandler.newOrganisationID();
//        String orgName = "test organisation";
//        double credits = 200;
//
//        organisationHandler.addOrganisation(newOrgID, orgName, credits);
//
//        organisationHandler.updateOrganisationCredits(newOrgID, BigDecimal.valueOf(100));
//
//        Organisation organisation = organisationHandler.getOrganisation(newOrgID);
//
//        // Lists to hold values from the expected and actual organisation
//        List<Object> expectedOrganisation = new ArrayList<>();
//        List<Object> actualOrganisation = new ArrayList<>();
//
//        expectedOrganisation.add(newOrgID);
//        expectedOrganisation.add(orgName);
//        expectedOrganisation.add(BigDecimal.valueOf(100).doubleValue());
//
//        actualOrganisation.add(organisation.getOrgID());
//        actualOrganisation.add(organisation.getOrgName());
//        actualOrganisation.add(organisation.getCredits().doubleValue());
//
//        // Assert both lists are equal
//        assertEquals(actualOrganisation, expectedOrganisation);
//    }
//
//    @Test
//    public void testUpdateOrganisationCreditsInvalid() throws Exception {
//
//        String newOrgID = organisationHandler.newOrganisationID();
//        String orgName = "test organisation";
//        double credits = 200;
//
//        organisationHandler.addOrganisation(newOrgID, orgName, credits);
//
//        Assertions.assertThrows(Exception.class, () -> {
//            organisationHandler.updateOrganisationCredits(newOrgID, BigDecimal.valueOf(-25));
//        });
//    }
//
//
//}