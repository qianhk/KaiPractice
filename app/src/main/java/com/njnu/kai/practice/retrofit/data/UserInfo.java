package com.njnu.kai.practice.retrofit.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-5-5
 */
public class UserInfo {

    @SerializedName("id")
    private long mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("hobby")
    private ArrayList<String> mHobby;

    @SerializedName("fruit")
    private ArrayList<Fruit> mFruit;

    public static class Fruit {

        @SerializedName("id")
        private long mId;
        @SerializedName("type")
        private int mType;
        @SerializedName("name")
        private String mName;


        public Fruit(long id, int type, String name) {
            mId = id;
            mType = type;
            mName = name;
        }
    }

    public static UserInfo makeMockData(long id) {
        UserInfo userInfo = new UserInfo();
        userInfo.mId = id;
        userInfo.mName = "kai凯测试Test";

        userInfo.mHobby = new ArrayList<>();
        userInfo.mHobby.add("hobby1");
        userInfo.mHobby.add("hobby2");
        userInfo.mHobby.add("hobby3");

        userInfo.mFruit = new ArrayList<>();
        userInfo.mFruit.add(new Fruit(id * 100 + 1, (int)(id * 10 + 1), "apple"));
        userInfo.mFruit.add(new Fruit(id * 100 + 2, (int)(id * 10 + 2), "orange"));
        userInfo.mFruit.add(new Fruit(134_000, 456_789, "large_data"));

        return userInfo;
    }
}
