package com.njnu.kai.practice.recycler.provider;

import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.njnu.kai.practice.recycler.modal.ButtonVO;
import com.njnu.kai.support.StringUtils;

import me.drakeet.multitype.BaseViewHolder;
import me.drakeet.multitype.ItemViewProvider;

import com.njnu.kai.practice.R;

/**
 * @author kai
 * @since 17/2/3
 */
public class ButtonProvider extends ItemViewProvider<ButtonVO, ButtonProvider.ButtonViewHolder> {

    @NonNull
    @Override
    protected ButtonViewHolder onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
        return new ButtonViewHolder(inflater, parent);
    }

    @Override
    protected void onBindViewHolder(@NonNull ButtonViewHolder holder, @NonNull ButtonVO buttonVO) {
        holder.bindView(buttonVO);
    }

    public static class ButtonViewHolder extends BaseViewHolder<ButtonVO> {

        private TextView mTvText;

        private View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!StringUtils.isEmpty(mData.mUrlScheme)) {
//                    NavigatorJump.toFullPath(mData.mUrlScheme);
                } else {
                    notifyMultiTypeViewClicked(null);
                }
            }
        };

        public ButtonViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent) {
            super(inflater.inflate(R.layout.provider_button, parent, false));
            initView();
        }

        @Override
        public void bindView(ButtonVO data) {
            super.bindView(data);

            if (data.mGravity != 0) {
                ((LinearLayout) itemView).setGravity(data.mGravity);
            }

            if (data.mViewWidth > 0) {
                itemView.getLayoutParams().width = data.mViewWidth;
            } else if (data.mViewHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                itemView.getLayoutParams().width = ViewGroup.LayoutParams.WRAP_CONTENT;
            }

            mTvText.setText(data.mText);
            if (data.mBkgResId != 0) {
                mTvText.setBackgroundResource(data.mBkgResId);
            } else if (data.mBackgroundColor != 0) {
                mTvText.setBackgroundColor(data.mBackgroundColor);
            }
            mTvText.setTextColor(data.mTextColor != 0 ? data.mTextColor : 0xFF666666);
            mTvText.setTextSize(TypedValue.COMPLEX_UNIT_SP, data.mTextSizeSp);
        }

        private void initView() {
            mTvText = (TextView) itemView.findViewById(R.id.tv_text);
            mTvText.setOnClickListener(mOnClickListener);
        }
    }

}
