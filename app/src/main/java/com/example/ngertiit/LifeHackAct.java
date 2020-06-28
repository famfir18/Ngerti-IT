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
import android.view.View;
import android.widget.LinearLayout;

import com.example.ngertiit.Adapter.LifeHackAdapter;
import com.example.ngertiit.Adapter.SolutionAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataHistory;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.ngertiit.Data.API.APIClient.URL_BASE;

public class LifeHackAct extends AppCompatActivity implements LifeHackAdapter.OnItemSelected{

    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.layout_not_found)
    LinearLayout layoutNotFound;

    ArrayList<String> source;

    List<DataLifehacks> myLizt;
    List<DataLifehacks> listWindows;
    List<DataLifehacks> listMacOS;
    List<DataLifehacks> listFiltered;

    String windows;
    String macOS;

    TelephonyManager telephonyManager;
    String idDevice;

    RecyclerView.LayoutManager SolutionRecyclerViewLayoutManager;
    LifeHackAdapter lifeHackAdapter;

    LinearLayoutManager SolutionHorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_hack);

        ButterKnife.bind(this);

        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        idDevice = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);

        initView();
        getDataLifehacks();
    }

    private void getDataLifehacks() {
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rv_solution.setLayoutManager(layoutManager);
        lifeHackAdapter = new LifeHackAdapter(this, myLizt, this);
        rv_solution.setAdapter(lifeHackAdapter);

        RestService apiService = APIClient.getClient().create(RestService.class);
        Call<List<DataLifehacks>> call = apiService.getDataLifehacks();

        call.enqueue(new Callback<List<DataLifehacks>>() {
            @Override
            public void onResponse(Call<List<DataLifehacks>> call, Response<List<DataLifehacks>> response) {
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
                                    lifeHackAdapter.setMovieList(listWindows);
                                }
                            }
                        } else if (getIntent().getExtras().containsKey(macOS)) {
                            for (int i = 0; i < myLizt.size(); i++) {
                                if (myLizt.get(i).getCategory().equals("macOS")) {
                                    System.out.println("Kontol 1 = " + myLizt.get(i).getCategory());
                                    listMacOS.add(myLizt.get(i));
                                    System.out.println("Kontol 2 = " + listMacOS.size());
                                    lifeHackAdapter.setMovieList(listMacOS);
                                }
                            }
                        }
                    }
                    else if (getIntent().getExtras() == null){
                        lifeHackAdapter.setMovieList(myLizt);
                    }

                } else {
                    System.out.println("Kontol 3 myList Null ");
                }
            }

            @Override
            public void onFailure(Call<List<DataLifehacks>> call, Throwable t) {
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

        SolutionRecyclerViewLayoutManager = new GridLayoutManager(this,2);
        rv_solution.setLayoutManager(SolutionRecyclerViewLayoutManager);

//        AddItemsToRecyclerViewArrayList();
//        lifeHackAdapter = new LifeHackAdapter(source);

        SolutionHorizontalLayout = new GridLayoutManager(this, 2);
        rv_solution.setLayoutManager(SolutionHorizontalLayout);

        rv_solution.setAdapter(lifeHackAdapter);
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
                    Call<List<DataLifehacks>> call = apiService.getDataLifehacks();

                    call.enqueue(new Callback<List<DataLifehacks>>() {
                        @Override
                        public void onResponse(Call<List<DataLifehacks>> call, Response<List<DataLifehacks>> response) {
                            myLizt = response.body();
                            Log.d("TAG","Response = "+ myLizt);
                            lifeHackAdapter.setMovieList(myLizt);
                        }

                        @Override
                        public void onFailure(Call<List<DataLifehacks>> call, Throwable t) {
                            System.out.println("gagalz");
                            Log.d("TAG","Response = "+t.toString());
                        }
                    });
                }

                for(int i = 0; i < myLizt.size(); i++){
                    String title = myLizt.get(i).getTitle().toLowerCase();
                    if(!nextText.equals("") && title.contains(nextText)){
                        layoutNotFound.setVisibility(View.GONE);
                        listFiltered.add(myLizt.get(i));
                    } else if (!title.contains(nextText)){
//                        layoutNotFound.setVisibility(View.VISIBLE);
                    } else if (nextText.equals("")){
                        layoutNotFound.setVisibility(View.GONE);
                    }
                }

                if (listFiltered != null || !nextText.equals("")) {
                    lifeHackAdapter.setMovieList(listFiltered);
                } else {
                    lifeHackAdapter.setMovieList(listFiltered);
                }

                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelected(DataLifehacks dataLifehacks) {

        Gson gson = new Gson();

        int idArtikel = dataLifehacks.getId();
        String idArtikelString = String.valueOf(idArtikel);
        String idUser = idDevice;
        String judul = dataLifehacks.getTitle();

        DataHistory dataHistory = new DataHistory();

        dataHistory.setIdArtikel(idArtikel);
        dataHistory.setIdUser(idUser);
        dataHistory.setJudulArtikel(judul);
        dataHistory.setStatus("Success");
        dataHistory.setLinkArtikel("Life-hack");

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
        Intent i = new Intent(LifeHackAct.this, KontenLifehackAct.class);
        i.putExtra("idArtikel", idArtikelString);
        startActivity(i);
    }
}
