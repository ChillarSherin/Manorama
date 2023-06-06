
package com.chillar.manoramaapp.Retrofit.PreorderLists;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderList_Class {

    @SerializedName("code")
    @Expose
    private List<PreOrderListCode> code = null;
    @SerializedName("status")
    @Expose
    private PreOrderListStatus status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderList_Class() {
    }

    /**
     * 
     * @param status
     * @param code
     */
    public PreOrderList_Class(List<PreOrderListCode> code, PreOrderListStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<PreOrderListCode> getCode() {
        return code;
    }

    public void setCode(List<PreOrderListCode> code) {
        this.code = code;
    }

    public PreOrderListStatus getStatus() {
        return status;
    }

    public void setStatus(PreOrderListStatus status) {
        this.status = status;
    }

}
