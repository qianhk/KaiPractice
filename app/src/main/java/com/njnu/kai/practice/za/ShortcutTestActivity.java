package com.njnu.kai.practice.za;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.base.BaseActivity;

/**
 * Created by kai
 * since 16/12/12
 */
public class ShortcutTestActivity extends BaseActivity {

    public static final String KEY_ID= "id";
    public static final String KEY_TWEET= "tweet";

    private TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        mTvResult = (TextView) findViewById(R.id.tv_result);

        Intent intent = getIntent();
        long id = intent.getLongExtra(KEY_ID, 0L);
        String tweet = intent.getStringExtra(KEY_TWEET);

        mTvResult.setText(String.format("id=%d\n%s", id, tweet));
    }
}
