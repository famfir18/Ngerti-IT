package com.app.ngertiit;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Objects;

public class WelcomeActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;

    Dialog dialogConfirm;
    CardView carDialog;

    Dialog dialogDisclaimer;
    Button yes;
    Button no;

    CheckBox checkBox;

    TextView contentPertama;
    ExpandableLayout expandableLayout0;

    Boolean clicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dialogDisclaimer = new Dialog(this);
        dialogDisclaimer.setContentView(R.layout.dialog_disclaimer);
        dialogDisclaimer.setCancelable(false);
        Objects.requireNonNull(dialogDisclaimer.getWindow()).setBackgroundDrawableResource(R.color.transparent);

        no = dialogDisclaimer.findViewById(R.id.btnNo);
        checkBox = dialogDisclaimer.findViewById(R.id.checkBox);

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()){
                    no.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_button_accent));
                    no.setEnabled(true);
                } else {
                    no.setBackground(ContextCompat.getDrawable(getApplicationContext(), R.drawable.bg_button_grey));
                    no.setEnabled(false);
                }
            }
        });


        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        contentPertama = dialogDisclaimer.findViewById(R.id.expand_button_0);
        expandableLayout0 = dialogDisclaimer.findViewById(R.id.expandable_layout_0);

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


        // mengecek lauch activity - sebelum memanggil setContentView()
        prefManager = new PrefManager(this);
        if (!prefManager.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        }

        // membuat transparan notifikasi
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        setContentView(R.layout.activity_welcome);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnNext = (Button) findViewById(R.id.btn_next);

        dialogConfirm = new Dialog(this);
        Button yes;
        Button no;
        TextView tv;
        ImageView iv;

        dialogConfirm.setContentView(R.layout.dialog_exit);
        dialogConfirm.setCancelable(false);
        yes = dialogConfirm.findViewById(R.id.btnYes);
        no =  dialogConfirm.findViewById(R.id.btnNo);
        tv = dialogConfirm.findViewById(R.id.textView);
        iv = dialogConfirm.findViewById(R.id.image);
        carDialog = dialogConfirm.findViewById(R.id.card_dialog_exit);

        carDialog.setVisibility(View.GONE);

        tv.setText("History Lo gabakal kesimpen, tetep mau lanjutin?");
        iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_alert));

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogConfirm.dismiss();
            }
        });

        // layout xml slide 1 sampai 4
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.slide1,
                R.layout.slide2,
                R.layout.slide3};

        // tombol dots (lingkaran kecil perpindahan slide)
        addBottomDots(0);

        // membuat transparan notifikasi
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // mengecek page terakhir (slide 4)
                // jika activity home sudah tampil
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);
                } else {
                    dialogDisclaimer.show();
//                    launchHomeScreen();
                   /* ActivityCompat.requestPermissions(WelcomeActivity.this,
                            new String[]{Manifest.permission.READ_PHONE_STATE},
                            1);

                    int permissionCheck = ContextCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.READ_PHONE_STATE);

                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(WelcomeActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        //TODO
                        dialogConfirm.show();
                        carDialog.setVisibility(View.VISIBLE);
                    }*/
                }
            }
        });
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } *//*else {
                      break
                    }*//*
                break;
          *//*  default:
                break;*//*
        }
    }*/

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] myImageList = new int[]{R.drawable.dot_active, R.drawable.dot_active, R.drawable.dot_active};

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);
        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
        finish();
    }

    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            // mengubah button lanjut 'NEXT' / 'GOT IT'
            if (position == layouts.length - 1) {

                final Animation animScaleTitle = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.anim_bounce);

                // last page. make button text to GOT IT
                btnNext.startAnimation(animScaleTitle);
                btnNext.setText(getString(R.string.start));
                btnNext.setVisibility(View.VISIBLE);
//                btnSkip.setVisibility(View.GONE);
            } else {
                // still pages are left
//                btnNext.setText(getString(R.string.next));
                btnSkip.setVisibility(View.GONE);
                btnNext.setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    /**
     * View pager dictionaryAdapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}