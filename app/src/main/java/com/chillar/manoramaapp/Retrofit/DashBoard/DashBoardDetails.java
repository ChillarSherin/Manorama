
package com.chillar.manoramaapp.Retrofit.DashBoard;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DashBoardDetails {

    @SerializedName("code")
    @Expose
    private DashBoardCode code;
    @SerializedName("status")
    @Expose
    private DashBoardStatus status;

    /**
     * No args constructor for use in serialization
     */
    public DashBoardDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public DashBoardDetails(DashBoardCode code, DashBoardStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public DashBoardCode getCode() {
        return code;
    }

    public void setCode(DashBoardCode code) {
        this.code = code;
    }

    public DashBoardStatus getStatus() {
        return status;
    }

    public void setStatus(DashBoardStatus status) {
        this.status = status;
    }

}
