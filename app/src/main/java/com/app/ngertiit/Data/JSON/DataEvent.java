package com.app.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataEvent {
    @SerializedName("image")
    String imageUrl;
    @SerializedName("slug")
    String id;
    @SerializedName("status")
    String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
