package com.njnu.kai.practice.di;

import javax.inject.Inject;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
public class JinGuBang implements IJinGuBang {

    @Inject
    public JinGuBang() {
    }

    @Override
    public String use() {
        return "user true Jin gu bang";
    }
}
