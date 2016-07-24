package com.njnu.kai.practice.material;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.njnu.kai.support.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-12-1
 */
public class StringAdapter extends RecyclerView.Adapter<StringAdapter.StringViewHolder> {

    private ArrayList<String> mDataList = new ArrayList<>();

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TextView textView = new TextView(parent.getContext());
        textView.setPadding(DisplayUtils.dp2px(16), DisplayUtils.dp2px(8), DisplayUtils.dp2px(16), DisplayUtils.dp2px(8));
        return new StringViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(StringViewHolder holder, int position) {
        holder.setText(mDataList.get(position));
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    public void updateData(List<String> dataList) {
        mDataList.clear();
        if (dataList != null) {
            mDataList.addAll(dataList);
        }
        notifyDataSetChanged();
    }


    public static class StringViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvText;

        public StringViewHolder(View itemView) {
            super(itemView);
            mTvText = (TextView) itemView;
        }

        public void setText(String text) {
            mTvText.setText(text);
        }
    }

    public static ArrayList<String> mockData(int index, int count) {
        final ArrayList<String> strings = new ArrayList<>();
        for (int idx = 0; idx < count; ++idx) {
            strings.add(String.format("mock string index=%d idx=%d", index, idx + 1));
        }
        return strings;
    }

}
