package com.and.wodness.api;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FBProfilePictureRedirectResponse implements Serializable {
    @SerializedName("data")
    @Expose
    private FBProfileData data;


    public FBProfileData getData() {
        return data;
    }

    public void setData(FBProfileData data) {
        this.data = data;
    }
}
