package com.njnu.kai.practice.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class StickPersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private boolean mHideTitle;

    public StickPersonAdapter(boolean hideTitle) {
        mHideTitle = hideTitle;
    }

    private ArrayList<Person> mPerson = new ArrayList<>();

    public void updateData(List<Person> dataList) {
        mPerson.clear();
        mPerson.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sticky_person_item, parent, false);
        return new PersonViewHolder(itemView, mHideTitle);
    }

    public final boolean showYearView(int pos) {
        if (pos == 0) {
            return true;
        }
        return !mPerson.get(pos).mYear.equals(mPerson.get(pos - 1).mYear);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.flushView(mPerson.get(position), showYearView(position));
    }

    @Override
    public int getItemCount() {
        return mPerson.size();
    }

    public String yearString(int pos) {
        return mPerson.get(pos).mYear;
    }
}
