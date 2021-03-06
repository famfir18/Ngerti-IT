package com.app.ngertiit.Data.API;

import com.app.ngertiit.Data.JSON.DataAppState;
import com.app.ngertiit.Data.JSON.DataCarousels;
import com.app.ngertiit.Data.JSON.DataDictionary;
import com.app.ngertiit.Data.JSON.DataEvent;
import com.app.ngertiit.Data.JSON.DataHistory;
import com.app.ngertiit.Data.JSON.DataKritikSaran;
import com.app.ngertiit.Data.JSON.DataLifehacks;
import com.app.ngertiit.Data.JSON.DataSearch;
import com.app.ngertiit.Data.JSON.DataSolution;
import com.app.ngertiit.Data.JSON.DataTestSDG;


import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RestService {

    @GET("SDG4Lyfe/sdg_api.php")
    Call<List<DataTestSDG>> getDataSDG();

    @GET("carousels")
    Call<List<DataCarousels>> getDataCarousels();

    @GET("lifehacks/all")
    Call<List<DataLifehacks>> getDataLifehacks();

    @GET("search/{context}")
    Call<List<DataSearch>> getDataSearch(@Path("context") String context);

    @GET("solutions/all")
    Call<List<DataSolution>> getDataSolutions();

    @GET("dictionary/all")
    Call<List<DataDictionary>> getDataDictionary();

    @GET("dictionary/time")
    Call<List<DataDictionary>> getDataDictionarybyTime();

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

    @GET("appstate")
    Call<DataAppState> getDataAppState();

    @GET("event/today")
    Call<DataEvent> getDataEventToday();

    @GET("event/{id}")
    Call<DataEvent> getDataEventNotif(@Path("id") String id);
}