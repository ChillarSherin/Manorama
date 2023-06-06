
package com.chillar.manoramaapp.Retrofit.PreorderLists;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderListStatus {

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
    public PreOrderListStatus() {
    }

    /**
     * 
     * @param message
     * @param code
     */
    public PreOrderListStatus(String code, String message) {
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
