package com.app.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class BodyHistory {
    @SerializedName("id_user")
    String idUser;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
