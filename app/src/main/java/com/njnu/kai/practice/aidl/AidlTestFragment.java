package com.njnu.kai.practice.aidl;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.ToastUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16-7-27
 */
public class AidlTestFragment extends BaseTestFragment {

    private static final String TAG = "AidlTestFragment";
    private TextView mTvResult;

    private View mSendView;
    private View mSendBigView;
    private Button mBtnAction;

    private boolean mConnected;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int viewId = v.getId();
            if (viewId == R.id.btn_action) {
                v.setEnabled(false);
                if (mConnected) {
                    getActivity().unbindService(mConnection);
                    onDisconnectedServiceFlushView();
                } else {
                    Bundle args = new Bundle();
                    Intent intent = new Intent(AidlTestFragment.this.getContext(), AidlTestService.class);
                    intent.putExtras(args);
                    final boolean bindServiceRsult = getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
                    ToastUtils.showToast("bindServiceResult " + bindServiceRsult);
                }
            } else if (viewId == R.id.btn_send) {
                if (mService == null) {
                    ToastUtils.showToast("未连接、无法发送命令");
                    return;
                }
                try {
                    mService.invokeCallBack(1);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (viewId == R.id.btn_send_big) {
                setBigData();
            }
        }
    };

    private void setBigData() {
        LogUtils.e(TAG, "lookaidl prepare big data...");
        int length = 1 * 1024 * 1024 + 100 * 1024;
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int idx = 0; idx < length; ++idx) {
            stringBuilder.append("A");
        }
        LogUtils.e(TAG, "lookaidl begin Send big data...  length=" + length);
        try {
            mService.sendBigData(stringBuilder.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
            mTvResult.setText(e.toString());
            LogUtils.e(TAG, "lookaidl send big data exception: " + e.toString());
        }
        LogUtils.e(TAG, "lookaidl end Send big data...");
    }
/*
android.os.TransactionTooLargeException: data parcel size 2302056 bytes
    at android.os.BinderProxy.transactNative(Native Method)
    at android.os.BinderProxy.transact(Binder.java:503)
    at com.njnu.kai.practice.aidl.ServiceInterface$Stub$Proxy.sendBigData(ServiceInterface.java:127)
    at com.njnu.kai.practice.aidl.AidlTestFragment.setBigData(AidlTestFragment.java:77)
    at com.njnu.kai.practice.aidl.AidlTestFragment.access$400(AidlTestFragment.java:25)
    at com.njnu.kai.practice.aidl.AidlTestFragment$1.onClick(AidlTestFragment.java:63)
    at android.view.View.performClick(View.java:5207)
    at android.view.View$PerformClick.run(View.java:21177)
    at android.os.Handler.handleCallback(Handler.java:739)
    at android.os.Handler.dispatchMessage(Handler.java:95)
    at android.os.Looper.loop(Looper.java:148)
    at android.app.ActivityThread.main(ActivityThread.java:5441)
    at java.lang.reflect.Method.invoke(Native Method)
    at com.android.internal.os.ZygoteInit$MethodAndArgsCaller.run(ZygoteInit.java:738)
    at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:628)
 */
    @Override
    public void onDestroyView() {
        if (mConnected) {
            getActivity().unbindService(mConnection);
        }
        super.onDestroy();
    }

    private UIInterface mCallback = new UIInterface.Stub() {

        public void performAction() throws RemoteException {
            mTvResult.setText("toast from service");
            ToastUtils.showToast("this toast is called from service");
            LogUtils.e(TAG, "lookaidl at ui performAction ");
        }

        @Override
        public void notifyDisplay(String result) throws RemoteException {
            mTvResult.setText(result);
        }
    };

    private ServiceInterface mService;

    private ServiceConnection mConnection = new ServiceConnection() {

        public void onServiceConnected(ComponentName className, IBinder service) {
            try {
                mService = ServiceInterface.Stub.asInterface(service);
                mService.registerTestCall(mCallback);
                mTvResult.setText("connect service success");
            } catch (RemoteException e) {
                mTvResult.setText("connect service RemoteException");
            }
            mConnected = true;
            mSendView.setEnabled(true);
            mSendBigView.setEnabled(true);
            mBtnAction.setEnabled(true);
            mBtnAction.setText("已连接，断开吧");
        }

        public void onServiceDisconnected(ComponentName className) {
            onDisconnectedServiceFlushView();
        }
    };

    private void onDisconnectedServiceFlushView() {
        mTvResult.setText("disconnect service");
        mService = null;
        mConnected = false;
        mSendView.setEnabled(false);
        mSendBigView.setEnabled(false);
        mBtnAction.setEnabled(true);
        mBtnAction.setText("已断开，连接吧");
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.activity_aidl_test, viewGroup, false);
        mTvResult = (TextView)view.findViewById(R.id.tv_result);
        mBtnAction = (Button)view.findViewById(R.id.btn_action);
        mBtnAction.setOnClickListener(mOnClickListener);
        mBtnAction.setEnabled(true);

        mSendView = view.findViewById(R.id.btn_send);
        mSendView.setOnClickListener(mOnClickListener);
        mSendView.setEnabled(false);

        mSendBigView = view.findViewById(R.id.btn_send_big);
        mSendBigView.setOnClickListener(mOnClickListener);
        mSendBigView.setEnabled(false);

        return view;
    }
}
