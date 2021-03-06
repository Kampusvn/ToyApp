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
        // TODO (5) Lưu địa chỉ vào một String

        // TODO (6) Sử dụng Uri.Builder với lược đồ và truy vấn thích hợp để tạo thành Uri cho địa chỉ

        // TODO (7) Thay thế Toast bằng lời gọi showMap và chuyền Uri từ bước trước vào
        Toast.makeText(this, "TODO: Open a map when this button is clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * Phương thức này được gọi khi nút Share Text Content được bấm. Nó chỉ có chức
     * năng chia sẻ text trong chuỗi textThatYouWantToShare.
     *
     * @param v Nút được bấm.
     */
    public void onClickShareTextButton(View v) {
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


    // TODO (1) Viết phương thức showMap với Uri là tham số
    // Thực hiện bước 2 - 4 trong phương thức showMap
    // TODO (2) Tạo Intent với action type Intent.ACTION_VIEW

    // TODO (3) Thiết lập dữ liệu của Intent là Uri được chuyền vào phương thức này

    // TODO (4) Xác minh rằng Intent này có thể được khởi chạy và sau đó gọi startActivity


}