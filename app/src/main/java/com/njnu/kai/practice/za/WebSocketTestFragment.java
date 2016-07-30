package com.njnu.kai.practice.za;

import android.os.Bundle;
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
            if (mConnected) {
                appendResult("will close connection");
                btnSend.setEnabled(false);
                btnConnect.setEnabled(false);
                mWebSocketClient.close();
                mWebSocketClient = null;
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
            if (sendTxt != null) {
                appendResult("send msg:" + sendTxt);
                mWebSocketClient.send(sendTxt);
            }
        }
    }

    public void setResult(Object object) {
        edtResult.setText(this.transformToStr(object));
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
            appendResult("onOpen");
        }

        @Override
        public void onMessage(String message) {
            appendResult("onMessage: " + message);
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            appendResult(String.format("onClose %d reason=%s remote=%b", code, reason, remote));
        }

        @Override
        public void onError(Exception ex) {
            appendResult("onError: " + ex.toString());
        }
    }
}
