package com.njnu.kai.practice.pinyin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.support.BaseTestFragment;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/**
 * /packages/providers/ContactsProvider/src/com/android/providers
 * /contacts/HanziToPinyin.java
 *
 * @author kai
 * @since 17/4/9
 */
public class HanziToPinyinFragment extends BaseTestFragment {

    private TextView mTextView;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        mTextView = new TextView(layoutInflater.getContext());
        return mTextView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        long beginTimeNs = System.nanoTime();
        String input = "这是一个很独立的类，需要使用的项目直接拷贝到自己对应的工程里面即可使用，需要注意的是，在Android 4.2.2的系统源码中拷贝出来的，" +
                "为什么选择4.2.2，一个是4.2.2之后（4.3开始）的HanziToPinyin不再可以独立使用，需要依赖于Transliterator，而这个类我们是无法直接引用的。" +
                "而Android 2.x的HanziToPinyin在测试了很多转换的结果发现是错误的，所以选择了最后一个可以采纳使用的版本Android 4.2.2。";
        ArrayList<HanziToPinyin.Token> tokenList = HanziToPinyin.getInstance().get(input);
        long timeNs = System.nanoTime() - beginTimeNs;
        StringBuilder builder = new StringBuilder();
        builder.append(String.format(Locale.getDefault(), "文本长:%d 耗时:%dms", input.length(), TimeUnit.NANOSECONDS.toMillis(timeNs)));
        for (HanziToPinyin.Token token : tokenList) {
            builder.append(String.format(Locale.getDefault(), "\n%d %s %s", token.type, token.source, token.target));
        }
        mTextView.setText(builder.toString());
    }
}
