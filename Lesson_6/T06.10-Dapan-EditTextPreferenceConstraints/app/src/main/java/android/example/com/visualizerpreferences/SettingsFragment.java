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
import android.support.v7.preference.EditTextPreference;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.widget.Toast;

// Hoàn thành (1) Implement OnPreferenceChangeListener
public class SettingsFragment extends PreferenceFragmentCompat implements
        OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Thêm visualizer preferences, định nghĩa ở file XML trong res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

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

        // Hoàn thành (3) Thêm OnPreferenceChangeListener vào EditTextPreference
        // Thêm preference listener kiểm tra xem size có đúng với size preference không
        Preference preference = findPreference(getString(R.string.pref_size_key));
        preference.setOnPreferenceChangeListener(this);
    }

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
        } else if (preference instanceof EditTextPreference) {
            // Đối với EditTextPreferences, thiết lập summary để biểu diễn chuỗi đơn giản của giá trị.
            preference.setSummary(value);
        }
    }

    // Hoàn thành (2) Override onPreferenceChange. Phương thức này sẽ cố gắng chuyển giá trị preference mới
    // thành float; nếu không được thì sẽ hiển thịa error message và trả về false. Nếu có thể chuyển
    // giá trị thành float, kiểm tra float giữa 0 (exclusive) và 3 (inclusive). Nếu không nằm trong
    // khoảng đó, hiển thị error message và trả về false. Nếu đó là số hợp lệ, trả về true.

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        // Trong ngữ cảnh này, chúng tôi đang sử dụng onPreferenceChange listener để kiểm tra xem thiết đặt
        // kích thước có giá trị hợp lệ hay không.

        Toast error = Toast.makeText(getContext(), "Please select a number between 0.1 and 3", Toast.LENGTH_SHORT);

        // Kiểm tra lại xem preference có bằng size preference hay khômg
        String sizeKey = getString(R.string.pref_size_key);
        if (preference.getKey().equals(sizeKey)) {
            String stringSize = (String) newValue;
            try {
                float size = Float.parseFloat(stringSize);
                // Nếu giá trị không nằm trong khoảng cho phép, hiển thị lỗi.
                if (size > 3 || size <= 0) {
                    error.show();
                    return false;
                }
            } catch (NumberFormatException nfe) {
                // Nếu kí tự người dùng nhập không chuyển sang số được, hiển thị lỗi
                error.show();
                return false;
            }
        }
        return true;
    }

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