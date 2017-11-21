package android.example.com.visualizerpreferences.AudioVisuals;

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.ColorInt;

import java.util.LinkedList;

/**
 * Abstract class biểu diễn một hình và một đường gấp khúc
 */
abstract class TrailedShape {

    // Các biến tĩnh cho trung tâm của khung nhìn cha và kích thước tối thiểu của các hình
    private static float sViewCenterX, sViewCenterY;
    private static float sMinSize;

    // Biến xác định kích thước
    private final float mMultiplier;

    // Các biến để xác định đường chạy (đường gấp khúc)
    private final Path mTrailPath;
    private final LinkedList<TrailPoint> mTrailList;

    // "Bút" vẽ
    private final Paint mPaint;
    private Paint mTrailPaint;

    // Biến xác định vị trí
    private float mShapeRadiusFromCenter;

    TrailedShape(float multiplier) {
        this.mMultiplier = multiplier;

        // Setup cho đường chạy
        this.mTrailPath = new Path();
        this.mTrailList = new LinkedList<>();

        // Setup bút vẽ và các thuộc tính
        this.mPaint = new Paint();
        this.mTrailPaint = new Paint();

        mPaint.setStyle(Paint.Style.FILL);
        mTrailPaint.setStyle(Paint.Style.STROKE);
        mTrailPaint.setStrokeWidth(5);
        mTrailPaint.setStrokeJoin(Paint.Join.ROUND);
        mTrailPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    // Các phương thức static
    static void setMinSize(float minSize) {
        TrailedShape.sMinSize = minSize;
    }

    static void setViewCenterY(float viewCenterY) {
        TrailedShape.sViewCenterY = viewCenterY;
    }

    static void setViewCenterX(float viewCenterX) {
        TrailedShape.sViewCenterX = viewCenterX;
    }

    /**
     * Phương thức draw này tóm tắt những thông số chung của các hình dạng
     *
     * @param canvas         "Khung" để vẽ lên
     * @param currentFreqAve Tần số trung bình, xác định việc tăng kích thước
     * @param currentAngle   Góc xung quanh tâm (để vẽ hình)
     */
    void draw(Canvas canvas, float currentFreqAve, double currentAngle) {

        float currentSize = sMinSize + mMultiplier * currentFreqAve;

        // Tính toán vị trí của hình
        float shapeCenterX = calcLocationInAnimationX(mShapeRadiusFromCenter, currentAngle);
        float shapeCenterY = calcLocationInAnimationY(mShapeRadiusFromCenter, currentAngle);

        // Tính toán điểm tiếp theo của đường chạy
        float trailX = calcLocationInAnimationX((mShapeRadiusFromCenter + currentSize - sMinSize), currentAngle);
        float trailY = calcLocationInAnimationY((mShapeRadiusFromCenter + currentSize - sMinSize), currentAngle);

        mTrailPath.rewind(); // Xóa đường chạy
        mTrailList.add(new TrailPoint(trailX, trailY, currentAngle)); // Thêm segment đường chạy mới

        // Giữ đúng kích thước đường
        while (currentAngle - mTrailList.peekFirst().theta > 2 * Math.PI) {
            mTrailList.poll();
        }

        // Vẽ đường chạy
        mTrailPath.moveTo(mTrailList.peekFirst().x, mTrailList.peekFirst().y);
        for (TrailPoint trailPoint : mTrailList) {
            mTrailPath.lineTo(trailPoint.x, trailPoint.y);
        }

        canvas.drawPath(mTrailPath, mTrailPaint);

        // Gọi phương thức abstract drawThisShape, việc này phải được xác định cho mỗi hình.
        drawThisShape(shapeCenterX, shapeCenterY, currentSize, canvas, mPaint);
    }

    /**
     * Xác định làm thế nào để vẽ một hình
     *
     * @param shapeCenterX Center X position of the shape
     * @param shapeCenterY Center Y position of the shape
     * @param currentSize  Size of the shape
     * @param canvas       The canvas to draw on
     * @param paint        The paint to draw with
     */
    protected abstract void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint);

    /**
     * Xóa đường
     */
    void restartTrail() {
        mTrailList.clear();
    }

    /**
     * Tính toán vị trí trung tâm X
     *
     * @param radiusFromCenter    Bán kính
     * @param currentAngleRadians Góc hiện tại của hình
     * @return
     */
    private float calcLocationInAnimationX(float radiusFromCenter, double currentAngleRadians) {
        return (float) (sViewCenterX + Math.cos(currentAngleRadians) * radiusFromCenter);

    }

    /**
     * Tính toán vị trí trung tâm Y
     *
     * @param radiusFromCenter    Bán kính
     * @param currentAngleRadians Góc hiện tại của hình
     * @return
     */
    private float calcLocationInAnimationY(float radiusFromCenter, double currentAngleRadians) {
        return (float) (sViewCenterY + Math.sin(currentAngleRadians) * radiusFromCenter);
    }

    /**
     * Thiết lập màu sắc cho hình
     *
     * @param color Màu muốn chọn
     */
    void setShapeColor(@ColorInt int color) {
        mPaint.setColor(color);
    }

    /**
     * Đặt màu đường chạy
     *
     * @param color Màu muốn đặt
     */
    void setTrailColor(@ColorInt int color) {
        mTrailPaint.setColor(color);
    }

    /**
     * Thiết lập khoảng cách với hình từ trung tâm của view
     *
     * @param mShapeRadiusFromCenter Khoảng cách
     */
    void setShapeRadiusFromCenter(float mShapeRadiusFromCenter) {
        this.mShapeRadiusFromCenter = mShapeRadiusFromCenter;
    }

    /**
     * Lớp bên trong đại diện cho các điểm trong đường chạy
     */
    private class TrailPoint {
        final float x;
        final float y;
        final double theta;

        TrailPoint(float x, float y, double theta) {
            this.x = x;
            this.y = y;
            this.theta = theta;
        }
    }
}
