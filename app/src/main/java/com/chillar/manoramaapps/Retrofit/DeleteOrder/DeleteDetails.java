
package com.chillar.manoramaapps.Retrofit.DeleteOrder;

import com.chillar.manoramaapps.Retrofit.Login.LoginCode;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteDetails {

    @SerializedName("code")
    @Expose
    private LoginCode code;
    @SerializedName("status")
    @Expose
    private DeleteStatus status;

    /**
     * No args constructor for use in serialization
     */
    public DeleteDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public DeleteDetails(LoginCode code, DeleteStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public LoginCode getCode() {
        return code;
    }

    public void setCode(LoginCode code) {
        this.code = code;
    }

    public DeleteStatus getStatus() {
        return status;
    }

    public void setStatus(DeleteStatus status) {
        this.status = status;
    }

}
