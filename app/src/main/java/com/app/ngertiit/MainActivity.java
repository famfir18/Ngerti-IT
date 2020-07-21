package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.app.Application;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.app.ngertiit.Util.NotificationHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

import java.util.Objects;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;

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

    Application application;

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
//                    .setNotificationOpenedHandler(new NotificationHandler(application))
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
//                        .setNotificationOpenedHandler(new NotificationHandler(application))
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

        Random random = new Random();
        int randomz = random.nextInt(5);

        final Animation animScaleTitle = AnimationUtils.loadAnimation(this, R.anim.anim_scale_dialog);
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
