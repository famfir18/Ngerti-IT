package com.example.ngertiit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ngertiit.Adapter.DictionaryAdapterMenu;
import com.example.ngertiit.Adapter.HistoryAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.BodyHistory;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataHistory;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SplittableRandom;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHistory extends Fragment implements HistoryAdapter.OnItemSelected{

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    ArrayList<String> source;
    Context context;
    RecyclerView.LayoutManager rvLayoutManager;
    LinearLayoutManager layoutManager;
    HistoryAdapter adapter;

    TelephonyManager telephonyManager;
    String idDevice;
    List<DataHistory> historyList;

    CardView cardLoading;

    Handler delay = new Handler();
    final static int DELAY_DIALOG = 1000;

    Dialog dialogLoading;

    public FragmentHistory() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_history,container,false);

        ButterKnife.bind(this,view);
        context = getContext();

        final Animation animScaleTitle = AnimationUtils.loadAnimation(context, R.anim.anim_scale_dialog);


        assert context != null;
        dialogLoading = new Dialog(context);
        dialogLoading.setContentView(R.layout.dialog_kritik);
        dialogLoading.setCancelable(false);

        Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        cardLoading = dialogLoading.findViewById(R.id.card_kritik);
        cardLoading.startAnimation(animScaleTitle);
        dialogLoading.show();

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        idDevice = telephonyManager.getDeviceId();
        System.out.println("ID nya = " + idDevice);

//        initView();
        delay.postDelayed(this::getDataHistory, DELAY_DIALOG);
//        getDataHistory();
        return view;

    }

    private void getDataHistory() {
        Gson gson = new Gson();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvHistory.setLayoutManager(layoutManager);
        adapter = new HistoryAdapter(context, historyList, this);
        rvHistory.setAdapter(adapter);

        /*BodyHistory bodyHistory = new BodyHistory();
        bodyHistory.setIdUser(idDevice);*/

//        Log.d("TAG","Data History = "+ gson.toJson(bodyHistory));


        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataHistory>> call = apiService.getHistory(idDevice);

        call.enqueue(new Callback<List<DataHistory>>() {
            @Override
            public void onResponse(Call<List<DataHistory>> call, Response<List<DataHistory>> response) {

                dialogLoading.dismiss();
                historyList = response.body();
                Log.d("TAG","Response Berhasil = "+ gson.toJson(historyList));
                adapter.setMovieList(historyList);
            }

            @Override
            public void onFailure(Call<List<DataHistory>> call, Throwable t) {

                dialogLoading.setCancelable(true);
                TextView gagal = dialogLoading.findViewById(R.id.tv_status);
                ImageView imageGagal = dialogLoading.findViewById(R.id.iv_status);
                gagal.setText("Loading gagal, mohon periksa koneksi anda lalu coba lagi");
                imageGagal.setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));
                imageGagal.setVisibility(View.VISIBLE);


                Log.d("TAG","Response Gagal = "+t.toString());
            }
        });
    }

    private void initView() {
//        content();

//        rvLayoutManager = new LinearLayoutManager(context);
//        rvHistory.setLayoutManager(rvLayoutManager);

//        rvHistory.setAdapter(adapter);

//        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        rvHistory.setLayoutManager(layoutManager);
    }

    private void content() {
        source = new ArrayList<>();
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Cara install Ms. Office pada Windows (..terusan judul)");
        source.add("Google Chrome terasa lemot? Begini caranya (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
        source.add("Wifi mati. Ga bisa konek ke internet. (..terusan judul)");
        source.add("Tombol FN gak bisa berfungsi. (..terusan judul)");
        source.add("Mau beli laptop tapi bingung seperti apa? (..terusan judul)");
    }

    @Override
    public void onSelected(DataHistory dataHistory) {

        Gson gson = new Gson();
        String link = dataHistory.getLinkArtikel();
        String idArtikel = String.valueOf(dataHistory.getIdArtikel());

        if (link.equals("Life-hack")) {

//            RestService apiService = APIClient.getAPI().create(RestService.class);
//            Call<DataLifehacks> call = apiService.getDataLifehacksFiltered(idArtikel);

            Intent intent = new Intent(context, KontenLifehackAct.class);
            intent.putExtra(KontenLifehackAct.ID_KONTEN, idArtikel);
            startActivity(intent);

        } else if (link.contains("Solution")){
//            RestService apiService = APIClient.getAPI().create(RestService.class);
//            Call<DataLifehacks> call = apiService.getDataLifehacksFiltered(idArtikel);

            Intent intent = new Intent(context, KontenSolusiAct.class);
            intent.putExtra(KontenSolusiAct.ID_KONTEN, idArtikel);
            startActivity(intent);
        }

          /*  call.enqueue(new Callback<DataLifehacks>() {
                @Override
                public void onResponse(Call<DataLifehacks> call, Response<DataLifehacks> response) {
                   *//* Intent intent = new Intent(context, KontenLifehackAct.class);
                    intent.putExtra(KontenLifehackAct.ID_KONTEN, idArtikel);
                    startActivity(intent);*//*
                }

                @Override
                public void onFailure(Call<DataLifehacks> call, Throwable t) {

                }
            });*/
//        }
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
