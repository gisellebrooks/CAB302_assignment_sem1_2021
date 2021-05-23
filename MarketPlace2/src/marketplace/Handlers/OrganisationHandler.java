package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Organisation;
import marketplace.TableObject;

import java.io.IOException;
import java.math.BigDecimal;

public class OrganisationHandler {
    private final Client client;

    public OrganisationHandler(Client client){
        this.client = client;
    }

    public Organisation getOrganisationInformation(String orgID){
        Organisation result = null;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '"+ orgID+"';", TableObject.ORGANISATION);
            result = (Organisation) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public void addOrganisation(String orgID, String orgName, double credits) {

        try {
            client.writeToServer("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES('" + orgID + "', '" + orgName
                    + "', '" + credits + "');", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeOrganisation(String orgID, String orgName, double credits) {

        try {
            client.writeToServer("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES('" + orgID + "', '" + orgName
                    + "', '" + credits + "');", TableObject.USER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOrganisationCredits(String orgID, BigDecimal credits) {

        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET credits= '" + credits + "' WHERE orgID= '"+ orgID +"';", TableObject.ORGANISATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean orgIDExists(String orgID) {
        Organisation organisation = null;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
            organisation = (Organisation) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (organisation.getOrgID() != null) {
            return true;
        }
        return false;
    }
}