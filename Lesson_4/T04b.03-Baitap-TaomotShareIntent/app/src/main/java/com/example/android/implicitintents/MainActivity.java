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
package com.example.android.implicitintents;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Phương thức này được gọi khi nút Open Website được bấm. Nó sẽ mở trang web
     * chỉ định bởi URL được đại diện bởi biến urlAsString sử dụng implicit Intents.
     *
     * @param v Nút được bấm.
     */
    public void onClickOpenWebpageButton(View v) {
        String urlAsString = "http://www.kampus.vn";
        openWebPage(urlAsString);
    }

    /**
     * Phương thức này được gọi khi nút Open Location được bấm. Nó sẽ mở
     * bản đồ đến vị trí được đại diện bởi biến addressString sử dụng implicit Intents.
     *
     * @param v Nút được bấm.
     */
    public void onClickOpenAddressButton(View v) {
        String addressString = "1600 Amphitheatre Parkway, CA";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("geo")
                .path("0,0")
                .query(addressString);
        Uri addressUri = builder.build();

        showMap(addressUri);
    }

    /**
     * Phương thức này được gọi khi nút Share Text Content được bấm. Nó chỉ có chức
     * năng chia sẻ text trong chuỗi textThatYouWantToShare.
     *
     * @param v Nút được bấm.
     */
    public void onClickShareTextButton(View v) {
        // TODO (5) Chỉ định một String bạn muốn chia sẻ

        // TODO (6) Thay thế Toast bằng shareText và chuyền vào String lấy từ bước 5
        Toast.makeText(this, "TODO: Share text when this is clicked", Toast.LENGTH_LONG).show();
    }

    /**
     * Đây là nơi bạn tự tạo và chạy implicit Intent. Bạn sẽ làm giống với những dòng code ở trên.
     * Bạn có thể xem danh sách các implicit Intent trên trang Common Intents từ tài liệu dành
     * cho nhà phát triển của Google.
     *
     * @see <http://developer.android.com/guide/components/intents-common.html/>
     *
     * @param v Nút được bấm.
     */
    public void createYourOwn(View v) {
        Toast.makeText(this,
                "TODO: Create Your Own Implicit Intent",
                Toast.LENGTH_SHORT)
                .show();
    }

    /**
     * Phương thức này kích hoạt một implicit Intent để mở một trang web.
     *
     * @param url Url để mở webpage. Khởi đầu bằng http:// hoặc https:// vì đó là lược đồ của URI
     * với Intent này theo trang Common Intents
     */
    private void openWebPage(String url) {
        /*
         * Ta dùng phương thức Uri.parse vì nó thường xuyên được sử dụng.
		 * Bạn có thể dễ dàng chuyền vào Uri như là tham số của phương thức này.
         */
        Uri webpage = Uri.parse(url);

        /*
         * Ở đây, ta tạo Intent với hành động của ACTION_VIEW. Hành động này
		 * cho phép người dùng xem nội dung cụ thể, mà trong trường hợp này là URL.
         */
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        /*
         * Đây là một kiểm tra được thực hiện với mọi implicit Intent mà ta khởi chạy.
		 * Trong một số trường hợp, thiết bị chạy code này có thể không có Activity để
		 * thực hiện hành động với dữ liệu đã chỉ định. Nếu không có kiểm tra này,
		 * trong những trường hợp đó ứng dụng của bạn sẽ gặp lỗi.
         */
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Phương thức này sẽ kích hoạt một implicit Intent để xem vị trí trên bản đồ.
     *
     * Khi xây dựng implicit Intents, bạn có thể sử dụng phương thức setData hoặc chỉ định URI
     * làm tham số thứ hai của constructor Intent như trong {@link #openWebPage (String)}
     *
     * @param geoLocation Uri đại diện cho vị trí sẽ được mở ra trên bản đồ
     */
    private void showMap(Uri geoLocation) {
        /*
         * Ta tạo Intent với ACTION_VIEW vì ta muốn xem (VIEW) nội dung của Uri
         */
        Intent intent = new Intent(Intent.ACTION_VIEW);

        /*
         * Sử dụng setData để thiết lập Uri của Intent này cũng tương tự như việc
		 * chuyền nó vào constructor của Intent. Bạn có thể làm cách nào cũng được
         */
        intent.setData(geoLocation);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    // TODO (1) Viết phương thức Void tên là shareText chấp nhận tham số kiểu String
    // Thực hiện bước 2 - 4 trong phương thức shareText

    // TODO (2) Tạo biến String tên là mimeType và đặt vào "text/plain"

    // TODO (3) Tạo tiêu đề cho pop up chọn lựa bật lên

    // TODO (4) Sử dụng ShareCompat.IntentBuilder để xây dựng Intent và khởi chạy pop up chọn lựa
}