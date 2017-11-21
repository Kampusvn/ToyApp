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

import android.content.Context;
import android.example.com.visualizerpreferences.R;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.SystemClock;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;


/**
 * {@link VisualizerView} chịu trách nhiệm thiết lập và vẽ "bản đồ" (tức là màn hình hiển thị)
 * các hình và đường chạy của chúng) cho âm nhạc được phát.
 */
public class VisualizerView extends View {

    // Các hằng số này xác định phần trăm tần số âm thanh của mỗi hình đại diện.
    // Ví dụ, vòng tròn bass thể hiện phần dưới 10% tần số.
    private static final float SEGMENT_SIZE = 100.f;
    private static final float BASS_SEGMENT_SIZE = 10.f / SEGMENT_SIZE;
    private static final float MID_SEGMENT_SIZE = 30.f / SEGMENT_SIZE;
    private static final float TREBLE_SEGMENT_SIZE = 60.f / SEGMENT_SIZE;

    // Kích thước tối thiểu của hình dạng, theo mặc định, trước khi phóng to
    private static final float MIN_SIZE_DEFAULT = 50;

    // Hệ số này được sử dụng để làm cho dao động theo tần số (rất nhỏ) có thể dễ nhìn hơn
    private static final float BASS_MULTIPLIER = 1.5f;
    private static final float MID_MULTIPLIER = 3;
    private static final float TREBLE_MULTIPLIER = 5;

    private static final float REVOLUTIONS_PER_SECOND = .3f;

    // Điều chỉnh Kích thước của hình tròn cho mỗi hình dạng
    private static final float RADIUS_BASS = 20 / 100.f;
    private static final float RADIUS_MID = 60 / 100.f;
    private static final float RADIUS_TREBLE = 90 / 100.f;

    // Các hình
    private final TrailedShape mBassCircle;
    private final TrailedShape mMidSquare;
    private final TrailedShape mTrebleTriangle;

    // Mảng giữ các byte FFT hiện hành
    private byte[] mBytes;

    // Thời điểm bắt đầu animation
    private long mStartTime;

    // Số đại diện cho mức trung bình hiện tại của tất cả các giá trị trong dải bass, mid
    // và treble trong fft
    private float bass;
    private float mid;
    private float treble;

    // Xác định việc hiển thị của các hình và đường
    private boolean showBass;
    private boolean showMid;
    private boolean showTreble;

    @ColorInt
    private int backgroundColor;

    public VisualizerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mBytes = null;
        TrailedShape.setMinSize(MIN_SIZE_DEFAULT);

