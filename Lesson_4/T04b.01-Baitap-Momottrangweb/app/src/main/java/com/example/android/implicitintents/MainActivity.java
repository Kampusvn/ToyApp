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
        // TODO (5) Tạo String chứa URL ( bắt đầu bằng http:// hoặc https:// )

        // TODO (6) Thay thế Toast bằng lời gọi openWebPage, chuyền vào URL String từ bước trước
        Toast.makeText(this, "TODO: Open a web page when this button is clicked", Toast.LENGTH_SHORT).show();
    }

    /**
     * Phương thức này được gọi khi nút Open Location được bấm. Nó sẽ mở
     * bản đồ đến vị trí được đại diện bởi biến addressString sử dụng implicit Intents.
     *
     * @param v Nút được bấm.
     */
    public void onClickOpenAddressButton(View v) {
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

    // TODO (1) Viết phương thức openWebPage nhận String làm tham số
    // Thực hiện bước 2 - 4 trong phuongq thức openWebPage

    // TODO (2) Dùng Uri.parse để phân tích String thành Uri

    // TODO (3) Tạo Intent với Intent.ACTION_VIEW và Uri là tham số

    // TODO (4) Xác minh rằng Intent này có thể được khởi chạy và sau đó gọi startActivity
}