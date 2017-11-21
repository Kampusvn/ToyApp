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
import android.media.MediaPlayer;
import android.media.audiofx.Visualizer;
import android.os.Build;


public class AudioInputReader {

    private final VisualizerView mVisualizerView;
    private final Context mContext;
    private MediaPlayer mPlayer;
    private Visualizer mVisualizer;


    public AudioInputReader(VisualizerView visualizerView, Context context) {
        this.mVisualizerView = visualizerView;
        this.mContext = context;
        initVisualizer();
    }

    private void initVisualizer() {
        // Cài đặt media player
        mPlayer = MediaPlayer.create(mContext, R.raw.anniversary);
        mPlayer.setLooping(true);

        // Cài đặt Visualizer
        // Kết nối với media player
        mVisualizer = new Visualizer(mPlayer.getAudioSessionId());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mVisualizer.setMeasurementMode(Visualizer.MEASUREMENT_MODE_PEAK_RMS);
            mVisualizer.setScalingMode(Visualizer.SCALING_MODE_NORMALIZED);
        }

        // Đặt kích thước của mảng byte return để hiển thị
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[0]);
        // Khi đang có dữ liệu về audio, update visualizer view
        mVisualizer.setDataCaptureListener(
                new Visualizer.OnDataCaptureListener() {
                    public void onWaveFormDataCapture(Visualizer visualizer,
                                                      byte[] bytes, int samplingRate) {

                        // Chưa làm gì ở đây
                    }

                    public void onFftDataCapture(Visualizer visualizer,
                                                 byte[] bytes, int samplingRate) {
                        // Nếu Visualizer đã sẵn sàng và có dữ liệu, gửi dữ liệu đó đến VisualizerView
                        if (mVisualizer != null && mVisualizer.getEnabled()) {
                            mVisualizerView.updateFFT(bytes);
                        }

                    }
                },
                Visualizer.getMaxCaptureRate(), false, true);

        // Khởi chạy
        mVisualizer.setEnabled(true);
        mPlayer.start();
    }

    public void shutdown(boolean isFinishing) {

        if (mPlayer != null) {
            mPlayer.pause();
            if (isFinishing) {
                mVisualizer.release();
                mPlayer.release();
                mPlayer = null;
                mVisualizer = null;
            }
        }

        if (mVisualizer != null) {
            mVisualizer.setEnabled(false);
        }
    }

    public void restart() {

        if (mPlayer != null) {
            mPlayer.start();
        }

        mVisualizer.setEnabled(true);
        mVisualizerView.restart();
    }
}