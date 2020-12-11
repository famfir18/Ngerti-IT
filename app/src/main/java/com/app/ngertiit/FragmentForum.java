package com.app.ngertiit;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentForum extends Fragment {

    @BindView(R.id.webview_forum)
    WebView webForum;
    TextView toolbarText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forum, container, false);
        ButterKnife.bind(this,view);

        toolbarText = getActivity().findViewById(R.id.toolbar_text);
        toolbarText.setText("Forum");

        WebSettings webSetting = webForum.getSettings();
        webSetting.setBuiltInZoomControls(false);
        webForum.setWebViewClient(new WebViewClient());
        webForum.getSettings().setJavaScriptEnabled(true);
        webForum.loadUrl("http://forum.banyakngerti.id/forums");

        return view;

    }
}