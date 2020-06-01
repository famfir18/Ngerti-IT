package com.example.ngertiit.Data.API;

import com.example.ngertiit.Data.JSON.DataCarousels;
import com.example.ngertiit.Data.JSON.DataTestSDG;


import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestService {

    @GET("SDG4Lyfe/sdg_api.php")
    Call<List<DataTestSDG>> getDataSDG();

    @GET("carousels")
    Call<List<DataCarousels>> getDataCarousels();
}
