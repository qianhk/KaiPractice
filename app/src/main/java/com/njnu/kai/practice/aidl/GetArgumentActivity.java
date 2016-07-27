package com.njnu.kai.practice.aidl;

import android.os.Bundle;
import android.widget.TextView;
import com.njnu.kai.support.base.BaseActivity;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-7-27
 */
public class GetArgumentActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView textView = new TextView(this);
        setContentView(textView);
        textView.setText("GetArgumentActivity");
    }
}
