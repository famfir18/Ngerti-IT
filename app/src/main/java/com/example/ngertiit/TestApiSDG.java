package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ngertiit.Adapter.RecyclerAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataTestSDG;
import com.google.gson.Gson;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TestApiSDG extends AppCompatActivity implements
        RecyclerAdapter.OnItemSelected {

    @BindView(R.id.tv_eventname)
    TextView eventName;
    @BindView(R.id.tv_tanggal)
    TextView tanggal;
    @BindView(R.id.tv_lokasi)
    TextView lokasi;
    @BindView(R.id.tv_deskripsi)
    TextView deskripsi;
    @BindView(R.id.button2)
    Button btn;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerView;

    AlertDialog dialog;

    List<DataTestSDG> myList;
    RecyclerAdapter recyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api_sdg);

        ButterKnife.bind(this);

        myList = new ArrayList<>();

//        new AlertDialog.Builder(this)
//                .setTitle("Loading..")
//                .show();

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(getApplicationContext(),myList, this);
        recyclerView.setAdapter(recyclerAdapter);

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataTestSDG>> call = apiService.getDataSDG();


        call.enqueue(new Callback<List<DataTestSDG>>() {
            @Override
            public void onResponse(Call<List<DataTestSDG>> call, Response<List<DataTestSDG>> response) {
                myList = response.body();
                Log.d("TAG","Response = "+ myList);

                recyclerAdapter.setMovieList(myList);
            }

            @Override
            public void onFailure(Call<List<DataTestSDG>> call, Throwable t) {
                Log.d("TAG","Response = "+t.toString());
            }
        });

        getData();

//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                getData();
//            }
//        });
    }

    private void getData() {

        Gson gson = new Gson();

        RestService restService = APIClient.getClient().create(RestService.class);
        Call<List<DataTestSDG>> call = restService.getDataSDG();

        call.enqueue(new Callback<List<DataTestSDG>>() {
            @Override
            public void onResponse(Call<List<DataTestSDG>> call, Response<List<DataTestSDG>> response) {


                for (int i = 0; i < response.body().size(); i++){
                    if (response.isSuccessful()){
                        eventName.setText(response.body().get(i).getEventName());
                        tanggal.setText(response.body().get(i).getTanggal());
                        lokasi.setText(response.body().get(i).getLokasi());
                        deskripsi.setText(response.body().get(i).getDeskripsi());

                        System.out.println("Hasil Datanya Adalah : " + gson.toJson(response));
                    }
                }


            }

            @Override
            public void onFailure(Call<List<DataTestSDG>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onSelected(DataTestSDG dataTestSDG) {
        Intent intent = new Intent(TestApiSDG.this, DonasiAct.class);
        intent.putExtra("eventName", dataTestSDG.getDeskripsi());
        startActivity(intent);
    }
}