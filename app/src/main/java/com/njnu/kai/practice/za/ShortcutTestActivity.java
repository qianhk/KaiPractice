package com.njnu.kai.practice.za;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.ToastUtils;
import com.njnu.kai.support.ViewUtils;
import com.njnu.kai.support.base.BaseActivity;

/**
 * Created by kai
 * since 16/12/12
 */
public class ShortcutTestActivity extends BaseActivity implements View.OnClickListener {

    public static final String KEY_ID= "id";
    public static final String KEY_TWEET= "tweet";

    private TextView mTvResult;

    private ViewGroup mLayoutTestClickEvent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shortcut);
        mTvResult = (TextView) findViewById(R.id.tv_result);

        Intent intent = getIntent();
        long id = intent.getLongExtra(KEY_ID, 0L);
        String tweet = intent.getStringExtra(KEY_TWEET);

        mTvResult.setText(String.format("id=%d\n%s", id, tweet));

        mLayoutTestClickEvent = (ViewGroup) findViewById(R.id.layout_test_click_event);

        ViewUtils.bindClickListener(this, this, R.id.btn_toggle_status, R.id.btn_test_click_event);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_test_click_event) {
            ToastUtils.showToast("you click test click event");
        } else if (viewId == R.id.btn_toggle_status) {
            mLayoutTestClickEvent.setEnabled(!mLayoutTestClickEvent.isEnabled());
            mTvResult.setText(String.format("now LayoutTestClickEvent enabled=%b", mLayoutTestClickEvent.isEnabled()));
        }
    }
}
