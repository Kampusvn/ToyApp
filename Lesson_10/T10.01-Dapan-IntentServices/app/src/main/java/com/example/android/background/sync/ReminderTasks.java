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
package com.example.android.background.sync;

import android.content.Context;

import com.example.android.background.utilities.PreferenceUtilities;

// Hoàn thành (1) Viết lớp ReminderTasks
public class ReminderTasks {

    // Hoàn thành (2) Khai báo biến public static constant String tên là ACTION_INCREMENT_WATER_COUNT
    public static final String ACTION_INCREMENT_WATER_COUNT = "increment-water-count";

    // Hoàn thành (6) Viết phương thức public static void tên là executeTask
    // Hoàn thành (7) Thêm Context đặt tên là context và tham số String tên là action vào danh sách tham số
    public static void executeTask(Context context, String action) {
        // Hoàn thành (8) Nếu action bằng với ACTION_INCREMENT_WATER_COUNT, gọi phương thức incrementWaterCount của lớp này
        if (ACTION_INCREMENT_WATER_COUNT.equals(action)) {
            incrementWaterCount(context);
        }
    }

    // Hoàn thành (3) Viết phương thức private static void tên là incrementWaterCount
    // Hoàn thành (4) Thêm Context tên là context vào danh sách đối số
    private static void incrementWaterCount(Context context) {
        // Hoàn thành (5) Từ incrementWaterCount, gọi phương thức PreferenceUtility để update water count
        PreferenceUtilities.incrementWaterCount(context);
    }
}