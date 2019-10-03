package com.premar.muvi.activity;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.net.http.SslError;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.premar.muvi.R;
import com.premar.muvi.temporary_storage.MovieCache;

import static com.premar.muvi.utils.AppConstants.WIKIPEDIA_PAGE_URL;

public class WikipediaProfile extends AppCompatActivity {
    private WebView webView;
    private String url;
    private ProgressBar progressBar;
    private TextView tvLoad;
    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wikipedia_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        url = WIKIPEDIA_PAGE_URL + MovieCache.wiki_profile_url;
        webView = findViewById(R.id.profile_webview);
        tvLoad = findViewById(R.id.loading_textview);
        progressBar = findViewById(R.id.webview_progressbar);

        bundle = getIntent().getExtras();
        if (bundle!=null) {
            String movie_title = (String) bundle.get("title");
            getSupportActionBar().setTitle(movie_title);
        }

        setWebView();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setWebView(){
        setProgressBarVisibility(View.VISIBLE);
        tvLoad.setVisibility(View.VISIBLE);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                setProgressBarVisibility(View.VISIBLE);
                tvLoad.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                setProgressBarVisibility(View.GONE);
                tvLoad.setVisibility(View.GONE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                setProgressBarVisibility(View.GONE);
                tvLoad.setVisibility(View.GONE);
            }
        });
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        //webSettings.setAllowContentAccess(true);
        // webSettings.setLoadsImagesAutomatically(true);
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(url);
    }

    private void loadAlcUrl(String url){

        try {
            webView.loadUrl(url);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            webView.getCertificate();
            webView.reload();
        }

    }


    private void setProgressBarVisibility(int visibility) {
        // If a user returns back, a NPE may occur if WebView is still loading a page and then tries to hide a ProgressBar.
        if (progressBar != null) {
            progressBar.setVisibility(visibility);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id =  item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
