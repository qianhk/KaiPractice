package com.njnu.kai.practice;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.webkit.WebView;

/**
 * @version 1.0.0
 * @since 14-9-17
 */
public class WebViewActivity extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_webview);
        WebView webView = (WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setBackgroundColor(0);
        Drawable background = webView.getBackground();
        if (background != null) {
            background.setAlpha(1);
        }
        webView.loadUrl("file:///sdcard/kai/splash/1410777165059dir/index.html");
    }
}