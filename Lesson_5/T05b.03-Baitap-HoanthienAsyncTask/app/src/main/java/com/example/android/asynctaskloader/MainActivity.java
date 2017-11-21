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

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.asynctaskloader.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    /* Hằng số lưu trữ và khôi phục URL hiển thị */
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    /*
     * Con số này sẽ xác định Loader. Bạn có thể thay đổi nó thành bất kỳ số nào bạn muốn,
	 * miễn là sử dụng cùng một tên biến.
     */
    private static final int GITHUB_SEARCH_LOADER = 22;

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

            mUrlDisplayTextView.setText(queryUrl);
        }

        /*
         * Khởi tạo loader
         */
        getSupportLoaderManager().initLoader(GITHUB_SEARCH_LOADER, null, this);
    }

    /**
     * Phương thức này lấy ra văn bản tìm kiếm từ EditText, xây dựng URL (sử dụng {@link NetworkUtils})
     * cho kho lưu trữ github bạn muốn tìm, hiển thị URL đó trong TextView và cuối cùng là kích hoạt
     * một AsyncTask để thực hiện yêu cầu GET bằng cách sử dụng {@link GithubQueryTask}
     */
    private void makeGithubSearchQuery() {
        String githubQuery = mSearchBoxEditText.getText().toString();

        /*
         * Nếu người dùng không nhập gì thì không phải tìm kiếm gì cả. Nếu không nhập gì mà bấm tìm kiếm,
		 * ta sẽ hiển thị text không có gì để tìm kiếm và không load gì lên.
         *
		 * Nếu có text trong search box và bấm nút tìm kiếm, ta sẽ tạo URL trả về kết quả tìm kiếm
		 * từ Github, hiển thị URL, và chuyền URL vào Loader.
         */
        if (TextUtils.isEmpty(githubQuery)) {
            mUrlDisplayTextView.setText("No query entered, nothing to search for.");
            return;
        }

        URL githubSearchUrl = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchUrl.toString());

        Bundle queryBundle = new Bundle();
        queryBundle.putString(SEARCH_QUERY_URL_EXTRA, githubSearchUrl.toString());

        /*
         * Bây giờ khi đã tạo bundle để chuyển đến Loader, ta cần phải quyết định xem nên khởi động lại Loader
		 * (nếu tồn tại) hoặc khởi tạo Loader (nếu không tồn tại) .
         *
         * Ta thực hiện việc này bằng cách lưu trữ support loader manager trong variable loaderManager.
		 * Tất cả mọi thứ liên quan đến Loader đều thông qua LoaderManager. Một khi có support loader manager,
		 * (là loaderManager), chúng ta có thể cố gắng truy cập githubSearchLoader. Để làm điều này, ta sẽ
		 * sử dụng phương thức của LoaderManager, "getLoader", và chuyền vào ID đã chỉ định trong quá trình
		 * tạo ra nó. Bạn có thể nghĩ đến quá trình này tương tự như việc tìm một View by ID. Ta cung cấp cho
		 * LoaderManager một ID và nó trả về một loader (nếu có). Nếu không tồn tại, ta yêu cầu LoaderManager
		 * tạo ra. Nếu tồn tại, ta yêu cầu LoaderManager khởi động lại nó.
         */
        LoaderManager loaderManager = getSupportLoaderManager();
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        if (githubSearchLoader == null) {
            loaderManager.initLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        } else {
            loaderManager.restartLoader(GITHUB_SEARCH_LOADER, queryBundle, this);
        }
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

    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<String>(this) {

            // TODO (1) Tạo một biến String member là mGithubJson để lưu trữ JSON thô

            @Override
            protected void onStartLoading() {

                /* Nếu không có đối số nào được chuyền, không có truy vấn để thực hiện. Đơn giản chỉ cần trả về. */
                if (args == null) {
                    return;
                }

                /*
                 * Khi bắt đầu tải ở chế độ nền, hiển thị loading indicator
                 */
                mLoadingIndicator.setVisibility(View.VISIBLE);

                // TODO (2) Nếu mGithubJson không phải là null, lấy kết quả đó. Nếu không, bắt buộc load
                forceLoad();
            }

            @Override
            public String loadInBackground() {

                /* Trích xuất truy vấn tìm kiếm từ các args bằng cách sử dụng hằng số đã có */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                /* Nếu người dùng không nhập bất cứ điều gì, không có gì để tìm kiếm */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                /* Phân tích cú pháp URL từ chuỗi được truyền qua và thực hiện tìm kiếm */
                try {
                    URL githubUrl = new URL(searchQueryUrlString);
                    String githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubUrl);
                    return githubSearchResults;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            // TODO (3) Override deliverResult và lưu trữ data mGithubJson
            // TODO (4) Gọi super.deliverResult sau khi lưu data
        };
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        /* Khi load xong, ẩn loading indicator */
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        /*
         * Nếu kết quả là null, chúng ta sẽ giả sử có lỗi xảy ra. Có rất nhiều phương pháp mạnh hơn
		 * để kiểm tra lỗi nhưng chúng tôi muốn làm ví dụ này đơn giản.
         */
        if (null == data) {
            showErrorMessage();
        } else {
            mSearchResultsTextView.setText(data);
            showJsonDataView();
        }
    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * Ta không sử dụng phương thức này trong ví dụ nhưng ta phải Override nó để triển khai
		 * giao diện LoaderCallbacks<String>
         */
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
    }
}