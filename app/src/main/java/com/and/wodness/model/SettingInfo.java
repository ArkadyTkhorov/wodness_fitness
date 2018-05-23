package com.and.wodness.model;
/**
 * Created by Ivan on 8/24/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class SettingInfo implements Serializable {
    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("password")
    @Expose
    private String password;

    public SettingInfo()
    {

    }

    public SettingInfo(LoginRequestInfo logininfo){
        setEmail(logininfo.getEmail());
        setPassword(logininfo.getPassword());

    }
    public String getEmail() {
        return email==null?"":email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}