
package com.chillar.manoramaapp.Retrofit.Login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginCode {

    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("userTypeKey")
    @Expose
    private String userTypeKey;
    @SerializedName("employeeID")
    @Expose
    private String employeeID;
    @SerializedName("employeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("branchOfficeID")
    @Expose
    private String branchOfficeID;
    @SerializedName("branchName")
    @Expose
    private String branchName;
    @SerializedName("designationName")
    @Expose
    private String designationName;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("userID")
    @Expose
    private String userID;

    /**
     * No args constructor for use in serialization
     */
    public LoginCode() {
    }

    /**
     * @param userID
     * @param branchOfficeID
     * @param employeeID
     * @param userTypeKey
     * @param designationName
     * @param branchName
     * @param userName
     * @param departmentName
     * @param employeeCode
     */
    public LoginCode(String userName, String userTypeKey, String employeeID, String employeeCode, String branchOfficeID, String branchName, String designationName, String departmentName, String userID) {
        super();
        this.userName = userName;
        this.userTypeKey = userTypeKey;
        this.employeeID = employeeID;
        this.employeeCode = employeeCode;
        this.branchOfficeID = branchOfficeID;
        this.branchName = branchName;
        this.designationName = designationName;
        this.departmentName = departmentName;
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTypeKey() {
        return userTypeKey;
    }

    public void setUserTypeKey(String userTypeKey) {
        this.userTypeKey = userTypeKey;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getBranchOfficeID() {
        return branchOfficeID;
    }

    public void setBranchOfficeID(String branchOfficeID) {
        this.branchOfficeID = branchOfficeID;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getDesignationName() {
        return designationName;
    }

    public void setDesignationName(String designationName) {
        this.designationName = designationName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

}
