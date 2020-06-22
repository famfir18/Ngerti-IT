package com.example.ngertiit.Data.API;

import com.example.ngertiit.Data.JSON.BodyHistory;
import com.example.ngertiit.Data.JSON.DataCarousels;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataHistory;
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
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestService {

    @GET("SDG4Lyfe/sdg_api.php")
    Call<List<DataTestSDG>> getDataSDG();

    @GET("carousels")
    Call<List<DataCarousels>> getDataCarousels();

    @GET("lifehacks/all")
    Call<List<DataLifehacks>> getDataLifehacks();

    @GET("solutions/all")
    Call<List<DataSolution>> getDataSolutions();

    @GET("dictionary/all")
    Call<List<DataDictionary>> getDataDictionary();

    @POST("kritiksaran")
    Call<ResponseBody> postKritikSaran(@Body DataKritikSaran dataKritikSaran);

    @POST("historysejarah")
    Call<ResponseBody> postHistory(@Body DataHistory dataHistory);

    @GET("historysejarah/{id_pengguna}")
    Call<List<DataHistory>> getHistory(@Path("id_pengguna") String idPengguna);

    @GET("lifehacks/{id_artikel}/")
    Call<DataLifehacks> getDataLifehacksFiltered(@Path("id_artikel") String id);

    @GET("solutions/{id_artikel}/")
    Call<DataSolution> getDataSolutionsFiltered(@Path("id_artikel") String id);

    @GET("dictionary/{id_artikel}/")
    Call<DataDictionary> getDataDictionaryFiltered(@Path("id_artikel") String id);

}