package com.njnu.kai.practice.di;

import dagger.Component;

import javax.inject.Singleton;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/16
 */
@Component(modules = {XiYouModule.class})
@Singleton
public interface XiYouComponent {

//    void inject(WuKong wk);
//
//    void inject(JinGuBang jinGuBang);

    TestDaggerFragment inject(TestDaggerFragment fragment);

}
