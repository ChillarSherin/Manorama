
package com.chillar.manoramaapp.Retrofit.OrderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderHistoryCode {

    @SerializedName("orderDate")
    @Expose
    private String orderDate;
    @SerializedName("orderSession")
    @Expose
    private String orderSession;
    @SerializedName("outletName")
    @Expose
    private String outletName;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("itemSaleTransactionID")
    @Expose
    private String itemSaleTransactionID;
    @SerializedName("totalEditable")
    @Expose
    private String totalEditable;

    /**
     * No args constructor for use in serialization
     */
    public OrderHistoryCode() {
    }

    /**
     * @param status
     * @param orderDate
     * @param totalAmount
     * @param orderSession
     * @param outletName
     */
    public OrderHistoryCode(String orderDate, String orderSession, String outletName, String totalAmount, String status) {
        super();
        this.orderDate = orderDate;
        this.orderSession = orderSession;
        this.outletName = outletName;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderSession() {
        return orderSession;
    }

    public void setOrderSession(String orderSession) {
        this.orderSession = orderSession;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getItemSaleTransactionID() {
        return itemSaleTransactionID;
    }

    public void setItemSaleTransactionID(String itemSaleTransactionID) {
        this.itemSaleTransactionID = itemSaleTransactionID;
    }

    public String getTotalEditable() {
        return totalEditable;
    }

    public void setTotalEditable(String totalEditable) {
        this.totalEditable = totalEditable;
    }
}
