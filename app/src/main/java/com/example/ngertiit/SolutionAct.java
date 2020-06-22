package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.ngertiit.Adapter.SolutionAdapter;
import com.example.ngertiit.Adapter.SolutionAdapterMenu;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataHistory;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ngertiit.Data.API.APIClient.URL_BASE;

public class SolutionAct extends AppCompatActivity implements SolutionAdapter.OnItemSelected {

    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<String> source;

    List<DataSolution> myLizt;
    List<DataSolution> listWindows;
    List<DataSolution> listMacOS;
    List<DataSolution> listFiltered;

    String windows;
    String macOS;

    SolutionAdapter solutionAdapter;

    TelephonyManager telephonyManager;
    String idDevice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution);

        ButterKnife.bind(this);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        idDevice = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

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
        listFiltered = new ArrayList();

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

    //Code Program pada Method dibawah ini akan Berjalan saat Option Menu Dibuat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #666666>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                //Data akan berubah saat user menginputkan text/kata kunci pada SearchView

                nextText = nextText.toLowerCase();
                if (!nextText.equals("")) {
                    listFiltered.clear();
                } else {
                    RestService apiService = APIClient.getClient().create(RestService.class);
                    Call<List<DataSolution>> call = apiService.getDataSolutions();

                    call.enqueue(new Callback<List<DataSolution>>() {
                        @Override
                        public void onResponse(Call<List<DataSolution>> call, Response<List<DataSolution>> response) {
                            myLizt = response.body();
                            Log.d("TAG","Response = "+ myLizt);
                            solutionAdapter.setMovieList(myLizt);
                        }

                        @Override
                        public void onFailure(Call<List<DataSolution>> call, Throwable t) {
                            System.out.println("gagalz");
                            Log.d("TAG","Response = "+t.toString());
                        }
                    });
                }

                for(int i = 0; i < myLizt.size(); i++){
                    String title = myLizt.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        listFiltered.add(myLizt.get(i));
                    }
                }

                if (listFiltered != null || !nextText.equals("")) {
                    solutionAdapter.setMovieList(listFiltered);
                } else {
                    solutionAdapter.setMovieList(listFiltered);
                }

                return true;
            }
        });
        return true;
    }

    @Override
    public void onSelected(DataSolution dataSolution) {
        Gson gson = new Gson();

        int idArtikel = dataSolution.getId();
        String idArtikelString = String.valueOf(idArtikel);
        String idUser = idDevice;
        String judul = dataSolution.getTitle();

        DataHistory dataHistory = new DataHistory();

        dataHistory.setIdArtikel(idArtikel);
        dataHistory.setIdUser(idUser);
        dataHistory.setJudulArtikel(judul);
        dataHistory.setStatus("Success");
        dataHistory.setLinkArtikel("Solusi");

        Log.d("TAG","Data History = "+ gson.toJson(dataHistory));

        RestService apiService = APIClient.getAPI().create(RestService.class);
        Call<ResponseBody> call = apiService.postHistory(dataHistory);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                Log.d("TAG","Response Berhasil = "+ gson.toJson(response.code()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("TAG","Response Gagal= "+t.toString());
            }
        });
        Intent i = new Intent(SolutionAct.this, KontenSolusiAct.class);
        i.putExtra("idArtikel", idArtikelString);
        startActivity(i);
    }
}
