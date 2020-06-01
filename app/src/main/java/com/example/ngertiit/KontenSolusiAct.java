package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataTestSDG;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KontenSolusiAct extends AppCompatActivity implements View.OnClickListener {

    private ExpandableLayout expandableLayout0;
    private ExpandableLayout expandableLayout1;
    private ExpandableLayout expandableLayout2;
    private ExpandableLayout expandableLayout3;

    @BindView(R.id.content_pertama)
    TextView contentPertama;
    @BindView(R.id.content_kedua)
    TextView contentKedua;
    @BindView(R.id.content_ketiga)
    TextView contentKetiga;
    @BindView(R.id.content_keempat)
    TextView contentKeempat;

    String langkahPertama;
    String langkahKedua;
    String langkahKetiga;
    String langkahKeempat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_solusi);

        ButterKnife.bind(this);

        expandLayout();

        RestService restService = APIClient.getClient().create(RestService.class);
        Call<List<DataTestSDG>> call = restService.getDataSDG();

        call.enqueue(new Callback<List<DataTestSDG>>() {
            @Override
            public void onResponse(Call<List<DataTestSDG>> call, Response<List<DataTestSDG>> response) {
                langkahPertama = response.body().get(0).getDeskripsi();
                langkahKedua = response.body().get(1).getDeskripsi();
                langkahKetiga = response.body().get(2).getDeskripsi();
                langkahKeempat = response.body().get(3).getDeskripsi();

                contentPertama.setText(langkahPertama);
                contentKedua.setText(langkahKedua);
                contentKetiga.setText(langkahKetiga);
                contentKeempat.setText(langkahKeempat);

            }

            @Override
            public void onFailure(Call<List<DataTestSDG>> call, Throwable t) {

            }
        });

    }

    private void expandLayout() {
        expandableLayout0 = findViewById(R.id.expandable_layout_0);
        expandableLayout1 = findViewById(R.id.expandable_layout_1);
        expandableLayout2 = findViewById(R.id.expandable_layout_2);
        expandableLayout3 = findViewById(R.id.expandable_layout_3);

        expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        expandableLayout1.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout1", "State: " + state);
            }
        });

        findViewById(R.id.expand_button_0).setOnClickListener(this);
        findViewById(R.id.expand_button_1).setOnClickListener(this);
        findViewById(R.id.expand_button_2).setOnClickListener(this);
        findViewById(R.id.expand_button_3).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.expand_button_0:
                expandableLayout0.expand();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout3.collapse();
                break;
            case R.id.expand_button_1:
                expandableLayout2.collapse();
                expandableLayout1.expand();
                expandableLayout3.collapse();
                expandableLayout0.collapse();
                break;
            case R.id.expand_button_2:
                expandableLayout2.expand();
                expandableLayout1.collapse();
                expandableLayout0.collapse();
                expandableLayout3.collapse();
                break;
            case R.id.expand_button_3:
                expandableLayout3.expand();
                expandableLayout1.collapse();
                expandableLayout2.collapse();
                expandableLayout0.collapse();
                break;
        }
    }
}
