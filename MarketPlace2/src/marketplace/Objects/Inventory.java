package marketplace.Objects;

import java.io.Serializable;

/**
 * Inventory class, each asset instance connected to an organisation.
 * so CPU in org1 is one instance of inventory.
 * Used to record what assets and how many an organisation owns.
 *
 * @see marketplace.Handlers.InventoryHandler
 *
 */
public class Inventory implements Serializable{
    private String assetID;
    private String assetName;
    private String orgID;
    private int quantity;

    /**
     * Constructor for User object, parameters match column titles in database for INVENTORY table.
     *
     * @param assetID is the unique identifier for each asset - organisation instance. "asset5" for example.
     * @param assetName is the name of each asset, "CPU HOURS" for example.
     * @param orgID is the associated organisation ID, "org5" for example.
     * @param quantity
     *
     */
    public Inventory(String assetID, String assetName, String orgID, int quantity){
        this.assetID = assetID;
        this.assetName = assetName;
        this.orgID = orgID;
        this.quantity = quantity;
    }

    public Inventory(){}

    /**
     * @param assetID sets the asset's ID
     */
    public void setAssetID(String assetID){
        this.assetID = assetID;
    }

    /**
     * @param assetName sets the asset's name
     */
    public void setAssetName(String assetName){
        this.assetName = assetName;
    }

    /**
     * @param orgID sets the asset's organisation ID, the asset in that organisation
     */
    public void setOrgID(String orgID){
        this.orgID = orgID;
    }

    /**
     * @param quantity sets the asset's quantity in that organisation
     */
    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    /**
     * @return the asset's ID
     */
    public String getAssetID(){
        return assetID;
    }

    /**
     * @return the asset's name
     */
    public String getAssetName(){
        return assetName;
    }

    /**
     * @return the asset's organisation ID
     */
    public String getOrgID(){
        return orgID;
    }

    /**
     * @return the asset's quantity in that organisation
     */
    public int getQuantity(){
        return quantity;
    }
}
