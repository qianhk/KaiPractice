package com.njnu.kai.practice.danmaku;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.njnu.kai.practice.R;
import com.njnu.kai.practice.danmaku.data.ExpPkg;
import com.njnu.kai.practice.danmaku.data.SongDanmaku;
import com.njnu.kai.practice.danmaku.data.SongDanmakuListResult;
import com.njnu.kai.support.JSONUtils;
import com.njnu.kai.support.StringUtils;
import com.njnu.kai.support.executor.TaskScheduler;

import java.util.ArrayList;
import java.util.Collections;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-8-18
 */
public class DanmaukuActivity extends Activity {

    private Button mBtnPlayPause;
    private DanmakuView mDanmakuView;
    private TextView mTvResult;

    private boolean mPlaying;

    private int mCurPlayingTime;

    private ArrayList<SongDanmaku> mSongDanmakuList;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final int viewId = v.getId();
            if (v == mBtnPlayPause) {
                mPlaying = !mPlaying;
                if (mPlaying) {
                    startFlushView();
                } else {
                    stopFlushView();
                }
                flushPlayPauseView();
            } else if (viewId == R.id.btn_reset_time) {
                mCurPlayingTime = 0;
                mDanmakuView.reset();
                setViewPlayTime();
            } else if (viewId == R.id.btn_add_200ms) {
                setPlayDetaTime(200);
            }
        }
    };

    private ArrayList<SongDanmaku> doPrepareDataInBkg() throws Exception {
//        final String pkgParentWithAsset = "file:///android_asset/expPkg";
//        String[] expPkgssss = getAssets().list(pkgParentWithAsset);
//        final String[] expPkgs = new File(pkgParent).list();
        final String pkgParent = "expPkg";
        final String[] expPkgs = getAssets().list(pkgParent);
        if (expPkgs != null && expPkgs.length > 0) {
            final ArrayList<ExpPkg> expPkgList = new ArrayList<ExpPkg>();
            for (String expPkgFolder : expPkgs) {
                try {
                    final String expPkgFullFolder = pkgParent + "/" + expPkgFolder + "/";
                    final String expPkgData = StringUtils.stringFromInputStream(getAssets().open(expPkgFullFolder + "main"));
//                    final String expPkgData = FileUtils.load(pkgParent + "/" + expPkgFolder + "/main");
                    final ExpPkg expPkg = JSONUtils.fromJsonString(expPkgData, ExpPkg.class);
                    if (expPkg != null) {
                        expPkg.setFullPkgPath(DanmakuView.ANDROID_ASSET + expPkgFullFolder);
                        expPkgList.add(expPkg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            mDanmakuView.setExpPkgList(expPkgList);
        }
        final String danmakuData = StringUtils.stringFromInputStream(getAssets().open("danmaku_data"));
        final SongDanmakuListResult danmakuListResult = JSONUtils.fromJsonString(danmakuData, SongDanmakuListResult.class);
        Collections.sort(danmakuListResult.getSongDanmakuList());
        return danmakuListResult.getSongDanmakuList();
    }

    private void prepareData() {
        TaskScheduler.execute(this, () -> {
            try {
                return doPrepareDataInBkg();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }, result -> {
            mSongDanmakuList = result;
            if (mSongDanmakuList == null) {
                mTvResult.setText("parse danmaku data failed");
            } else {
                mDanmakuView.setSongDanmakuList(mSongDanmakuList);
                mTvResult.setText(String.format("parse danmaku data, total=%d", mSongDanmakuList.size()));
            }
            flushPlayPauseView();
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danmaku);
        mBtnPlayPause = (Button) findViewById(R.id.btn_play_pause);
        mDanmakuView = (DanmakuView) findViewById(R.id.danmaku_view);
        mBtnPlayPause.setOnClickListener(mOnClickListener);
        mTvResult = (TextView) findViewById(R.id.tv_result);
        findViewById(R.id.btn_reset_time).setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_add_200ms).setOnClickListener(mOnClickListener);
        flushPlayPauseView();

        prepareData();
    }

    @Override
    protected void onDestroy() {
        stopFlushView();
        super.onDestroy();
    }

    private void flushPlayPauseView() {
        mBtnPlayPause.setText(mPlaying ? "pause it" : "play it");
        mBtnPlayPause.setEnabled(mSongDanmakuList != null && !mSongDanmakuList.isEmpty());
    }

    private void setPlayDetaTime(int detaTime) {
        mCurPlayingTime += detaTime;
        if (mCurPlayingTime < 0) {
            mCurPlayingTime = 0;
        } else if (mCurPlayingTime > MAX_DURATION_TIME) {
            mCurPlayingTime = MAX_DURATION_TIME;
        }
        setViewPlayTime();
    }

    private static final int MAX_DURATION_TIME = 6 * 60 * 1000;  //假设最大6分钟位置

    private static final int FLUSH_INTERVAL = 500;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCurPlayingTime += FLUSH_INTERVAL;
            setViewPlayTime();
            if (mCurPlayingTime < MAX_DURATION_TIME) {
                mHandler.sendEmptyMessageDelayed(0, FLUSH_INTERVAL);
            }
        }
    };

    private void setViewPlayTime() {
        mTvResult.setText("now pay time is: " + mCurPlayingTime);
        mDanmakuView.setPlayTime(mCurPlayingTime);
    }

    private void startFlushView() {
        mHandler.sendEmptyMessageDelayed(0, FLUSH_INTERVAL);
    }

    private void stopFlushView() {
        mHandler.removeCallbacksAndMessages(null);
    }
}
