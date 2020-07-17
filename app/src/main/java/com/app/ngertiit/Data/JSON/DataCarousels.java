package com.app.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataCarousels {
    @SerializedName("order_id")
    String order_id;
    @SerializedName("name")
    String name;
    @SerializedName("link")
    String link;
    @SerializedName("image")
    String image;

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
