package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;

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
    @BindView(R.id.layout_bca)
    LinearLayout layoutBca;
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
        toolbarTitleCollaps();
    }

    private void toolbarTitleCollaps() {
        final CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = true;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Donasi");
                    isShow = true;
                } else if(isShow) {
                    collapsingToolbarLayout.setTitle(" ");//careful there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });

    }

    private void initEvent() {

        ImageView ivQR;
        TextView tvPayment;
        ImageButton btnCopy;
        TextView tvName;
//        TextView tvVa;

        dialog.setContentView(R.layout.dialog_payment);

        ivQR = dialog.findViewById(R.id.iv_qr);
        tvPayment = dialog.findViewById(R.id.tv_payment);
        btnCopy = dialog.findViewById(R.id.btn_copy);
        tvName = dialog.findViewById(R.id.tv_name);
//        tvVa = dialog.findViewById(R.id.tv_va);

        layoutOvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                tvPayment.setText("085920024574");
                tvName.setText("Faisal Amirul Firdaus");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_ovo));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });

                /*tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });*/

            }
        });

        layoutGopay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                tvPayment.setText("085920024574");
                tvName.setText("Faisal Amirul Firdaus");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_gopay));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);

                dialog.show();

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });


               /* tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });*/

            }
        });

        layoutDana.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tvName.setText("Faisal Amirul Firdaus");
                tvPayment.setText("085920024574");
//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_dana));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });


                /*tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });*/

            }
        });

        layoutLinkAja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.qr_linkaja));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                tvName.setText("Kevinn Ernest");
                tvPayment.setText("087887077630");

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });



               /* tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
*/
            }
        });

        layoutBca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.logo_bca));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                tvName.setText("Mahendra Dwi Syathi");
                tvPayment.setText("3701171045");

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });



               /* tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });
*/
            }
        });

        layoutMandiri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                tvVa.setText("Tekan lama nomor diatas untuk copy ke clipboard");
                ivQR.setImageDrawable(getResources().getDrawable(R.drawable.ic_mandiri));
                Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawableResource(R.color.transparent);
                dialog.show();

                tvName.setText("Faisal Amirul Firdaus");
                tvPayment.setText("1570005061693");

                btnCopy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
                    }
                });


                /*tvPayment.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {

                        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                        clipboard.setText(tvPayment.getText());

                        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();

                        return false;
                    }
                });*/

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