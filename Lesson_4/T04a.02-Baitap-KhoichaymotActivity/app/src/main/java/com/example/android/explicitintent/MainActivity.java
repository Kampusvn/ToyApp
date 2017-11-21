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

    /* Trường lưu trữ EditText và Button */
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
             * Phương thức onClick được kích hoạt khi nút (mDoSomethingCoolButton) được bấm.
             *
             * @param v View được bấm. Ở đây là mDoSomethingCoolButton.
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

                // TODO (1) Lưu trữ ChildActivity.class trong lớp destinationActivity

                // TODO (2) Tạo Intent để khởi chạy ChildActivity

                // TODO (3) Thay thế Toast bằng code chạy ChildActivity
                String message = "Button clicked!\nStart a new Activity and pass some data.";
                Toast.makeText(context, message, Toast.LENGTH_LONG).show();

            }
        });
    }
}

