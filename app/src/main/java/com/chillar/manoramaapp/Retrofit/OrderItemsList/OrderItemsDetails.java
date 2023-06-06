
package com.chillar.manoramaapp.Retrofit.OrderItemsList;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OrderItemsDetails {

    @SerializedName("code")
    @Expose
    private List<OrderItemsCode> code = null;
    @SerializedName("status")
    @Expose
    private OrderItemsStatus status;

    /**
     * No args constructor for use in serialization
     */
    public OrderItemsDetails() {
    }

    /**
     * @param status
     * @param code
     */
    public OrderItemsDetails(List<OrderItemsCode> code, OrderItemsStatus status) {
        super();
        this.code = code;
        this.status = status;
    }

    public List<OrderItemsCode> getCode() {
        return code;
    }

    public void setCode(List<OrderItemsCode> code) {
        this.code = code;
    }

    public OrderItemsStatus getStatus() {
        return status;
    }

    public void setStatus(OrderItemsStatus status) {
        this.status = status;
    }

}
