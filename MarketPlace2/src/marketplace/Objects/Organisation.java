package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Organisation class, each unique organisation.
 * Employs users who can use the organisation's credits to buy and sell assets.
 * Credits are used as the currency for this market.
 *
 * @see marketplace.Handlers.OrganisationHandler
 *
 */
public class Organisation implements Serializable{
    private String orgID;
    private String orgName;
    private BigDecimal credits;

    /**
     * Constructor for User object, parameters match column titles in database for USER_INFORMATION table.
     *
     * @param orgID is the unique identifier for each organisation, matches an ID in the ORGANISATIONAL_UNIT_INFORMATION
     *             table in the database. "org4" for example is an orgID.
     * @param orgName is the name of the organisation, this is a unique name for each organisation.
     * @param credits is the currency used to buy and sell assets in this market system.
     *
     */
    public Organisation(String orgID, String orgName, BigDecimal credits){
        this.orgID = orgID;
        this.orgName = orgName;
        this.credits = credits;
    }

    public Organisation(){}

    /**
     * @param orgID sets the organisation's ID
     */
    public void setOrgID(String orgID){
        this.orgID = orgID;
    }

    /**
     * @param orgName sets the organisation's name
     */
    public void setOrgName(String orgName){
        this.orgName = orgName;
    }

    /**
     * @param credits sets the organisation's credits
     */
    public void setCredits(BigDecimal credits){
        this.credits = credits;
    }

    /**
     * @return the organisation's ID
     */
    public String getOrgID(){
        return orgID;
    }

    /**
     * @return the organisation's name
     */
    public String getOrgName(){
        return orgName;
    }

    /**
     * @return the organisation's credits
     */
    public BigDecimal getCredits(){
        return credits;
    }
}
