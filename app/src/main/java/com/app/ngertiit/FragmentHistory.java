package com.app.ngertiit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.ngertiit.Adapter.HistoryAdapter;
import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataHistory;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentHistory extends Fragment implements HistoryAdapter.OnItemSelected{

    @BindView(R.id.rv_history)
    RecyclerView rvHistory;
    @BindView(R.id.layout_gagal)
    RelativeLayout layoutGagal;
    ArrayList<String> source;
    Context context;
    RecyclerView.LayoutManager rvLayoutManager;
    LinearLayoutManager layoutManager;
    HistoryAdapter adapter;

    TelephonyManager telephonyManager;
    String idDevice;
    List<DataHistory> historyList;

    TextView toolbarText;

//    Handler delay = new Handler();
//    final static int DELAY_DIALOG = 500;

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

        toolbarText = getActivity().findViewById(R.id.toolbar_text);
        toolbarText.setText("History");

        assert context != null;
        dialogLoading = new Dialog(context);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);

        Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        dialogLoading.show();

        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        idDevice = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("ID nya = " + idDevice);

//        initView();
        getDataHistory();
//        delay.postDelayed(this::getDataHistory, DELAY_DIALOG);
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

                if (response.body().get(0).getStatus() == null){
                    dialogLoading.dismiss();
                    historyList = response.body();
                    Log.d("TAG","Response Berhasil = "+ gson.toJson(historyList));
                    adapter.setMovieList(historyList);
                } else  if (response.body().get(0).getStatus().equals("1001")){
                    dialogLoading.dismiss();
                    layoutGagal.setVisibility(View.VISIBLE);
                }
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

//        rvHistory.setAdapter(dictionaryAdapter);

//        layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//        rvHistory.setLayoutManager(layoutManager);
    }

    @Override
    public void onSelected(DataHistory dataHistory) {

        Gson gson = new Gson();
        String link = dataHistory.getLinkArtikel();
        String idArtikel = String.valueOf(dataHistory.getIdArtikel());

        System.out.println("ID Artikel = " + idArtikel);

        if (link.equals("Life-hack")) {

//            RestService apiService = APIClient.getAPI().create(RestService.class);
//            Call<DataLifehacks> call = apiService.getDataLifehacksFiltered(idArtikel);

            Intent intent = new Intent(context, KontenLifehackAct.class);
            intent.putExtra("idArtikel", idArtikel);
            startActivity(intent);

        } else if (link.equals("Solusi")){
//            RestService apiService = APIClient.getAPI().create(RestService.class);
//            Call<DataLifehacks> call = apiService.getDataLifehacksFiltered(idArtikel);

            Intent intent = new Intent(context, KontenSolusiAct.class);
            intent.putExtra("idArtikel", idArtikel);
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
