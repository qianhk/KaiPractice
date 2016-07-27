package com.njnu.kai.practice.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import com.njnu.kai.support.LogUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-7-27
 */
public class AidlTestService extends Service {

    private static final String TAG = "AidlTestService";
    private UIInterface mUiCallback;

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    private final ServiceInterface.Stub mBinder = new ServiceInterface.Stub() {
        @Override
        public void invokeCallBack(int type) throws RemoteException {
            LogUtils.e(TAG, "lookaidl at service invokCallBack type " + type);
            mUiCallback.performAction();
        }

        @Override
        public void sendBigData(String data) throws RemoteException {
            LogUtils.e(TAG, "lookaidl at service send big data, size=" + data.length());
            mUiCallback.notifyDisplay("receive big data, size=" + data.length());
        }

        @Override
        public void registerTestCall(UIInterface cb) throws RemoteException {
            mUiCallback = cb;
        }

    };
}
