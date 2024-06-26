package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryLifehack extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ln_macos)
    LinearLayout macos;
    @BindView(R.id.ln_windows)
    LinearLayout windows;
    @BindView(R.id.ln_android)
    LinearLayout androiD;
    @BindView(R.id.ln_ios)
    LinearLayout ios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_lifehack);

        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    private void initEvent() {
        Animation animScalein = AnimationUtils.loadAnimation(this, R.anim.anim_scale_in);

        macos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(animScalein);
                Intent i = new Intent(CategoryLifehack.this,LifeHackAct.class);
                i.putExtra("macOS", "macOS");
                startActivity(i);
            }
        });

        windows.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(animScalein);
                Intent i = new Intent(CategoryLifehack.this,LifeHackAct.class);
                i.putExtra("Windows", "Windows");
                startActivity(i);
            }
        });

        androiD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(animScalein);
                Intent i = new Intent(CategoryLifehack.this,LifeHackAct.class);
                i.putExtra("Android", "Android");
                startActivity(i);
            }
        });

        ios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                v.startAnimation(animScalein);
                Intent i = new Intent(CategoryLifehack.this,LifeHackAct.class);
                i.putExtra("iOS", "iOS");
                startActivity(i);
            }
        });
    }

    private void initView() {
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
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
