package com.njnu.kai.practice.ui;

import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.ui.dividerdrawable.DividerDrawable;
import com.njnu.kai.practice.ui.dividerdrawable.DividerLayout;
import com.njnu.kai.practice.ui.dividerdrawable.DividerUtils;
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

        testBadgeDrawable(view);

        testDividerDrawable(view);

        return view;
    }

    private void testDividerDrawable(View view) {
        TextView tvHtml = (TextView) view.findViewById(R.id.tv_html);

        String html="<html><head><title>TextView使用HTML</title></head><body><p><strong>强调</strong></p><p><em>斜体</em></p>"
                +"<p><a href=\"http://www.dreamdu.com/xhtml/\">超链接HTML入门</a>学习HTML!</p><p><font color=\"#aabb00\"  style=\"font-size:40%\">颜色1"
                +"</p><p><font color=\"#00bbaa\"  style=\"font-size:200%\">颜色2</p><h1>标题1</h1><h3>标题2</h3><h6>标题3</h6><p>大于>小于<</p><p>" +
                "</body></html>";

        tvHtml.setText(Html.fromHtml(html));
        DividerDrawable dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setCenter(DividerLayout.CENTER_VERTICAL);
        DividerUtils.addDividersTo(view.findViewById(R.id.linear1), dividerDrawable);

        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setMarginLeftDp(50)
                .setCenter(DividerLayout.CENTER_VERTICAL);
        DividerUtils.addDividersTo(view.findViewById(R.id.linear2), dividerDrawable);

        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setOrientation(DividerLayout.ORIENTATION_VERTICAL)
                .setAlign(DividerLayout.ALIGN_PARENT_RIGHT)
                .setMarginTopDp(16)
                .setMarginBottomDp(16);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_3_1), dividerDrawable);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_3_3), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setOrientation(DividerLayout.ORIENTATION_VERTICAL)
                .setAlign(DividerLayout.ALIGN_PARENT_RIGHT)
                .setMarginTopDp(32)
                .setMarginBottomDp(32);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_3_2), dividerDrawable);

        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setMarginLeftDp(16)
                .setLengthDp(100);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_1), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setOrientation(DividerLayout.ORIENTATION_VERTICAL)
                .setCenter(DividerLayout.CENTER_VERTICAL)
                .setLengthDp(46);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_1), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setCenter(DividerLayout.CENTER_HORIZONTAL)
                .setLengthDp(100);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_2), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setOrientation(DividerLayout.ORIENTATION_VERTICAL)
                .setAlign(DividerLayout.ALIGN_PARENT_RIGHT)
                .setCenter(DividerLayout.CENTER_VERTICAL)
                .setLengthDp(46);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_2), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setAlign(DividerLayout.ALIGN_PARENT_RIGHT)
                .setMarginRightDp(16)
                .setLengthDp(100);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_3), dividerDrawable);
        dividerDrawable = new DividerDrawable();
        dividerDrawable
                .setStrokeWidth(10)
                .setColor(0xFFFFFFFF)
                .getLayout()
                .setCenter(DividerLayout.CENTER_HORIZONTAL)
                .setLengthDp(300);
        DividerUtils.addDividersTo(view.findViewById(R.id.text_4_4), dividerDrawable);
    }

    private void testBadgeDrawable(View view) {
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
    }
}
