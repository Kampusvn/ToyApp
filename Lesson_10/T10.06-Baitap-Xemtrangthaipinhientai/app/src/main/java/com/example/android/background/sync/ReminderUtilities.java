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
    /*
     * Khoảng thời gian để nhắc nhở người dùng uống nước. Hãy sử dụng TimeUnit thay vì tự viết ra một loạt
	 * phép tính và có thể có sai sót.
     */
    private static final int REMINDER_INTERVAL_MINUTES = 15;
    private static final int REMINDER_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toSeconds(REMINDER_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = REMINDER_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "hydration_reminder_tag";

    private static boolean sInitialized;

    synchronized public static void scheduleChargingReminder(@NonNull final Context context) {

        if (sInitialized) return;

        Driver driver = new GooglePlayDriver(context);
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(driver);

        /* Tạo công việc nhắc nhở định kỳ */
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

        /* Lập lịch công việc với dispatcher */
        dispatcher.schedule(constraintReminderJob);

        /* Công việc được khởi tạo */
        sInitialized = true;
    }

}