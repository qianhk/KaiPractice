package com.njnu.kai.practice.danmaku;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.graphics.drawable.NinePatchDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.njnu.kai.practice.R;
import com.njnu.kai.practice.danmaku.data.ExpPkg;
import com.njnu.kai.practice.danmaku.data.SongDanmaku;
import com.njnu.kai.support.LogUtils;
import com.njnu.kai.support.StringUtils;
import com.njnu.kai.support.ToastUtils;

import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 15-8-18
 */
public class DanmakuView extends FrameLayout implements ValueAnimator.AnimatorUpdateListener, Animator.AnimatorListener, Handler.Callback {

    private static final String TAG = "DanmakuView";

    private List<SongDanmaku> mSongDanmakuList;
    private ArrayMap<String, ExpPkg> mExpPkgMap;
    private HashMap<String, ExpPkg.Exp> mExpMap;

    private static final int DANMAKU_DURATION_MS = 5000;

    private static final int MAX_COUNT_PER_LINE = 3;

    private long mPrePlayTime;
    private long mPlayTime;
    private int mIndex = -1;

    private int mHorizontalPos = 0;

    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private HandlerThread mHandlerThread;
    private Handler mThreadHandler;

    private Handler mMainHandler = new MainHandler(this);

    private static final int WHAT_DATA_PREPARD = 0;
    private static final int WHAT_SET_PLAY_TIME = 1;

    static class MainHandler extends Handler {
        private WeakReference<DanmakuView> mWeakReference;

        public MainHandler(DanmakuView view) {
            mWeakReference = new WeakReference<DanmakuView>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mWeakReference != null && mWeakReference.get() != null) {
                mWeakReference.get().handleMessage(msg);
            }
        }
    }

    public DanmakuView(Context context) {
        super(context);
        initView(context);
    }

    public DanmakuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    private void initView(Context context) {

    }

    public void reset() {
        mIndex = -1;
        mPrePlayTime = 0;
        mPlayTime = 0;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (mHandlerThread == null) {
            mHandlerThread = new HandlerThread("songDanmakuThread");
            mHandlerThread.start();
            mThreadHandler = new Handler(mHandlerThread.getLooper(), this);
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (mHandlerThread != null) {
            mHandlerThread.quit();
            mHandlerThread = null;
            mThreadHandler = null;
        }
    }

    public void setExpPkgList(List<ExpPkg> expPkgList) {
        if (mExpPkgMap == null) {
            mExpPkgMap = new ArrayMap<>();
        } else {
            mExpPkgMap.clear();
        }
        if (mExpMap == null) {
            mExpMap = new HashMap<>();
        } else {
            mExpMap.clear();
        }
        if (expPkgList != null) {
            for (ExpPkg expPkg : expPkgList) {
                mExpPkgMap.put(expPkg.getPkg(), expPkg);
                final ArrayList<ExpPkg.Exp> expList = expPkg.getExpList();
                if (expList != null && !expList.isEmpty()) {
                    for (ExpPkg.Exp exp : expList) {
                        exp.setPkg(expPkg.getPkg());
                        mExpMap.put(exp.getMd5(), exp);
                    }
                }
            }
        }
    }

    public void setSongDanmakuList(List<SongDanmaku> songDanmakuList) {
        mSongDanmakuList = songDanmakuList;
    }

    public void setPlayTime(int time) {
        mThreadHandler.removeMessages(WHAT_SET_PLAY_TIME);
        final Message message = mThreadHandler.obtainMessage(WHAT_SET_PLAY_TIME, time, 0);
        mThreadHandler.sendMessage(message);
    }

    private void giveViewMagic(DataCarrier dataCarrier) {
        final View view = obtainView();
        view.setVisibility(View.VISIBLE);
        giveViewData(view, dataCarrier.mSongDanmaku, dataCarrier.mExp, dataCarrier.mBitmap);
        animateView(view);
    }

    private ExpPkg.Exp getExp(SongDanmaku danmaku) {
        return mExpMap.get(danmaku.getMd5());
    }

    private void animateView(View view) {
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        ValueAnimator verticalTranslateAnim = ObjectAnimator.ofFloat(view, "y", getBottom(), -layoutParams.height);
        view.setX(mHorizontalPos * layoutParams.width);
        verticalTranslateAnim.setDuration(DANMAKU_DURATION_MS);
//        verticalTranslateAnim.setRepeatCount(ValueAnimator.INFINITE);
//        verticalTranslateAnim.setRepeatMode(ValueAnimator.REVERSE);
        verticalTranslateAnim.setInterpolator(new LinearInterpolator());
        verticalTranslateAnim.addUpdateListener(this);
        verticalTranslateAnim.addListener(this);
        verticalTranslateAnim.start();
        ++mHorizontalPos;
        if (mHorizontalPos >= MAX_COUNT_PER_LINE) {
            mHorizontalPos = 0;
        }
    }

    private void giveViewData(View view, SongDanmaku danmaku, ExpPkg.Exp exp, Bitmap bitmap) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_view_holder);
        viewHolder.flushView(danmaku, exp, bitmap);
    }

    private ArrayList<View> mViewPool;

    private View obtainView() {
        if (mViewPool == null) {
            mViewPool = new ArrayList<View>();
        }
        if (mViewPool.isEmpty()) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.song_danmaku_item, null);
            final int width = getWidth() / MAX_COUNT_PER_LINE;
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(width, width);
            addView(view, layoutParams);
            view.setOnClickListener(mOnClickListener);
            view.setTag(R.id.tag_view_holder, new ViewHolder(view));
            return view;
        } else {
            return mViewPool.remove(mViewPool.size() - 1);
        }
    }

    private void recycleView(View view) {
        ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_view_holder);
        viewHolder.recycle();
        mViewPool.add(view);
    }

    private SongDanmaku findDanmaku() {
        int danmakuCount = mSongDanmakuList != null ? mSongDanmakuList.size() : 0;
        if (danmakuCount > 0 && mIndex < danmakuCount) {
            SongDanmaku preDanmaku;
            SongDanmaku danmaku = null;
            for (int idx = mIndex + 1; idx < danmakuCount; ++idx) {
                preDanmaku = danmaku;
                danmaku = mSongDanmakuList.get(idx);
                if (danmaku.getTimeMs() > mPlayTime) {
                    if (preDanmaku != null) {
                        mIndex = idx - 1;
                        return preDanmaku;
                    } else {
                        return null;
                    }
                }
            }
            mIndex = danmakuCount - 1;
            return danmaku;
        }
        return null;
    }

    private static final long DRAW_INTERVAL = 50 * 1000 * 1000;
    private long mNanoTime;

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        if (animation instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animation;
            final View view = (View) objectAnimator.getTarget();
            ViewHolder viewHolder = (ViewHolder) view.getTag(R.id.tag_view_holder);
        } else {
            LogUtils.e(TAG, "onAnimationUpdate not ObjectAnimator type=%s", animation.getClass().getSimpleName());
        }

