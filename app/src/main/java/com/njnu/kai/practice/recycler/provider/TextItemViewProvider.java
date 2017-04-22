package com.njnu.kai.practice.recycler.provider;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.recycler.modal.Text;

import me.drakeet.multitype.BaseViewHolder;
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

    static class ViewHolder extends BaseViewHolder<Text> {

        private final TextView mTvText;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTvText = (TextView) itemView.findViewById(R.id.tv_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    notifyMultiTypeViewClicked(null);
                }
            });
            itemView.setClickable(false);
        }

        void flushView(Text text) {
            bindView(text);
            mTvText.setText(text.mText);
            itemView.setClickable(text.mClickable);
        }
    }

}
