package com.njnu.kai.practice.recycler.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.recycler.modal.Category;

import me.drakeet.multitype.ItemViewProvider;

public class CategoryItemViewProvider extends ItemViewProvider<Category, CategoryItemViewProvider.ViewHolder> {

    @NonNull
    @Override protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        View view = inflater.inflate(R.layout.type_item_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Category category) {
        holder.flushView(category);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvTitle;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvTitle = (TextView) itemView.findViewById(R.id.tv_title);
        }

        void flushView(Category category) {
            mTvTitle.setText(category.mTitle);
        }
    }
}
