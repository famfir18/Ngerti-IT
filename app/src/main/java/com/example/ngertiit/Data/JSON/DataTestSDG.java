package com.example.ngertiit.Data.JSON;

import com.google.gson.annotations.SerializedName;

public class DataTestSDG {
    @SerializedName("eventname")
    String eventName;
    @SerializedName("lokasi")
    String lokasi;
    @SerializedName("tanggal")
    String tanggal;
    @SerializedName("deskripsi")
    String deskripsi;

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getLokasi() {
        return lokasi;
    }

    public void setLokasi(String lokasi) {
        this.lokasi = lokasi;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }
}
