package com.app.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataAppState {
    @SerializedName("parameters")
    String appState;

    public String getAppState() {
        return appState;
    }

    public void setAppState(String appState) {
        this.appState = appState;
    }
}