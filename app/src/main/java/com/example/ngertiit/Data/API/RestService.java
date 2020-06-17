package com.example.ngertiit.Data.API;

import com.example.ngertiit.Data.JSON.DataCarousels;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataKritikSaran;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.example.ngertiit.Data.JSON.DataTestSDG;


import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestService {

    @GET("SDG4Lyfe/sdg_api.php")
    Call<List<DataTestSDG>> getDataSDG();

    @GET("carousels")
    Call<List<DataCarousels>> getDataCarousels();

    @GET("lifehacks")
    Call<List<DataLifehacks>> getDataLifehacks();

    @GET("solutions")
    Call<List<DataSolution>> getDataSolutions();

    @GET("dictionary")
    Call<List<DataDictionary>> getDataDictionary();

    @POST("kritiksaran")
    Call<ResponseBody> postKritikSaran(@Body DataKritikSaran dataKritikSaran);
}