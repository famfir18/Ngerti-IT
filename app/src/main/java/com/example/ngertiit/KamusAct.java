package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ngertiit.Adapter.DictionaryAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KamusAct extends AppCompatActivity {

    ArrayList<String> source;
    @BindView(R.id.rv_solution)
    RecyclerView rv_solution;

    RecyclerView.LayoutManager SolutionRecyclerViewLayoutManager;

    LinearLayoutManager SolutionHorizontalLayout;

    DictionaryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kamus);

        ButterKnife.bind(this);

        Toolbar toolbars = findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Kamus");
        }

        toolbarTitleCollaps();

        initView();
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
        SolutionRecyclerViewLayoutManager = new LinearLayoutManager(this);
        rv_solution.setLayoutManager(SolutionRecyclerViewLayoutManager);

        AddItemsToRecyclerViewArrayList();
        adapter = new DictionaryAdapter(source);

        SolutionHorizontalLayout = new LinearLayoutManager(this);
        rv_solution.setLayoutManager(SolutionHorizontalLayout);

        rv_solution.setAdapter(adapter);
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
}
