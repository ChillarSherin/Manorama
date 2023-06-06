
package com.chillar.manoramaapp.Retrofit.PlaceOrdeList;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrdersList_Class {

    @SerializedName("code")
    @Expose
    private List<PlaceOrdersCode> code = null;
    @SerializedName("status")
    @Expose
    private PlaceOrdersStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PlaceOrdersList_Class() {
    }

    /**
     * 
     * @param status
     * @param code
     */
    public PlaceOrdersList_Class(List<PlaceOrdersCode> code, PlaceOrdersStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PlaceOrdersCode> getCode() {
        return code;
    }

    public void setCode(List<PlaceOrdersCode> code) {
        this.code = code;
    }

    public PlaceOrdersStatus getStatus() {
        return status;
    }

    public void setStatus(PlaceOrdersStatus status) {
        this.status = status;
    }

}
