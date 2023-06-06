
package com.chillar.manoramaapp.Retrofit.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBoardCode {

    @SerializedName("totalTransaction")
    @Expose
    private Integer totalTransaction;
    @SerializedName("firstDate")
    @Expose
    private String firstDate;
    @SerializedName("lastDate")
    @Expose
    private String lastDate;
    @SerializedName("totalAmount")
    @Expose
    private String totalAmount;

    /**
     * No args constructor for use in serialization
     */
    public DashBoardCode() {
    }

    /**
     * @param totalTransaction
     * @param firstDate
     * @param lastDate
     */
    public DashBoardCode(Integer totalTransaction, String firstDate, String lastDate) {
        super();
        this.totalTransaction = totalTransaction;
        this.firstDate = firstDate;
        this.lastDate = lastDate;
    }

    public Integer getTotalTransaction() {
        return totalTransaction;
    }

    public void setTotalTransaction(Integer totalTransaction) {
        this.totalTransaction = totalTransaction;
    }

    public String getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(String firstDate) {
        this.firstDate = firstDate;
    }

    public String getLastDate() {
        return lastDate;
    }

    public void setLastDate(String lastDate) {
        this.lastDate = lastDate;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
