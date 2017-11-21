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

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v7.preference.CheckBoxPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;

// Hoàn thành (1) Implement OnSharedPreferenceChangeListener

public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Thêm visualizer preferences, định nghĩa ở file XML trong res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // Hoàn thành (3) Lấy màn hình preference, lấy số lượng preferences và lặp qua tất cả các preferences nếu nó
        // không phải là checkbox, gọi phương thức setSummary chuyền vào một preferences và giá trị của nó

        SharedPreferences sharedPreferences = getPreferenceScreen().getSharedPreferences();
        PreferenceScreen prefScreen = getPreferenceScreen();
        int count = prefScreen.getPreferenceCount();

        // Đi qua tất cả các preference, và thiết lập preference summary.
        for (int i = 0; i < count; i++) {
            Preference p = prefScreen.getPreference(i);
            // Không cần thiết lập preference summary cho checkbox vì chúng đã được thiết lập bằng xml bằng
            // cách sử dụng summaryOff và summaryOn
            if (!(p instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(p.getKey(), "");
                setPreferenceSummary(p, value);
            }
        }
    }

    // Hoàn thành (4) Override onSharedPreferenceChanged và, nếu không phải là checkbox preference,
    // gọi phương thức setPreferenceSummary với preference đã thay đổi
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // Tìm ra preference nào đã được thay đổi
        Preference preference = findPreference(key);
        if (null != preference) {
            // Cập nhật summary cho preference
            if (!(preference instanceof CheckBoxPreference)) {
                String value = sharedPreferences.getString(preference.getKey(), "");
                setPreferenceSummary(preference, value);
            }
        }
    }

    // Hoàn thành (2) Tạo ra một setPreferenceSummary lấy giá trị Preference và String làm tham số. Phương thức này nên
    // kiểm tra xem Preference có là một ListPreference không, và nếu có, tìm label phù hợp với value.
    // Bạn có thể làm điều này bằng cách sử dụng phương thức findIndexOfValue và getEntries của Preference.
    /**
     * Cập nhật summary cho preference
     *
     * @param preference Preference được cập nhật
     * @param value     Giá trị cập nhật
     */
    private void setPreferenceSummary(Preference preference, String value) {
        if (preference instanceof ListPreference) {
            // Với danh sách preferences, tìm label của giá trị được chọn
            ListPreference listPreference = (ListPreference) preference;
            int prefIndex = listPreference.findIndexOfValue(value);
            if (prefIndex >= 0) {
                // Đặt summary vào label đó
                listPreference.setSummary(listPreference.getEntries()[prefIndex]);
            }
        }
    }

    // Hoàn thành (5) Register (đăng ký) và unregister OnSharedPreferenceChange listener (this class) trong
    // onCreate và onDestroy tương ứng.

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}