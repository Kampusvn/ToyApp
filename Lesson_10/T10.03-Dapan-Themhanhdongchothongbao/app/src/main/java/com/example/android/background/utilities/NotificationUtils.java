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
package com.example.android.background.utilities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Action;
import android.support.v4.content.ContextCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;
import com.example.android.background.sync.ReminderTasks;
import com.example.android.background.sync.WaterReminderIntentService;


/**
 * Lớp tiện ích để tạo thông báo
 */
public class NotificationUtils {

    /*
     * ID thông báo này có thể được sử dụng để truy cập vào thông báo sau khi hiển thị. Việc này có thể
	 * hữu ích khi ta cần hủy hoặc cập nhật thông báo. Số này được đặt tùy ý.
     */
    private static final int WATER_REMINDER_NOTIFICATION_ID = 1138;
    /**
     * Pending intent id này dùng để tham chiếu đến một pending intent
     */
    private static final int WATER_REMINDER_PENDING_INTENT_ID = 3417;
    private static final int ACTION_DRINK_PENDING_INTENT_ID = 1;
    private static final int ACTION_IGNORE_PENDING_INTENT_ID = 14;

    //  Hoàn thành (1) Viết phương thức xóa các thông báo
    public static void clearAllNotifications(Context context) {
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void remindUserBecauseCharging(Context context) {

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setSmallIcon(R.drawable.ic_drink_notification)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getString(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                // Hoàn thành (17) Thêm hai hành động mới bằng cách sử dụng phương thức addAction và phương thức helper
                .addAction(drinkWaterAction(context))
                .addAction(ignoreReminderAction(context))
                .setAutoCancel(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }


        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        /* WATER_REMINDER_NOTIFICATION_ID cho phép bạn cập nhật hoặc hủy thông báo */
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }
    //  Hoàn thành (5) Thêm phương thức static là ignoreReminderAction
    private static Action ignoreReminderAction(Context context) {
        //      Hoàn thành (6) Tạo một Intent để khởi chạy WaterReminderIntentService
        Intent ignoreReminderIntent = new Intent(context, WaterReminderIntentService.class);
        //      Hoàn thành (7) Đặt hành động của intent chỉ định để xóa thông báo
        ignoreReminderIntent.setAction(ReminderTasks.ACTION_DISMISS_NOTIFICATION);
        //      Hoàn thành (8) Tạo một PendingIntent từ intent để khởi chạy WaterReminderIntentService
        PendingIntent ignoreReminderPendingIntent = PendingIntent.getService(
                context,
                ACTION_IGNORE_PENDING_INTENT_ID,
                ignoreReminderIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        //      Hoàn thành (9) Tạo một Action cho người dùng bỏ qua thông báo (và xóa nó)
        Action ignoreReminderAction = new Action(R.drawable.ic_cancel_black_24px,
                "No, thanks.",
                ignoreReminderPendingIntent);
        //      Hoàn thành (10) Trả về action
        return ignoreReminderAction;
    }

    //  Hoàn thành (11) Thêm phương thức static là drinkWaterAction
    private static Action drinkWaterAction(Context context) {
        //      Hoàn thành (12) Tạo Intent khởi chạy WaterReminderIntentService
        Intent incrementWaterCountIntent = new Intent(context, WaterReminderIntentService.class);
        //      Hoàn thành (13) Đặt hành động của intent chỉ định để tăng số đếm
        incrementWaterCountIntent.setAction(ReminderTasks.ACTION_INCREMENT_WATER_COUNT);
        //      Hoàn thành (14) Tạo một PendingIntent từ intent để khởi chạy WaterReminderIntentService
        PendingIntent incrementWaterPendingIntent = PendingIntent.getService(
                context,
                ACTION_DRINK_PENDING_INTENT_ID,
                incrementWaterCountIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
        //      Hoàn thành (15) Tạo Action cho người dùng sau khi đã uống nước
        Action drinkWaterAction = new Action(R.drawable.ic_local_drink_black_24px,
                "I did it!",
                incrementWaterPendingIntent);
        //      Hoàn thành (16) Trả về action
        return drinkWaterAction;
    }

    private static PendingIntent contentIntent(Context context) {
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context) {
        Resources res = context.getResources();
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}