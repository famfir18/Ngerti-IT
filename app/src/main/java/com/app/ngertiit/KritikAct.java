package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataKritikSaran;
import com.google.android.material.snackbar.Snackbar;
import com.novoda.merlin.Merlin;
import com.novoda.merlin.MerlinsBeard;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KritikAct extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_nama)
    EditText etNama;
    @BindView(R.id.et_kritiksaran)
    EditText etKritikSaran;
    @BindView(R.id.btn_submit)
    Button btnSubmit;

    DataKritikSaran dataKritikSaran;
    Animation shake;

    private Merlin merlin;
    private MerlinsBeard merlinsBeard;

    String responseCode;
    String failConnect;

    Dialog dialogKritik;

    Handler delay = new Handler();
    Handler delayDialog = new Handler();
    final static int DELAYS = 5000;
    final static int DELAY_DIALOG = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik);

        ButterKnife.bind(this);

        dataKritikSaran = new DataKritikSaran();
        shake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);

        setSupportActionBar(toolbar);

        dialogKritik = new Dialog(this);

        merlin = new Merlin.Builder()
                .withConnectableCallbacks()
                .withDisconnectableCallbacks()
                .build(this);

        merlinsBeard = MerlinsBeard.from(this);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initEvent();

    }

    @SuppressLint("SetTextI18n")
    private void initEvent() {
        btnSubmit.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String email = etEmail.getText().toString();
            String kritiksaran = etKritikSaran.getText().toString();

            merlin.bind();

            if (nama.matches("")){
                etNama.startAnimation(shake);
                Snackbar.make(v, "Isi namanya dulu dong", Snackbar.LENGTH_SHORT)
                        .show();
            } else if (nama != null && email.matches("")){
                etEmail.startAnimation(shake);
                Snackbar.make(v, "Isi emailnya dulu dong", Snackbar.LENGTH_SHORT)
                        .show();
            } else if (email != null && kritiksaran.matches("")){
                etKritikSaran.startAnimation(shake);
                Snackbar.make(v, "Katanya mau kasih kritik saran, kok kosyong??", Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                final Animation animScaleTitle = AnimationUtils.loadAnimation(this, R.anim.anim_bounce);

                submitKritikSaran();
                /*etNama.getText().clear();
                etKritikSaran.getText().clear();
                etEmail.getText().clear();
*/
                btnSubmit.setBackground(getResources().getDrawable(R.drawable.bg_button_grey));
                btnSubmit.setTextColor(getResources().getColor(R.color.black));
                btnSubmit.setEnabled(false);

                TextView tvStatus;
                ImageView ivStatus;
                ProgressBar progressBar;
                CardView cardKritik;

                dialogKritik.setContentView(R.layout.dialog_kritik);
                dialogKritik.setCancelable(false);

                Objects.requireNonNull(dialogKritik.getWindow()).setBackgroundDrawableResource(R.color.transparent);

                tvStatus = dialogKritik.findViewById(R.id.tv_status);
                ivStatus = dialogKritik.findViewById(R.id.iv_status);
                progressBar = dialogKritik.findViewById(R.id.loading_indicator);
                cardKritik = dialogKritik.findViewById(R.id.card_kritik);

                cardKritik.startAnimation(animScaleTitle);

                tvStatus.setText("Loading...");
                dialogKritik.show();


//                System.out.println("Response dari delay" + responseCode);
                delay.postDelayed(() -> {
                    ConnectivityManager cm =
                            (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);

                    NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null &&
                            activeNetwork.isConnectedOrConnecting();

                    System.out.println("Response dari delay " + isConnected);

                    delayDialog.postDelayed(() ->{
                        if (merlinsBeard.isConnected()) {
                            etNama.getText().clear();
                            etKritikSaran.getText().clear();
                            etEmail.getText().clear();
                            ivStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_check));
                            ivStatus.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            tvStatus.setText("Terima kasih kritik dan sarannya");
                            dialogKritik.setCancelable(true);
                       /* Snackbar.make(v, "Terima kasih kritik dan sarannya", Snackbar.LENGTH_INDEFINITE)
                                .show();*/
                        } else {
                            ivStatus.setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));
                            ivStatus.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.INVISIBLE);
                            tvStatus.setText("Gagal mengirim kritik dan saran, silahkan cek jaringan anda lalu coba lagi");

                            btnSubmit.setBackground(getResources().getDrawable(R.drawable.bg_button_accent));
                            btnSubmit.setTextColor(getResources().getColor(R.color.putih));
                            btnSubmit.setEnabled(true);
                            dialogKritik.setCancelable(true);

                        /*Snackbar.make(v, "Gagal mengirim kritik dan saran, silahkan cek jaringan anda lalu coba lagi", Snackbar.LENGTH_INDEFINITE)
                                .show();*/
                        }
                    },DELAY_DIALOG);


                }, DELAYS);
            }

        });
    }

    private void submitKritikSaran() {

                String nama = etNama.getText().toString();
                String email = etEmail.getText().toString();
                String kritiksaran = etKritikSaran.getText().toString();

                DataKritikSaran data = new DataKritikSaran();
                data.setTitle(nama);
                data.setDescription(kritiksaran);
                data.setEmail(email);

                RestService restService = APIClient.getKritikSaran().create(RestService.class);
                Call<ResponseBody> call = restService.postKritikSaran(data);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        responseCode = String.valueOf(response.code());
                        Log.i("Respone Body %s", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Gagaal submit komen" + t.toString());

//                        failConnect = t.toString();
                    }
                });
    }

    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if (item.getItemId()==android.R.id.home){
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

    @Override
    protected void onResume() {
        super.onResume();
        merlin.bind();
    }
}
