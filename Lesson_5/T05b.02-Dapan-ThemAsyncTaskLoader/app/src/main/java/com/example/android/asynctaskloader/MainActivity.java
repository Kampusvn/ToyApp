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

// Hoàn thành (1) Implement LoaderManager.LoaderCallbacks<String> trong MainActivity
public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<String> {

    /* Hằng số lưu trữ và khôi phục URL hiển thị */
    private static final String SEARCH_QUERY_URL_EXTRA = "query";

    // Hoàn thành (28) Xóa key lưu trữ kết quả tìm kiếm JSON

    // Hoàn thành (2) Tạo một hằng int để nhận dạng loader. Đặt tên nó là GITHUB_SEARCH_LOADER
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
            // Hoàn thành (26) Loại bỏ code lấy JSON

            mUrlDisplayTextView.setText(queryUrl);
            // Hoàn thành (25) Loại bỏ code hiển thị JSON
        }

        // Hoàn thành (24) Khởi tạo loader với GITHUB_SEARCH_LOADER làm ID, null cho bundle và this cho context
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

        // Hoàn thành (17) Nếu không có gì tìm kiếm, chỉ ra rằng không có gì để tìm kiếm và trả về
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

        // Hoàn thành (18) Loại bỏ lời gọi để thực hiện AsyncTask

        // Hoàn thành (19) Tạo một bundle gọi là queryBundle
        Bundle queryBundle = new Bundle();
        // Hoàn thành (20) Sử dụng putString với SEARCH_QUERY_URL_EXTRA làm key và giá trị String của URL làm giá trị
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
        // Hoàn thành (21) Gọi getSupportLoaderManager và lưu trữ nó trong một biến LoaderManager
        LoaderManager loaderManager = getSupportLoaderManager();
        // Hoàn thành (22) Tải Loader bằng cách gọi getLoader và chuyển ID đã chỉ định
        Loader<String> githubSearchLoader = loaderManager.getLoader(GITHUB_SEARCH_LOADER);
        // Hoàn thành (23) Nếu Loader null, khoeir tạo nó. Nếu không thì restart nó.
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
    // Hoàn thành (3) Override phương thức onCreateLoader
    @Override
    public Loader<String> onCreateLoader(int id, final Bundle args) {
        // Hoàn thành (4) Trả về AsyncTaskLoader<String> như một lớp ẩn danh bên trong với this là tham số của constructor
        return new AsyncTaskLoader<String>(this) {

            // Hoàn thành (5) Override onStartLoading
            @Override
            protected void onStartLoading() {

                // Hoàn thành (6) Nếu args null thì trả về.
                /* Nếu không có đối số nào được chuyền, không có truy vấn để thực hiện. Đơn giản chỉ cần trả về. */
                if (args == null) {
                    return;
                }

                // Hoàn thành (7) Hiển thị loading indicator
                /*
                 * Khi bắt đầu tải ở chế độ nền, hiển thị loading indicator
                 */
                mLoadingIndicator.setVisibility(View.VISIBLE);

                // Hoàn thành (8) Buộc tải
                forceLoad();
            }

            // Hoàn thành (9) Override loadInBackground
            @Override
            public String loadInBackground() {

                // Hoàn thành (10) Nhận String cho URL từ bundle được chuyển đến onCreateLoader
                /* Trích xuất truy vấn tìm kiếm từ các args bằng cách sử dụng hằng số đã có */
                String searchQueryUrlString = args.getString(SEARCH_QUERY_URL_EXTRA);

                // Hoàn thành (11) Nếu URL null hay trống, trả về null
                /* Nếu người dùng không nhập bất cứ điều gì, không có gì để tìm kiếm */
                if (searchQueryUrlString == null || TextUtils.isEmpty(searchQueryUrlString)) {
                    return null;
                }

                // Hoàn thành (12) Sao chép khối try / catch từ phương thức doInBackground của AsyncTask
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
        };
    }

    // Hoàn thành (13) Override onLoadFinished
    @Override
    public void onLoadFinished(Loader<String> loader, String data) {

        // Hoàn thành (14) Ẩn loading indicator
        /* Khi load xong, ẩn loading indicator */
        mLoadingIndicator.setVisibility(View.INVISIBLE);

        // Hoàn thành (15) Sử dụng cùng một cách như trong onPostExecute để hiển thị dữ liệu hoặc thông báo lỗi
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

    // Hoàn thành (16) Override onLoaderReset vì nó là một phần của giao diện mà ta thực hiện, nhưng không làm bất cứ điều gì trong phương thức này
    @Override
    public void onLoaderReset(Loader<String> loader) {
        /*
         * Ta không sử dụng phương thức này trong ví dụ nhưng ta phải Override nó để triển khai
		 * giao diện LoaderCallbacks<String>
         */
    }

    // Hoàn thành (29) Xóa lớp AsyncTask

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

        // Hoàn thành (27) Loại bỏ mã duy trì JSON
    }
}