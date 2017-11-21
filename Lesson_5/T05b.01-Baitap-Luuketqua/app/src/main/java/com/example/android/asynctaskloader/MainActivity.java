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
package com.example.android.asynctaskloader;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    // TODO (1) Tạo một static final key để lưu trữ URL truy vấn

    // TODO (2) Tạo một static final key để lưu trữ JSON nguyên bản của tìm kiếm

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;
    private TextView mSearchResultsTextView;

    private TextView mErrorMessageDisplay;

    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        mErrorMessageDisplay = (TextView) findViewById(R.id.tv_error_message_display);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.pb_loading_indicator);

        // TODO (9) Nếu savedInstanceState bundle không phải null, thiết lập văn bản của URL và kết quả tìm kiếm TextView tương ứng
    }

    /**
     * Phương thức này lấy ra văn bản tìm kiếm từ EditText, xây dựng URL (sử dụng {@link NetworkUtils})
     * cho kho lưu trữ github bạn muốn tìm, hiển thị URL đó trong TextView và cuối cùng là kích hoạt
     * một AsyncTask để thực hiện yêu cầu GET bằng cách sử dụng {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        new GithubQueryTask().execute(githubSearchUrl);
    }

    /**
     * Phương thức này sẽ làm cho View cho dữ liệu JSON hiển thị và ẩn thông báo lỗi.
     *
     * Vì bạn có thể thiết lập khả năng hiển thị của View, ta không cần phải kiểm
     * tra xem từng View hiện tại có hiển thị hay không.
     */
    private void showJsonDataView() {
        /* Đầu tiên, ẩn lỗi */
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        /* Sau đó, hiển thị JSON data */
        mSearchResultsTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Phương thức này sẽ làm cho thông báo lỗi hiển thị và ẩn JSON view.
     *
     * Vì bạn có thể thiết lập khả năng hiển thị của View, ta không cần
     * phải kiểm tra xem từng View hiện tại có hiển thị hay không.
     */
    private void showErrorMessage() {
        /* Đầu tiên, ẩn data đang hiển thị */
        mSearchResultsTextView.setVisibility(View.INVISIBLE);
        /* Sau đó hiển thị lỗi*/
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(URL... params) {
            URL searchUrl = params[0];
            String githubSearchResults = null;
            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;
        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                showJsonDataView();
                mSearchResultsTextView.setText(githubSearchResults);
            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_search) {
            makeGithubSearchQuery();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // TODO (3) Ghi đè lên SaveInstanceState để duy trì dữ liệu trên Activity
    // Làm các bước sau ở trong onSaveInstanceState
    // TODO (4) Chắc chắn rằng super.onSaveInstanceState được gọi trước khi làm các việc khác

    // TODO (5) Đặt nội dung của TextView chứa URL vào một biến
    // TODO (6) Sử dụng key cho URL truy vấn, đặt chuỗi trong Bundle bên ngoài

    // TODO (7) Đặt nội dung của TextView có chứa các kết quả tìm kiếm JSON thô vào một biến
    // TODO (8) Sử dụng key cho kết quả tìm kiếm JSON thô, đưa kết quả tìm kiếm vào Bundle bên ngoài
}