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
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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
            LogUtils.e(TAG, "lookaidl atui performAction ");
        }

        @Override
        public void testAbc() throws RemoteException {

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
        return view;
    }
}
