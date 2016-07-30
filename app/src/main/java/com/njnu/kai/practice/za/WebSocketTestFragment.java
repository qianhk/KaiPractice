package com.njnu.kai.practice.za;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.njnu.kai.practice.R;
import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.ViewUtils;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_76;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 16/7/30
 */
public class WebSocketTestFragment extends BaseTestFragment implements View.OnClickListener {

    protected View rootView;
    protected EditText edtResult;
    protected Button btnConnect;
    protected EditText edtText;
    protected Button btnSend;

    private boolean mConnected;
    private TestWebSocketClient mWebSocketClient;

    public static final int WHAT_ON_OPEN = 1;
    public static final int WHAT_ON_MESSAGE = 2;
    public static final int WHAT_ON_CLOSE = 3;
    public static final int WHAT_ON_ERROR = 4;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_ON_OPEN:
                    appendResult("onOpen");
                    flushButtonStatus(true);
                    break;

                case WHAT_ON_MESSAGE:
                    appendResult("onMessage: " + msg.obj);
                    break;

                case WHAT_ON_CLOSE:
                    receiveOnClose(msg);
                    flushButtonStatus(false);
                    break;

                case WHAT_ON_ERROR:
                    appendResult("onError: " + msg.obj);
                    flushButtonStatus(false);
                    break;
            }
        }
    };

    private void receiveOnClose(Message msg) {
        appendResult(String.format("onClose %d reason=%s remote=%d", msg.arg1, msg.obj, msg.arg2));
        if (mWebSocketClient == null) {
            appendResult("will exit ui");
            edtText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            }, 5000);
        } else {
            if (msg.arg2 != 0) { //from remote

            }
        }
        mWebSocketClient = null;
    }

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        View view = layoutInflater.inflate(R.layout.fragment_websocket_test, viewGroup, false);
        initView(view);
        flushButtonStatus(false);
        return view;
    }

    private void flushButtonStatus(boolean connected) {
        mConnected = connected;
        btnSend.setEnabled(connected);
        btnConnect.setEnabled(true);
        btnConnect.setText(connected ? "断开" : "连接");
    }

    private void initView(View rootView) {
        edtResult = (EditText) rootView.findViewById(R.id.edt_result);
        btnConnect = (Button) rootView.findViewById(R.id.btn_connect);
        edtText = (EditText) rootView.findViewById(R.id.edt_text);
        btnSend = (Button) rootView.findViewById(R.id.btn_send);
        ViewUtils.bindClickListener(this, btnConnect, btnSend);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btn_connect) {
            btnSend.setEnabled(false);
            btnConnect.setEnabled(false);
            if (mConnected) {
                appendResult("will close connection");
                mWebSocketClient.close();
            } else {
                try {
                    setResult("will connect");
                    mWebSocketClient = new TestWebSocketClient(new URI("ws://echo.websocket.org"), new Draft_76());
                    mWebSocketClient.connect();
                } catch (Exception e) {
                    e.printStackTrace();
                    mWebSocketClient = null;
                    appendResult("connect exception:" + e.toString());
                    flushButtonStatus(false);
                }
            }
        } else if (viewId == R.id.btn_send) {
            String sendTxt = edtText.getText().toString().trim();
            if (!sendTxt.isEmpty()) {
                appendResult("send msg:" + sendTxt);
                mWebSocketClient.send(sendTxt);
            }
        }
    }

    public void setResult(Object object) {
        if (Looper.myLooper() != Looper.getMainLooper()) {

        } else {
            edtResult.setText(this.transformToStr(object));
        }
    }

    public void appendResult(Object object) {
        String oriStr = edtResult.getText().toString();
        edtResult.setText(oriStr + "\n" + this.transformToStr(object));
    }

    private String transformToStr(Object object) {
        return object != null ? object.toString() : "<object=null>";
    }

    public class TestWebSocketClient extends WebSocketClient {

        public TestWebSocketClient(URI serverUri, Draft draft) {
            super(serverUri, draft);
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            mHandler.sendMessage(mHandler.obtainMessage(WHAT_ON_OPEN, handshakedata));
        }

        @Override
        public void onMessage(String message) {
            mHandler.sendMessage(mHandler.obtainMessage(WHAT_ON_MESSAGE, message));
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            Message message = mHandler.obtainMessage(WHAT_ON_CLOSE, reason);
            message.arg1 = code;
            message.arg2 = remote ? 1 : 0;
            mHandler.sendMessage(message);
        }

        @Override
        public void onError(Exception ex) {
            mHandler.sendMessage(mHandler.obtainMessage(WHAT_ON_ERROR, ex));
        }
    }

    @Override
    protected void onBackPressed() {
        if (mWebSocketClient != null) {
            appendResult("onBackPressed");
            btnSend.setEnabled(false);
            btnConnect.setEnabled(false);
            mWebSocketClient.close();
            mWebSocketClient = null;
        } else {
            super.onBackPressed();
        }
    }
}
