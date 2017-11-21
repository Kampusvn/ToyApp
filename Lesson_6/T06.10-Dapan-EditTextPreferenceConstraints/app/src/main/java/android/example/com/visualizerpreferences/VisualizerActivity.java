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
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.example.com.visualizerpreferences.AudioVisuals.AudioInputReader;
import android.example.com.visualizerpreferences.AudioVisuals.VisualizerView;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class VisualizerActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private static final int MY_PERMISSION_RECORD_AUDIO_REQUEST_CODE = 88;
    private VisualizerView mVisualizerView;
    private AudioInputReader mAudioInputReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizer);
        mVisualizerView = (VisualizerView) findViewById(R.id.activity_visualizer);
        setupSharedPreferences();
        setupPermissions();
    }

    // Hoàn thành (2) Sửa đổi phương thức setupSharedPreferences và onSharedPreferencesChanged để cập nhật minSizeScale,
    // giả sử một giá trị số được lưu trong shared preferences
    private void setupSharedPreferences() {
        // Nhận tất cả các giá trị từ shared preferences để thiết lập
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mVisualizerView.setShowBass(sharedPreferences.getBoolean(getString(R.string.pref_show_bass_key),
                getResources().getBoolean(R.bool.pref_show_bass_default)));
        mVisualizerView.setShowMid(sharedPreferences.getBoolean(getString(R.string.pref_show_mid_range_key),
                getResources().getBoolean(R.bool.pref_show_mid_range_default)));
        mVisualizerView.setShowTreble(sharedPreferences.getBoolean(getString(R.string.pref_show_treble_key),
                getResources().getBoolean(R.bool.pref_show_treble_default)));
        loadColorFromPreferences(sharedPreferences);
        loadSizeFromSharedPreferences(sharedPreferences);

        // Register listener
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    private void loadColorFromPreferences(SharedPreferences sharedPreferences) {
        mVisualizerView.setColor(sharedPreferences.getString(getString(R.string.pref_color_key),
                getString(R.string.pref_color_red_value)));
    }

    private void loadSizeFromSharedPreferences(SharedPreferences sharedPreferences) {
        float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key),
                getString(R.string.pref_size_default)));
        mVisualizerView.setMinSizeScale(minSize);
    }


    // Cập nhật lên màn hình nếu shared preferences thay đổi. Phương thức này
    // là cần thiết khi bạn tạo một class implement OnSharedPreferenceChangedListener
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_show_bass_key))) {
            mVisualizerView.setShowBass(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_bass_default)));
        } else if (key.equals(getString(R.string.pref_show_mid_range_key))) {
            mVisualizerView.setShowMid(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_mid_range_default)));
        } else if (key.equals(getString(R.string.pref_show_treble_key))) {
            mVisualizerView.setShowTreble(sharedPreferences.getBoolean(key, getResources().getBoolean(R.bool.pref_show_treble_default)));
        } else if (key.equals(getString(R.string.pref_color_key))) {
            loadColorFromPreferences(sharedPreferences);
        } else if (key.equals(getString(R.string.pref_size_key))) {
            float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key), "1.0"));
            mVisualizerView.setMinSizeScale(minSize);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hủy VisualizerActivity như một OnPreferenceChangedListener để tránh bị thất thoát bộ nhớ.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Phương thức thiết lập menu
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        /* Sử dụng phương thức getMenuInflater của AppCompatActivity để có được một trình xử lý trên trình đơn inflater */
        MenuInflater inflater = getMenuInflater();
        /* Sử dụng phương thức của Inflater để inflate layout visualizer_menu vào menu này */
        inflater.inflate(R.menu.visualizer_menu, menu);
        /* Trả về true để visualizer_menu được hiển thị trong Toolbar */
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Bạn không cần chỉnh sửa code bên dưới. Code này để bắt đầu và dọn dẹp AudioInputReader
     **/

    /**
     * onPause Cleanup audio stream
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
}