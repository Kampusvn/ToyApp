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
import android.support.v4.content.ContextCompat;

import com.example.android.background.MainActivity;
import com.example.android.background.R;

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

    // Hoàn thành (7) Viết phương thức remindUserBecauseCharging nhận vào một Context.
    // Phương thức này sẽ tạo một thông báo khi sạc. Bạn có thể xem hướng dẫn chi tiết tại:
    // https://developer.android.com/training/notify-user/build-notification.html
    public static void remindUserBecauseCharging(Context context) {
        // Hoàn thành (8) Trong phương thức remindUser, sử dụng NotificationCompat.Builder để tạo thông báo
        // trong đó:
        // - màu của R.colorPrimary - dùng ContextCompat.getColor
        // - có icon nhỏ là ic_drink_notification
        // - sử dụng icon trả về bởi phương thức trợ giúp bigIcon làm biểu tượng lớn
        // - đặt tiêu đề cho String resource charging_reminder_notification_title
        // - đặt text cho String resource charging_reminder_notification_body
        // - thiết lập style cho NotificationCompat.BigTextStyle().bigText(text)
        // - đặt mặc định là thông báo rung
        // - sử dụng content intent được trả về bởi phương thức trợ giúp contentIntent cho contentIntent
        // - xóa thông báo khi người dùng đã bấm vào
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
                .setAutoCancel(true);

        // Hoàn thành (9) Nếu phiên bản build lớn hơn JELLY_BEAN, hãy đặt mức độ ưu tiên của thông báo là PRIORITY_HIGH.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            notificationBuilder.setPriority(Notification.PRIORITY_HIGH);
        }

        // Hoàn thành (11) Lấy NotificationManager, sử dụng context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationManager notificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Hoàn thành (12) Kích hoạt thông báo bằng cách gọi thông báo trên NotificationManager.
        // Truyền một ID duy nhất mà bạn chọn cho thông báo và notificationBuilder.build()
        notificationManager.notify(WATER_REMINDER_NOTIFICATION_ID, notificationBuilder.build());
    }

    // Hoàn thành (1) Tạo phương thức trợ giúp là contentIntent với tham số là một Context. Phương thức này sẽ trả về
    // PendingIntent.  Phương thức này sẽ tạo ra pending intent, và sẽ kích hoạt khi thông báo
    // được nhấn. Intent này sẽ mở MainActivity.
    private static PendingIntent contentIntent(Context context) {
        // Hoàn thành (2) Tạo intent mở MainActivity
        Intent startActivityIntent = new Intent(context, MainActivity.class);
        // Hoàn thành (3) Tạo PendingIntent sử dụng getActivity và:
        // - Lấy context truyền vào như một tham số
        // - Lấy một int duy nhất cho pending intent (bạn có thể tạo ra một hằng cho số nguyên này ở trên)
        // - Lấy intent mở MainActivity mà bạn vừa tạo; đây là những gì được kích hoạt khi thông báo được kích hoạt
        // - Có cờ FLAG_UPDATE_CURRENT, để nếu intent được tạo lại, hãy giữ intent nhưng cập nhật dữ liệu
        return PendingIntent.getActivity(
                context,
                WATER_REMINDER_PENDING_INTENT_ID,
                startActivityIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }


    // Hoàn thành (4) Viết phương thức trợ giúp tên là largeIcon lấy tham số truyền vào là một Context
    // và trả về một Bitmap. Phương pháp này cần thiết để giải mã một bitmap cho thông báo.
    private static Bitmap largeIcon(Context context) {
        // Hoàn thành (5) Lấy Resources object từ context.
        Resources res = context.getResources();
        // Hoàn thành (6) Tạo và trả về một ảnh bitmap bằng BitmapFactory.decodeResource, chuyền vào
        // resource object và R.drawable.ic_local_drink_black_24px
        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.ic_local_drink_black_24px);
        return largeIcon;
    }
}