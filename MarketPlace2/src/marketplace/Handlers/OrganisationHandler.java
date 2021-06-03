package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUIHandler;
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

    public Organisation getOrganisation(String orgID) throws Exception {
        List<Organisation> result = null;

        if (!orgID.contains("org")) {
            throw new Exception("Organisation couldn't be found");
        }

        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            result = (List) client.readListFromServer();
            return result.get(0);

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (IndexOutOfBoundsException exception) {
            throw new Exception("Organisation couldn't be found");
        }

        if (result == null) {
            throw new Exception("Organisation couldn't be found");
        }

        return result.get(0);
    }

    public void addOrganisation(String orgID, String orgName, double credits) throws Exception {

        if (orgName.length() < 2) {
            throw new Exception("Organisation name is too short");
        }

        if (orgName.length() > 200) {
            throw new Exception("Organisation name is too long");
        }

        if (credits > 1000000000 * 1000000000) {
            throw new Exception("Credits are too large");
        }

        if (organisationNameExists(orgName)) {
            throw new Exception("That organisation name is taken");
        }

        try {
            client.writeToServer("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES('" + orgID + "', '" + orgName
                    + "', '" + credits + "');", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException exception) {
            throw new Exception("That organisation couldn't be added");
        }
    }

    public void removeOrganisation(String orgID) throws Exception {
        try {
            client.writeToServer("DELETE FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException exception) {
            throw new Exception("That organisation couldn't be removed");
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

    public boolean organisationIDExists(String orgID) throws Exception {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();

            if (result.size() > 0 && result.get(0).getOrgID().equals(orgID)) {
                return true;
            }

        } catch (Exception exception) {
            throw new Exception("Organisation ID couldn't be found");
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

    public String getOrganisationID(String organisationName) throws Exception {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
                    organisationName + "';", TableObject.ORGANISATION);
            result = (List) client.readListFromServer();

            return result.get(0).getOrgID();

        } catch (Exception exception) {
            throw new Exception("Organisation does not exist");
        }
    }

    public boolean organisationNameExists(String organisationName) throws Exception {
        List<Organisation> result;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
                    organisationName + "';", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();

            if (result != null) {
                return true;
            } else {
                throw new Exception("Organisation does not exist");
            }

        } catch (Exception exception) {
            throw new Exception("Organisation does not exist");
        }
    }

    public void updateOrganisationCredits(String orgID, BigDecimal credits) throws Exception {

        if (credits == null || credits.compareTo(BigDecimal.valueOf(0)) != 1) {
            throw new Exception("Please enter a valid number");
        }

        if (credits.compareTo(getOrganisationTotalOwing(orgID)) != 1) {
            throw new Exception("Current buy orders don't allow that");
        }

        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET credits = '" + credits + "' WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    public BigDecimal getOrganisationTotalOwing(String organisationID) throws Exception {
        List<User> users = null;
        List<Order> activeBuyOrders = new ArrayList<>();
        BigDecimal result = BigDecimal.valueOf(0);


        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE orgID = '" +
                    organisationID + "';", TableObject.USER);
            users = ((List) client.readListFromServer());

            for (User user : users) {
                client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS WHERE userID = '" +
                        user.getUserID() + "';", TableObject.BUY_ORDER);
                activeBuyOrders = ((List) client.readListFromServer());
            }

            for (Order order : activeBuyOrders) {
                result = result.add(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            }
            return result;

        } catch (Exception exception) {
            throw new Exception("Error");
        }
    }

    public int getOrganisationSellingQuantity(String organisationID) throws Exception {
        List<User> users = null;
        List<Order> activeSellOrders = new ArrayList<>();
        int result = 0;

        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE orgID = '" +
                    organisationID + "';", TableObject.USER);
            users = ((List) client.readListFromServer());

            for (User user : users) {
                client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS WHERE userID = '" +
                        user.getUserID() + "';", TableObject.SELL_ORDER);
                activeSellOrders = ((List) client.readListFromServer());
            }

            for (Order order : activeSellOrders) {
                result += order.getQuantity();
            }

            return result;

        } catch (Exception exception) {
            throw new Exception("Error");
        }
    }
}