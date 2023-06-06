package com.chillar.manoramaapp;

class PreOrderModels {

    String ItemID,ItemName,ItemAvailbility,ItemPrice;
    int Isselected;

    public PreOrderModels(String itemID, String itemName, String itemAvailbility, String itemPrice, int isselected) {
        ItemID = itemID;
        ItemName = itemName;
        ItemAvailbility = itemAvailbility;
        ItemPrice = itemPrice;
        Isselected = isselected;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getItemAvailbility() {
        return ItemAvailbility;
    }

    public void setItemAvailbility(String itemAvailbility) {
        ItemAvailbility = itemAvailbility;
    }

    public String getItemPrice() {
        return ItemPrice;
    }

    public void setItemPrice(String itemPrice) {
        ItemPrice = itemPrice;
    }

    public int getIsselected() {
        return Isselected;
    }

    public void setIsselected(int isselected) {
        Isselected = isselected;
    }
}
