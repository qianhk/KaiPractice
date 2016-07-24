package com.njnu.kai.practice.di;

import dagger.Module;
import dagger.Provides;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
@Module
public class XiYouModule {

    @Provides
    IWuKong provideWuKong(IJinGuBang jinGuBang) {
        return new MockWuKong(jinGuBang);
    }

    @Provides
    IJinGuBang provideJinGuBang() {
        return new MockJinGuBang();
    }
}
