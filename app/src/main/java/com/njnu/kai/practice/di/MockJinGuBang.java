package com.njnu.kai.practice.di;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
public class MockJinGuBang implements IJinGuBang {

    public MockJinGuBang() {
    }

    @Override
    public String use() {
        return "user mock Jin gu bang";
    }
}
