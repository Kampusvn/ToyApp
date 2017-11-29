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
import android.support.annotation.NonNull;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.concurrent.TimeUnit;

public class ReminderUtilities {


    // Hoàn thành (15) khai báo 3 hằng và 1 biến:
    //  - REMINDER_INTERVAL_SECONDS dạng hằng số integer lưu trữ số giây trong 15 phút
    //  - SYNC_FLEXTIME_SECONDS cũng là dạng hằng số integer lưu trữ số giây trong 15 phút
    //  - REMINDER_JOB_TAG là hằng số String, lưu trữ "hydration_reminder_tag"
    //  - sInitialized là biến private static boolean, cho biết công việc đã được kích hoạt hay chưa
    /*
     * Khoảng thời gian để nhắc nhở người dùng uống nước. Hãy sử dụng TimeUnit thay vì tự viết ra một loạt
	 * phép tính và có thể có sai sót.
     */
    private static final int REMINDER_INTERVAL_MINUTES = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

    private static boolean sInitialized;

    // Hoàn thành (16) Tạo một phương thức được đồng bộ hóa có dạng public static tên là scheduleChargingReminder
    // lấy vào một context. Phương thức này dùng FirebaseJobDispatcher để to lên lịch cho công việc lặp lại
    // mỗi khi sạc thiết bị. Nó sẽ kích hoạt WaterReminderFirebaseJobService.
    synchronized public static void scheduleChargingReminder(@NonNull final Context context) {


        // Hoàn thành (17) Nếu công việc đã được khởi tạo, hãy trả về
        if (sInitialized) return;

        // Hoàn thành (18) Tạo GooglePlayDriver
        Driver driver = new GooglePlayDriver(context);
        // Hoàn thành (19) Tạo mới FirebaseJobDispatcher với driver
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        // Hoàn thành (20) Sử dụng phương thức newJobBuilder của FirebaseJobDispatcher để xây dựng công việc:
        // - có service là WaterReminderFirebaseJobService
        // - có tag REMINDER_JOB_TAG
        // - chỉ kích hoạt khi sạc thiết bị
        // - có vòng đời không kết thúc
        // - có công việc được thực hiện lại mỗi 15 phút. Bạn có thể sử dụng
        //   setTrigger, chuyền vào Trigger.executionWindow
        // - thay thế cho công việc hiện tại nếu nó đang được chạy
        // Cuối cùng là xây dựng công việc.
        /* Tạo công việc để nhắc người dùng uống nước định kỳ */
        Job constraintReminderJob = dispatcher.newJobBuilder()
                /* Service sẽ được sử dụng để ghi vào preference */
                .setService(WaterReminderFirebaseJobService.class)
                /*
                 * Tag duy nhất để nhận dạng công việc.
                 */
                .setTag(REMINDER_JOB_TAG)
                /*
                 * Các ràng buộc về mạng mà Job này sẽ chạy. Trong ứng dụng này, chúng ta dùng
				 * DEVICE_CHARGING Constraint để công việc chỉ thực hiện nếu thiết bị đang sạc.
                 *
                 * Trong ứng dụng thông thường, bạn nên có preference cho việc này vì những người
				 * dùng khác nhau có thể có các tuỳ chọn khác nhau khi đồng bộ hóa dữ liệu ứng dụng.
                 */
                .setConstraints(Constraint.DEVICE_CHARGING)
                /*
                 * setLifetime đặt thời gian diễn ra công việc này. Các tùy chọn là để Job tồn tại
				 * "mãi mãi" hoặc hủy trong lần khởi động tiếp theo.
                 */
                .setLifetime(Lifetime.FOREVER)
                /*
                 * Chúng ta muốn những lời nhắc nhở liên tục xảy ra, vì vậy ta để setRecurring(true)
                 */
                .setRecurring(true)
                /*
				 * Mỗi 15 phút ứng dụng sẽ nhắc một lần. Đối số đầu tiên cho phương thức tĩnh
				 * static executionWindow của lớp Trigger là sự khởi đầu của khung thời để công việc
				 * được thực hiện. Đối số thứ hai là điểm mới nhất trong thời gian mà dữ liệu cần được
				 * đồng bộ.
                 */
                .setTrigger(Trigger.executionWindow(
                        REMINDER_INTERVAL_SECONDS,
                        REMINDER_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                /*
                 * Nếu một công việc với tag được cung cấp đã tồn tại, công việc mới này sẽ thay thế công việc cũ.
                 */
                .setReplaceCurrent(true)
                /* Khi công việc đã sẵn sàng, hãy gọi phương thức build của builder để trả về công việc */
                .build();

        // Hoàn thành (21) Sử dụng phương thức schedule của dispatcher để lên lịch công việc
        /* Lập lịch công việc với dispatcher */
        dispatcher.schedule(constraintReminderJob);

        // Hoàn thành (22) Đặt sInitialized thành true để đánh dấu rằng chúng ta đã hoàn tất thiết lập công việc
        /* Công việc được khởi tạo */
        sInitialized = true;
    }

}