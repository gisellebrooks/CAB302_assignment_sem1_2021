package marketplace.Objects;

import java.io.Serializable;

public class Inventory implements Serializable{
    private String assetID;
    private String assetName;
    private String orgID;
    private int quantity;

    public Inventory(String assetID, String assetName, String orgID, int quantity){
        this.assetID = assetID;
        this.assetName = assetName;
        this.orgID = orgID;
        this.quantity = quantity;
    }

    public Inventory(){

    }

    public void setAssetID(String assetID){

        this.assetID = assetID;
    }

    public void setAssetName(String assetName){

        this.assetName = assetName;
    }

    public void setOrgID(String orgID){

        this.orgID = orgID;
    }

    public void setQuantity(int quantity){

        this.quantity = quantity;
    }

    public String getAssetID(){

        return assetID;
    }

    public String getAssetName(){

        return assetName;
    }

    public String getOrgID(){

        return orgID;
    }

    public int getQuantity(){

        return quantity;
    }
}
