package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.ngertiit.Adapter.DictionaryAdapter;
import com.example.ngertiit.Adapter.LifeHackAdapter;
import com.example.ngertiit.Adapter.SearchAdapter;
import com.example.ngertiit.Adapter.SolutionAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAll extends AppCompatActivity implements SearchAdapter.OnItemSelected {

    @BindView(R.id.rv_search_all)
    RecyclerView rvSearchAll;

    DictionaryAdapter dictionaryAdapter;
    LifeHackAdapter lifeHackAdapter;
    SolutionAdapter solutionAdapter;

    SearchAdapter searchAdapter;


    List<DataSolution> solutions;
    List<DataLifehacks> lifehacks;
    List<DataDictionary> dictionaries;

    ArrayList<Object> listSemua;

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


        lifehacks = new ArrayList();
        solutions = new ArrayList();
        dictionaries = new ArrayList();
        listSemua = new ArrayList<>();

        getDataDictionary();
        getDataLifehack();
        getDataSolution();

        delayDialog.postDelayed(() ->{
            getDataAll();
        }, DELAYS);
    }

    private void getDataSolution() {

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
    }

    private void getDataAll() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvSearchAll.setLayoutManager(layoutManager);
        searchAdapter = new SearchAdapter(this, dictionaries, listSemua,this);
        rvSearchAll.setAdapter(searchAdapter);

        System.out.println("isi list semua = " + gson.toJson(listSemua));


        searchAdapter.setMovieList(listSemua);
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


                /*for(int i = 0; i < dictionaries.size(); i++){
                    String title = dictionaries.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        dictionariesFiltered.add(dictionaries.get(i));
                    }
                }

                for(int i = 0; i < lifehacks.size(); i++){
                    String title = lifehacks.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        lifehacksFiltered.add(lifehacks.get(i));
                    }
                }

                for(int i = 0; i < solutions.size(); i++){
                    String title = solutions.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        solutionsFiltered.add(solutions.get(i));
                    }
                }*/

                /*for(int i = 0; i < lifehacks.size(); i++){
                    String title = lifehacks.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        dictionariesFiltered.add(lifehacks.get(i));
                    }
                }

                for(int i = 0; i < dictionaries.size(); i++){
                    String title = dictionaries.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        dictionariesFiltered.add(dictionaries.get(i));
                    }
                }*/

               /* if (dictionariesFiltered != null) {
                    dictionaryAdapter.setMovieList(dictionariesFiltered);
                } else  */

               /*if (solutionsFiltered != null) {
                    solutionAdapter.setMovieList(solutionsFiltered);
                } else if (lifehacksFiltered != null) {
                    lifeHackAdapter.setMovieList(lifehacksFiltered);
                }*/
                return true;
            }
        });
        return true;
    }

    @Override
    public void onSelected(DataDictionary dataDictionary) {

    }
}
