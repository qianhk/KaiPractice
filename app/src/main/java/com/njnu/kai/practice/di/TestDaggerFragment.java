package com.njnu.kai.practice.di;

import android.os.Bundle;
import android.view.View;

import com.njnu.kai.practice.za.EditTextOutputFragment;

import javax.inject.Inject;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
public class TestDaggerFragment extends EditTextOutputFragment {

    @Inject
    IWuKong mWuKong;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setResult("test di");
//        XiYouComponent component = DaggerXiYouComponent.builder().xiYouModule(new XiYouModule()).build();
        XiYouComponent component = DaggerXiYouComponent.create();
        component.inject(this);
        appendResult(mWuKong.useJinGuBang());
    }
}