//        long curNanoTime = System.nanoTime();
//        boolean draw = curNanoTime - mNanoTime > DRAW_INTERVAL;
//        if (draw) {
//            mNanoTime = curNanoTime;
        invalidate();
//        }
    }

    @Override
    public void onAnimationStart(Animator animation) {
//        LogUtils.e(TAG, "onAnimationStart type=%s", animation.getClass().getSimpleName());
    }

    @Override
    public void onAnimationEnd(Animator animation) {
//        LogUtils.e(TAG, "onAnimationEnd type=%s", animation.getClass().getSimpleName());
        if (animation instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animation;
            final View view = (View) objectAnimator.getTarget();
            recycleView(view);
        } else {
            LogUtils.e(TAG, "onAnimationEnd not ObjectAnimator type=%s", animation.getClass().getSimpleName());
        }
    }

    @Override
    public void onAnimationCancel(Animator animation) {
        LogUtils.e(TAG, "onAnimationCancel type=%s", animation.getClass().getSimpleName());
    }

    @Override
    public void onAnimationRepeat(Animator animation) {
        LogUtils.e(TAG, "onAnimationRepeat type=%s", animation.getClass().getSimpleName());
    }

    private View.OnClickListener mOnClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            ViewHolder viewHolder = (ViewHolder) v.getTag(R.id.tag_view_holder);
            final SongDanmaku songDanmaku = viewHolder.mSongDanmaku;
            ToastUtils.showToast(String.format("time=%d txt=%s", songDanmaku.getTimeMs(), songDanmaku.getText()));
        }
    };

    @Override
    public boolean handleMessage(Message msg) {
        boolean dealed = true;

        switch (msg.what) {

            case WHAT_SET_PLAY_TIME:
                mPrePlayTime = mPlayTime;
                mPlayTime = msg.arg1;
                final SongDanmaku danmaku = findDanmaku();
                if (danmaku != null) {
                    final ExpPkg.Exp exp = getExp(danmaku);
                    if (exp != null) {
                        Bitmap bitmap = getExpBitmapInBkg(exp);
                        mMainHandler.sendMessage(mMainHandler.obtainMessage(WHAT_DATA_PREPARD, new DataCarrier(danmaku, exp, bitmap)));
                    }
                }
                break;

            case WHAT_DATA_PREPARD:
                giveViewMagic((DataCarrier) msg.obj);
                break;

            default:
                dealed = false;
                break;

        }
        return dealed;
    }

    public static final String ANDROID_ASSET = "file:///android_asset/";

    private Bitmap getExpBitmapInBkg(ExpPkg.Exp exp) {
        final ExpPkg expPkg = mExpPkgMap.get(exp.getPkg());
        String expFullPath = expPkg.getFullPkgPath() + exp.getFileName();
        if (expFullPath.startsWith(ANDROID_ASSET)) {
            final String assetPath = expFullPath.substring(ANDROID_ASSET.length(), expFullPath.length());
            return decodeAssetBitmap(getContext(), assetPath);
        } else {
            return null;
        }
    }

    private static Bitmap decodeAssetBitmap(Context context, String assetPath) {
        InputStream inputStream = null;
        try {
            inputStream = context.getResources().getAssets().open(assetPath);
            return BitmapFactory.decodeStream(inputStream);
        } catch (Throwable t) {
            t.printStackTrace();
            return null;
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class DataCarrier {
        SongDanmaku mSongDanmaku;
        ExpPkg.Exp mExp;
        Bitmap mBitmap;

        DataCarrier(SongDanmaku songDanmaku, ExpPkg.Exp exp, Bitmap bitmap) {
            mSongDanmaku = songDanmaku;
            mExp = exp;
            mBitmap = bitmap;
        }
    }

    private final static class ViewHolder {

        private View mRootView;
        private TextView mTvLeft;
        private TextView mTvRight;
        private ImageView mImageView;

        private SongDanmaku mSongDanmaku;
        private ExpPkg.Exp mExp;

        private ViewHolder(View view) {
            mRootView = view;
            mImageView = (ImageView) view.findViewById(R.id.imageview);
            mTvLeft = (TextView) view.findViewById(R.id.tv_left);
            mTvRight = (TextView) view.findViewById(R.id.tv_right);

            mTvLeft.setBackgroundResource(R.drawable.left_txt_bkg);
            mTvRight.setBackgroundResource(R.drawable.right_txt_bkg);
            final Resources resources = mRootView.getContext().getResources();

            final Bitmap leftBitmap = decodeAssetBitmap(view.getContext(), "textBoxPkg/default/left_blue.9.png");
            if (leftBitmap != null) {
                final NinePatchDrawable leftNinePatchDrawable = dealNinePathDrawable(resources, leftBitmap);
                if (leftNinePatchDrawable != null) {
                    mTvLeft.setBackgroundDrawable(leftNinePatchDrawable);
                }
            }
            final Bitmap rightBitmap = decodeAssetBitmap(view.getContext(), "textBoxPkg/default/right_blue.9.png");
            if (rightBitmap != null) {
                final NinePatchDrawable rightNinePatchDrawable = dealNinePathDrawable(resources, rightBitmap);
                if (rightNinePatchDrawable != null) {
                    mTvRight.setBackgroundDrawable(rightNinePatchDrawable);
                }
            }
        }

        private void recycle() {
            mTvLeft.setText(null);
            mTvRight.setText(null);
            mImageView.setImageDrawable(null);
            mRootView.setVisibility(View.GONE);
            mSongDanmaku = null;
            mExp = null;
        }

        public void flushView(SongDanmaku danmaku, ExpPkg.Exp exp, Bitmap bitmap) {
            mSongDanmaku = danmaku;
            mExp = exp;

            if (bitmap != null) {
                mImageView.setImageBitmap(bitmap);
            } else {
                mImageView.setImageResource(R.drawable.ic_launcher);
            }
            final String text = danmaku.getText();
            if (!StringUtils.isEmpty(text)) {
                if (RANDOM.nextBoolean()) {
                    mTvLeft.setText(text);
                    mTvLeft.setVisibility(View.VISIBLE);
                    mTvRight.setVisibility(View.GONE);
                } else {
                    mTvRight.setText(text);
                    mTvLeft.setVisibility(View.GONE);
                    mTvRight.setVisibility(View.VISIBLE);
                }
            } else {
                mTvLeft.setVisibility(View.GONE);
                mTvRight.setVisibility(View.GONE);
            }
        }
    }

    private static NinePatchDrawable dealNinePathDrawable(Resources resources, Bitmap bitmap) {
        if (bitmap != null) {
            byte[] nineChunk = bitmap.getNinePatchChunk();
            if (nineChunk != null && NinePatch.isNinePatchChunk(nineChunk)) {
                final NinePatchChunk npc = NinePatchChunk.deserialize(nineChunk);
                return new NinePatchDrawable(resources, bitmap, nineChunk, npc.getPaddingRect(), null);
            }
        }
        return null;
    }
}
