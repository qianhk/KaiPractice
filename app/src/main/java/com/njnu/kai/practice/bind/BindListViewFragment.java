package com.njnu.kai.practice.bind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.njnu.kai.practice.R;
import com.njnu.kai.practice.bind.vm.UserModel;
import com.njnu.kai.support.base.BaseFragment;
import com.njnu.kai.support.list.adapter.ListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/15
 */
public class BindListViewFragment extends BaseFragment {

    private ListView mListView;
    private UserListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.common_listview, container, false);
        mListView = (ListView) view.findViewById(R.id.listview);
        mAdapter = new UserListAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.flushData(mockUserList(0));
        return view;
    }

    private List<UserModel> mockUserList(int time) {
        ArrayList<UserModel> userModelList = new ArrayList<>();
        for (int idx = 0; idx < 20; ++idx) {
            userModelList.add(new UserModel(time * 1000 + idx, "f" + time, "l" + idx));
        }
        return userModelList;
    }

    public static class UserListAdapter extends ListAdapter<UserModel> {

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                com.njnu.kai.practice.databinding.BindListItemTestUserBinding binding = com.njnu.kai.practice.databinding.BindListItemTestUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
//                BindListItemTestUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext())
//                        , R.layout.bind_list_item_test_user, parent, false);
                convertView = binding.getRoot();
                convertView.setTag(R.id.dataBinding, binding);
            }
            com.njnu.kai.practice.databinding.BindListItemTestUserBinding binding = (com.njnu.kai.practice.databinding.BindListItemTestUserBinding) convertView.getTag(R.id.dataBinding);
            binding.setUser(getItem(position));
            return convertView;
        }
    }
}
