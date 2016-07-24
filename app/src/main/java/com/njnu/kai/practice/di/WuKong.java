package com.njnu.kai.practice.di;

import javax.inject.Inject;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
public class WuKong implements IWuKong {

    IJinGuBang mJinGuBang;

    @Inject
    public WuKong(IJinGuBang jinGuBang) {
        mJinGuBang = jinGuBang;
    }

    @Override
    public String useJinGuBang() {
        return "true wukong " + mJinGuBang.use();
    }
}
