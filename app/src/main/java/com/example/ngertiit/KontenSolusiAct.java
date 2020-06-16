package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.example.ngertiit.Data.JSON.DataCarousels;
import com.example.ngertiit.Data.JSON.DataSolution;
import com.example.ngertiit.Data.JSON.DataTestSDG;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.squareup.picasso.Picasso;

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

    public static final String ID_KONTEN = "id";
    private Integer kontenId;

    String langkahPertama;
    String langkahKedua;
    String langkahKetiga;
    String langkahKeempat;
    String urlMethodOne;
    String urlMethodTwo;
    String urlMethodThree;
    String urlMethodFour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konten_solusi);

        ButterKnife.bind(this);

        expandLayout();

        collapsing.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        if (getIntent().getExtras() != null) {
            kontenId = getIntent().getExtras().getInt(ID_KONTEN);
        }

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceID = telephonyManager.getDeviceId();
        System.out.println("Device ID = " + deviceID);


//        link = "https://www.google.com/";

//        loadPage();

        RestService restService = APIClient.getAPI().create(RestService.class);
        Call<List<DataSolution>> call = restService.getDataSolutions();

        call.enqueue(new Callback<List<DataSolution>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<List<DataSolution>> call, Response<List<DataSolution>> response) {

                String em = "<em>";
                String closeEm = "</em>";
                int position = kontenId -1;
                String description = response.body().get(position).getDescription();
                String imageUrl = response.body().get(position).getImage();

                Toolbar toolbars = findViewById(R.id.toolbar);
                setSupportActionBar(toolbars);
                if(getSupportActionBar() != null){
                    getSupportActionBar().setDisplayShowTitleEnabled(true);
                    getSupportActionBar().setTitle(response.body().get(position).getTitle());
                }

                if (description.contains(closeEm) || description.contains(em)){
                    description = description.replaceAll(em, "");
                    description = description.replaceAll(closeEm,"");
                    tvDescription.setText(description);
                } else {
                    tvDescription.setText(description);
                }

                langkahPertama = response.body().get(position).getMethodOne();
                langkahKedua = response.body().get(position).getMethodTwo();
                langkahKetiga = response.body().get(position).getMethodThree();
//                langkahKeempat = response.body().get(3).getDeskripsi();

                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .into(ivBanner);

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

            }

            @Override
            public void onFailure(Call<List<DataSolution>> call, Throwable t) {

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
