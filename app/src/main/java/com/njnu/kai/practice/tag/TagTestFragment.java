package com.njnu.kai.practice.tag;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kai
 * @since 2017/6/4
 */
public class TagTestFragment extends BaseTestFragment {

    private TagFlowLayout mTagLayout;

    private List<Object> mTagList;

    private LabelTagAdapter mLabelTagAdapter;

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View rootView = layoutInflater.inflate(R.layout.fragment_tag_test, viewGroup, false);
        mTagLayout = (TagFlowLayout) rootView.findViewById(R.id.tag_layout);

        mLabelTagAdapter = new LabelTagAdapter();
        mTagLayout.setAdapter(mLabelTagAdapter);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mTagList = new ArrayList<>();
        for (int idx = 0; idx < 20; ++idx) {
            mTagList.add("TagTest" + idx);
        }
        mLabelTagAdapter.flushView(mTagList);
    }
}