        // Tạo các hình dạng và xác định cách chúng được vẽ trên màn hình
        // Vẽ bass hình tròn
        mBassCircle = new TrailedShape(BASS_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                canvas.drawCircle(shapeCenterX, shapeCenterY, currentSize, paint);
            }
        };

        // Vẽ midrange hình vuông
        mMidSquare = new TrailedShape(MID_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                canvas.drawRect(shapeCenterX - currentSize,
                        shapeCenterY - currentSize,
                        shapeCenterX + currentSize,
                        shapeCenterY + currentSize,
                        paint);
            }
        };

        // Vẽ treble hình tam giác
        mTrebleTriangle = new TrailedShape(TREBLE_MULTIPLIER) {
            @Override
            protected void drawThisShape(float shapeCenterX, float shapeCenterY, float currentSize, Canvas canvas, Paint paint) {
                Path trianglePath = new Path();
                trianglePath.moveTo(shapeCenterX, shapeCenterY - currentSize);
                trianglePath.lineTo(shapeCenterX + currentSize, shapeCenterY + currentSize / 2);
                trianglePath.lineTo(shapeCenterX - currentSize, shapeCenterY + currentSize / 2);
                trianglePath.lineTo(shapeCenterX, shapeCenterY - currentSize);
                canvas.drawPath(trianglePath, paint);
            }
        };
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        // Thiết lập tất cả các code về đo đạc xem sau khi có view. Nếu làm việc này sớm hơn thì
        // chiều cao và chiều rộng chưa được xác định
        mStartTime = SystemClock.uptimeMillis();

        float viewCenterX = getWidth() / 2.f;
        float viewCenterY = getHeight() / 2.f;
        float shortSide = viewCenterX < viewCenterY ? viewCenterX : viewCenterY;
        TrailedShape.setViewCenterX(viewCenterX);
        TrailedShape.setViewCenterY(viewCenterY);

        mBassCircle.setShapeRadiusFromCenter(shortSide * RADIUS_BASS);
        mMidSquare.setShapeRadiusFromCenter(shortSide * RADIUS_MID);
        mTrebleTriangle.setShapeRadiusFromCenter(shortSide * RADIUS_TREBLE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBytes == null) {
            return;
        }

        // Lấy góc hiện tại của tất cả các hình
        double currentAngleRadians = calcCurrentAngle();

        // Vẽ background
        canvas.drawColor(backgroundColor);

        // Vẽ các hình
        if (showBass) {
            mBassCircle.draw(canvas, bass, currentAngleRadians);
        }
        if (showMid) {
            mMidSquare.draw(canvas, mid, currentAngleRadians);
        }
        if (showTreble) {
            mTrebleTriangle.draw(canvas, treble, currentAngleRadians);
        }

        // Hủy bỏ view để vẽ lại ngay lập tức
        invalidate();
    }

    /**
     * Tính toán góc của tất cả các hình dạng dựa trên thời gian hiện tại
     *
     * @return Giá trị góc
     */
    private double calcCurrentAngle() {
        long elapsedTime = SystemClock.uptimeMillis() - mStartTime;
        float revolutions = elapsedTime * REVOLUTIONS_PER_SECOND / 1000;
        return revolutions * 2 * Math.PI;
    }

    /**
     * Phương thức này được gọi bởi lớp {@link AudioInputReader} để chuyền vào các byte transform
     * Fourier hiện tại. Sau đó, mảng được chia thành các segment và mỗi segment được tính trung
     * bình để xác định độ lớn của hình ảnh hiển thị.
     *
     * Xem thêm thông tin ở:
     * http://cmc.music.columbia.edu/musicandcomputers/chapter3/03_04.php
     *
     * @param bytes
     */
    public void updateFFT(byte[] bytes) {
        mBytes = bytes;

        // Tính toán trung bình cho bass segment
        float bassTotal = 0;
        for (int i = 0; i < bytes.length * BASS_SEGMENT_SIZE; i++) {
            bassTotal += Math.abs(bytes[i]);
        }
        bass = bassTotal / (bytes.length * BASS_SEGMENT_SIZE);

        // Tính toán trung bình cho mid segment
        float midTotal = 0;
        for (int i = (int) (bytes.length * BASS_SEGMENT_SIZE); i < bytes.length * MID_SEGMENT_SIZE; i++) {
            midTotal += Math.abs(bytes[i]);
        }
        mid = midTotal / (bytes.length * MID_SEGMENT_SIZE);

        // Tính toán trung bình cho treble segment
        float trebleTotal = 0;
        for (int i = (int) (bytes.length * MID_SEGMENT_SIZE); i < bytes.length; i++) {
            trebleTotal += Math.abs(bytes[i]);
        }
        treble = trebleTotal / (bytes.length * TREBLE_SEGMENT_SIZE);

        invalidate();
    }

    /**
     * Restarts visualization
     */
    public void restart() {
        mBassCircle.restartTrail();
        mMidSquare.restartTrail();
        mTrebleTriangle.restartTrail();
    }

    /** Phương thức dưới đây có thể được gọi để thay đổi visualization **/

    /**
     * Thiết lập khả năng hiển thị của vòng tròn bass
     *
     * @param showBass biến boolean xác định khả năng hiển thị của bass
     */
    public void setShowBass(boolean showBass) {
        this.showBass = showBass;
    }

    /**
     * Thiết lập khả năng hiển thị của hình vuông mid-range
     *
     * @param showMid biến boolean xác định khả năng hiển thị của mid-range
     */
    public void setShowMid(boolean showMid) {
        this.showMid = showMid;
    }

    /**
     * Thiết lập khả năng hiển thị của tam giác treble
     *
     * @param showTreble biến boolean xác định khả năng hiển thị của treble
     */
    public void setShowTreble(boolean showTreble) {
        this.showTreble = showTreble;
    }

    /**
     * Thiết lập scale cho kích thước tối thiểu của hình
     *
     * @param scale scale cho kích thước của hình dạng
     */
    public void setMinSizeScale(float scale) {
        TrailedShape.setMinSize(MIN_SIZE_DEFAULT * scale);
    }

    /**
     * Màu cho hình.
     *
     * @param newColorKey
     */
    public void setColor(String newColorKey) {

        @ColorInt
        int shapeColor;

        @ColorInt
        int trailColor;

        if (newColorKey.equals(getContext().getString(R.string.pref_color_blue_value))) {
            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeBlue);
            trailColor = ContextCompat.getColor(getContext(), R.color.trailBlue);
            backgroundColor = ContextCompat.getColor(getContext(), R.color.backgroundBlue);
        } else if (newColorKey.equals(getContext().getString(R.string.pref_color_green_value))) {
            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeGreen);
            trailColor = ContextCompat.getColor(getContext(), R.color.trailGreen);
            backgroundColor = ContextCompat.getColor(getContext(), R.color.backgroundGreen);
        } else {
            shapeColor = ContextCompat.getColor(getContext(), R.color.shapeRed);
            trailColor = ContextCompat.getColor(getContext(), R.color.trailRed);
            backgroundColor = ContextCompat.getColor(getContext(), R.color.backgroundRed);
        }

        mBassCircle.setShapeColor(shapeColor);
        mMidSquare.setShapeColor(shapeColor);
        mTrebleTriangle.setShapeColor(shapeColor);

        mBassCircle.setTrailColor(trailColor);
        mMidSquare.setTrailColor(trailColor);
        mTrebleTriangle.setTrailColor(trailColor);
    }
}
