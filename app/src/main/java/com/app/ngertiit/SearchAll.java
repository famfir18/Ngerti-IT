package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.app.ngertiit.Adapter.DictionaryAdapter;
import com.app.ngertiit.Adapter.LifeHackAdapter;
import com.app.ngertiit.Adapter.SearchAdapter;
import com.app.ngertiit.Adapter.SolutionAdapter;
import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataDictionary;
import com.app.ngertiit.Data.JSON.DataHistory;
import com.app.ngertiit.Data.JSON.DataLifehacks;
import com.app.ngertiit.Data.JSON.DataSearch;
import com.app.ngertiit.Data.JSON.DataSolution;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAll extends AppCompatActivity
        implements SearchAdapter.OnItemSelected {

    @BindView(R.id.rv_search_all)
    RecyclerView rvSearchAll;

    DictionaryAdapter dictionaryAdapter;
    LifeHackAdapter lifeHackAdapter;
    SolutionAdapter solutionAdapter;

    SearchAdapter searchAdapter;

    List<DataSolution> solutions;
    List<DataLifehacks> lifehacks;
    List<DataDictionary> dictionaries;

    List<DataSearch> myLizt;
    List<DataSearch> listFiltered;

    ArrayList<Object> listSemua;

    String idDevice;

    Gson gson = new Gson();

    Handler delayDialog = new Handler();
    final static int DELAYS = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        idDevice = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        lifehacks = new ArrayList();
        solutions = new ArrayList();
        dictionaries = new ArrayList();
        listSemua = new ArrayList<>();

        myLizt = new ArrayList<>();
        listFiltered = new ArrayList<>();

//        getDataDictionary();
//        getDataLifehack();
//        getDataSolution();

       /* delayDialog.postDelayed(() ->{
            getDataAll();
        }, DELAYS);*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    /*private void getDataSolution() {

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataSolution>> call = apiService.getDataSolutions();

        call.enqueue(new Callback<List<DataSolution>>() {
            @Override
            public void onResponse(Call<List<DataSolution>> call, Response<List<DataSolution>> response) {
                solutions = response.body();
                Log.d("TAG","Response = "+ dictionaries);

                for (int i = 0; i < solutions.size(); i++){
                    String titleSolution = solutions.get(i).getTitle();
                    listSemua.add(titleSolution);
                }

                System.out.println("Semua dari Solution " + gson.toJson(listSemua));

            }

            @Override
            public void onFailure(Call<List<DataSolution>> call, Throwable t) {
                System.out.println("gagalz");
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }

    private void getDataLifehack() {

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataLifehacks>> call = apiService.getDataLifehacks();

        call.enqueue(new Callback<List<DataLifehacks>>() {
            @Override
            public void onResponse(Call<List<DataLifehacks>> call, Response<List<DataLifehacks>> response) {
                lifehacks = response.body();
                Log.d("TAG","Response = "+ dictionaries);

                for (int i = 0; i < lifehacks.size(); i++){
                    String titleLifehacks = lifehacks.get(i).getTitle();
                    listSemua.add(titleLifehacks);
                }

                System.out.println("Semua dari Lifehack " + gson.toJson(listSemua));
            }

            @Override
            public void onFailure(Call<List<DataLifehacks>> call, Throwable t) {
                System.out.println("gagalz");
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }

    private void getDataDictionary() {

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataDictionary>> call = apiService.getDataDictionary();

        call.enqueue(new Callback<List<DataDictionary>>() {
            @Override
            public void onResponse(Call<List<DataDictionary>> call, Response<List<DataDictionary>> response) {
                dictionaries = response.body();
                Log.d("TAG", "Response = " + dictionaries);

                for (int i = 0; i < dictionaries.size(); i++){
                    String titleDictionary = dictionaries.get(i).getTitle();
                    listSemua.add(titleDictionary);
                }

                System.out.println("Semua dari Dictionary " + gson.toJson(listSemua));
//                dictionaryAdapter.setMovieList(dictionaries);
            }

            @Override
            public void onFailure(Call<List<DataDictionary>> call, Throwable t) {
                System.out.println("gagalz");
                Log.d("TAG", "Response = " + t.toString());
            }
        });
    }*/

    private void getDataAll() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSearchAll.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this, myLizt,this);
        rvSearchAll.setAdapter(searchAdapter);

        System.out.println("isi list semua = " + gson.toJson(myLizt));
        searchAdapter.setMovieList(myLizt);
    }

    //Code Program pada Method dibawah ini akan Berjalan saat Option Menu Dibuat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        searchItem.expandActionView();
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #666666>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

        MenuItemCompat.setOnActionExpandListener(searchItem, new MenuItemCompat.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return false;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                finish();
                return true;
            }
        });

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

                    RestService apiService = APIClient.getAPI().create(RestService.class);
                    Call<List<DataSearch>> call = apiService.getDataSearch(nextText);

                    call.enqueue(new Callback<List<DataSearch>>() {
                        @Override
                        public void onResponse(Call<List<DataSearch>> call, Response<List<DataSearch>> response) {
                            myLizt = response.body();
                            Log.d("TAG","Response = "+ myLizt);
                            if (myLizt != null){
                                getDataAll();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<DataSearch>> call, Throwable t) {
                            System.out.println("gagalz");
                            Log.d("TAG","Response = "+t.toString());
                        }
                    });

              /*  for(int i = 0; i < myLizt.size(); i++){
                    String title = myLizt.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
//                        layoutNotFound.setVisibility(View.GONE);
                        myLizt.add(myLizt.get(i));
                    } *//*else if (!title.contains(nextText)){
//                        layoutNotFound.setVisibility(View.VISIBLE);
                    } else if (nextText.equals("")){
//                        layoutNotFound.setVisibility(View.GONE);
                    }*//*
                }*/

               /* if (listFiltered != null || !nextText.equals("")) {
                    searchAdapter.setMovieList(listFiltered);
                } else {
                    searchAdapter.setMovieList(listFiltered);
                }*/

                return true;
            }
        });
        return true;
    }

    @Override
    public void onSelected(DataSearch dataSearch) {

        Gson gson = new Gson();

        int idArtikel = dataSearch.getId();
        String kategori = dataSearch.getCategory();
        String idArtikelString = String.valueOf(idArtikel);
        String idUser = idDevice;
        String judul = dataSearch.getTitle();

        DataHistory dataHistory = new DataHistory();

        dataHistory.setIdArtikel(idArtikel);
        dataHistory.setIdUser(idUser);
        dataHistory.setJudulArtikel(judul);
        dataHistory.setStatus("Success");
        dataHistory.setLinkArtikel(kategori);

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

        if (kategori.equals("Life-hack")){
            Intent i = new Intent(SearchAll.this, KontenLifehackAct.class);
            i.putExtra("idArtikel", idArtikelString);
            startActivity(i);
        } else if (kategori.equals("Solusi")){
            Intent i = new Intent(SearchAll.this, KontenSolusiAct.class);
            i.putExtra("idArtikel", idArtikelString);
            startActivity(i);
        } else {
            Intent i = new Intent(SearchAll.this, KamusAct.class);
            i.putExtra("idArtikel", idArtikelString);
            startActivity(i);
        }
    }
}
