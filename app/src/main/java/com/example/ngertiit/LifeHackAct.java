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

import com.example.ngertiit.Adapter.LifeHackAdapter;
import com.example.ngertiit.Adapter.SolutionAdapter;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.example.ngertiit.Data.JSON.DataLifehacks;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LifeHackAct extends AppCompatActivity implements LifeHackAdapter.OnItemSelected{

    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    ArrayList<String> source;

    List<DataLifehacks> myLizt;
    List<DataLifehacks> listWindows;
    List<DataLifehacks> listMacOS;

    String windows;
    String macOS;

    RecyclerView.LayoutManager SolutionRecyclerViewLayoutManager;
    LifeHackAdapter lifeHackAdapter;

    LinearLayoutManager SolutionHorizontalLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_hack);

        ButterKnife.bind(this);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSelected(DataLifehacks dataLifehacks) {
        Intent i = new Intent(LifeHackAct.this, KontenLifehackAct.class);
        i.putExtra(KontenLifehackAct.ID_KONTEN, dataLifehacks.getId());
        startActivity(i);
    }
}
