package com.njnu.kai.practice.guest;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.ProxyActivity;
import com.njnu.kai.support.ToastUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-11-16
 */
public class Guest2Activity extends ProxyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GuestActivity2");
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(generateContentView(mProxyActivity));
    }

    private View generateContentView(final Context context) {
        TextView textView = new TextView(context);
        textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        textView.setText("at guest 2 Activity");
        textView.setBackgroundResource(R.drawable.image_guide_duck_1);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("in guest 2");
                startActivityByProxy("MainActivity", null);
            }
        });
        return textView;
    }
}
