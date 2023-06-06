
package com.chillar.manoramaapp.Retrofit.placeOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceCode {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("count")
    @Expose
    private String count;

    /**
     * No args constructor for use in serialization
     */
    public PlaceCode() {
    }

    /**
     * @param count
     * @param status
     */
    public PlaceCode(String status, String count) {
        super();
        this.status = status;
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

}
