package marketplace.Objects;

import java.io.Serializable;
import java.math.BigDecimal;

public class Organisation implements Serializable{
    private String orgID;
    private String orgName;
    private BigDecimal credits;

    public Organisation(String orgID, String orgName, BigDecimal credits){
        this.orgID = orgID;
        this.orgName = orgName;
        this.credits = credits;
    }

    public Organisation(){

    }

    public void setOrgID(String orgID){

        this.orgID = orgID;
    }

    public void setOrgName(String orgName){

        this.orgName = orgName;
    }

    public void setCredits(BigDecimal credits){

        this.credits = credits;
    }

    public String getOrgID(){

        return orgID;
    }

    public String getOrgName(){

        return orgName;
    }

    public BigDecimal getCredits(){

        return credits;
    }
}
