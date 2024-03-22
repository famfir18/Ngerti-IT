package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.ActivityManager;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.app.ngertiit.Data.API.APIClient;
import com.app.ngertiit.Data.API.RestService;
import com.app.ngertiit.Data.JSON.DataEvent;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.onesignal.Continue;
import com.onesignal.OneSignal;
import com.onesignal.debug.LogLevel;
import com.squareup.picasso.Picasso;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bn_main)
    BottomNavigationView bnMain;
    @BindView(R.id.switch_notif)
    Switch switchNotif;
    @BindView(R.id.layout_update)
    LinearLayout layoutNotif;
    @BindView(R.id.layout_disclaimer)
    LinearLayout layoutDisclaimer;
    @BindView(R.id.layout_fragment_setting)
    ScrollView layoutFragmentSetting;
    @BindView(R.id.layout_donasi)
    LinearLayout layoutDonasi;
    @BindView(R.id.layout_kritik)
    LinearLayout layoutKritik;
    @BindView(R.id.layout_sumber_link)
    LinearLayout layoutSumberLink;
    @BindView(R.id.layout_tentang)
    LinearLayout layoutTentang;
    @BindView(R.id.search)
    ImageButton search;
    @BindView(R.id.layout_rate)
    LinearLayout layoutRate;

    Dialog dialogExit;
    Dialog dialogEvent;
    Dialog dialogDisclaimer;

    String updateYes;
    String updateNo;

    ImageView imageEvent;
    ProgressBar progressBar;

    Application application;

    String eventId;

    PrefManager prefManager;
    CheckBox checkBox;
    TextView closeEvent;

    Boolean clicked = false;

    String status;
    String id;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        application =  (Application) getApplicationContext();

        Gson gson = new Gson();
        RestService restService = APIClient.getAPI().create(RestService.class);
        Call<DataEvent> call = restService.getDataEventToday();

        call.enqueue(new Callback<DataEvent>() {
            @Override
            public void onResponse(Call<DataEvent> call, Response<DataEvent> response) {
                System.out.println("RES[ONNYA " + gson.toJson(response.body()));
                status = response.body().getStatus();
                imageUrl = response.body().getImageUrl();
                id = response.body().getId();

                imageEvent = dialogEvent.findViewById(R.id.image_event);
                progressBar = dialogEvent.findViewById(R.id.loading_indicator);
                checkBox = dialogEvent.findViewById(R.id.checkBox);
                closeEvent = dialogEvent.findViewById(R.id.btn_close);

                SharedPreferences sharedPrefz = getSharedPreferences("checkEvent", MODE_PRIVATE);
                Boolean wkwk = sharedPrefz.getBoolean("checkEvent", false);

                checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    Boolean checked = false;
                    if (checkBox.isChecked()){
                        System.out.println( "KECEK NIH WOY");
                        checked = true;
                    }
                    SharedPreferences.Editor editor = getSharedPreferences("checkEvent", MODE_PRIVATE).edit();
                    editor.putBoolean("checkEvent", true);
                    editor.commit();
                });

                if (status.equals("1004")){
                    if (!wkwk){
                        dialogEvent.show();
                    }
                } else if (status.equals("1003")){
                    sharedPrefz.edit().clear().commit();
                }

                Picasso.with(getApplicationContext())
                        .load(imageUrl)
                        .into(imageEvent, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                progressBar.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {
//                        progressBar.setVisibility(View.GONE);
                                Toast.makeText(MainActivity.this, "Gagal memuat gambar", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
            }

            @Override
            public void onFailure(Call<DataEvent> call, Throwable t) {

            }
        });

        bnMain.setItemIconTintList(null);
        bnMain.setOnNavigationItemSelectedListener(this);

        if (getIntent().getExtras() != null) {
            eventId = getIntent().getStringExtra("idArtikel");
            System.out.println("Ini ID Event" + eventId);
        }

        loadFragment(new FragmentHome());

        dialogExit = new Dialog(this);
        dialogEvent = new Dialog(this);
        dialogDisclaimer = new Dialog(this);


        dialogExit.setContentView(R.layout.dialog_exit);
        dialogEvent.setContentView(R.layout.dialog_event);
        dialogDisclaimer.setContentView(R.layout.dialog_disclaimer);
        Objects.requireNonNull(dialogExit.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        Objects.requireNonNull(dialogEvent.getWindow()).setBackgroundDrawableResource(R.color.transparent);
        Objects.requireNonNull(dialogDisclaimer.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        CheckBox checkBoxDisclaimer = dialogDisclaimer.findViewById(R.id.checkBox);
        Button btnNo = dialogDisclaimer.findViewById(R.id.btnNo);

        btnNo.setVisibility(View.GONE);
        checkBoxDisclaimer.setChecked(true);
        checkBoxDisclaimer.setEnabled(false);

        TextView contentPertama = dialogDisclaimer.findViewById(R.id.expand_button_0);
        ExpandableLayout expandableLayout0 = dialogDisclaimer.findViewById(R.id.expandable_layout_0);

        expandableLayout0.setOnExpansionUpdateListener(new ExpandableLayout.OnExpansionUpdateListener() {
            @Override
            public void onExpansionUpdate(float expansionFraction, int state) {
                Log.d("ExpandableLayout0", "State: " + state);
            }
        });

        contentPertama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!expandableLayout0.isExpanded()) {
                    contentPertama.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.white));
                    expandableLayout0.expand();
                    clicked = true;
                } else if (clicked = true){
                    contentPertama.setBackgroundColor(getApplicationContext().getResources().getColor(R.color.putih));
                    expandableLayout0.collapse();
                }
            }
        });

        SharedPreferences sharedPrefs = getSharedPreferences("check", MODE_PRIVATE);
        switchNotif.setChecked(sharedPrefs.getBoolean("check", true));

        createShortCut();
        initView();

    }

    private void createShortCut() {

        Intent shortcutIntent = new Intent(getApplicationContext(),MainActivity.class);
        shortcutIntent.setAction(Intent.ACTION_MAIN);
        Intent intent = new Intent();
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, R.string.app_name);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,Intent.ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
        getApplicationContext().sendBroadcast(intent);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                Log.i ("Service status", "Running");
                return true;
            }
        }
        Log.i ("Service status", "Not running");
        return false;
    }

    private void initView() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, SearchAll.class);
                startActivity(i);
            }
        });

        layoutTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, TentangAct.class);
                startActivity(i);
            }
        });

        layoutKritik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, KritikAct.class);
                startActivity(i);
            }
        });

        layoutDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, DonasiAct.class);
                startActivity(i);
            }
        });

        layoutSumberLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, BrowserAct.class);
                startActivity(i);
                CustomIntent.customType(MainActivity.this,"bottom-to-up");
            }
        });

        layoutDisclaimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDisclaimer.show();
            }
        });

        layoutRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent viewIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.app.ngertiit"));
                startActivity(viewIntent);
            }
        });
    }


    @Override
    public void onBackPressed() {

        Random random = new Random();
        int randomz = random.nextInt(7);

        final Animation animScaleTitle = AnimationUtils.loadAnimation(this, R.anim.anim_bounce);
        final Animation animShake = AnimationUtils.loadAnimation(this, R.anim.anim_shake);

        Button yes;
        Button no;
        ImageView imageView;
        TextView textView;
        CardView cardExit;

        dialogExit.setContentView(R.layout.dialog_exit);

        Objects.requireNonNull(dialogExit.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        yes = dialogExit.findViewById(R.id.btnYes);
        no = dialogExit.findViewById(R.id.btnNo);
        textView = dialogExit.findViewById(R.id.textView);
        imageView = dialogExit.findViewById(R.id.image);
        cardExit = dialogExit.findViewById(R.id.card_dialog_exit);

        imageView.setClickable(false);

        cardExit.startAnimation(animScaleTitle);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (randomz == 3){
                    imageView.setClickable(true);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            imageView.setImageDrawable(getDrawable(R.drawable.alone));
                            textView.setText("Ampuuuun Bosss :(");
                            yes.setVisibility(View.VISIBLE);
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            });
                        }
                    });
                    textView.setText("Yhaaa Kena Deeehh! :v");
                    imageView.setImageDrawable(getResources().getDrawable(R.drawable.lol));
                    cardExit.startAnimation(animShake);
                    yes.setVisibility(View.GONE);
                    dialogExit.setCancelable(false);
                    no.setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExit.dismiss();
//                addNotification();
            }
        });

        dialogExit.show();

    }

    private static int getRandomNumberInRange(int min, int max) {

        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }


    private boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fl_container, fragment, "h")
                    .addToBackStack("h")
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.home_menu:
                search.setVisibility(View.VISIBLE);
                layoutFragmentSetting.setVisibility(View.GONE);
                fragment = new FragmentHome();
                break;
            case R.id.history_menu:
                layoutFragmentSetting.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                fragment = new FragmentHistory();
                break;
            case R.id.forum_menu:
                layoutFragmentSetting.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                Intent i = new Intent(MainActivity.this, ForumActivity.class);
                startActivity(i);
                break;
            case R.id.setting_menu:
                layoutFragmentSetting.setVisibility(View.VISIBLE);
                search.setVisibility(View.GONE);
                fragment = new FragmentSetting();
                break;
        }
        return loadFragment(fragment);
    }
}
