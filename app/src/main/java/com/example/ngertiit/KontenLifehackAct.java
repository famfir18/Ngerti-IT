package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataLifehacks;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KontenLifehackAct extends AppCompatActivity {

    @BindView(R.id.tv_lifehack_desc)
    TextView tvDesc;
    @BindView(R.id.iv_banner_lifehack)
    ImageView ivBanner;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.tv_lifehack_title)
    TextView tvTitle;

    public  static  final String ID_KONTEN = "id";
    private Integer kontenId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_lifehack);

        ButterKnife.bind(this);

        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        collapsing.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        if (getIntent().getExtras() != null) {
            kontenId = getIntent().getExtras().getInt(ID_KONTEN);
        }


        RestService apiService = APIClient.getAPI().create(RestService.class);
        Call<List<DataLifehacks>> call = apiService.getDataLifehacks();

        call.enqueue(new Callback<List<DataLifehacks>>() {
            @Override
            public void onResponse(Call<List<DataLifehacks>> call, Response<List<DataLifehacks>> response) {

                String div = "<div>";
                String closeDiv = "</div>";
                String nbsp = "&nbsp;";
                int position = kontenId;
                String imageUrl = response.body().get(position).getImage();
                String description = response.body().get(position).getDescription();

                Toolbar toolbars = findViewById(R.id.toolbar);
                    setSupportActionBar(toolbars);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        getSupportActionBar().setTitle(response.body().get(position).getTitle());
                    }

                if (description.contains(closeDiv) || description.contains(div)  || description.contains(nbsp)) {
                    description = description.replaceAll(div, "");
                    description = description.replaceAll(closeDiv,"");
                    description = description.replaceAll(nbsp,"");
                    tvDesc.setText(description);
                } else {
                    tvDesc.setText(description);
                }

                tvTitle.setText(response.body().get(position).getTitle());


                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .into(ivBanner);

            }

            @Override
            public void onFailure(Call<List<DataLifehacks>> call, Throwable t) {

            }
        });

    }
}