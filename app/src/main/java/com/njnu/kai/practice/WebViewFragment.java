package com.njnu.kai.practice;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.LogUtils;

/**
 * @version 1.0.0
 * @since 14-9-17
 */
public class WebViewFragment extends BaseTestFragment {

    private static final String TAG = "WebViewFragment";
    private WebView mWebView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.main_webview, viewGroup, false);
        mWebView = (WebView) rootView.findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setBackgroundColor(0);
        Drawable background = mWebView.getBackground();
        if (background != null) {
            background.setAlpha(1);
        }
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                LogUtils.i(TAG, "shouldOverrideUrlLoading %s", url);
                view.loadUrl(url);
                return true;
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mWebView.loadUrl("http://weibo.com/6113048084/Er5Zxiv3N");
    }
}