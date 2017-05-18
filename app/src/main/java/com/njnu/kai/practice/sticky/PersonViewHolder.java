package com.njnu.kai.practice.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.njnu.kai.practice.R;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class PersonViewHolder extends RecyclerView.ViewHolder {

    private TextView mTvName;

    public PersonViewHolder(View itemView) {
        super(itemView);
        mTvName = (TextView) itemView.findViewById(R.id.tv_name);
    }

    public void flushView(Person person) {
        mTvName.setText(person.mName);
    }
}
