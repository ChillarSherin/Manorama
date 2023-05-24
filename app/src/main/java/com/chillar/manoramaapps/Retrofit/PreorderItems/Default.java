
package com.chillar.manoramaapps.Retrofit.PreorderItems;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Default {

    @SerializedName("itemName")
    @Expose
    private String itemName;
    @SerializedName("itemPrice")
    @Expose
    private String default_itemPrice;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Default() {
    }

    /**
     * 
     * @param itemName
     */
    public Default(String itemName) {
        super();
        this.itemName = itemName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getDefault_itemPrice() {
        return default_itemPrice;
    }

    public void setDefault_itemPrice(String default_itemPrice) {
        this.default_itemPrice = default_itemPrice;
    }
}
