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
package com.example.android.datafrominternet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }

    // Làm bước 2 - 7 ở menu.xml ///////////////////////////////////////////////////////////////////////
    // TODO (2) Tạo menu xml tên là 'main.xml' ở thư mục res->menu
    // TODO (3) Thêm một menu item vào menu
    // TODO (4) Đặt ID cho menu item là @+id/action_search
    // TODO (5) Đặt orderInCategory là 1
    // TODO (6) Hiển thị item nếu còn chỗ trống (dùng app:showAsAction, không dùng android:showAsAction)
    // TODO (7) Đặt tiêu đề cho chuỗi tìm kiếm ("Search") trong strings.xml
    // Làm bước 2 - 7 ở menu.xml ///////////////////////////////////////////////////////////////////////


    // TODO (8) Override onCreateOptionsMenu
    // TODO (9) Bên trong onCreateOptionsMenu, sử dụng getMenuInflater().inflate để inflate menu
    // TODO (10) Trả về true để hiển thị menu

    // TODO (11) Override onOptionsItemSelected
    // TODO (12) Bên trong onOptionsItemSelected, lấy ID của item được chọn
    // TODO (13) Nếu ID của item là R.id.action_search, hiển thị Toast pop up và trả về true để Android biết rằng bạn đã quản lý menu click
    // TODO (14) Nhớ gọi .show() trong Toast
    // TODO (15) Nếu bạn không quản lý menu click, trả về super.onOptionsItemSelected để Android quản lý
}
