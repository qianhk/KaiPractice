package com.njnu.kai.practice.tag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.ToastUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author kai
 * @since 2017/6/4
 */
public class TagTestFragment extends BaseTestFragment {

    private TagFlowLayout mTagLayout;

    private TextView mTvResult;

    private List<Object> mTagList;

    private LabelTagAdapter mLabelTagAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_tag_test, viewGroup, false);
        mTagLayout = (TagFlowLayout) rootView.findViewById(R.id.tag_layout);
        mTvResult = (TextView) rootView.findViewById(R.id.tv_result);
        mLabelTagAdapter = new LabelTagAdapter();
        mTagLayout.setAdapter(mLabelTagAdapter);
        return rootView;
    }


    private StringBuilder mStrBuilder = new StringBuilder();

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTagList = new ArrayList<>();
        for (int idx = 0; idx < 20; ++idx) {
            mTagList.add("TagTest" + idx);
        }
        mLabelTagAdapter.flushView(mTagList);

        mTagLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                ToastUtils.showToast("点击了tag: " + mTagList.get(position));
                return true;
            }
        });
        mTagLayout.setOnTagSelectedListener(new TagFlowLayout.OnTagSelectedListener() {
            @Override
            public void onTagSelected(HashSet<Integer> selectedViews) {
                mStrBuilder.setLength(0);
                mStrBuilder.append("选中的tag: ");
                for (Integer pos : selectedViews) {
                    mStrBuilder.append(mTagList.get(pos) + " ");
                }
                mTvResult.setText(mStrBuilder.toString());
            }
        });
    }
}
