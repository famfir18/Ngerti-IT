package com.app.ngertiit.Data.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {

    //URL LIVE
    public static final String URL_BASE = "https://banyakngerti.id/apis/semogacepetkaya/";

    //URL Development
//    public static final String URL_BASE = "http://25.67.180.195:8788/apis/semogacepetkaya/";

    //URL DEV mac kepin
//    public static final String URL_BASE = "http://25.26.227.186:8788//apis/semogacepetkaya/";

    public static Retrofit retrofit = null;


    public static Retrofit getAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getKritikSaran() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static Retrofit getDataLifehacks() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(URL_BASE)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
