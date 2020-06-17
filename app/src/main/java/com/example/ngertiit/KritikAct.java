package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataKritikSaran;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kritik);

        ButterKnife.bind(this);

        dataKritikSaran = new DataKritikSaran();
        shake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        initEvent();

    }

    private void initEvent() {
        btnSubmit.setOnClickListener(v -> {
            String nama = etNama.getText().toString();
            String email = etEmail.getText().toString();
            String kritiksaran = etKritikSaran.getText().toString();

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
                Snackbar.make(v, "Katanya mau komen, kok kosyong??", Snackbar.LENGTH_SHORT)
                        .show();
            } else {
                submitKritikSaran();
                finish();
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

                        Log.i("Respone Body %s", String.valueOf(response.code()));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        System.out.println("Gagaal submit komen");
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
}
