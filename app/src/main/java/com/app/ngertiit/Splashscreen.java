package com.app.ngertiit;

import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
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
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class Splashscreen extends Activity {

    //Set waktu lama splashscreen
    private static int splashInterval = 2000;
    private ProgressBar mProgress;

    Dialog dialogLoading;

    @BindView(R.id.pse)
    ImageView pse;

    // NOTE: Replace the below with your own ONESIGNAL_APP_ID
    private static final String ONESIGNAL_APP_ID = "b595c9ff-db01-4633-8416-76b30da34f27";

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

        // Verbose Logging set to help debug issues, remove before releasing your app.
        OneSignal.getDebug().setLogLevel(LogLevel.VERBOSE);

        // OneSignal Initialization
        OneSignal.initWithContext(this, ONESIGNAL_APP_ID);

        Log.d("famfir", "Success");

        // requestPermission will show the native Android notification permission prompt.
        // NOTE: It's recommended to use a OneSignal In-App Message to prompt instead.
        OneSignal.getNotifications().requestPermission(true, Continue.with(r -> {
            if (r.isSuccess()) {
                Timber.tag("famfir").d("success");
                if (r.getData()) {
                    // `requestPermission` completed successfully and the user has accepted permission
                }
                else {
                    // `requestPermission` completed successfully but the user has rejected permission
                }
            }
            else {
                Timber.tag("famfir").d("success");
                // `requestPermission` completed unsuccessfully, check `r.getThrowable()` for more info on the failure reason
            }
        }));

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
