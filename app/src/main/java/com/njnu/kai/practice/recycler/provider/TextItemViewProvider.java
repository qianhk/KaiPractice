package com.njnu.kai.practice.recycler.provider;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.recycler.modal.Text;

import me.drakeet.multitype.ItemViewProvider;

/**
 * @author kai
 * @since 17/1/8
 */
public class TextItemViewProvider extends ItemViewProvider<Text, TextItemViewProvider.ViewHolder> {

    @NonNull
    @Override
    protected ViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ViewHolder(inflater.inflate(R.layout.type_item_text, parent, false));
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, @NonNull Text text) {
        holder.flushView(text);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView mTvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvText = (TextView) itemView.findViewById(R.id.tv_text);
        }

        void flushView(Text text) {
            mTvText.setText(text.mText);
        }
    }

}
