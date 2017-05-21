package com.herokuapp.jarilabs.jarilabs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    private String webAddress = "https://jarilabs.herokuapp.com";
    private WebView webView;
    private ProgressBar progressBar;
    private FrameLayout frameLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        progressBar = (ProgressBar) findViewById(R.id.progresBar);
        progressBar.setMax(100);
        webView = (WebView) findViewById(R.id.my_webview);

        webView.setWebViewClient(new HelpClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                frameLayout.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);

                setTitle("Loading...");

                if(newProgress == 100){
                    frameLayout.setVisibility(View.GONE);
                    setTitle(view.getTitle());
                }

                super.onProgressChanged(view, newProgress);

            }
        });

        webView.getSettings().setJavaScriptEnabled(true);
        webView.setVerticalScrollBarEnabled(false);
        webView.loadUrl(webAddress);
        progressBar.setProgress(0);

    }

    private class HelpClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            frameLayout.setVisibility(View.VISIBLE);
            return true;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            if(webView.canGoBack()){
                webView.goBack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
