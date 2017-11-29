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


public class ReminderUtilities {
    // TODO (15) khai báo 3 hằng và 1 biến:
    //  - REMINDER_INTERVAL_SECONDS dạng hằng số integer lưu trữ số giây trong 15 phút
    //  - SYNC_FLEXTIME_SECONDS cũng là dạng hằng số integer lưu trữ số giây trong 15 phút
    //  - REMINDER_JOB_TAG là hằng số String, lưu trữ "hydration_reminder_tag"
    //  - sInitialized là biến private static boolean, cho biết công việc đã được kích hoạt hay chưa

    // TODO (16) Tạo một phương thức được đồng bộ hóa có dạng public static tên là scheduleChargingReminder
    // lấy vào một context. Phương thức này dùng FirebaseJobDispatcher để to lên lịch cho công việc lặp lại
    // mỗi khi sạc thiết bị. Nó sẽ kích hoạt WaterReminderFirebaseJobService.
        // TODO (17) Nếu công việc đã được khởi tạo, hãy trả về
        // TODO (18) Tạo GooglePlayDriver
        // TODO (19) Tạo mới FirebaseJobDispatcher với driver
        // TODO (20) Sử dụng phương thức newJobBuilder của FirebaseJobDispatcher để xây dựng công việc:
            // - có service là WaterReminderFirebaseJobService
            // - có tag REMINDER_JOB_TAG
            // - chỉ kích hoạt khi sạc thiết bị
            // - có vòng đời không kết thúc
            // - có công việc được thực hiện lại mỗi 15 phút. Bạn có thể sử dụng
            //   setTrigger, chuyền vào Trigger.executionWindow
            // - thay thế cho công việc hiện tại nếu nó đang được chạy
        // Cuối cùng là xây dựng công việc.
        // TODO (21) Sử dụng phương thức schedule của dispatcher để lên lịch công việc
        // TODO (22) Đặt sInitialized thành true để đánh dấu rằng chúng ta đã hoàn tất thiết lập công việc
}
