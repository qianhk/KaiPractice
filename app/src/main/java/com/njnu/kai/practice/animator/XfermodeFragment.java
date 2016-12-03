package com.njnu.kai.practice.animator;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.njnu.kai.support.BaseTestFragment;
import com.njnu.kai.support.DisplayUtils;

/**
 * Created by kai
 * since 16/12/2
 */
public class XfermodeFragment extends BaseTestFragment {

    @Override
    protected View onCreateContentView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return new SampleView(layoutInflater.getContext());
    }

    private static class SampleView extends View {
        private static final int W = DisplayUtils.dp2px(64);
        private static final int H = DisplayUtils.dp2px(64);
        private static final int ROW_MAX = 4;   // number of samples per row

        private Bitmap mSrcB;
        private Bitmap mDstB;
        private Shader mBG;     // background checker-board pattern

        private static final Xfermode[] sModes = {
                new PorterDuffXfermode(PorterDuff.Mode.CLEAR),
                new PorterDuffXfermode(PorterDuff.Mode.SRC),
                new PorterDuffXfermode(PorterDuff.Mode.DST),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OVER),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_IN),
                new PorterDuffXfermode(PorterDuff.Mode.DST_IN),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.DST_OUT),
                new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.DST_ATOP),
                new PorterDuffXfermode(PorterDuff.Mode.XOR),
                new PorterDuffXfermode(PorterDuff.Mode.DARKEN),
                new PorterDuffXfermode(PorterDuff.Mode.LIGHTEN),
                new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY),
                new PorterDuffXfermode(PorterDuff.Mode.SCREEN)
        };

        private static final String[] sLabels = {
                "Clear", "Src", "Dst", "SrcOver",
                "DstOver", "SrcIn", "DstIn", "SrcOut",
                "DstOut", "SrcATop", "DstATop", "Xor",
                "Darken", "Lighten", "Multiply", "Screen"
        };

        public SampleView(Context context) {
            super(context);

            mSrcB = makeSrc(W, H);
            mDstB = makeDst(W, H);

            // make a ckeckerboard pattern
            Bitmap bm = Bitmap.createBitmap(new int[] { 0xFFFFFFFF, 0xFFCCCCCC, 0xFFCCCCCC, 0xFFFFFFFF}, 2, 2, Bitmap.Config.RGB_565);
            mBG = new BitmapShader(bm, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT);
            Matrix m = new Matrix();
            m.setScale(DisplayUtils.dp2px(6), DisplayUtils.dp2px(6));
            mBG.setLocalMatrix(m);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);

            Paint labelP = new Paint(Paint.ANTI_ALIAS_FLAG);
            labelP.setTextAlign(Paint.Align.CENTER);
            labelP.setTextSize(DisplayUtils.dp2px(14));

            Paint paint = new Paint();
            paint.setFilterBitmap(false);

            canvas.drawText("先画Dst", DisplayUtils.dp2px(100), DisplayUtils.dp2px(24), labelP);

            canvas.translate(DisplayUtils.dp2px(15), DisplayUtils.dp2px(35));

            int x = 0;
            int y = DisplayUtils.dp2px(30);
            for (int i = 0; i < sModes.length; i++) {
                // draw the border
                paint.setStyle(Paint.Style.STROKE);
                paint.setShader(null);
                canvas.drawRect(x - 0.5f, y - 0.5f, x + W + 0.5f, y + H + 0.5f, paint);

                // draw the checker-board pattern
                paint.setStyle(Paint.Style.FILL);
                paint.setShader(mBG);
                canvas.drawRect(x, y, x + W, y + H, paint);

                // draw the src/dst example into our offscreen bitmap
                int sc = canvas.saveLayer(x, y, x + W, y + H, null, Canvas.ALL_SAVE_FLAG);
                canvas.translate(x, y);
                canvas.drawBitmap(mDstB, 0, 0, paint);

                paint.setXfermode(sModes[i]);
                canvas.drawBitmap(mSrcB, 0, 0, paint);

                paint.setXfermode(null);
                canvas.restoreToCount(sc);

                // draw the label
                canvas.drawText(sLabels[i], x + W / 2, y - labelP.getTextSize()/2, labelP);

                x += W + DisplayUtils.dp2px(10);

                // wrap around when we've drawn enough for one row
                if ((i % ROW_MAX) == ROW_MAX - 1) {
                    x = 0;
                    y += H + DisplayUtils.dp2px(30);
                }
            }
        }
    }


    // create a bitmap with a circle, used for the "dst" image
    public static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44); //黄色
        c.drawOval(new RectF(0, 0, w*3/4, h*3/4), p);
        return bm;
    }

    // create a bitmap with a rect, used for the "src" image
    public static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF); //蓝色
        c.drawRect(w/3, h/3, w*19/20, h*19/20, p);
        return bm;
    }

}
