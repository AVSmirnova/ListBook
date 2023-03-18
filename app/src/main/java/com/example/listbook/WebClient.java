package com.example.listbook;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url) {
        return false;
    }
}
