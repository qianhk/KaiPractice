package com.njnu.kai.practice.aidl;

import com.njnu.kai.practice.aidl.UIInterface;

interface ServiceInterface {

    void registerTestCall(UIInterface activity);

    void invokeCallBack(int type);

    void sendBigData(String data);
}