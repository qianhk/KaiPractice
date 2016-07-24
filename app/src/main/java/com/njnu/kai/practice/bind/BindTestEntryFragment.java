package com.njnu.kai.practice.bind;

import android.os.Bundle;
import android.view.View;

import com.njnu.kai.support.BaseTestListFragment;
import com.njnu.kai.support.TestFunction;

/**
 * 原文: http://developer.android.com/intl/zh-cn/tools/data-binding/guide.html
 * 翻译: https://segmentfault.com/a/1190000002876984
 * github: https://github.com/LyndonChin/MasteringAndroidDataBinding
 *
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/15
 */
public class BindTestEntryFragment extends BaseTestListFragment {

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setResultViewVisibility(View.GONE);
    }

    @TestFunction("单一View绑定测试")
    private void test00() {
        launchFragment(new BindSimpleViewFragment());
    }

    @TestFunction("ListView绑定测试")
    private void test10() {
        launchFragment(new BindListViewFragment());
    }
}
