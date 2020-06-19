package com.example.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataHistory {
    @SerializedName("id_user")
    String idUser;
    @SerializedName("link_article")
    String linkArtikel;
    @SerializedName("id_artikel")
    int idArtikel;
    @SerializedName("judul")
    String judulArtikel;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getLinkArtikel() {
        return linkArtikel;
    }

    public void setLinkArtikel(String linkArtikel) {
        this.linkArtikel = linkArtikel;
    }

    public int getIdArtikel() {
        return idArtikel;
    }


    public void setIdArtikel(int idArtikel) {
        this.idArtikel = idArtikel;
    }

    public String getJudulArtikel() {
        return judulArtikel;
    }

    public void setJudulArtikel(String judulArtikel) {
        this.judulArtikel = judulArtikel;
    }
}
