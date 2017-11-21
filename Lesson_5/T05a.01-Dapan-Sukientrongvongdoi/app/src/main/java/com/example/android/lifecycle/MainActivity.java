package com.example.android.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /*
     * Tag này được dùng cho log. Cách tốt nhất là sử dụng tên của lớp bằng cách sử dụng
     * getSimpleName vì nó sẽ giúp xác định vị trí mà log hiển thị
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /* Hằng số mang giá trị theo tên của mỗi callback vòng đời tương ứng*/
    private static final String ON_CREATE = "onCreate";
    private static final String ON_START = "onStart";
    private static final String ON_RESUME = "onResume";
    private static final String ON_PAUSE = "onPause";
    private static final String ON_STOP = "onStop";
    private static final String ON_RESTART = "onRestart";
    private static final String ON_DESTROY = "onDestroy";
    private static final String ON_SAVE_INSTANCE_STATE = "onSaveInstanceState";

    /*
     * TextView này sẽ chứa log của mọi phương thức lifecycle callback được gọi
     * từ Activity này. TextView có thể được đặt lại về trạng thái mặc định của nó bằng cách
     * bấm vào nút "Reset Log"
     */
    private TextView mLifecycleDisplay;

    /**
     * Được gọi là khi activity được tạo ra lần đầu tiên. Đây là nơi nên thực hiện tất cả các
     * thiết lập tĩnh: tạo view, ràng buộc dữ liệu vào danh sách, vv
     *
     * Luôn luôn theo sau bởi onStart().
     *
     * @param savedInstanceState Trạng thái bị đóng băng trước đây của activity, nếu có.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLifecycleDisplay = (TextView) findViewById(R.id.tv_lifecycle_events_display);

        // Hoàn thành (1) Dùng logAndAppend bên trong onCreate
        logAndAppend(ON_CREATE);
    }

    // Hoàn thành (2) Override onStart, gọi super.onStart, và gọi logAndAppend với ON_START
    /**
     * Được gọi khi activity hiển thị với người dùng.
     *
     * Tiếp theo là onResume() nếu activity được đưa lên foreground, hoặc onStop() nếu nó bị ẩn.
     */
    @Override
    protected void onStart() {
        super.onStart();

        logAndAppend(ON_START);
    }

    // Hoàn thành (3) Override onResume, gọi super.onResume, và gọi logAndAppend với ON_RESUME
    /**
     * Được gọi khi activity bắt đầu tương tác với người dùng. Tại thời điểm này activity đang
	 * ở trên đầu stack, với đầu vào người sử dụng đi đến nó.
     */
    @Override
    protected void onResume() {
        super.onResume();

        logAndAppend(ON_RESUME);
    }

    // Hoàn thành (4) Override onPause, gọi super.onPause, và gọi logAndAppend vớiON_PAUSE
    /**
     * Được gọi là khi hệ thống sắp bắt đầu lại một activity. Việc này thường được sử dụng để
	 * commit thay đổi chưa lưu cho dữ liệu liên tục, ngừng animation và những thứ có thể
	 * tiêu tốn CPU, vv. Việc triển khai phương thức này phải nhanh chóng bởi vì activity
	 * tiếp theo sẽ không được tiếp tục cho đến khi phương thức này trả về.
     *
     * Theo sau bởi onResume() nếu activity quay lại hiển thị, hoặc onStop() nếu nó được ẩn.
     */
    @Override
    protected void onPause() {
        super.onPause();

        logAndAppend(ON_PAUSE);
    }

    // Hoàn thành (5) Override onStop, gọi super.onStop, và gọi logAndAppend với ON_STOP
    /**
     * Được gọi khi activity không còn hiển thị với người dùng, bởi vì một activity khác
	 * đã được tiếp tục và bao gồm activity này. Điều này có thể xảy ra bởi vì một
	 * activity mới đang được bắt đầu, hay một activity hiện tại đang được hiển thị phía trước,
	 * hoặc là một trong những activity đang bị phá hủy.
     *
     * Theo sau bởi onRestart() nếu activity đang quay lại tương tác với người dùng, hoặc
     * onDestroy() nếu activity không còn nữa.
     */
    @Override
    protected void onStop() {
        super.onStop();

        logAndAppend(ON_STOP);
    }

    // Hoàn thành (6) Override onRestart, gọi super.onRestart, và gọi logAndAppend với ON_RESTART
    /**
     * Được gọi sau khi hoạt động của bạn đã bị dừng lại, trước khi nó được bắt đầu lại.
     *
     * Theo sau bởi onStart()
     */
    @Override
    protected void onRestart() {
        super.onRestart();

        logAndAppend(ON_RESTART);
    }

    // Hoàn thành (7) Override onDestroy, gọi super.onDestroy, và gọi logAndAppend với ON_DESTROY
    /**
     * Lời gọi cuối cùng nhận được trước khi activity bị hủy. Điều này có thể xảy ra bởi vì
	 * activity kết thúc (gọi finish()), hoặc vì hệ thống đang tạm thời phá hủy thể hiện này
	 * để tiết kiệm không gian. Bạn có thể phân biệt giữa hai kịch bản trên bằng phương thức isFinishing().
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        logAndAppend(ON_DESTROY);
    }

    /**
     * Đăng nhập vào giao diện điều khiển và gắn tên phương thức vòng đời vào TextView để
     * có thể xem hàng loạt các callback của phương thức, được gọi từ ứng dụng và cả Logcat
     * của Android Studio.
     *
     * @param lifecycleEvent Tên event được log.
     */
    private void logAndAppend(String lifecycleEvent) {
        Log.d(TAG, "Lifecycle Event: " + lifecycleEvent);

        mLifecycleDisplay.append(lifecycleEvent + "\n");
    }

    /**
     * Phương thức này đặt lại nội dung của TextView thành văn bản mặc định là "Lifecycle Callbacks"
     *
     * @param view View được bấm. Trường hợp này là, it is the Button from our layout.
     */
    public void resetLifecycleDisplay(View view) {
        mLifecycleDisplay.setText("Lifecycle callbacks:\n");
    }
}