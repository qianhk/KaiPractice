package com.njnu.kai.practice.animator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.njnu.kai.practice.R;

/**
 * Created by kai
 * since 16/12/2
 */
public class ClipImageView extends ImageView {

    private int mShowHeight = 1;

    private Paint mEdgePaint;

    private Bitmap mBitmap;

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
        mEdgePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mEdgePaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mEdgePaint.setColor(Color.RED);
        mEdgePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        mBitmap = ((BitmapDrawable)context.getResources().getDrawable(R.drawable.test_mask)).getBitmap();
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
        int saveCount = canvas.saveLayer(0, 0, getRight(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, (getWidth() - mBitmap.getWidth()) / 2, (getHeight() - mBitmap.getHeight()) / 2, mEdgePaint);
        canvas.restoreToCount(saveCount);
    }
}
