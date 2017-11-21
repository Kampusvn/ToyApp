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

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

// TODO (1) Implement OnSharedPreferenceChangeListener
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {

        // Thêm visualizer preferences, định nghĩa ở file XML trong res->xml->pref_visualizer
        addPreferencesFromResource(R.xml.pref_visualizer);

        // TODO (3) Lấy màn hình preference, lấy số lượng preferences và lặp qua tất cả các preferences nếu nó
        // không phải là checkbox, gọi phương thức setSummary chuyền vào một preferences và giá trị của nó
    }

    // TODO (4) Override onSharedPreferenceChanged và, nếu không phải là checkbox preference,
    // gọi phương thức setPreferenceSummary với preference đã thay đổi

    // TODO (2) Tạo ra một setPreferenceSummary lấy giá trị Preference và String làm tham số. Phương thức này nên
    // kiểm tra xem Preference có là một ListPreference không, và nếu có, tìm label phù hợp với value.
    // Bạn có thể làm điều này bằng cách sử dụng phương thức findIndexOfValue và getEntries của Preference.

    // TODO (5) Register (đăng ký) và unregister OnSharedPreferenceChange listener (this class) trong
    // onCreate và onDestroy tương ứng.



}