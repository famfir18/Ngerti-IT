package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DonasiAct extends AppCompatActivity {

    @BindView(R.id.layout_gopay)
    LinearLayout layoutGopay;
    @BindView(R.id.layout_ovo)
    LinearLayout layoutOvo;
    @BindView(R.id.layout_linkaja)
    LinearLayout layoutLinkAja;
    @BindView(R.id.layout_dana)
    LinearLayout layoutDana;
    @BindView(R.id.layout_mandiri)
    LinearLayout layoutMandiri;
    @BindView(R.id.textz)
    TextView text;

    Dialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donasi);

        ButterKnife.bind(this);

        dialog = new Dialog(this);

        Intent getIntent = getIntent();

        String eventName = getIntent.getStringExtra("eventName");

        if(eventName == null){
            text.setText("Bantu beliin developer kita kopi dan gorengan biar bisa update terus.");
        } else {
            text.setText(eventName);
        }

        Toolbar toolbars = findViewById(R.id.toolbar);
        setSupportActionBar(toolbars);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
            getSupportActionBar().setTitle("Donasi");
        }

        initEvent();
    }

    private void initEvent() {

        ImageView ivQR;
        TextView tvPayment;
        TextView tvVa;

        dialog.setContentView(R.layout.dialog_payment);

        ivQR = dialog.findViewById(R.id.iv_qr);
        tvPayment = dialog.findViewById(R.id.tv_payment);
        tvVa = dialog.findViewById(R.id.tv_va);

        layoutOvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                tvPayment.setText("085920024574");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_ovo));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });

            }
        });

        layoutGopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                tvPayment.setText("085920024574");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_gopay));
                dialog.show();

                tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });

            }
        });

        layoutDana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvPayment.setText("085920024574");
                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_dana));
                dialog.show();

                tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });

            }
        });

        layoutLinkAja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_gopay));
                dialog.show();

                tvPayment.setText("085920024574");


                tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });

            }
        });

        layoutMandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.ic_mandiri));
                dialog.show();

                tvPayment.setText("1570005061693");

                tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });

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