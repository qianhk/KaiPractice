package com.njnu.kai.practice.bind.vm;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.njnu.kai.practice.BR;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/5/15
 */
public class UserModel extends BaseObservable {

    private long mId;
    private String mFirstName;
    private String mLastName;

    public UserModel(long id, String firstName, String lastName) {
        mId = id;
        mFirstName = firstName;
        mLastName = lastName;
    }

    @Bindable
    public long getId() {
        return mId;
    }

    @Bindable
    public String getFirstName() {
        return mFirstName;
    }

    @Bindable
    public String getLastName() {
        return mLastName;
    }

    public void setId(long id) {
        mId = id;
        notifyPropertyChanged(BR.id);
    }
}
