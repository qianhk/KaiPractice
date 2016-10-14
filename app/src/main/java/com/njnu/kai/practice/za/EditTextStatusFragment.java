package com.njnu.kai.practice.za;

import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.ToastUtils;

/**
 * Created by kai
 * since 16/10/13
 */
public class EditTextStatusFragment extends BaseTestFragment {

    private static final String TAG = "EditTextStatusFragment";
    private View mRootView;
    private EditText mEdtText;
    private TextView mTvText;
    private Button mBtnTest;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == mTvText) {
                ToastUtils.showToast("点击了mTvText");
            } else if (v == mBtnTest) {
                ToastUtils.showToast("点击了mBtnTest");
            } else if (v == mEdtText) {
                ToastUtils.showToast("mEdtText");
            }
        }
    };

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_edit_text_status, viewGroup, false);
        initView(view);
        return view;
    }

    private void initView(View rootView) {
        mRootView = rootView;
        mEdtText = (EditText) rootView.findViewById(R.id.edt_text);

        InputFilter[] oriFilter = mEdtText.getFilters();
        InputFilter[] newFilter;

        int len = 1;
        if (oriFilter != null && oriFilter.length > 0) {
            len += oriFilter.length;
            newFilter = new InputFilter[len];
            System.arraycopy(oriFilter, 0, newFilter, 0, oriFilter.length);
        } else {
            newFilter = new InputFilter[len];
        }
        newFilter[len - 1] = new InputFilter.LengthFilter(11);
        mEdtText.setFilters(newFilter);
        mEdtText.setRawInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        mTvText = (TextView) rootView.findViewById(R.id.tv_text);
        mBtnTest = (Button) rootView.findViewById(R.id.btn_test);
        mTvText.setOnClickListener(mOnClickListener);
        mBtnTest.setOnClickListener(mOnClickListener);
        mEdtText.setOnClickListener(mOnClickListener);
        mEdtText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.i(TAG, "onFocusChange mEdtText hasFocus=%b", hasFocus);
            }
        });
        mEdtText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                LogUtils.i(TAG, "afterTextChanged mEdtText txt=%s", s);
            }
        });
        mBtnTest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                LogUtils.i(TAG, "onFocusChange mBtnTest hasFocus=%b", hasFocus);
            }
        });
    }
}
