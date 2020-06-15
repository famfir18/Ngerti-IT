package com.example.ngertiit;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;


public class FragmentSetting extends Fragment {

    @BindView(R.id.layout_donasi)
    LinearLayout layoutDonasi;
    @BindView(R.id.layout_kritik)
    LinearLayout layoutKritik;
    @BindView(R.id.layout_sumber_link)
    LinearLayout layoutSumberLink;
    @BindView(R.id.layout_tentang)
    LinearLayout layoutTentang;
    @BindView(R.id.layout_update)
    LinearLayout layoutUpdate;

    Context context;

    public FragmentSetting() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_setting, container, false);

        ButterKnife.bind(this,view);
        context = getContext();

        initView();


        return view;
    }

    private void initView() {
        layoutTentang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,TentangAct.class);
                startActivity(i);
            }
        });

        layoutKritik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,KritikAct.class);
                startActivity(i);
            }
        });

        layoutDonasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,DonasiAct.class);
                startActivity(i);
            }
        });

        layoutSumberLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,BrowserAct.class);
                startActivity(i);
                CustomIntent.customType(context,"bottom-to-up");
            }
        });
    }
}
