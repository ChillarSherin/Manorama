package com.chillar.manoramaapp.Adapter;

/**
 * Created by Shanil on 12/6/2017.
 */

public class PreOrderDataModel {

    String itemID, itemName, itemPrice, availability, itemQty;
    Boolean isItemChecked;
    int itemPos;

    public PreOrderDataModel(String itemID, String itemName, String itemPrice, String availability, Boolean isItemChecked, String qty, int pos) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.availability = availability;
        this.isItemChecked = isItemChecked;
        this.itemQty = qty;
        this.itemPos = pos;
    }

    public String getItemID() {
        return itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public String getAvailability() {
        return availability;
    }

    public Boolean getIsItemChecked() {
        return isItemChecked;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public void setItemChecked(Boolean itemChecked) {
        isItemChecked = itemChecked;
    }

    public int getItemPos() {
        return itemPos;
    }

    public void setItemPos(int itemPos) {
        this.itemPos = itemPos;
    }
}
