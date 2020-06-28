package com.example.ngertiit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsoluteLayout;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;

import com.example.ngertiit.Data.API.APIClient;
import com.example.ngertiit.Data.API.RestService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import maes.tech.intentanim.CustomIntent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bn_main)
    BottomNavigationView bnMain;
    @BindView(R.id.switch_notif)
    Switch switchNotif;
    @BindView(R.id.layout_update)
    LinearLayout layoutNotif;
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


    Dialog dialogExit;
    String updateYes;
    String updateNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        bnMain.setItemIconTintList(null);
        bnMain.setOnNavigationItemSelectedListener(this);


        loadFragment(new FragmentHome());

        dialogExit = new Dialog(this);

        if (switchNotif.isChecked()){
            // OneSignal Initialization
            OneSignal.startInit(this)
                    .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                    .unsubscribeWhenNotificationsAreDisabled(true)
                    .autoPromptLocation(true)
                    .init();
            OneSignal.setSubscription(true);
        }else if (!switchNotif.isChecked()){
            System.out.println("Ga kekirim notifnya");
            OneSignal.setSubscription(false);
        }

        switchNotif.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (switchNotif.isChecked()){
                OneSignal.startInit(this)
                        .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                        .unsubscribeWhenNotificationsAreDisabled(true)
                        .autoPromptLocation(true)
                        .init();
                OneSignal.setSubscription(true);

                SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
                editor.putBoolean("check", true);
                editor.commit();
            } else if (!switchNotif.isChecked()){
                System.out.println("Ga kekirim notifnya");
                OneSignal.setSubscription(false);

                SharedPreferences.Editor editor = getSharedPreferences("check", MODE_PRIVATE).edit();
                editor.putBoolean("check", false);
                editor.commit();
            }});

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
    }


    @Override
    public void onBackPressed() {

        final Animation animScaleTitle = AnimationUtils.loadAnimation(this, R.anim.anim_scale_dialog);

        Button yes;
        Button no;
        CardView cardExit;

        dialogExit.setContentView(R.layout.dialog_exit);

        Objects.requireNonNull(dialogExit.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        yes = dialogExit.findViewById(R.id.btnYes);
        no = dialogExit.findViewById(R.id.btnNo);
        cardExit = dialogExit.findViewById(R.id.card_dialog_exit);

        cardExit.startAnimation(animScaleTitle);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                search.setVisibility(View.GONE);
                layoutFragmentSetting.setVisibility(View.GONE);
                fragment = new FragmentHome();
                break;
            case R.id.history_menu:
                layoutFragmentSetting.setVisibility(View.GONE);
                search.setVisibility(View.GONE);
                fragment = new FragmentHistory();
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
