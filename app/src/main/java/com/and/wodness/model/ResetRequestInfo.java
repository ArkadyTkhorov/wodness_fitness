package com.and.wodness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ivan on 8/31/2017.
 */

public class ResetRequestInfo implements Serializable {

    @SerializedName("email")
    @Expose
    String email;
    public ResetRequestInfo()
    {

    }

    public ResetRequestInfo(ResetRequestInfo resetinfo){
        setEmail(resetinfo.getEmail());
    }
    public String getEmail() {
        return email==null?"":email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
