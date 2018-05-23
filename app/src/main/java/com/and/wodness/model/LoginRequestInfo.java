package com.and.wodness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ivan on 8/31/2017.
 */

public class LoginRequestInfo implements Serializable {

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    public LoginRequestInfo()
    {

    }

    public LoginRequestInfo(LoginRequestInfo logininfo){
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
