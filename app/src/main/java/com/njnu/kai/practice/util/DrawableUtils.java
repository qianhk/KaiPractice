package com.njnu.kai.practice.util;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;

/**
 */
public final class DrawableUtils {

    public static Drawable makeRoundRectDrawable(int color, float radius) {
        float outRadii[] = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
        Shape shape = new RoundRectShape(outRadii, null, null);
        ShapeDrawable shapeDrawable = new ShapeDrawable(shape);
        shapeDrawable.getPaint().setColor(color);
        return shapeDrawable;
    }
}
