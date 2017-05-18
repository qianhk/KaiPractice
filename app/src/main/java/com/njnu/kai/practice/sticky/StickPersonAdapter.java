package com.njnu.kai.practice.sticky;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 2017/5/18
 */
public class StickPersonAdapter extends RecyclerView.Adapter<PersonViewHolder> {

    private ArrayList<Person> mPerson = new ArrayList<>();

    public void updateData(List<Person> dataList) {
        mPerson.clear();
        mPerson.addAll(dataList);
        notifyDataSetChanged();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_sticky_person_item, parent, false);
        return new PersonViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder holder, int position) {
        holder.flushView(mPerson.get(position));
    }

    @Override
    public int getItemCount() {
        return mPerson.size();
    }
}
