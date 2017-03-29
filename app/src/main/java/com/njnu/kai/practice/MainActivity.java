package com.njnu.kai.practice;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.njnu.kai.support.base.WrapFragmentActivity;

public class MainActivity extends WrapFragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // ATTENTION: This was auto-generated to handle app links.
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        Uri appLinkData = appLinkIntent.getData();
    }

    @Override
    protected Class wrapFragmentClass() {
        return MainFragment.class;
    }
}
