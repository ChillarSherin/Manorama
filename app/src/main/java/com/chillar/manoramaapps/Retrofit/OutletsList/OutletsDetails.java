
package com.chillar.manoramaapps.Retrofit.OutletsList;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletsDetails {

    @SerializedName("code")
    @Expose
    private List<OutletsCode> code = null;
    @SerializedName("status")
    @Expose
    private OutletsStatus status;

    /**
     * No args constructor for use in serialization
     */
    public OutletsDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public OutletsDetails(List<OutletsCode> code, OutletsStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<OutletsCode> getCode() {
        return code;
    }

    public void setCode(List<OutletsCode> code) {
        this.code = code;
    }

    public OutletsStatus getStatus() {
        return status;
    }

    public void setStatus(OutletsStatus status) {
        this.status = status;
    }

}
