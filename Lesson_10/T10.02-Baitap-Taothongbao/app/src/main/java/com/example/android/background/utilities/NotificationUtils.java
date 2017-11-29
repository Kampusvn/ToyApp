package com.example.android.background.utilities;

/**
 * Lớp tiện ích để tạo thông báo
 */
public class NotificationUtils {

    // TODO (7) Viết phương thức remindUserBecauseCharging nhận vào một Context.
    // Phương thức này sẽ tạo một thông báo khi sạc. Bạn có thể xem hướng dẫn chi tiết tại:
    // https://developer.android.com/training/notify-user/build-notification.html
        // TODO (8) Trong phương thức remindUser, sử dụng NotificationCompat.Builder để tạo thông báo
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
        // TODO (9) Nếu phiên bản build lớn hơn JELLY_BEAN, hãy đặt mức độ ưu tiên của thông báo là PRIORITY_HIGH.
        // TODO (11) Lấy NotificationManager, sử dụng context.getSystemService(Context.NOTIFICATION_SERVICE);
        // TODO (12) Kích hoạt thông báo bằng cách gọi thông báo trên NotificationManager.
        // Truyền một ID duy nhất mà bạn chọn cho thông báo và notificationBuilder.build()



    // TODO (1) Tạo phương thức trợ giúp là contentIntent với tham số là một Context. Phương thức này sẽ trả về
    // PendingIntent.  Phương thức này sẽ tạo ra pending intent, và sẽ kích hoạt khi thông báo
    // được nhấn. Intent này sẽ mở MainActivity.
        // TODO (2) Tạo intent mở MainActivity
        // TODO (3) Tạo PendingIntent sử dụng getActivity và:
            // - Lấy context truyền vào như một tham số
            // - Lấy một int duy nhất cho pending intent (bạn có thể tạo ra một hằng cho số nguyên này ở trên)
            // - Lấy intent mở MainActivity mà bạn vừa tạo; đây là những gì được kích hoạt khi thông báo được kích hoạt
            // - Có cờ FLAG_UPDATE_CURRENT, để nếu intent được tạo lại, hãy giữ intent nhưng cập nhật dữ liệu


    // TODO (4) Viết phương thức trợ giúp tên là largeIcon lấy tham số truyền vào là một Context
    // và trả về một Bitmap. Phương pháp này cần thiết để giải mã một bitmap cho thông báo.
        // TODO (5) Lấy Resources object từ context.
        // TODO (6) Tạo và trả về một ảnh bitmap bằng BitmapFactory.decodeResource, chuyền vào
        // resource object và R.drawable.ic_local_drink_black_24px


}
