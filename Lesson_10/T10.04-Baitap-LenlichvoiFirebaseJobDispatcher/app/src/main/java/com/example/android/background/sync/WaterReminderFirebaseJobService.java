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

public class WaterReminderFirebaseJobService {
    // TODO (3) WaterReminderFirebaseJobService được extend từ JobService

    // TODO (4) Override onStartJob
        // TODO (5) Mặc định, jcác công việc được thực hiện trên main thread, vì vậy hãy tạo một lớp extend
        //  từ AsyncTask và đặt tên là mBackgroundTask.
            // TODO (6) Override doInBackground
                // TODO (7) Dùng ReminderTasks để thực hiện charging reminder task, sử dụng
                // service này làm context(WaterReminderFirebaseJobService.this) và trả về null
                // khi hoàn thành.
            // TODO (8) Override onPostExecute và gọi jobFinished. Chuyền tham số của công việc
            // và false vào jobFinished. Việc này sẽ thông báo cho JobManager rằng công việc của bạn đã hoàn thành và
            // bạn không muốn sắp xếp lại công việc.

        // TODO (9) Thực thi AsyncTask
        // TODO (10) Trả về true

    // TODO (11) Override onStopJob
        // TODO (12) Nếu mBackgroundTask hợp lệ, hãy hủy nó
        // TODO (13) Trả về true để biểu thị công việc nên được thử lại

}
