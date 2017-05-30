package com.njnu.kai.practice.ui;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;

/**
 * @author kai
 * @since 2017/6/1
 */
public class UIShowFragment extends BaseTestFragment {

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_ui_show, viewGroup, false);

        final TextView textView = (TextView) view.findViewById(R.id.tvHelloWorld);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        final BadgeDrawable drawable =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_NUMBER)
                        .number(9)
                        .build();

        final BadgeDrawable drawable2 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .badgeColor(0xff336699)
                        .text1("VIP")
                        .build();

        final BadgeDrawable drawable3 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_WITH_TWO_TEXT_COMPLEMENTARY)
                        .badgeColor(0xffCC9933)
                        .text1("LEVEL")
                        .text2("10")
                        .build();

        final BadgeDrawable drawable4 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_WITH_TWO_TEXT)
                        .badgeColor(0xffCC9999)
                        .text1("TEST")
                        .text2("Pass")
                        .build();

        final BadgeDrawable drawable5 =
                new BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_NUMBER)
                        .number(999)
                        .badgeColor(0xff336699)
                        .build();

        SpannableString spannableString =
                new SpannableString(TextUtils.concat(
                        "TextView: ",
                        drawable.toSpannable(),
                        " ",
                        drawable2.toSpannable(),
                        " ",
                        drawable3.toSpannable(),
                        " ",
                        drawable4.toSpannable(),
                        " ",
                        drawable5.toSpannable()
                ));

        if (textView != null) {
            textView.setText(spannableString);
        }

        if (imageView != null) {
            final BadgeDrawable drawable6 =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_WITH_TWO_TEXT_COMPLEMENTARY)
                            .badgeColor(0xff336633)
                            .textSize(DisplayUtils.dp2px(14))
                            .text1("Author")
                            .text2("Github")
//                            .typeFace(Typeface.createFromAsset(getAssets(), "fonts/code-bold.otf"))
                            .build();

            imageView.setImageDrawable(drawable6);
        }


        return view;
    }
}
