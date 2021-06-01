package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.Objects.Order;
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
        List<Organisation> result = null;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION;", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
    }

    public Organisation getOrganisation(String orgID){
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            result = (List<Organisation> ) client.readListFromServer();

            if (result.size() > 0) {
                return result.get(0);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;
    }

    public void addOrganisation(String orgID, String orgName, double credits) {

        try {
            client.writeToServer("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES('" + orgID + "', '" + orgName
                    + "', '" + credits + "');", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void removeOrganisation(String orgID) {
        try {
            client.writeToServer("DELETE FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void updateOrganisation(String orgID, String orgName, double credits) {

        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET orgName = '" + orgName
                    + "', credits = '" + credits + "' WHERE orgID = '" + orgID + "');", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public boolean organisationIDExists(String orgID) {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();

            if (result.size() > 0 && result.get(0).getOrgID().equals(orgID)) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public boolean organisationNameExists(String organisationName) {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
                    organisationName + "';", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();

            if (result.size() > 0 && result.get(0).getOrgName().equals(organisationName)) {
                return true;
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return false;
    }

    public String newOrganisationID() {
        List<Organisation> organisations = getAllOrganisations();
        int currentID = 0;
        int maxID = 0;
        String holder;
        String newID;

        if (organisations.size() > 0) {
            for (Organisation organisation : organisations) {
                holder = (organisation.getOrgID());
                holder = holder.replace("org", ""); // remove org from eg "org5"
                currentID = (Integer.parseInt((holder)));

                if (currentID > maxID) {
                    maxID = currentID;
                }
            }

            newID = "org" + (maxID + 1);
            return newID;
        }

        return "org1"; // 1 if no organisations found
    }

    public List<String> getAllOrganisationsNames() {
        List<Organisation> allOrganisations = getAllOrganisations();
        List<String> allOrganisationsNames = new ArrayList<>();

        if (allOrganisations != null) {
            for (Organisation organisation : allOrganisations) {
                allOrganisationsNames.add(organisation.getOrgName());
            }

            return allOrganisationsNames;
        }

        return new ArrayList<>();
    }

    public String getOrganisationID(String organisationName) {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
                    organisationName + "';", TableObject.ORGANISATION);
            result = (List) client.readListFromServer();

            return result.get(0).getOrgID();

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;

    }

    public void updateOrganisationCredits(String orgID, BigDecimal credits) {
        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET credits = '" + credits + "' WHERE orgID = '" + orgID + "');", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal getOrganisationTotalOwing(String organisationID) {
        List<User> users;
        List<Order> activeBuyOrders = new ArrayList<>();
        BigDecimal result = BigDecimal.valueOf(0);

        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE orgID = '" +
                    organisationID + "';", TableObject.USER);
            users = (List) client.readListFromServer();

            for (User user : users) {
                client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS WHERE userID = '" +
                        user.getUserID() + "';", TableObject.BUY_ORDER);
                activeBuyOrders.add((Order) client.readListFromServer());
            }

            for (Order order : activeBuyOrders) {
                result.add(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            }

            return result;

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return null;

    }
}