
package com.chillar.manoramaapp.Retrofit.PreorderItems;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PreOrderDetails {

    @SerializedName("code")
    @Expose
    private List<PreOrderDetailsCode> code = null;
    @SerializedName("default")
    @Expose
    private List<Default> _default = null;
    @SerializedName("status")
    @Expose
    private PreOrderDetails_Status status;

    /**
     * No args constructor for use in serialization
     * 
     */
    public PreOrderDetails() {
    }

    /**
     * 
     * @param status
     * @param _default
     * @param code
     */
    public PreOrderDetails(List<PreOrderDetailsCode> code, List<Default> _default, PreOrderDetails_Status status) {
        super();
        this.code = code;
        this._default = _default;
        this.status = status;
    }

    public List<PreOrderDetailsCode> getCode() {
        return code;
    }

    public void setCode(List<PreOrderDetailsCode> code) {
        this.code = code;
    }

    public List<Default> getDefault() {
        return _default;
    }

    public void setDefault(List<Default> _default) {
        this._default = _default;
    }

    public PreOrderDetails_Status getStatus() {
        return status;
    }

    public void setStatus(PreOrderDetails_Status status) {
        this.status = status;
    }

}
