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

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ChildActivity extends AppCompatActivity {

    /* Trường lưu trữ TextView */
    private TextView mDisplayText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_child);

        /* Cách sử dụng điển hình của findViewById... */
        mDisplayText = (TextView) findViewById(R.id.tv_display);

        // Hoàn thành (3) Sử dụng phương thức getIntent để lưu trữ Intent khởi chạy Activity này trong một biến
        /*
         * Phương thức getIntent cung cấp cho ta Intent để khởi chạy Activity
         */
        Intent intentThatStartedThisActivity = getIntent();

        // Hoàn thành (4) Tạo một câu lệnh if để kiểm tra xem Intent này có extra truyền vào từ MainActivity hay không

        if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT)) {
        // Hoàn thành (5) Nếu Intent có extra, lấy text

            String textEntered = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
        // Hoàn thành (6) Nếu Intent có extra, dùng nó để đặt text trong TextView
            mDisplayText.setText(textEntered);
        }
    }
}