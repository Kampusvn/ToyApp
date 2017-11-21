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
package com.example.android.explicitintent;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    /* Các trường lưu trữ EditText and Button */
    private EditText mNameEntry;
    private Button mDoSomethingCoolButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Sử dụng findViewById để có được tham chiếu đến button từ xml.
         * Điều này cho phép chúng ta làm những việc như thiết lập onClickListener để quản lý
         * chuyện gì sẽ xảy ra khi nút được bấm.
         */
        mDoSomethingCoolButton = (Button) findViewById(R.id.b_do_something_cool);
        mNameEntry = (EditText) findViewById(R.id.et_text_entry);

        /* Thiết lập OnClickListener cho phép ta làm một số việc khi nút được bấm. */
        mDoSomethingCoolButton.setOnClickListener(new OnClickListener() {

            /**
             * The onClick method is triggered when this button (mDoSomethingCoolButton) is clicked.
             *
             * @param v The view that is clicked. In this case, it's mDoSomethingCoolButton.
             */
            @Override
            public void onClick(View v) {
                /*
                 * Lưu trữ Context trong một biến trong trường hợp này là không cần thiết vì
                 * chúng ta có thể sử dụng "this" hoặc "MainActivity.this" trong lời gọi
                 * phương thức dưới đây. Tuy nhiên, ở đây tôi muốn giải thích cho bạn
                 * "MainActivity.this" là gì một cách rõ ràng.
                 */
                Context context = MainActivity.this;
                String message = "Button clicked!\nTODO: Start a new Activity and pass some data.";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();
            }
        });
    }
}

// TODO (1) Dùng trình hỗ trợ tạo Activity của Android Studio để tạo Activity tên là ChildActivity

// Thực hiện bước 2 - 5 trong activity_child.xml
// TODO (2) Đổi ConstraintLayout sang FrameLayout và chỉnh sửa cho phù hợp
// TODO (3) Đặt Id cho TextView là tv_display
// TODO (4) Đặt text hiển thị mặc định
// TODO (5) Đặt cỡ chữ lớn hơn một chút

// Thực hiện 6 & 7 trong ChildActivity.java
// TODO (6) Tạo trường TextView để hiển thị message
// TODO (7) Lấy thể hiện của TextView trong Java