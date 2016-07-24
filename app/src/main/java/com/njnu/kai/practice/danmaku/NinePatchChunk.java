package com.njnu.kai.practice.danmaku;

import android.graphics.Rect;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * 9png数据处理
 * @author none.none
 * @version 1.0.0
 */
public class NinePatchChunk {

    private final Rect mPaddingRect = new Rect();

    private int[] mDivX;
    private int[] mDivY;
    private int[] mColor;

    private static void readIntArray(int[] data, ByteBuffer buffer) {
        for (int i = 0, n = data.length; i < n; ++i) {
            data[i] = buffer.getInt();
        }
    }

    private static void checkDivCount(int length) {
        if (length == 0 || (length & 0x01) != 0) {
            throw new RuntimeException("invalid nine-patch: " + length);
        }
    }

    /**
     * 获取 padding rect
     * @return 如题
     */
    public Rect getPaddingRect() {
        return mPaddingRect;
    }

    /**
     * 反序列化
     * @param data 数据
     * @return 9png附属数据
     */
    public static NinePatchChunk deserialize(byte[] data) {
        ByteBuffer byteBuffer =
                ByteBuffer.wrap(data).order(ByteOrder.nativeOrder());

        byte wasSerialized = byteBuffer.get();
        if (wasSerialized == 0) {
            return null;
        }

        NinePatchChunk chunk = new NinePatchChunk();
        chunk.mDivX = new int[byteBuffer.get()];
        chunk.mDivY = new int[byteBuffer.get()];
        chunk.mColor = new int[byteBuffer.get()];

        checkDivCount(chunk.mDivX.length);
        checkDivCount(chunk.mDivY.length);

        // skip 8 bytes
        byteBuffer.getInt();
        byteBuffer.getInt();

        chunk.mPaddingRect.left = byteBuffer.getInt();
        chunk.mPaddingRect.right = byteBuffer.getInt();
        chunk.mPaddingRect.top = byteBuffer.getInt();
        chunk.mPaddingRect.bottom = byteBuffer.getInt();

        // skip 4 bytes
        byteBuffer.getInt();

        readIntArray(chunk.mDivX, byteBuffer);
        readIntArray(chunk.mDivY, byteBuffer);
        readIntArray(chunk.mColor, byteBuffer);

        return chunk;
    }
}
