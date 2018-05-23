package com.and.wodness.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Ivan on 8/31/2017.
 */

public class RegisterRequestInfo implements Serializable {

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("password")
    @Expose
    String password;

    @SerializedName("user_name")
    @Expose
    String user_name;

    @SerializedName("firstname")
    @Expose
    String firstname="";

    @SerializedName("lastname")
    @Expose
    String lastname="";


    @SerializedName("dob")
    @Expose
    String dob = "";

    @SerializedName("password_confirmation")
    @Expose
    String password_confirmation="";

    @SerializedName("gender")
    @Expose
    String gender="";

    @SerializedName("height")
    @Expose
    double height=0;

    @SerializedName("weight")
    @Expose
    double weight=0;

    @SerializedName("photo_url")
    @Expose
    String photo_url="";

    @SerializedName("country")
    @Expose
    String country="";


    @SerializedName("private_profile")
    @Expose
    int private_profile=0;

    @SerializedName("_token")
    @Expose
    String _token="";



    public RegisterRequestInfo()
    {

    }

    public RegisterRequestInfo(RegisterRequestInfo registerinfo){
        setEmail(registerinfo.getEmail());
        setPassword(registerinfo.getPassword());
        setUsername(registerinfo.getUsername());

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

    public String getPasswordConfirm() {
        return password_confirmation;
    }

    public void setPasswordConfirm(String password) {
        this.password_confirmation = password;
    }

    public String getUsername() { return user_name;}
    public void setUsername(String username){this.user_name = username;}

    public String getFirstname() { return firstname;}
    public void setFirstname(String firstname){this.firstname = firstname;}

    public String getLastname() { return lastname;}
    public void setLastname(String lastname){this.lastname = lastname;}

    public String getDob() { return dob;}
    public void setDob(String dob){this.dob = dob;}

    public String getPhoto_url() { return photo_url;}
    public void setPhoto_url(String photo_url){this.photo_url = photo_url;}

    public String getGender() { return gender;}
    public void setGender(String gender){this.gender = gender;}

    public String getCountry() { return country;}
    public void setCountry(String country){this.country = country;}

    public int getPrivate_profile() {return  private_profile;}
    public void setPrivate_profile(int private_profile){this.private_profile = private_profile;}

    public double getHeight() {return height;}
    public void setHeight(double height) {this.height = height;};


    public double getWeight() {return weight;}
    public void setWeight(double weight) {this.weight = weight;};

}
