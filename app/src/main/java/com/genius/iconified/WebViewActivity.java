package com.genius.iconified;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by manjeet on 3/3/18.
 */

public class WebViewActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView titleToolbarTextView;

    private WebView webView;

    private String searchQuery;
    private ProgressBar mProgressBar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        init();
    }

    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbarTextView = findViewById(R.id.toolbar_title_textview);

        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/Pacifico.ttf");
        titleToolbarTextView.setTypeface(customFont);
        titleToolbarTextView.setText(getString(R.string.app_name));

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = findViewById(R.id.activity_webview_webview);
        mProgressBar = (ProgressBar) findViewById(R.id.activity_webview_progressbar);

        searchQuery = getIntent().getStringExtra("query");

        if (isInternetOn())
            loadURL();
        else {
            Toast.makeText(getApplicationContext(), "Unable To Connect To Internet", Toast.LENGTH_SHORT).show();
        }
    }

    boolean isInternetOn() {
        ConnectivityManager conn = (ConnectivityManager) getSystemService(getBaseContext().CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = conn.getActiveNetworkInfo();

        if (netInfo != null && netInfo.isConnected())
            return true;
        else
            return false;
    }

    private void loadURL() {
        String url = "http://www.google.com/search?q=" +
                searchQuery.replaceAll(" ", "+") +
                "&btnI";

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
            }

        });

        webView.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                mProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    mProgressBar.setVisibility(View.GONE);
                }
            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
    }
}
