package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.widget.NestedScrollView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataSolution;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;
import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import java.util.Objects;

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
    @BindView(R.id.tv_solution_desc)
    TextView tvDescription;
    @BindView(R.id.iv_banner_solution)
    ImageView ivBanner;
    @BindView(R.id.web)
    WebView webView;
    @BindView(R.id.web2)
    WebView webView2;
    @BindView(R.id.web3)
    WebView webView3;
    @BindView(R.id.web4)
    WebView webView4;
    @BindView(R.id.collapsing)
    CollapsingToolbarLayout collapsing;
    @BindView(R.id.layout_cara2)
    LinearLayout layoutCara2;
    @BindView(R.id.layout_cara3)
    LinearLayout layoutCara3;
    @BindView(R.id.card)
    CardView card;

    public static final String ID_KONTEN = "id";
    String kontenId;
    String kontenIdString;

    String langkahPertama;
    String langkahKedua;
    String langkahKetiga;
    String langkahKeempat;
    String urlMethodOne;
    String urlMethodTwo;
    String urlMethodThree;
    String urlMethodFour;

    ImageView imageView;

    Dialog dialogImage;
    ProgressBar progressBar;

    Dialog dialogLoading;

    boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_solusi);

        ButterKnife.bind(this);

        expandLayout();

        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);

        Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        dialogLoading.show();

        collapsing.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (getIntent().getExtras() != null) {
            kontenId = getIntent().getStringExtra("idArtikel");
            System.out.println("id Artikel asu " + kontenId);
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("Device ID = " + deviceID);

        dialogImage = new Dialog(this);
        dialogImage.setContentView(R.layout.dialog_full_image);
        dialogImage.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        dialogImage.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialogImage.setCanceledOnTouchOutside(true);
        imageView = dialogImage.findViewById(R.id.picNewsFull);
        progressBar = dialogImage.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);


//        link = "https://www.google.com/";

//        loadPage();

        RestService restService = APIClient.getAPI().create(RestService.class);
        Call<DataSolution> call = restService.getDataSolutionsFiltered(kontenId);

        call.enqueue(new Callback<DataSolution>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<DataSolution> call, Response<DataSolution> response) {

                dialogLoading.dismiss();
                card.setVisibility(View.VISIBLE);

                Gson gson = new Gson();
                System.out.println("Response nyaaa " + gson.toJson(response));
                String em = "<em>";
                String closeEm = "</em>";
                String description = response.body().getDescription();
                String imageUrl = "https:banyakngerti.id" + response.body().getImage();

                Toolbar toolbars = findViewById(R.id.toolbar);
                setSupportActionBar(toolbars);
                if(getSupportActionBar() != null){
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle(response.body().getTitle());
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }

                if (description.contains(closeEm) || description.contains(em)){
                    description = description.replaceAll(em, "");
                    description = description.replaceAll(closeEm,"");
                    tvDescription.setText(description);
                } else {
                    tvDescription.setText(description);
                }

                langkahPertama = response.body().getMethodOne();
                langkahKedua = response.body().getMethodTwo();
                langkahKetiga = response.body().getMethodThree();
//                langkahKeempat = response.body().get(3).getDeskripsi();

                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .into(ivBanner);

                if (langkahKedua == null){
                    layoutCara2.setVisibility(View.GONE);
                    layoutCara3.setVisibility(View.GONE);
                } else if (langkahKetiga == null){
                    layoutCara3.setVisibility(View.GONE);
                }

                WebSettings webSetting = webView.getSettings();
                webSetting.setBuiltInZoomControls(false);
                webView.setWebViewClient(new WebViewClient());
                webView.loadUrl(langkahPertama);
                webView2.setWebViewClient(new WebViewClient());
                webView2.loadUrl(langkahKedua);
                webView3.setWebViewClient(new WebViewClient());
                webView3.loadUrl(langkahKetiga);
/*                contentPertama.setText(langkahPertama);
                contentKedua.setText(langkahKedua);
                contentKetiga.setText(Html.fromHtml(getString(R.string.cobaan)));
                contentKeempat.setText(langkahKeempat);*/

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
                                Toast.makeText(KontenSolusiAct.this, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });

                ivBanner.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogImage.show();
                    }
                });

            }

            @Override
            public void onFailure(Call<DataSolution> call, Throwable t) {

                dialogLoading.setCancelable(true);
                TextView gagal = dialogLoading.findViewById(R.id.tv_status);
                ImageView imageGagal = dialogLoading.findViewById(R.id.iv_status);
                gagal.setText("Loading gagal, mohon periksa koneksi anda lalu coba lagi");
                imageGagal.setImageDrawable(getResources().getDrawable(R.drawable.ic_cross));
                imageGagal.setVisibility(View.VISIBLE);

                Gson gson = new Gson();
                Log.d("TAG","Response gagalnya = "+ gson.toJson(t.getCause()));
            }
        });

    }

    private void loadPage() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(urlMethodOne);
        webView2.setWebViewClient(new WebViewClient());
        webView2.loadUrl(urlMethodTwo);
        webView3.setWebViewClient(new WebViewClient());
        webView3.loadUrl(urlMethodThree);
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
                if (!expandableLayout0.isExpanded()) {
                    view.setBackgroundColor(this.getResources().getColor(R.color.white));
                    findViewById(R.id.expand_button_1).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_2).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_3).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout0.expand();
                    expandableLayout1.collapse();
                    expandableLayout2.collapse();
                    expandableLayout3.collapse();
                    clicked = true;
                } else if (clicked = true){
                    view.setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout0.collapse();
                }
//                expandableLayout0.expand();
                break;
            case R.id.expand_button_1:
                if (!expandableLayout1.isExpanded()) {
                    view.setBackgroundColor(this.getResources().getColor(R.color.white));
                    findViewById(R.id.expand_button_0).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_2).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_3).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout1.expand();
                    expandableLayout2.collapse();
                    expandableLayout3.collapse();
                    expandableLayout0.collapse();
                    clicked = true;
                } else if (clicked = true){
                    view.setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout1.collapse();
                }
                break;
            case R.id.expand_button_2:
                if (!expandableLayout2.isExpanded()) {
                    view.setBackgroundColor(this.getResources().getColor(R.color.white));
                    findViewById(R.id.expand_button_1).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_0).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_3).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout2.expand();
                    expandableLayout1.collapse();
                    expandableLayout0.collapse();
                    expandableLayout3.collapse();
                    clicked = true;
                } else if (clicked = true){
                    view.setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout2.collapse();
                }
                break;
            case R.id.expand_button_3:
                if (!expandableLayout3.isExpanded()) {
                    view.setBackgroundColor(this.getResources().getColor(R.color.white));
                    findViewById(R.id.expand_button_1).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_2).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    findViewById(R.id.expand_button_0).setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout3.expand();
                    expandableLayout1.collapse();
                    expandableLayout2.collapse();
                    expandableLayout0.collapse();
                    clicked = true;
                } else if (clicked = true){
                    view.setBackgroundColor(this.getResources().getColor(R.color.putih));
                    expandableLayout3.collapse();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
