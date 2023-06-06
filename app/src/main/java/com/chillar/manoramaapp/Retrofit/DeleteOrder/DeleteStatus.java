
package com.chillar.manoramaapp.Retrofit.DeleteOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteStatus {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     */
    public DeleteStatus() {
    }

    /**
     * @param message
     * @param code
     */
    public DeleteStatus(String code, String message) {
        super();
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
