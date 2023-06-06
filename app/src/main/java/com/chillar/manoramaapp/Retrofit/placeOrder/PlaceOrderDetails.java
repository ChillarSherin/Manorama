
package com.chillar.manoramaapp.Retrofit.placeOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderDetails {

    @SerializedName("code")
    @Expose
    private PlaceCode code;
    @SerializedName("status")
    @Expose
    private PlaceStatus status;

    /**
     * No args constructor for use in serialization
     */
    public PlaceOrderDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public PlaceOrderDetails(PlaceCode code, PlaceStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public PlaceCode getCode() {
        return code;
    }

    public void setCode(PlaceCode code) {
        this.code = code;
    }

    public PlaceStatus getStatus() {
        return status;
    }

    public void setStatus(PlaceStatus status) {
        this.status = status;
    }

}
