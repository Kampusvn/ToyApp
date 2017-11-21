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

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.android.datafrominternet.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultsTextView;

    // TODO (12) Khai báo biến cho TextView thông báo lỗi

    // TODO (24) Khai báo biến cho ProgressBar

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);

        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultsTextView = (TextView) findViewById(R.id.tv_github_search_results_json);

        // TODO (13) Lấy thể hiện của TextView báo lỗi bằng findViewById

        // TODO (25) Lấy thể hiện của ProgressBar bằng findViewById
    }

    /**
     * Phương thức này thu thập văn bản tìm kiếm từ EditText, xây dựng URL
     * (sử dụng {@link NetworkUtils}) cho kho lưu trữ github bạn muốn tìm, hiển thị URL đó
     * trong TextView và cuối cùng là kích hoạt một AsyncTask để thực hiện yêu cầu GET
     * sử dụng {@link GithubQueryTask} (lúc này chưa được tạo)
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());
        new GithubQueryTask().execute(githubSearchUrl);
    }

    // TODO (14) Viết phương thức showJsonDataView để hiển thị dữ liệu và ẩn lỗi

    // TODO (15) Viết phương thức showErrorMessage để hiển thị lỗi và ẩn dữ liệu

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        // TODO (26) Ghi đè phương thức onPreExecute để đặt loading indicator thành visible

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
            // TODO (27) Ngay khi load xong thì ẩn loading indicator
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                // TODO (17) Gọi showJsonDataView nếu kết quả hợp lệ và không phải là null
                mSearchResultsTextView.setText(githubSearchResults);
            }
            // TODO (16) Gọi phương thức showErrorMessage nếu kết quả là null ở onPostExecute
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
}
