package com.example.ngertiit.Util;

public class DataFilter {
    private String Nama;
    private int ImageID;

    DataFilter(String Nama, int ImageID){
        this.Nama = Nama;
        this.ImageID = ImageID;
    }

    public String getNama() {
        return Nama;
    }

    int getImageID() {
        return ImageID;
    }
}
