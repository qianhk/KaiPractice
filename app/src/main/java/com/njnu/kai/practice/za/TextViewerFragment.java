package com.njnu.kai.practice.za;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.njnu.kai.support.base.ActionBarLayoutFragment;
import com.njnu.kai.support.base.BaseFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/4/24
 */
public class TextViewerFragment extends ActionBarLayoutFragment {

    private WebView mWebView;

    private static final String KEY_VIEW_TEXT = "key_view_text";

    public static void launch(BaseFragment fragment, String text) {
        TextViewerFragment viewerFragment = new TextViewerFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_VIEW_TEXT, text);
        viewerFragment.setArguments(bundle);
        fragment.launchFragment(viewerFragment);
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mWebView = new WebView(layoutInflater.getContext());
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setDefaultTextEncodingName("utf-8");
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }
        });

        String string = getArguments().getString(KEY_VIEW_TEXT);
//        string = HtmlEscapers.htmlEscaper().escape(string);
//        string = "alksdfjlasdkfasdf";
//        setTitle(string);
//        mWebView.loadDataWithBaseURL("file:///android_asset/prettify/"
//                , "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html xmlns=\"http://www.w3.org/1999/xhtml\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><script src=\"run_prettify.js?skin=sons-of-obsidian\"></script></head><body bgcolor=\"#000000\"><pre class=\"prettyprint \">"
//                + string
//                + "</pre></body></html>", "text/html", "UTF-8", null);
        mWebView.loadData(string, "text/html", "UTF-8");


        return mWebView;
    }
}
