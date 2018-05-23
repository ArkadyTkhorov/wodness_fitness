package com.and.wodness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ivan on 8/31/2017.
 */

public class ModifyRequestInfo implements Serializable {

    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("code")
    @Expose
    String code;
    @SerializedName("new_password")
    @Expose
    String new_password;
    public ModifyRequestInfo()
    {

    }

    public ModifyRequestInfo(ModifyRequestInfo resetinfo){
        setEmail(resetinfo.getEmail());
    }
    public String getEmail() {
        return email==null?"":email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getNew_Password() {
        return new_password;
    }

    public void setNew_Password(String new_password) {
        this.new_password = new_password;
    }
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
