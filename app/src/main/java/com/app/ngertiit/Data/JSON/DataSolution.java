package com.app.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataSolution {
    @SerializedName("id")
    int id;
    @SerializedName("title")
    String title;
    @SerializedName("description")
    String description;
    @SerializedName("category")
    String category;
    @SerializedName("image")
    String image;
    @SerializedName("url_method_one")
    String methodOne;
    @SerializedName("url_method_two")
    String methodTwo;
    @SerializedName("url_method_three")
    String methodThree;
    @SerializedName("url_method_four")
    String methodFour;

    public String getMethodOne() {
        return methodOne;
    }

    public void setMethodOne(String methodOne) {
        this.methodOne = methodOne;
    }

    public String getMethodTwo() {
        return methodTwo;
    }

    public void setMethodTwo(String methodTwo) {
        this.methodTwo = methodTwo;
    }

    public String getMethodThree() {
        return methodThree;
    }

    public void setMethodThree(String methodThree) {
        this.methodThree = methodThree;
    }

    public String getMethodFour() {
        return methodFour;
    }

    public void setMethodFour(String methodFour) {
        this.methodFour = methodFour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
