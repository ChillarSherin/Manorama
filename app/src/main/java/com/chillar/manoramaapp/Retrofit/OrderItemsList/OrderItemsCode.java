
package com.chillar.manoramaapp.Retrofit.OrderItemsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemsCode {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemID")
    @Expose
    private String itemID;
    @SerializedName("itemPrice")
    @Expose
    private String itemPrice;
    @SerializedName("quantity")
    @Expose
    private String quantity;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("itemEditable")
    @Expose
    private String itemEditable;

    /**
     * No args constructor for use in serialization
     */
    public OrderItemsCode() {
    }

    /**
     * @param itemName
     * @param status
     * @param itemEditable
     * @param quantity
     * @param itemPrice
     */
    public OrderItemsCode(String itemName, String itemPrice, String quantity, String status, String itemEditable) {
        super();
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
        this.status = status;
        this.itemEditable = itemEditable;
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

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemEditable() {
        return itemEditable;
    }

    public void setItemEditable(String itemEditable) {
        this.itemEditable = itemEditable;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }
}
