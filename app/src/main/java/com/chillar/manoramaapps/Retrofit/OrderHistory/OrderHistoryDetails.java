
package com.chillar.manoramaapps.Retrofit.OrderHistory;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderHistoryDetails {

    @SerializedName("code")
    @Expose
    private List<OrderHistoryCode> code = null;
    @SerializedName("status")
    @Expose
    private OrderHistoryStatus status;

    /**
     * No args constructor for use in serialization
     */
    public OrderHistoryDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public OrderHistoryDetails(List<OrderHistoryCode> code, OrderHistoryStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<OrderHistoryCode> getCode() {
        return code;
    }

    public void setCode(List<OrderHistoryCode> code) {
        this.code = code;
    }

    public OrderHistoryStatus getStatus() {
        return status;
    }

    public void setStatus(OrderHistoryStatus status) {
        this.status = status;
    }

}
