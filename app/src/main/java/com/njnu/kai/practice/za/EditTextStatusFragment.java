package com.njnu.kai.practice.za;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.os.MessageQueue;
import android.support.annotation.Nullable;
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
import com.njnu.kai.support.ViewUtils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kai
 * since 16/10/13
 */
public class EditTextStatusFragment extends BaseTestFragment {

    private static final String TAG = "EditTextStatusFragment";
    private View mRootView;
    private EditText mEdtText;
    private TextView mTvText;
    private TextView mTvResult;
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

//        ClipboardManager cm =(ClipboardManager) layoutInflater.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//        ClipData primaryClip = cm.getPrimaryClip();
//        ClipDescription primaryClipDescription = cm.getPrimaryClipDescription();
//        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append("description: " + primaryClipDescription.toString());
//        stringBuilder.append("\nprimary clip: " + primaryClip.toString());
//        mTvResult.setText(stringBuilder);

        mTvResult.setText(getClipboardString());
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

        mTvResult = ViewUtils.findTextViewById(rootView, R.id.tv_result);
    }

    public CharSequence getClipboardString() {
        Context context = getContext();
        ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);

        Map<String, Object> map = new HashMap<>(2);
        ClipData clip = clipboard.getPrimaryClip();
        if (clip != null && clip.getItemCount() > 0) {
            ClipData.Item item = clip.getItemAt(0);
            CharSequence text = coerceToText(context, item);
            return text;
        } else {
            return "no clipboard data";
        }
    }

    @Nullable
    private CharSequence coerceToText(Context context, ClipData.Item item) {
        // Condition 1. just a simple text
        CharSequence text = item.getText();
        if (text != null) {
            return text;
        }

        // Condition 2. a URI value
        Uri uri = item.getUri();
        if (uri != null) {
            FileInputStream stream = null;
            try {
                AssetFileDescriptor assetFileDescriptor = context.getContentResolver().openTypedAssetFileDescriptor(uri, "text/*", null);
                stream = assetFileDescriptor.createInputStream();
                InputStreamReader reader = new InputStreamReader(stream, "UTF-8");

                StringBuilder builder = new StringBuilder(128);
                char[] buffer = new char[8192];
                int len;
                while ((len = reader.read(buffer)) > 0) {
                    builder.append(buffer, 0, len);
                }
                return builder.toString();

            } catch (FileNotFoundException e) {
                //  ignore.
            } catch (IOException e) {
                LogUtils.w("ClippedData Failure loading text.", e);
            } finally {
                if (stream != null) {
                    try {
                        stream.close();
                    } catch (IOException e) {
                        // ignore
                    }
                }
            }

            return uri.toString();
        }

        // Condition 3.  an intent.
        Intent intent = item.getIntent();
        if (intent != null) {
            return intent.toUri(Intent.URI_INTENT_SCHEME);
        }

        // else case
        return null;
    }

    private MessageQueue.IdleHandler mIdleHandler;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mIdleHandler = new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                LogUtils.i(TAG, "queueIdle");
                return true;
            }
        };
        Looper.myQueue().addIdleHandler(mIdleHandler);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Looper.myQueue().removeIdleHandler(mIdleHandler);
    }
}
