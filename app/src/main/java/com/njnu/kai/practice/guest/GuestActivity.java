package com.njnu.kai.practice.guest;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import com.njnu.kai.practice.guideanimator.ChickenAnimatorActivity;
import com.njnu.kai.support.ProxyActivity;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-11-16
 */
public class GuestActivity extends ProxyActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("GuestActivity1");
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {
        setContentView(generateContentView(mProxyActivity));
    }

    private View generateContentView(final Context context) {
        LinearLayout layout = new LinearLayout(context);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(Color.parseColor("#F79AB5"));
        Button button = new Button(context);
        button.setText("at 1, invoke guest activity 2");
        layout.addView(button, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(context, "in guest 1");
                startActivityByProxy("Guest2Activity", null);
            }
        });

        Button button2 = new Button(context);
        button2.setText("at 1, invoke ChickenAnimatorActivity ball");
        layout.addView(button2, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(context, "in guest 1");
                final Bundle bundle = new Bundle();
                bundle.putInt("key_function", 1);
                startActivityByProxy("ChickenAnimatorActivity", bundle);
            }
        });

        Button button3 = new Button(context);
        button3.setText("at 1, invoke ChickenAnimatorActivity chicken");
        layout.addView(button3, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showToast(context, "in guest 1");
                final Bundle bundle = new Bundle();
                bundle.putInt(ChickenAnimatorActivity.KEY_FUNCTION, 2);
                startActivityByProxy("ChickenAnimatorActivity", bundle);
            }
        });
        return layout;
    }
}
