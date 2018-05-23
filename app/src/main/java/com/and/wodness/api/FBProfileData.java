package com.and.wodness.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FBProfileData  implements Serializable{
    @SerializedName("is_silhouette")
    @Expose
    private boolean is_silhouette;

    @SerializedName("url")
    @Expose
    private String url;


    public boolean getIsSilhouette() {
        return is_silhouette;
    }

    public void setIsSilhouette(boolean is_silhouette) {
        this.is_silhouette = is_silhouette;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
