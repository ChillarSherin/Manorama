
package com.chillar.manoramaapp.Retrofit.PreorderItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderDetailsCode {

    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("availability")
    @Expose
    private String availability;
    @SerializedName("isSelected")
    @Expose
    private Integer isSelected;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderDetailsCode() {
    }

    /**
     * 
     * @param itemName
     * @param itemID
     * @param isSelected
     * @param itemPrice
     * @param availability
     */
    public PreOrderDetailsCode(String itemID, String itemName, String itemPrice, String availability, Integer isSelected) {
        super();
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.availability = availability;
        this.isSelected = isSelected;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Integer getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(Integer isSelected) {
        this.isSelected = isSelected;
    }

}
