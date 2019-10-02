package com.premar.muvi.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.premar.muvi.R;
import com.premar.muvi.temporary_storage.MovieCache;

public class WatchTrailerActivity extends AppCompatActivity {
    private String link = "d7rlUe-Thvk";
    private WebView webView;
    private String youtube_link;
    private ProgressBar progressBar;
    private TextView tvLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_trailer);
        setTitle("Trailer Preview");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        webView = findViewById(R.id.trailer_webview);
        //tvLoad = findViewById(R.id.loading_video_textview);
        progressBar = findViewById(R.id.trailer_wv_progressbar);

        WebSettings ws = webView.getSettings();
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        ws.setPluginState(WebSettings.PluginState.ON);
        ws.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webView.reload();


        if (MovieCache.youtube_trailer_id.equalsIgnoreCase("")) {
            Toast.makeText(this, "Trailer currently not available, try again later!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        youtube_link = getHTML(MovieCache.youtube_trailer_id);
        webView.loadData(youtube_link, "text/html", "UTF-8");

        WebClientClass webViewClient = new WebClientClass(progressBar);
        webView.setWebViewClient(webViewClient);
        WebChromeClient webChromeClient = new WebChromeClient();
        webView.setWebChromeClient(webChromeClient);
    }

    public class WebClientClass extends WebViewClient {
        ProgressBar ProgressBar = null;

        WebClientClass(ProgressBar progressBar) {
            ProgressBar = progressBar;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            ProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            ProgressBar.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(getHTML(link));
            return true;
        }
    }

    public String getHTML(String url) {
        String html = "<iframe class=\"youtube-player\" " + "style=\"border: 0; width: 100%; height: 96%;"
                + "padding:0px; margin:0px\" " + "id=\"ytplayer\" type=\"text/html\" "
                + "src=\"http://www.youtube.com/embed/" + url
                + "?&theme=dark&autohide=2&modestbranding=1&showinfo=0&autoplay=1\fs=0\" frameborder=\"0\" "
                + "allowfullscreen autobuffer " + "controls onclick=\"this.play()\">\n" + "</iframe>\n";
        return html;
    }

    private void setProgressBarVisibility(int visibility) {
        // If a user returns back, a NPE may occur if WebView is still loading a page and then tries to hide a ProgressBar.
        if (progressBar != null) {
            progressBar.setVisibility(visibility);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
