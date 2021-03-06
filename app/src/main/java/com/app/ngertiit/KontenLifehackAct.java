package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataLifehacks;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

import java.util.Objects;

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
    @BindView(R.id.webViewLifehack)
    WebView webViewLifehack;
    @BindView(R.id.card)
    CardView card;

    public  static  final String ID_KONTEN = "id";
    String kontenId;
    ImageView imageView;

    Dialog dialogImage;
    ProgressBar progressBar;

    Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_lifehack);

        ButterKnife.bind(this);

        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);

        Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        dialogLoading.show();

        // In Activity's onCreate() for instance
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        collapsing.setExpandedTitleTextAppearance(R.style.CollapsedAppBarTransparent);

        if (getIntent().getExtras() != null) {
            kontenId = getIntent().getStringExtra("idArtikel");
        }

        dialogImage = new Dialog(this);
        dialogImage.setContentView(R.layout.dialog_full_image);
        dialogImage.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogImage.setCanceledOnTouchOutside(true);
        imageView = dialogImage.findViewById(R.id.picNewsFull);
        progressBar = dialogImage.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


        RestService apiService = APIClient.getAPI().create(RestService.class);
        Call<DataLifehacks> call = apiService.getDataLifehacksFiltered(kontenId);

        call.enqueue(new Callback<DataLifehacks>() {
            @Override
            public void onResponse(Call<DataLifehacks> call, Response<DataLifehacks> response) {

                dialogLoading.dismiss();
                card.setVisibility(View.VISIBLE);
                WebSettings webSetting = webViewLifehack.getSettings();
                webSetting.setBuiltInZoomControls(false);

                String div = "<div>";
                String closeDiv = "</div>";
                String p = "<p>";
                String closeP = "</p>";
                String nbsp = "&nbsp;";
                String iamgeUrl = response.body().getImage();
                String imageUrl = "https:banyakngerti.id" + response.body().getImage();
                String description = response.body().getDescription();
                String urlDesc = response.body().getUrls();

                if (!iamgeUrl.contains("http")){
                    Picasso.with(getApplicationContext())
                            .load(imageUrl)
                            .into(ivBanner);
                } else if (iamgeUrl.contains("http")){
                    Picasso.with(getApplicationContext())
                            .load(iamgeUrl)
                            .into(ivBanner);
                }

                webViewLifehack.setWebViewClient(new WebViewClient());
                webViewLifehack.loadUrl(urlDesc);

                Toolbar toolbars = findViewById(R.id.toolbar);
                    setSupportActionBar(toolbars);
                    if(getSupportActionBar() != null){
                        getSupportActionBar().setDisplayShowTitleEnabled(true);
                        getSupportActionBar().setTitle(response.body().getTitle());
                        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    }

                if (description.contains(closeDiv) || description.contains(div)  || description.contains(nbsp) || description.contains(p) || description.contains(closeP)) {
                    description = description.replaceAll(div, "");
                    description = description.replaceAll(closeDiv,"");
                    description = description.replaceAll(nbsp,"");
                    description = description.replaceAll(p, "");
                    description = description.replaceAll(closeP, "");
                    tvDesc.setText(description);
                } else {
                    tvDesc.setText(description);
                }

                tvTitle.setText(response.body().getTitle());

                if (!iamgeUrl.contains("http")){
                    Picasso.with(getApplicationContext())
                            .load(imageUrl)
                            .into(imageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(KontenLifehackAct.this, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                } else if (iamgeUrl.contains("http")){
                    Picasso.with(getApplicationContext())
                            .load(iamgeUrl)
                            .into(imageView, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    progressBar.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(KontenLifehackAct.this, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                                            .show();
                                }
                            });
                }

                ivBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogImage.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<DataLifehacks> call, Throwable t) {

                dialogLoading.setCancelable(true);
                TextView gagal = dialogLoading.findViewById(R.id.tv_status);
                ImageView imageGagal = dialogLoading.findViewById(R.id.iv_status);
                gagal.setText("Loading gagal, mohon periksa koneksi anda lalu coba lagi");
                imageGagal.setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));
                imageGagal.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}