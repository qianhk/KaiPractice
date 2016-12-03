package com.njnu.kai.practice.animator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.njnu.kai.support.DisplayUtils;

/**
 * Created by kai
 * since 16/12/2
 */
public class ClipImageView extends ImageView {

    private int mShowHeight = 1;

    private Paint mEdgePaint;

    private Bitmap mBitmapMask;
    private Bitmap mBitmap;

    private PorterDuffXfermode mXfermode;

    public ClipImageView(Context context) {
        super(context);
        init(context);
    }

    public ClipImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ClipImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mEdgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        mEdgePaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        mEdgePaint.setColor(Color.RED);
        mEdgePaint.setFilterBitmap(false);
        mXfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
//        mBitmap = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.img_artist)).getBitmap();
//        mBitmap = makeDst(DisplayUtils.dp2px(100), DisplayUtils.dp2px(100));
//        mBitmapMask = ((BitmapDrawable) context.getResources().getDrawable(R.drawable.icon_share_sns_friend)).getBitmap();
        mBitmapMask = makeSrc(DisplayUtils.dp2px(100), DisplayUtils.dp2px(80));
    }

    public void setShowHeight(int height) {
        mShowHeight = height;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mShowHeight < 0) {
            super.onDraw(canvas);
            return;
        }
        int saveCount = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
//        canvas.drawBitmap(mBitmap, 0, 0, mEdgePaint);
        super.onDraw(canvas);
        mEdgePaint.setXfermode(mXfermode);
//        canvas.clipRect(getWidth() / 2, getHeight() / 2, getWidth(), getHeight());
//        canvas.drawBitmap(mBitmapMask, (getWidth() - mBitmapMask.getWidth()) / 2, (getHeight() - mBitmapMask.getHeight()) / 2, mEdgePaint);
        canvas.drawBitmap(mBitmapMask, DisplayUtils.dp2px(60), DisplayUtils.dp2px(10), mEdgePaint);
        mEdgePaint.setXfermode(null);
        canvas.restoreToCount(saveCount);
    }

//    private void drawOri(Canvas canvas) {
//        Drawable mDrawable = getDrawable();
//        if (mDrawable == null) {
//            return; // couldn't resolve the URI
//        }
//
//        Matrix mDrawMatrix = getImageMatrix();
//        if (mDrawMatrix == null) {
//            mDrawable.draw(canvas);
//        } else {
//            int saveCount = canvas.getSaveCount();
//            canvas.save();
//
//            if (mDrawMatrix != null) {
//                canvas.concat(mDrawMatrix);
//            }
//            mDrawable.draw(canvas);
//        }
//    }

    public static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFCC44); //黄色
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    public static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFF66AAFF); //蓝色
        c.drawRect(0, 0, w, h, p);
        return bm;
    }
}
