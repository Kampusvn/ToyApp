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
package com.example.android.background;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.background.sync.ReminderTasks;
import com.example.android.background.sync.ReminderUtilities;
import com.example.android.background.sync.WaterReminderIntentService;
import com.example.android.background.utilities.PreferenceUtilities;

public class MainActivity extends AppCompatActivity implements
        SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView mWaterCountDisplay;
    private TextView mChargingCountDisplay;
    private ImageView mChargingImageView;

    private Toast mToast;

    ChargingBroadcastReceiver mChargingReceiver;
    IntentFilter mChargingIntentFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** Lấy view **/
        mWaterCountDisplay = (TextView) findViewById(R.id.tv_water_count);
        mChargingCountDisplay = (TextView) findViewById(R.id.tv_charging_reminder_count);
        mChargingImageView = (ImageView) findViewById(R.id.iv_power_increment);

        /** Đặt giá trị mặc định trên UI **/
        updateWaterCount();
        updateChargingReminderCount();
        ReminderUtilities.scheduleChargingReminder(this);

        /** Thiết lập shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        // TODO (5) Tạo và instantiate một biến thể hiện mới cho ChargingBroadcastReceiver và một IntentFilter
        /*
         * Tạo và đặng ký broadcast receiver
         */
        mChargingIntentFilter = new IntentFilter();
        mChargingReceiver = new ChargingBroadcastReceiver();
        // Hoàn thành (6) Gọi phương thức addAction trên intent filter và thêm Intent.ACTION_POWER_CONNECTED
        // với Intent.ACTION_POWER_DISCONNECTED. Việc này sẽ thiết lập một intent filter để kích hoạt
        // khi trạng thái sạc thay đổi.
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        mChargingIntentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
    }

    // Hoàn thành (7) Override onResume và thiết lập broadcast receiver. Thực hiện việc này bằng cách gọi
    // registerReceiver với ChargingBroadcastReceiver và IntentFilter.
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mChargingReceiver, mChargingIntentFilter);
    }


    // Hoàn thành (8) Override onPause và hủy đăng ký receiver bằng phương thức unregisterReceiver
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mChargingReceiver);
    }


    /**
     * Cập nhật TextView để hiển thị số đếm mới từ SharedPreferences
     */
    private void updateWaterCount() {
        int waterCount = PreferenceUtilities.getWaterCount(this);
        mWaterCountDisplay.setText(waterCount+"");
    }

    /**
     * Cập nhật TextView để hiển thị bộ đếm thông báo khi sạc từ SharedPreferences
     */
    private void updateChargingReminderCount() {
        int chargingReminders = PreferenceUtilities.getChargingReminderCount(this);
        String formattedChargingReminders = getResources().getQuantityString(
                R.plurals.charge_notification_count, chargingReminders, chargingReminders);
        mChargingCountDisplay.setText(formattedChargingReminders);

    }

    /**
     * Tăng thêm 1 ở bộ đếm và hiển thị Toast
     */
    public void incrementWater(View view) {
        if (mToast != null) mToast.cancel();
        mToast = Toast.makeText(this, R.string.water_chug_toast, Toast.LENGTH_SHORT);
        mToast.show();

        Intent incrementWaterCountIntent = new Intent(this, WaterReminderIntentService.class);
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        startService(incrementWaterCountIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /** Dọn dẹp shared preference listener **/
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Đây là một listener cập nhật giao diện người dùng khi water count và
     * charging reminder counts thay đổi
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (PreferenceUtilities.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount();
        } else if (PreferenceUtilities.KEY_CHARGING_REMINDER_COUNT.equals(key)) {
            updateChargingReminderCount();
        }
    }

    // Hoàn thành (1) Viết phương thức showCharging nhận vào một boolean. Phương thức này thay đổi hình ảnh của
    // mChargingImageView thành ic_power_pink_80px nếu boolean là true hoặc R.drawable.ic_power_grey_80px
    // nếu là false. Phương thức này cuối cùng sẽ cập nhật giao diện người dùng khi broadcast được kích hoạt
    // khi trạng thái sạc thay đổi.
    private void showCharging(boolean isCharging){
        if (isCharging) {
            mChargingImageView.setImageResource(R.drawable.ic_power_pink_80px);

        } else {
            mChargingImageView.setImageResource(R.drawable.ic_power_grey_80px);
        }
    }


    // Hoàn thành (2) Tạo inner class ChargingBroadcastReceiver extends từ BroadcastReceiver
    private class ChargingBroadcastReceiver extends BroadcastReceiver {
    // Hoàn thành (3) Override onReceive để có action từ intent và xem nó có match với
    // Intent.ACTION_POWER_CONNECTED hay không. Nếu match thì thiết bị đang được sạc.
    // Nếu không thì thiết bị đang không sạc.
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            boolean isCharging = (action.equals(Intent.ACTION_POWER_CONNECTED));

    		// Hoàn thành (4) Cập nhật UI bằng phương thức showCharging
            showCharging(isCharging);
        }
    }
}