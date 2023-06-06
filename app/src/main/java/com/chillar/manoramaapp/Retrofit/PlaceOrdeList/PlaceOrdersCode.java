
package com.chillar.manoramaapp.Retrofit.PlaceOrdeList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrdersCode {

    @SerializedName("hardwareModuleID")
    @Expose
    private Integer hardwareModuleID;
    @SerializedName("hardwareModuleName")
    @Expose
    private String hardwareModuleName;
    @SerializedName("preorderSessionTimingID")
    @Expose
    private Integer preorderSessionTimingID;
    @SerializedName("preorderSessionName")
    @Expose
    private String preorderSessionName;
    @SerializedName("orderTime")
    @Expose
    private Integer orderTime;
    @SerializedName("prerderEndTiming")
    @Expose
    private String prerderEndTiming;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceOrdersCode() {
    }

    /**
     * 
     * @param preorderSessionTimingID
     * @param prerderEndTiming
     * @param hardwareModuleID
     * @param orderTime
     * @param hardwareModuleName
     * @param preorderSessionName
     */
    public PlaceOrdersCode(Integer hardwareModuleID, String hardwareModuleName, Integer preorderSessionTimingID, String preorderSessionName, Integer orderTime, String prerderEndTiming) {
        super();
        this.hardwareModuleID = hardwareModuleID;
        this.hardwareModuleName = hardwareModuleName;
        this.preorderSessionTimingID = preorderSessionTimingID;
        this.preorderSessionName = preorderSessionName;
        this.orderTime = orderTime;
        this.prerderEndTiming = prerderEndTiming;
    }

    public Integer getHardwareModuleID() {
        return hardwareModuleID;
    }

    public void setHardwareModuleID(Integer hardwareModuleID) {
        this.hardwareModuleID = hardwareModuleID;
    }

    public String getHardwareModuleName() {
        return hardwareModuleName;
    }

    public void setHardwareModuleName(String hardwareModuleName) {
        this.hardwareModuleName = hardwareModuleName;
    }

    public Integer getPreorderSessionTimingID() {
        return preorderSessionTimingID;
    }

    public void setPreorderSessionTimingID(Integer preorderSessionTimingID) {
        this.preorderSessionTimingID = preorderSessionTimingID;
    }

    public String getPreorderSessionName() {
        return preorderSessionName;
    }

    public void setPreorderSessionName(String preorderSessionName) {
        this.preorderSessionName = preorderSessionName;
    }

    public Integer getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Integer orderTime) {
        this.orderTime = orderTime;
    }

    public String getPrerderEndTiming() {
        return prerderEndTiming;
    }

    public void setPrerderEndTiming(String prerderEndTiming) {
        this.prerderEndTiming = prerderEndTiming;
    }

}
