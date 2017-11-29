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

import android.app.IntentService;
import android.content.Intent;

/**
 * Lớp con {@link IntentService} để xử lý yêu cầu công việc không đồng bộ trong service trên một trình xử lý riêng.
 */
// Hoàn thành (9) Tạo WaterReminderIntentService và extend từ IntentService
public class WaterReminderIntentService extends IntentService {

    //  Hoàn thành (10) Tạo một constructor mặc định gọi đến super với tên của lớp này
    public WaterReminderIntentService() {
        super("WaterReminderIntentService");
    }

    //  Hoàn thành (11) Override phương thức onHandleIntent
    @Override
    protected void onHandleIntent(Intent intent) {
        //      Hoàn thành (12) Lấy action từ Intent để bắt đầu Service
        String action = intent.getAction();

        //      Hoàn thành (13) Gọi ReminderTasks.executeTaskForTag và chuyền vào action để thực hiện
        ReminderTasks.executeTask(this, action);
    }
}