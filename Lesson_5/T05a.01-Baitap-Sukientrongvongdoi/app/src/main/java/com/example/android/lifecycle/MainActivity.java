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

        // TODO (1) Dùng logAndAppend bên trong onCreate
    }

    // TODO (2) Override onStart, gọi super.onStart, và gọi logAndAppend với ON_START

    // TODO (3) Override onResume, gọi super.onResume, và gọi logAndAppend với ON_RESUME

    // TODO (4) Override onPause, gọi super.onPause, và gọi logAndAppend vớiON_PAUSE

    // TODO (5) Override onStop, gọi super.onStop, và gọi logAndAppend với ON_STOP

    // TODO (6) Override onRestart, gọi super.onRestart, và gọi logAndAppend với ON_RESTART

    // TODO (7) Override onDestroy, gọi super.onDestroy, và gọi logAndAppend với ON_DESTROY

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
