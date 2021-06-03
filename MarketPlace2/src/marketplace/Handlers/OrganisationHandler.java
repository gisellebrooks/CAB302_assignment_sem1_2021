package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Organisation;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrganisationHandler implements Serializable {
    private final Client client;

    public OrganisationHandler(Client client){
        this.client = client;
    }

    public List<Organisation> getAllOrganisations(){
        List result = null;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION;", TableObject.ORGANISATION);
            result = client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public Organisation getOrganisation(String orgID){
        List<Organisation> organisations = getAllOrganisations();
        Organisation result = null;

        if (organisations != null) {
            for (Organisation organisation : organisations) {
                if (organisation.getOrgID().equals(orgID)) {
                    result = organisation;
                }
            }
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
        Organisation organisation = getOrganisation(orgID);

        if (organisation != null && organisation.getOrgID() != null) {
            return true;
        }
        return false;
    }

    public boolean organisationNameExists(String organisationName) {
        List<String> allOrganisationsNames = getAllOrganisationsNames();

        if (allOrganisationsNames != null) {
            for (String allOrganisationsName : allOrganisationsNames) {
                if (allOrganisationsName.equals(organisationName)) {
                    return true;
                }
            }
        }

        return false;
    }

    public String newOrganisationID() {
        List<Organisation> organisations = getAllOrganisations();
        int currentID = 0;
        int maxID = 0;
        String holder;
        String newID = null;

        if (organisations != null) {
            for (Organisation organisation : organisations) {
                holder = (organisation.getOrgID());
                holder = holder.replace("org", "");
                currentID = (Integer.parseInt((holder)));

                if (currentID > maxID) {
                    maxID = currentID;
                }
            }

            newID = "org" + (maxID + 1);
        }

        return newID;
    }

    public List<String> getAllOrganisationsNames() {
        List<Organisation> allOrganisations = getAllOrganisations();
        List<String> allOrganisationsNames = new ArrayList<>();

        if (allOrganisations != null) {
            for (Organisation allOrganisation : allOrganisations) {
                allOrganisationsNames.add(allOrganisation.getOrgName());
            }

            return allOrganisationsNames;
        }

        return new ArrayList<>();
    }

    public String getOrganisationID(String organisationName) {
//        Organisation result = null;
//        try {
//            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
//                    organisationName + "';", TableObject.ORGANISATION);
//            result = (Organisation) client.readObjectFromServer();
//
//            return result.getOrgID();
//
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//
//        return null;
        List<Organisation> allOrganisations = getAllOrganisations();

        for (Organisation allOrganisation : allOrganisations) {
            System.out.println(allOrganisation.getOrgName().equals(organisationName));
            if (allOrganisation.getOrgName().equals(organisationName)) {
                return allOrganisation.getOrgID();
            }
        }
        return null;
    }

}