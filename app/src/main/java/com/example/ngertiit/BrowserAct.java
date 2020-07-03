package com.example.ngertiit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import maes.tech.intentanim.CustomIntent;

public class BrowserAct extends AppCompatActivity {

    @BindView(R.id.imageButton)
    ImageButton close;
    @BindView(R.id.web)
    WebView webView;

    Context context;

    String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        ButterKnife.bind(this);
        context = getApplication();
        link = "http://banyakngerti.id/sumber";

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadLocalPage();

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.open_browser) {
           openBrowser();
        }
        if (id == R.id.copy_link) {
            copyLink();
        }
        if (id == R.id.share) {
          shareLink();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openBrowser() {
        Intent i = new Intent();
        i.setAction(Intent.ACTION_VIEW);
        i.addCategory(Intent.CATEGORY_BROWSABLE);
        i.setData(Uri.parse(link));
        startActivity(i);
    }

    private void shareLink() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, link);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void copyLink() {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboard.setText(link);
        Toast.makeText(getApplicationContext(), "Copied to clipboard", Toast.LENGTH_LONG).show();
    }

    public void loadLocalPage() {
        WebSettings webSetting = webView.getSettings();
        webSetting.setBuiltInZoomControls(false);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(link);
    }
}
