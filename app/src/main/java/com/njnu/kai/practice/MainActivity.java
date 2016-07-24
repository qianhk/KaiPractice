package com.njnu.kai.practice;

import com.njnu.kai.support.base.WrapFragmentActivity;

public class MainActivity extends WrapFragmentActivity {

    @Override
    protected Class wrapFragmentClass() {
        return MainFragment.class;
    }
}
