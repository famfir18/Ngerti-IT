package com.app.ngertiit;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataAppState;
import com.google.gson.Gson;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Splashscreen extends Activity {

    //Set waktu lama splashscreen
    private static int splashInterval = 2000;
    private ProgressBar mProgress;

    Dialog dialogLoading;

    @BindView(R.id.pse)
    ImageView pse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splashscreen);
//        mProgress = findViewById(R.id.simpleProgressBar);

        ButterKnife.bind(this);

        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(true);

        pse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://pse.kominfo.go.id/sistem/3452"));
                startActivity(intent);
            }
        });


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // TODO Auto-generated method stub
                    Gson gson = new Gson();

                    RestService apiService = APIClient.getAPI().create(RestService.class);
                    Call<DataAppState> call = apiService.getDataAppState();

                    call.enqueue(new Callback<DataAppState>() {
                        @Override
                        public void onResponse(Call<DataAppState> call, Response<DataAppState> response) {
                            System.out.println("App States nya apa : " + gson.toJson(response.body()));
                            String appState = response.body().getAppState();
                            System.out.println("App State : " + appState);

                            if (appState.equals("live")){
                                Intent i = new Intent(Splashscreen.this, WelcomeActivity.class);
                                startActivity(i);

                            } else if (appState.equals("maintenance")){
                                Intent i = new Intent(Splashscreen.this, Maintenance.class);
                                startActivity(i);
                            }
                        }

                        @Override
                        public void onFailure(Call<DataAppState> call, Throwable t) {
                            TextView gagal = dialogLoading.findViewById(R.id.tv_status);
                            ImageView imageGagal = dialogLoading.findViewById(R.id.iv_status);
                            gagal.setText("Loading gagal, mohon periksa koneksi anda lalu coba lagi");
                            imageGagal.setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));
                            imageGagal.setVisibility(View.VISIBLE);
                            Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                            dialogLoading.show();
                        }
                    });
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);


                //jeda selesai Splashscreen
                this.finish();
            }

            private void finish() {
                // TODO Auto-generated method stub

            }
        }, splashInterval);
    }
}
