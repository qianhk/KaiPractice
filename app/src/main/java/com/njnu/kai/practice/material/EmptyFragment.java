package com.njnu.kai.practice.material;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.base.BaseFragment;


/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-12-2
 */
public class EmptyFragment extends BaseFragment {

    public static EmptyFragment instantiate(String tweet) {
        final Bundle bundle = new Bundle();
        bundle.putString("tweet", tweet);
        final EmptyFragment fragment = new EmptyFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_empty_with_text, container, false);
        final TextView textView = (TextView) view.findViewById(R.id.tv_text);
        Bundle arguments = getArguments();
        textView.setText(arguments != null ? arguments.getString("tweet") : "no arguments");
        return view;
    }
}
