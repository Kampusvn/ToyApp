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

    // Hoàn thành (26) Khai báo biến EditText tên là mSearchBoxEditText
    private EditText mSearchBoxEditText;
    // Hoàn thành (27) Khai báo biến TextView tên là mUrlDisplayTextView
    private TextView mUrlDisplayTextView;
    // Hoàn thành (28) Khai báo biến TextView tên là mSearchResultsTextView
    private TextView mSearchResultsTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hoàn thành (29) Sử dụng findViewById để lấy tham chiếu đến mSearchBoxEditText
        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        // Hoàn thành (30) Sử dụng findViewById để lấy tham chiếu đến mUrlDisplayTextView
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        // Hoàn thành (31) Sử dụng findViewById để lấy tham chiếu đến mSearchResultsTextView
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);
    }
}
