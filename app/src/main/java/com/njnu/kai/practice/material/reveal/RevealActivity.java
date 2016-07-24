package com.njnu.kai.practice.material.reveal;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import com.njnu.kai.practice.R;
import com.njnu.kai.practice.material.reveal.action.BackAction;
import com.njnu.kai.practice.material.reveal.action.CloseAction;
import com.njnu.kai.practice.material.reveal.action.DrawerAction;
import com.njnu.kai.practice.material.reveal.action.PlusAction;
import com.njnu.kai.support.ToastUtils;


public class RevealActivity extends Activity implements View.OnClickListener {

    private RevealColorView revealColorView;
    private ActionView mActionView;
    private View selectedView;
    private int backgroundColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reveal);

        revealColorView = (RevealColorView)findViewById(R.id.reveal);
        backgroundColor = Color.parseColor("#212121");
        revealColorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showToast("click on revealColorView");
            }
        });

        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);

        mActionView = (ActionView)findViewById(R.id.action_view);
        mActionView.setColor(Color.BLUE);
    }

    @Override
    public void onClick(View v) {
        final int color = getColor(v);
        final Point p = getLocationInView(revealColorView, v);

        if (selectedView == v) {
            revealColorView.hide(p.x, p.y, backgroundColor, 0, 300, null);
            selectedView = null;
        } else {
            revealColorView.reveal(p.x, p.y, color, v.getHeight() / 2, 340, null);
            selectedView = v;
        }

        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_1:
                mActionView.setColor(Color.CYAN);
                mActionView.setAction(new BackAction(), ActionView.ROTATE_CLOCKWISE);
                break;
            case R.id.btn_2:
                mActionView.setColor(Color.RED);
                mActionView.setAction(new CloseAction(), ActionView.ROTATE_COUNTER_CLOCKWISE);
                break;
            case R.id.btn_3:
                mActionView.setColor(Color.WHITE);
                mActionView.setAction(new DrawerAction());
                break;
            case R.id.btn_4:
                mActionView.setColor(Color.GREEN);
                mActionView.setAction(new PlusAction());
                break;

            default:
                break;
        }
    }

    private Point getLocationInView(View src, View target) {
        final int[] l0 = new int[2];
        src.getLocationOnScreen(l0);

        final int[] l1 = new int[2];
        target.getLocationOnScreen(l1);

        l1[0] = l1[0] - l0[0] + target.getWidth() / 2;
        l1[1] = l1[1] - l0[1] + target.getHeight() / 2;

        return new Point(l1[0], l1[1]);
    }

    private int getColor(View view) {
        return Color.parseColor((String)view.getTag());
    }
}
