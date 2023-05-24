
package com.chillar.manoramaapps.Retrofit.FoodSessions;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodSessionsCode {

    @SerializedName("preorderSessionTimingID")
    @Expose
    private String preorderSessionTimingID;
    @SerializedName("preorderSessionName")
    @Expose
    private String preorderSessionName;
    @SerializedName("orderTime")
    @Expose
    private String orderTime;
    @SerializedName("prerderEndTiming")
    @Expose
    private String prerderEndTiming;

    /**
     * No args constructor for use in serialization
     */
    public FoodSessionsCode() {
    }

    /**
     * @param preorderSessionTimingID
     * @param prerderEndTiming
     * @param orderTime
     * @param preorderSessionName
     */
    public FoodSessionsCode(String preorderSessionTimingID, String preorderSessionName, String orderTime, String prerderEndTiming) {
        super();
        this.preorderSessionTimingID = preorderSessionTimingID;
        this.preorderSessionName = preorderSessionName;
        this.orderTime = orderTime;
        this.prerderEndTiming = prerderEndTiming;
    }

    public String getPreorderSessionTimingID() {
        return preorderSessionTimingID;
    }

    public void setPreorderSessionTimingID(String preorderSessionTimingID) {
        this.preorderSessionTimingID = preorderSessionTimingID;
    }

    public String getPreorderSessionName() {
        return preorderSessionName;
    }

    public void setPreorderSessionName(String preorderSessionName) {
        this.preorderSessionName = preorderSessionName;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getPrerderEndTiming() {
        return prerderEndTiming;
    }

    public void setPrerderEndTiming(String prerderEndTiming) {
        this.prerderEndTiming = prerderEndTiming;
    }

}
