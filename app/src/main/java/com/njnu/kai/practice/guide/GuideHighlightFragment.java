package com.njnu.kai.practice.guide;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.*;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/6/7
 */
public class GuideHighlightFragment extends BaseTestFragment implements View.OnClickListener {

    private View bt_top_left;
    private View bt_top_right;
    private View bt_middle;
    private View bt_bottom_left;
    private View bt_bottom_right;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.guide_highlight_fragment, viewGroup, false);
        bt_top_left = view.findViewById(R.id.btn_top_left);
        bt_top_right = view.findViewById(R.id.btn_top_right);
        bt_middle = view.findViewById(R.id.btn_center);
        bt_bottom_left = view.findViewById(R.id.btn_bottom_left);
        bt_bottom_right = view.findViewById(R.id.btn_bottom_right);
        com.njnu.kai.support.ViewUtils.bindClickListener(view, this, R.id.btn_top_left, R.id.btn_top_right, R.id.btn_bottom_left, R.id.btn_bottom_right, R.id.btn_center);
        return view;
    }

    @Override
    public void onClick(View v) {
        Activity activity = getActivity();
        switch (v.getId()) {
            case R.id.btn_top_left:
                HighLightGuideView.builder(activity)
                        .addHighLightGuidView(bt_top_left, R.drawable.dmtext)
                        .setHighLightStyle(HighLightGuideView.VIEWSTYLE_OVAL)
                        .show();
                break;
            case R.id.btn_top_right:
                HighLightGuideView.builder(activity)
                        .addHighLightGuidView(bt_top_right, R.drawable.dstext)
                        .setHighLightStyle(HighLightGuideView.VIEWSTYLE_RECT)
                        .show();
                break;
            case R.id.btn_center:
                HighLightGuideView.builder(activity)
                        .addHighLightGuidView(bt_middle, R.drawable.dmtext)
                        .setHighLightStyle(HighLightGuideView.VIEWSTYLE_CIRCLE)
                        .show();
                break;
            case R.id.btn_bottom_left:
                HighLightGuideView.builder(activity)
                        .addHighLightGuidView(bt_bottom_left, R.drawable.dstext)
                        .addHighLightGuidView(bt_top_right, R.drawable.dmtext)
                        .setHighLightStyle(HighLightGuideView.VIEWSTYLE_RECT)
                        .show();
                break;
            case R.id.btn_bottom_right:
                HighLightGuideView.builder(activity)
                        .addHighLightGuidView(bt_bottom_right, R.drawable.dstext)
                        .addHighLightGuidView(bt_top_left, R.drawable.dmtext)
                        .setHighLightStyle(HighLightGuideView.VIEWSTYLE_OVAL)
                        .show();
                break;
        }
    }
}
