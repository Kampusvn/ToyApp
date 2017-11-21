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

// TODO (1) Implement LoaderManager.LoaderCallbacks<String> trong MainActivity
public class MainActivity extends AppCompatActivity {

    /* Hằng số lưu trữ và khôi phục URL hiển thị */
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    // TODO (28) Xóa key lưu trữ kết quả tìm kiếm JSON
    /* Hằng số lưu trữ và khôi phục JSON hiển thị */
    private static final String SEARCH_RESULTS_RAW_JSON = "results";

    // TODO (2) Tạo một hằng int để nhận dạng loader. Đặt tên nó là GITHUB_SEARCH_LOADER

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

        if (savedInstanceState != null) {
            String queryUrl = savedInstanceState.getString(SEARCH_QUERY_URL_EXTRA);

            // TODO (26) Loại bỏ code lấy JSON
            String rawJsonSearchResults = savedInstanceState.getString(SEARCH_RESULTS_RAW_JSON);

            mUrlDisplayTextView.setText(queryUrl);
            // TODO (25) Loại bỏ code hiển thị JSON
            mSearchResultsTextView.setText(rawJsonSearchResults);
        }

        // TODO (24) Khởi tạo loader với GITHUB_SEARCH_LOADER làm ID, null cho bundle và this cho context
    }

    /**
     * Phương thức này lấy ra văn bản tìm kiếm từ EditText, xây dựng URL (sử dụng {@link NetworkUtils})
     * cho kho lưu trữ github bạn muốn tìm, hiển thị URL đó trong TextView và cuối cùng là kích hoạt
     * một AsyncTask để thực hiện yêu cầu GET bằng cách sử dụng {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();

        // TODO (17) Nếu không có gì tìm kiếm, chỉ ra rằng không có gì để tìm kiếm và trả về

        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        // TODO (18) Loại bỏ lời gọi để thực hiện AsyncTask
        new GithubQueryTask().execute(githubSearchUrl);

        // TODO (19) Tạo một bundle gọi là queryBundle
        // TODO (20) Sử dụng putString với SEARCH_QUERY_URL_EXTRA làm key và giá trị String của URL làm giá trị

        // TODO (21) Gọi getSupportLoaderManager và lưu trữ nó trong một biến LoaderManager
        // TODO (22) Tải Loader bằng cách gọi getLoader và chuyển ID đã chỉ định
        // TODO (23) Nếu Loader null, khoeir tạo nó. Nếu không thì restart nó.
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

    // TODO (3) Override phương thức onCreateLoader
	
	// Trong onCreateLoader
	
    // TODO (4) Trả về AsyncTaskLoader<String> như một lớp ẩn danh bên trong với this là tham số của constructor
    // TODO (5) Override onStartLoading
	
	// Trong onStartLoading

    // TODO (6) Nếu args null thì trả về.

    // TODO (7) Hiển thị loading indicator

    // TODO (8) Buộc tải
    // Kết thúc - onStartLoading

    // TODO (9) Override loadInBackground

    // Trong loadInBackground
    // TODO (10) Nhận String cho URL từ bundle được chuyển đến onCreateLoader

    // TODO (11) Nếu URL null hay trống, trả về null

    // TODO (12) Sao chép khối try / catch từ phương thức doInBackground của AsyncTask
    // Kết thúc - loadInBackground

    // TODO (13) Override onLoadFinished

    // Trong onLoadFinished
    // TODO (14) Ẩn loading indicator

    // TODO (15) Sử dụng cùng một cách như trong onPostExecute để hiển thị dữ liệu hoặc thông báo lỗi
    // Kết thúc - onLoadFinished

    // TODO (16) Override onLoaderReset vì nó là một phần của giao diện mà ta thực hiện, nhưng không làm bất cứ điều gì trong phương thức này

    // TODO (29) Xóa lớp AsyncTask
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        String queryUrl = mUrlDisplayTextView.getText().toString();
        outState.putString(SEARCH_QUERY_URL_EXTRA, queryUrl);

        // TODO (27) Loại bỏ mã duy trì JSON
        String rawJsonSearchResults = mSearchResultsTextView.getText().toString();
        outState.putString(SEARCH_RESULTS_RAW_JSON, rawJsonSearchResults);
    }
}