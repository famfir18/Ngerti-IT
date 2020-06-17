package com.example.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataKritikSaran {
    @SerializedName("email")
    String email;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
