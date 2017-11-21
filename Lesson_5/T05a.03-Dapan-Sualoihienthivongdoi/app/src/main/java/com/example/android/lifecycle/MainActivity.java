package com.example.android.lifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*
     * Tag này được dùng cho log. Cách tốt nhất là sử dụng tên của lớp bằng cách sử dụng
     * getSimpleName vì nó sẽ giúp xác định vị trí mà log hiển thị
     */
    private static final String TAG = MainActivity.class.getSimpleName();

    /*
     * Hằng số String này sẽ được sử dụng để lưu trữ nội dung của TextView hiển thị danh sách callback.
	 * Lý do chúng ta lưu trữ nội dung của TextView là để bạn có thể xem toàn bộ các callbacks
	 * khi chúng được gọi.
     */
    private static final String LIFECYCLE_CALLBACKS_TEXT_KEY = "callbacks";

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

    // Hoàn thành (1) Khai báo và khởi tạo một ArrayList tĩnh của Strings gọi mLifecycleCallbacks
    /*
     * ArrayList này sẽ theo dõi callback trong vòng đời xảy ra sau khi ta có thể lưu chúng.
	 * Vì, như chúng ta đã quan sát được, nội dung của TextView được lưu trong onSaveInstanceState
	 * trước khi onStop và onDestroy được gọi, ta phải theo dõi khi onStop và onDestroy được gọi,
	 * và sau đó cập nhật giao diện người dùng trong onStart khi Activity trở lại màn hình.
     */

    private static final ArrayList<String> mLifecycleCallbacks = new ArrayList<>();
    /**
     * Được gọi khi activity được tạo ra lần đầu tiên. Đây là nơi nên thực hiện tất cả các
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

        /*
         * Nếu savedInstanceState không phải là null, có nghĩa là Activity chưa
		 * được bắt đầu. Ngay cả khi savedInstanceState không phải là null, ta cũng nên kiểm tra
		 * xem bundle có chứa key mà chúng ta đang tìm kiếm không. Trong trường hợp này, key
		 * chúng ta đang tìm kiếm map với nội dung của TextView hiển thị danh sách callback.
		 * Nếu bundle có chứa key đó, chúng tôi sẽ thiết lập nội dung của TextView cho phù hợp.
         */
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIFECYCLE_CALLBACKS_TEXT_KEY)) {
                String allPreviousLifecycleCallbacks = savedInstanceState
                        .getString(LIFECYCLE_CALLBACKS_TEXT_KEY);
                mLifecycleDisplay.setText(allPreviousLifecycleCallbacks);
            }
        }

        // Hoàn thành (4) Lặp qua mLifecycleCallbacks, nối mỗi chuỗi và dòng mới vào mLifecycleDisplay
        /*
         * Vì bất kỳ bản cập nhật nào cho giao diện người dùng mà chúng ta thực hiện sau khi
		 * onSaveInstanceState (onStop, onDestroy, v.v ...), ta sử dụng một ArrayList để theo dõi xem
		 *sự kiện vòng đời này có xảy ra hay không. Nếu chúng xảy ra, ta sẽ thêm tên tương ứng vào TextView.
         *
         * Lý do chúng tôi bắt đầu lặp lại từ phía sau của ArrayList và kết thúc ở phía trước là
		 * các callbacks gần nhất được chèn vào phía trước của ArrayList, do đó, các callbacks
		 * cũ được lưu trữ xa hơn. Chúng ta có thể sử dụng Queue để thực hiện việc này, nhưng
		 * Java có các tên API lạ cho giao diện Queue mà dễ gây nhầm lẫn hơn là việc sử dụng
		 * giải pháp ArrayList này.
         */
        for (int i = mLifecycleCallbacks.size() - 1; i >= 0; i--) {
            mLifecycleDisplay.append(mLifecycleCallbacks.get(i) + "\n");
        }

        // Hoàn thành (5) Dọn dẹp mLifecycleCallbacks sau khi lặp xong
        /*
         * Một khi nối lại mỗi callback từ ArrayList đến TextView, chúng ta cần phải
		 * làm sạch ArrayList để các mục trong TextView không bị trùng lặp với nhau.
         */
        mLifecycleCallbacks.clear();

        logAndAppend(ON_CREATE);
    }

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

    /**
     * Được gọi khi activity bắt đầu tương tác với người dùng. Tại thời điểm này activity đang
     * ở trên đầu stack, với đầu vào người sử dụng đi đến nó.
     */
    @Override
    protected void onResume() {
        super.onResume();

        logAndAppend(ON_RESUME);
    }

    /**
     * Được gọi khi hệ thống sắp bắt đầu lại một activity. Việc này thường được sử dụng để
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

        // Hoàn thành (2) Add the ON_STOP String to the front of mLifecycleCallbacks
        /*
         * Vì bất kỳ bản cập nhật nào cho giao diện người dùng mà chúng tôi thực hiện sau
		 * onSaveInstanceState (onStop, onDestroy, v.v ...), chúng tôi sử dụng một ArrayList
		 * để theo dõi xem sự kiện vòng đời này có xảy ra hay không. Nếu bất kỳ thứ nào trong
		 * đó xảy ra, ta sẽ thêm tên tương ứng vào TextView.
         */
        mLifecycleCallbacks.add(0, ON_STOP);

        logAndAppend(ON_STOP);
    }

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

    /**
     * Lời gọi cuối cùng nhận được trước khi activity bị hủy. Điều này có thể xảy ra bởi vì
     * activity kết thúc (gọi finish()), hoặc vì hệ thống đang tạm thời phá hủy thể hiện này
     * để tiết kiệm không gian. Bạn có thể phân biệt giữa hai kịch bản trên bằng phương thức isFinishing().
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Hoàn thành (3) Thêm ON_DESTROY String vào trước mLifecycleCallbacks
        /*
         * Vì bất kỳ bản cập nhật nào cho giao diện người dùng mà chúng tôi thực hiện sau
		 * onSaveInstanceState (onStop, onDestroy, v.v ...), chúng tôi sử dụng một ArrayList
		 * để theo dõi xem sự kiện vòng đời này có xảy ra hay không. Nếu bất kỳ thứ nào trong
		 * đó xảy ra, ta sẽ thêm tên tương ứng vào TextView.
         */
        mLifecycleCallbacks.add(0, ON_DESTROY);

        logAndAppend(ON_DESTROY);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        logAndAppend(ON_SAVE_INSTANCE_STATE);
        String lifecycleDisplayTextViewContents = mLifecycleDisplay.getText().toString();
        outState.putString(LIFECYCLE_CALLBACKS_TEXT_KEY, lifecycleDisplayTextViewContents);
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