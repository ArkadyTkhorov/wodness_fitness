package com.and.wodness.model;
/**
 * Created by Ivan on 8/24/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.security.Timestamp;
import java.util.Date;

public class UserInfo implements Serializable {

    @SerializedName("id")
    @Expose
    int _id;

    @SerializedName("firstname")
    @Expose
    String firstname;

    @SerializedName("lastname")
    @Expose
    String lastname;


    @SerializedName("dob")
    @Expose
    String dob;


    @SerializedName("gender")
    @Expose
    String gender;

    @SerializedName("height")
    @Expose
    double height;

    @SerializedName("weight")
    @Expose
    double weight;

    @SerializedName("photo_url")
    @Expose
    String photo_url;

    @SerializedName("user_name")
    @Expose
    String user_name;

    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("country")
    @Expose
    String country;

    @SerializedName("password")
    @Expose
    String password;


    @SerializedName("private_profile")
    @Expose
    int private_profile;

    @SerializedName("remember_token")
    @Expose
    String remember_token;

    @SerializedName("created_at")
    @Expose
    String created_at;

    @SerializedName("updated_at")
    @Expose
    String updated_at;

    /**
     * No args constructor for use in serialization
     *
     */

    private final static long serialVersionUID = 2326427430815069605L;

    /**
     * No args constructor for use in serialization
     *
     */
    public UserInfo() {
    }






}
