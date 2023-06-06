
package com.chillar.manoramaapp.Retrofit.FoodSessions;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoodSessionsDetails {

    @SerializedName("code")
    @Expose
    private List<FoodSessionsCode> code = null;
    @SerializedName("status")
    @Expose
    private FoodSessionsStatus status;

    /**
     * No args constructor for use in serialization
     */
    public FoodSessionsDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public FoodSessionsDetails(List<FoodSessionsCode> code, FoodSessionsStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<FoodSessionsCode> getCode() {
        return code;
    }

    public void setCode(List<FoodSessionsCode> code) {
        this.code = code;
    }

    public FoodSessionsStatus getStatus() {
        return status;
    }

    public void setStatus(FoodSessionsStatus status) {
        this.status = status;
    }

}
