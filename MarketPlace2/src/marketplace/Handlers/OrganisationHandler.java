package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Organisation;
import marketplace.TableObject;

import java.io.IOException;
import java.util.ArrayList;

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
                    + "', '" + credits + "');", TableObject.ORGANISATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeOrganisation(String orgID) {

        try {
            client.writeToServer("DELETE FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateOrganisation(String orgID, String orgName, double credits) {

        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET orgName = '" + orgName
                    + "', credits = '" + credits + "' WHERE orgID = '" + orgID + "');", TableObject.ORGANISATION);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean organisationIDExists(String orgID) {
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

    public boolean organisationNameExists(String organisationName) {
        Organisation organisation = null;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" + organisationName + "';", TableObject.ORGANISATION);
            organisation = (Organisation) client.readObjectFromServer();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        if (organisation.getOrgName() != null) {
            return true;
        }

        return false;
    }

    public String newOrganisationID() {
        Organisation organisation = null;
        String returnID;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION ORDER BY LENGTH(orgID) , orgID;", TableObject.ORGANISATION);

            organisation = (Organisation) client.readObjectFromServer(); // this is the issue


        } catch (Exception exception) {

            exception.printStackTrace();
        }

        System.out.println(organisation.getOrgID());

        if (organisation.getOrgID() == null) {
            return ("org1");
        } else {
            returnID = organisation.getOrgID();
            returnID = returnID.replace("org", "");
            returnID = (String.valueOf(Integer.parseInt(returnID) + 1));
            returnID = "org" + returnID;
            return (returnID);
        }
    }

    // !!!!!!!!!!!!!!!!!!!!!!!  this needs to get multiple
    public ArrayList<String> getAllOrganisationID() {
        Organisation organisation = null;
        ArrayList<String> organisationsIDList = new ArrayList<String>();
        try {
            client.writeToServer("SELECT ordID FROM ORGANISATIONAL_UNIT_INFORMATION;", TableObject.ORGANISATION);
            organisation = (Organisation) client.readObjectFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return organisationsIDList;
    }
}