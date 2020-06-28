package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ngertiit.Adapter.DictionaryAdapter;
import com.example.ngertiit.Adapter.DictionaryAdapterMenu;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataDictionary;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KamusAct extends AppCompatActivity implements DictionaryAdapter.OnItemSelected{

    ArrayList<String> source;
    @BindView(R.id.rv_dictionary)
    RecyclerView rv_dictionary;
    @BindView(R.id.layout_not_found)
    LinearLayout layoutNotFound;

    RecyclerView.LayoutManager SolutionRecyclerViewLayoutManager;

    LinearLayoutManager SolutionHorizontalLayout;

    DictionaryAdapter adapter;
    List<DataDictionary> dictionaries;
    List<DataDictionary> dictionariesFiltered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        ButterKnife.bind(this);

        Toolbar toolbars = findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);

        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }*/

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Kamus");
        }

        toolbarTitleCollaps();

        initView();
        getDataDictionary();
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

    //Code Program pada Method dibawah ini akan Berjalan saat Option Menu Dibuat
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);

        boolean fullyExpanded =
                (appBarLayout.getHeight() - appBarLayout.getBottom()) == 0;

        //Memanggil/Memasang menu item pada toolbar dari layout menu_bar.xml
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        final SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setQueryHint(Html.fromHtml("<font color = #666666>" + getResources().getString(R.string.hintSearchMess) + "</font>"));

        if (fullyExpanded){
            searchItem.setIcon(getResources().getDrawable(R.drawable.ic_search_black));
        } else {
            searchItem.setIcon(getResources().getDrawable(R.drawable.ic_search));
        }

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String nextText) {
                final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
                AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
                appBarLayout.setExpanded(false);
                //Data akan berubah saat user menginputkan text/kata kunci pada SearchView

                nextText = nextText.toLowerCase();
                if (!nextText.equals("")) {
                    dictionariesFiltered.clear();
                } else {
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

                for(int i = 0; i < dictionaries.size(); i++){
                    String title = dictionaries.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
//                        layoutNotFound.setVisibility(View.GONE);
                        dictionariesFiltered.add(dictionaries.get(i));
                    } else if (nextText.equals("")){
//                        layoutNotFound.setVisibility(View.GONE);
                        adapter.setMovieList(dictionaries);
                    }/* else if (!title.contains(nextText)){
                        layoutNotFound.setVisibility(View.VISIBLE);
                    }*/
                }

                if (dictionariesFiltered != null || !nextText.equals("")) {
                    adapter.setMovieList(dictionariesFiltered);
                } else {
                    adapter.setMovieList(dictionaries);
                }

                return true;
            }
        });
        return true;
    }

    private void toolbarTitleCollaps() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Kamus");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void initView() {

        dictionaries = new ArrayList();
        dictionariesFiltered = new ArrayList();
    }

    private void AddItemsToRecyclerViewArrayList() {
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
    public void onSelected(DataDictionary dataDictionary) {

    }
}
