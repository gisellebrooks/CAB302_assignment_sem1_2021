package marketplace.Handlers;

import marketplace.Client.Client;
import marketplace.GUI.MainGUI;
import marketplace.Objects.Order;
import marketplace.Objects.Organisation;
import marketplace.Objects.User;
import marketplace.TableObject;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Handler class for Organisation object type. Handles main methods for interacting with Organisation type.
 * Interacts with ORGANISATIONAL_UNIT_INFORMATION table in database.
 *
 * @see Organisation
 *
 */
public class OrganisationHandler implements Serializable {

    private final Client client;

    /**
     * Constructor for each OrganisationHandler, just creates an instance and sets the client connection.
     *
     * @param client sets the connection to the server so that queries for the database can be sent.
     *
     */
    public OrganisationHandler(Client client){
        this.client = client;
    }

    /**
     * Sends a query to the server to then get all the organisations from the database,
     * ORGANISATIONAL_UNIT_INFORMATION table.
     *
     * @return a list of all organisations in the database, returns an empty list if no organisations are found.
     * @throws Exception when an error occurs in the process and returns an error message.
     *
     */
    public List<Organisation> getAllOrganisations() throws Exception {

        List<Organisation> result = null;

        // try and get all organisations from the database, ORGANISATIONAL_UNIT_INFORMATION table
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION;", TableObject.ORGANISATION);
            result = (List<Organisation>) client.readListFromServer();
        } catch (Exception exception) {

            // if an error occurs in trying to get the organisation list
            throw new Exception("Organisations can't be found");
        }

