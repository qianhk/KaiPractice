package com.njnu.kai.practice.bind;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.bind.vm.UserModel;
import com.njnu.kai.practice.binding.SimpleViewBinding;
import com.njnu.kai.support.base.BaseFragment;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/15
 */
public class BindSimpleViewFragment extends BaseFragment {

    private SimpleViewBinding mBinding;
    private UserModel mUserModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bind_fragment_test_user, container, false);
        mBinding = DataBindingUtil.bind(view);
        return view;

//        mBinding = DataBindingUtil.inflate(inflater, R.layout.bind_fragment_test_user, container, false);
//        return mBinding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUserModel = new UserModel(1, "钱", "掌柜");
        mBinding.setUser(mUserModel);
        mBinding.setHandlers(this);
    }

    public void onClickIdPlus1(View view) {
        mUserModel.setId(mUserModel.getId() + 1);
    }

    public void onClickIdMinus1(View view) {
        mUserModel.setId(mUserModel.getId() - 1);
    }
}
