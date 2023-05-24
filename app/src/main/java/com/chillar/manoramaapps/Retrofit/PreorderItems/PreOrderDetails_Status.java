
package com.chillar.manoramaapps.Retrofit.PreorderItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderDetails_Status {

    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("message")
    @Expose
    private String message;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderDetails_Status() {
    }

    /**
     * 
     * @param message
     * @param code
     */
    public PreOrderDetails_Status(String code, String message) {
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