        // will return empty if no organisation exist in the database
        return result;
    }

    /**
     * Sends a query to the server to then get an organisation using their orgID.
     *
     * @return the organisation with that orgID, and throw exceptions if it can't be found.
     * @param orgID is the orgID of the organisation being looked for.
     *
     * @throws Exception when an error occurs in the process and returns an error message.
     * @throws Exception when the orgID can't be found in the database.
     *
     */
    public Organisation getOrganisation(String orgID) throws Exception {
        List result = null;

        if (!orgID.contains("org")) {
            throw new Exception("Organisation couldn't be found");
        }

        // try and find the organisation with the orgID provided
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            result = client.readListFromServer();
            return (Organisation) result.get(0);
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        } catch (IndexOutOfBoundsException exception) {
            // if no organisation with that orgID is found
            throw new Exception("Organisation couldn't be found");
        }

        // if no organisation is found throw an exception
        if (result == null) {
            throw new Exception("Organisation couldn't be found");
        }

        return null;
    }

    /**
     * Creates a new organisation in the ORGANISATIONAL_UNIT_INFORMATION table in the database if all values are valid.
     *
     * @throws Exception when an error occurs in the process and returns an error message.
     * @throws Exception when the orgName is too short or too long.
     * @throws Exception when the credits value is too large.
     * @throws Exception when the organisationName already exists in the database.
     *
     * @param orgID is the orgID of the organisation being looked for.
     * @param orgName is the name of the organisation, unique to each organisation.
     * @param credits is the amount of credits the users in the organisation have to spend on assets in the market.
     *
     */
    public void addOrganisation(String orgID, String orgName, double credits) throws Exception {

        if (orgName.length() < 2) {
            throw new Exception("Organisation name is too short");
        }

        if (orgName.length() > 249) {
            throw new Exception("Organisation name is too long");
        }

        if (credits > 1000000000) {
            throw new Exception("Credits are too large");
        }

        if (credits < 0) {
            throw new Exception("Credits must be positive");
        }

        if (getOrganisationID(orgName) != null) {
            throw new Exception("That organisation name is taken");
        }

        // try and add the new organisation to the database, ORGANISATIONAL_UNIT_INFORMATION table
        try {
            client.writeToServer("INSERT INTO ORGANISATIONAL_UNIT_INFORMATION VALUES('" + orgID + "', '" + orgName
                    + "', '" + credits + "');", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (IOException exception) {
            throw new Exception("That organisation couldn't be added");
        }
    }

    public String newOrganisationID() {

        int currentID;
        int maxID = 0;
        String holder;
        String newID;
        List<Organisation> organisations = null;

        try {
            organisations = getAllOrganisations();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // if there are organisations
        if (organisations.size() > 0) {

            // for all users, compare the numbers in their user ID to get the max ID
            for (Organisation organisation : organisations) {
                holder = (organisation.getOrgID());
                holder = holder.replace("org", ""); // eg remove "org" from "org5"
                currentID = (Integer.parseInt((holder)));

                if (currentID > maxID) {
                    maxID = currentID;
                }
            }

            // add the starting string for user ID to the start of the new number
            newID = "org" + (maxID + 1);
            return newID;
        }

        // org1 returned if no organisations found
        return "org1";
    }

    /**
     * Searches through all the organisation's to get each orgName and returns it in a list of strings.
     *
     * @return a lsit of strings with all organisation names in it.
     */
    public List<String> getAllOrganisationsNames() {

        List<Organisation> allOrganisations = null;
        List<String> allOrganisationsNames = new ArrayList<>();

        // try and get all organisations
        try {
            allOrganisations = getAllOrganisations();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        // if there are organisations, get their orgName and add it to the return list
        if (allOrganisations != null) {
            for (Organisation organisation : allOrganisations) {
                allOrganisationsNames.add(organisation.getOrgName());
            }
            return allOrganisationsNames;
        }

        // if no organisationNames found list will be empty
        return new ArrayList<>();
    }

    /**
     * Searches for an organisation with the given orgName and returns that organisations orgID.
     *
     * @throws Exception when an error occurs in the database process or the organisation can't be found.
     * @param organisationName the orgName of the organisation who's ID is to be searched for.
     * @return the orgID of the organisation with the orgName provided.
     */
    public String getOrganisationID(String organisationName) throws Exception {

        List<Organisation> result;

        // try and find an organisation with the given orgName
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgName = '" +
                    organisationName + "';", TableObject.ORGANISATION);
            result = (List) client.readListFromServer();

            // if no organisation can be found then return null
            if (result.size() == 0 || result == null) {
                return null;
            }

            // return the orgID of the found organisation
            return result.get(0).getOrgID();

        } catch (Exception exception) {
            throw new Exception("Organisation does not exist");
        }
    }

    public boolean organisationHasCredits(String orgID, BigDecimal totalPrice) throws Exception {
        List<Organisation> orgInformation;
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            orgInformation = client.readListFromServer();

            if (orgInformation != null){
                if (orgInformation.get(0).getCredits().compareTo(totalPrice) >= 0){
                    return true;
                }
            }

        } catch (Exception exception) {
            throw new Exception("Organisation does not exist");
        }
        return false;
    }

    public BigDecimal organisationCredits(String orgID) throws Exception {
        List<Organisation> orgInformation;
        BigDecimal result = new BigDecimal(0);
        try {
            client.writeToServer("SELECT * FROM ORGANISATIONAL_UNIT_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.ORGANISATION);
            orgInformation = client.readListFromServer();

            if (orgInformation != null){
                result = orgInformation.get(0).getCredits();
            }

        } catch (Exception exception) {
            throw new Exception("Organisation does not exist");
        }
        return result;
    }

    public void updateOrganisationCredits(String orgID, BigDecimal credits) throws Exception {

        // if the provided credits is below zero or is empty/null
        if (credits == null || credits.compareTo(BigDecimal.valueOf(0)) != 1) {
            throw new Exception("Please enter a valid number");
        }

        // if the organisation currently owes more credits in active buy order than the new credits amount allows
        if (credits.compareTo(getOrganisationTotalOwing(orgID)) != 1) {
            throw new Exception("Current buy orders don't allow that");
        }

        // try to update the organisation's credits
        try {
            client.writeToServer("UPDATE ORGANISATIONAL_UNIT_INFORMATION SET credits = '" + credits +
                    "' WHERE orgID = '" + orgID + "';", TableObject.ORGANISATION);
            client.readListFromServer();
        } catch (Exception exception) {
            throw new Exception(exception.getMessage());
        }
    }

    /**
     * Get the total amount of credits that an organisation owes in active buy orders. So an overdraft can't occur.
     *
     * @throws Exception when an error occurs in the database process or the organisation can't be found.
     * @return the bigdecmial of the organisation's current active buy order's credit amount.
     * @param orgID for finding the organisation to check their active buy orders and to update their values.
     *
     */
    public BigDecimal getOrganisationTotalOwing(String orgID) throws Exception {

        List<User> users;
        List<Order> activeBuyOrders = new ArrayList<>();
        BigDecimal result = BigDecimal.valueOf(0);

        // try and get all the users in the organisation and add the total credits in each of their active buy orders
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE orgID = '" +
                    orgID + "';", TableObject.USER);
            users = (List) client.readListFromServer();

            // for all the users in the provided organisation check if they have active buy orders
            for (User user : users) {
                client.writeToServer("SELECT * FROM ACTIVE_BUY_ORDERS WHERE userID = '" +
                        user.getUserID() + "';", TableObject.BUY_ORDER);
                activeBuyOrders = (List) client.readListFromServer();
            }

            // for each active buy order for that organisation, add the credits involved to the total owing value
            for (Order order : activeBuyOrders) {
                result = result.add(order.getPrice().multiply(BigDecimal.valueOf(order.getQuantity())));
            }

            return result;

        } catch (Exception exception) {
            throw new Exception("Error");
        }
    }

    /**
     * Get the total amount of an asset that an organisation is selling. So an overdraft can't occur in Inventory table.
     *
     * @throws Exception when an error occurs in the database process or the organisation can't be found.
     * @return the int quantity of that asset that an organisation has in active sell orders.
     *
     * @param orgID for finding the organisation to check their active buy orders and to update their values.
     * @param assetID is the identifier of the asset being checked for quantity.
     *
     */
    public int getOrganisationSellingQuantity(String orgID, String assetID) throws Exception {

        List<User> users;
        List<Order> activeSellOrders = new ArrayList<>();
        int result = 0;

        // try and get all active sell orders from the provided organisation with the provided assetID
        try {
            client.writeToServer("SELECT * FROM USER_INFORMATION WHERE orgID = '" +
                    orgID  +  "';", TableObject.USER);
            users = (List) client.readListFromServer();

            // for each user in the provided organisation get their active sell orders if they have the assetID
            for (User user : users) {
                client.writeToServer("SELECT * FROM ACTIVE_SELL_ORDERS WHERE userID = '" +
                        user.getUserID() + "' assetID = '" + assetID + "';", TableObject.SELL_ORDER);
                activeSellOrders = (List) client.readListFromServer();
            }

            // for each active sell order from that organisation and for that asset, add it's quantity together
            for (Order order : activeSellOrders) {
                result += order.getQuantity();
            }

            return result;

        } catch (Exception exception) {
            throw new Exception("Error");
        }
    }
}