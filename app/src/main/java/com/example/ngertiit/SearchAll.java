package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.ngertiit.Adapter.DataFilter;
import com.example.ngertiit.Adapter.DictionaryAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataDictionary;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchAll extends AppCompatActivity implements DictionaryAdapter.OnItemSelected{

    @BindView(R.id.rv_search)
    RecyclerView rv_dictionary;

    DictionaryAdapter adapter;
    List<DataDictionary> dictionaries;
    List<DataDictionary> dictionariesFiltered;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_all);

        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getDataDictionary();
        dictionaries = new ArrayList();
        dictionariesFiltered = new ArrayList();
    }

    private void getDataDictionary() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_dictionary.setLayoutManager(layoutManager);
        adapter = new DictionaryAdapter(this, dictionaries, this);
        rv_dictionary.setAdapter(adapter);

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataDictionary>> call = apiService.getDataDictionary();

        call.enqueue(new Callback<List<DataDictionary>>() {
            @Override
            public void onResponse(Call<List<DataDictionary>> call, Response<List<DataDictionary>> response) {
                dictionaries = response.body();
                Log.d("TAG","Response = "+ dictionaries);
                adapter.setMovieList(dictionaries);
            }

            @Override
            public void onFailure(Call<List<DataDictionary>> call, Throwable t) {
                System.out.println("gagalz");
                Log.d("TAG","Response = "+t.toString());
            }
        });
    }

   /* //Code Program pada Method dibawah ini akan Berjalan saat Option Menu Dibuat
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

                for(int i = 0; i < dictionaries.size(); i++){
                    String title = dictionaries.get(i).getTitle().toLowerCase();
                    if(title.contains(nextText)){
                        dictionariesFiltered.add(dictionaries.get(i));
                    }
                }
                adapter.setMovieList(dictionariesFiltered);
                return true;
            }
        });
        return true;
    }*/

    @Override
    public void onSelected(DataDictionary dataDictionary) {

    }
}
