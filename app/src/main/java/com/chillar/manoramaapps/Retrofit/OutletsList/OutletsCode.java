
package com.chillar.manoramaapps.Retrofit.OutletsList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OutletsCode {

    @SerializedName("hardwareModuleID")
    @Expose
    private String hardwareModuleID;
    @SerializedName("hardwareModuleName")
    @Expose
    private String hardwareModuleName;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;

    /**
     * No args constructor for use in serialization
     */
    public OutletsCode() {
    }

    /**
     * @param vendorName
     * @param hardwareModuleID
     * @param hardwareModuleName
     */
    public OutletsCode(String hardwareModuleID, String hardwareModuleName, String vendorName) {
        super();
        this.hardwareModuleID = hardwareModuleID;
        this.hardwareModuleName = hardwareModuleName;
        this.vendorName = vendorName;
    }

    public String getHardwareModuleID() {
        return hardwareModuleID;
    }

    public void setHardwareModuleID(String hardwareModuleID) {
        this.hardwareModuleID = hardwareModuleID;
    }

    public String getHardwareModuleName() {
        return hardwareModuleName;
    }

    public void setHardwareModuleName(String hardwareModuleName) {
        this.hardwareModuleName = hardwareModuleName;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

}
