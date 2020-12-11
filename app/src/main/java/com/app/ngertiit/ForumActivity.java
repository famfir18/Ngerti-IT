package com.app.ngertiit;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForumActivity extends AppCompatActivity {

    @BindView(R.id.webview_forum)
    WebView webForum;
    TextView toolbarText;

    Dialog dialogLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        ButterKnife.bind(this);

        dialogLoading = new Dialog(this);
        dialogLoading.setContentView(R.layout.dialog_loading);
        dialogLoading.setCancelable(false);
        Objects.requireNonNull(dialogLoading.getWindow()).setBackgroundDrawableResource(R.color.transparent);

//        toolbarText = findViewById(R.id.toolbar_text);
//        toolbarText.setText("Forum");

        WebSettings webSetting = webForum.getSettings();
        webSetting.setBuiltInZoomControls(false);
        webForum.setWebViewClient(new MyBrowser());
        webForum.getSettings().setJavaScriptEnabled(true);
        webForum.loadUrl("http://forum.banyakngerti.id/");

    }

    private class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            dialogLoading.show();
            view.loadUrl(url);

            return true;
        }
        @Override
        public void onPageFinished(WebView view, final String url) {
            dialogLoading.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        if (webForum.copyBackForwardList().getCurrentIndex() > 0) {
            webForum.goBack();
        }
        else {
            super.onBackPressed(); // finishes activity
        }
    }
}