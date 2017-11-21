package android.example.com.visualizerpreferences;

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

import android.Manifest;
import android.content.pm.PackageManager;
import android.example.com.visualizerpreferences.AudioVisuals.AudioInputReader;
import android.example.com.visualizerpreferences.AudioVisuals.VisualizerView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class VisualizerActivity extends AppCompatActivity {

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
    private VisualizerView mVisualizerView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);
        mVisualizerView = (VisualizerView) findViewById(R.id.activity_visualizer);
        defaultSetup();
        setupPermissions();
    }

    private void defaultSetup() {
        mVisualizerView.setShowBass(true);
        mVisualizerView.setShowMid(true);
        mVisualizerView.setShowTreble(true);
        mVisualizerView.setMinSizeScale(1);
        mVisualizerView.setColor(getString(R.string.pref_color_red_value));
    }

    /**
     * Bạn không cần chỉnh sửa code bên dưới. Code này để bắt đầu và dọn dẹp AudioInputReader
     **/

    /**
     * onPause Cleanup (dọn dẹp) audio stream
     **/
    @Override
    protected void onPause() {
        super.onPause();
        if (mAudioInputReader != null) {
            mAudioInputReader.shutdown(isFinishing());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAudioInputReader != null) {
            mAudioInputReader.restart();
        }
    }

    /**
     * App Permissions cho Audio
     **/
    private void setupPermissions() {
        // Nếu ta chưa cấp quyền ghi âm ...
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            // Và nếu thiết bị đang dùng SDK M hoặc mới hơn...
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // Tiếp tục xin cấp quyền.
                String[] permissionsWeNeed = new String[]{ Manifest.permission.RECORD_AUDIO };
                requestPermissions(permissionsWeNeed, MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE);
            }
        } else {
            // Nếu không, quyền đã được cấp và tất cả đã sẵn sàng!
            mAudioInputReader = new AudioInputReader(mVisualizerView, this);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE: {
                // Nếu yêu cầu bị hủy bỏ, các mảng kết quả sẽ trống.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Quyền đã được cấp và bắt đầu hiển thị visualizer
                    mAudioInputReader = new AudioInputReader(mVisualizerView, this);

                } else {
                    Toast.makeText(this, "Permission for audio not granted. Visualizer can't run.", Toast.LENGTH_LONG).show();
                    finish();
                    // Quyền đã bị từ chối, vì vậy hiển thị một thông báo lí do
                    // chúng ta không thể chạy ứng dụng và sau đó đóng ứng dụng.
                }
            }
            // Các quyền khác có thể được viết ở đây

        }
    }

    // TODO (1) Tạo Empty Activity va đặt tên là SettingsActivity; đảm bảo sẽ sinh ra file layout
    // activity_settings.xml và thêm activity vào manifest

    // TODO (2) Thêm thư mục tài nguyên mới tên là menu và tạo visualizer_menu.xml
    // TODO (3) Trong visualizer_menu.xml, tạo menu item chứa 1 item. Id đặt là "action_settings",
    // lưu tiêu đề của nó trong strings.xml, mục này không bao giờ được hiển thị dưới
    // dạng action, và orderInCategory đặt là 100

    // TODO (5) Thêm menu vào thanh menu (menu bar)
    // TODO (6) Khi người dùng bấm "Settings", mở SettingsActivity
}