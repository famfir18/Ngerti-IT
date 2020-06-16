package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;


import com.example.ngertiit.Adapter.SolutionAdapter;
import com.example.ngertiit.Adapter.SolutionAdapterMenu;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataSolution;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolutionAct extends AppCompatActivity implements SolutionAdapter.OnItemSelected {

    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<String> source;

    List<DataSolution> myLizt;
    List<DataSolution> listWindows;
    List<DataSolution> listMacOS;

    String windows;
    String macOS;

    SolutionAdapter solutionAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        ButterKnife.bind(this);

        initView();
        getDataSolutions();
    }

    private void getDataSolutions() {
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_solution.setLayoutManager(layoutManager);
        solutionAdapter = new SolutionAdapter(this, myLizt, this);
        rv_solution.setAdapter(solutionAdapter);

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataSolution>> call = apiService.getDataSolutions();

        call.enqueue(new Callback<List<DataSolution>>() {
            @Override
            public void onResponse(Call<List<DataSolution>> call, Response<List<DataSolution>> response) {
                myLizt = response.body();

                if (getIntent().getExtras() != null) {
                    windows = getIntent().getExtras().getString("Windows");
                    macOS = getIntent().getExtras().getString("macOS");
                }

                if (myLizt != null){
                    System.out.println("Kontol 0 = "+ myLizt.size());

                    if (getIntent().getExtras() != null){
                        if (getIntent().getExtras().containsKey(windows)){
                            for (int i =0; i < myLizt.size(); i++){
                                if (myLizt.get(i).getCategory().equals("Windows")){
                                    System.out.println("Kontol 1 = "+ myLizt.get(i).getCategory());
                                    listWindows.add(myLizt.get(i));
                                    System.out.println("Kontol 2 = "+ listWindows.size());
                                    solutionAdapter.setMovieList(listWindows);
                                }
                            }
                        } else if (getIntent().getExtras().containsKey(macOS)) {
                            for (int i = 0; i < myLizt.size(); i++) {
                                if (myLizt.get(i).getCategory().equals("macOS")) {
                                    System.out.println("Kontol 1 = " + myLizt.get(i).getCategory());
                                    listMacOS.add(myLizt.get(i));
                                    System.out.println("Kontol 2 = " + listMacOS.size());
                                    solutionAdapter.setMovieList(listMacOS);
                                }
                            }
                        }
                    }
                   else if (getIntent().getExtras() == null){
                        solutionAdapter.setMovieList(myLizt);
                    }

                } else {
                    System.out.println("Kontol 3 myList Null ");
                }
            }

            @Override
            public void onFailure(Call<List<DataSolution>> call, Throwable t) {
                System.out.println("gagalz");
                Log.d("TAG","Response = "+t.toString());
            }
        });

    }

    private void initView() {

        myLizt = new ArrayList();
        listWindows = new ArrayList();
        listMacOS = new ArrayList();

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

//        SolutionRecyclerViewLayoutManager = new GridLayoutManager(this,2);
//        rv_solution.setLayoutManager(SolutionRecyclerViewLayoutManager);
//
////        AddItemsToRecyclerViewArrayList();
////        solutionAdapter = new SolutionAdapter(source);
//
//        SolutionHorizontalLayout = new GridLayoutManager(this, 2);
//        rv_solution.setLayoutManager(SolutionHorizontalLayout);
//
//        rv_solution.setAdapter(solutionAdapter);
    }

    public void AddItemsToRecyclerViewArrayList()
    {
        // Adding items to ArrayList
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
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelected(DataSolution dataSolution) {
        Intent i = new Intent(SolutionAct.this, KontenSolusiAct.class);
        i.putExtra(KontenSolusiAct.ID_KONTEN, dataSolution.getId());
        startActivity(i);
    }
}
