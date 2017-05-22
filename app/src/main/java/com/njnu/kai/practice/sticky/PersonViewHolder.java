package com.njnu.kai.practice.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.njnu.kai.practice.R;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    private boolean mHideTitle;

    private ImageView mIvAvatar;
    private TextView mTvName;
    private TextView mTvYear;

    public PersonViewHolder(View itemView, boolean hideTitle) {
        super(itemView);
        mHideTitle = hideTitle;
        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
        mTvYear = (TextView) itemView.findViewById(R.id.tv_year);
        mIvAvatar = (ImageView) itemView.findViewById(R.id.iv_avatar);
        mTvYear.setVisibility(hideTitle ? View.GONE : View.VISIBLE);
    }

    public void flushView(Person person, boolean showYearView) {
        mTvName.setText(person.mName);
        mTvYear.setText(person.mYear);
        mIvAvatar.setImageResource(person.mAvatarId);
        if (!mHideTitle) {
            mTvYear.setVisibility(showYearView ? View.VISIBLE : View.GONE);
        }
    }
}
