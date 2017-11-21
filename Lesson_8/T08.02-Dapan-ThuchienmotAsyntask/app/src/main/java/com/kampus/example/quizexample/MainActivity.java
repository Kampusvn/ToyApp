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

package com.kampus.example.quizexample;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.kampus.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Lấy dữ liệu từ các ContentProvider và hiển thị một series các flash cards.
 */

public class MainActivity extends AppCompatActivity {

    // Hoàn thành (3) Tạo một biến instance lưu trữ một Cursor và đặt tên là mData
    // Dữ liệu từ DroidTermsExample content provider
    private Cursor mData;

    // Trạng thái hiện tại của ứng dụng
    private int mCurrentState;

    private Button mButton;

    // Trạng thái này là từ definition được ẩn và nhấn vào nút sẽ hiển thị definition
    private final int STATE_HIDDEN = 0;

    // Trạng thái này là khi definition được hiển thị và nhấn vào nút sẽ đến từ tiếp theo
    private final int STATE_SHOWN = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Lấy các view
        mButton = (Button) findViewById(R.id.button_next);

        // Chạy database operation để lấy con trỏ ra khỏi luồng chính
        // Hoàn thành (5) Tạo và khởi chạy AsyncTask
        new WordFetchTask().execute();

    }

    /**
     * Được gọi từ layout khi nút được nhấn và chuyển đổi giữa hai trạng thái ứng dụng.
     * @param view View được nhấn
     */
    public void onButtonClick(View view) {

        // Hoặc hiển thị định nghĩa của từ hiện tại, hoặc nếu định nghĩa hiện đang hiển thị
        // thì chuyển sang từ tiếp theo.
        switch (mCurrentState) {
            case STATE_HIDDEN:
                showDefinition();
                break;
            case STATE_SHOWN:
                nextWord();
                break;
        }
    }

    public void nextWord() {

        // Đổi chữ trên nút
        mButton.setText(getString(R.string.show_definition));

        mCurrentState = STATE_HIDDEN;

    }

    public void showDefinition() {

        // Đổi chữ trên nút
        mButton.setText(getString(R.string.next_word));

        mCurrentState = STATE_SHOWN;

    }
    // Hoàn thành (1) Tạo AsyncTask với các generic type sau đây <Void, Void, Cursor>
    // Hoàn thành (2) Trong phương thức doInBackground, viết code để truy cập DroidTermsExample
    // provider và trả về đối tượng Cursor
    // Hoàn thành (4) Trong phương thức onPostExecute, lưu trữ đối tượng Cursor trong mData

    // Sử dụng AsyncTask để lấy dữ liệu từ luồng chính.
    public class WordFetchTask extends AsyncTask<Void, Void, Cursor> {

        // Được gọi trên luồng chạy ngầm
        @Override
        protected Cursor doInBackground(Void... params) {
            // Thực hiện truy vấn để lấy dữ liệu

            // Lấy trình xử lý nội dung (content resolver)
            ContentResolver resolver = getContentResolver();

            // Gọi phương thức truy vấn trên bộ resolver với Uri từ lớp contract
            Cursor cursor = resolver.query(DroidTermsExampleContract.CONTENT_URI,
                    null, null, null, null);
            return cursor;
        }


        // Gọi trên luồng UI
        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            // Thiết lập dữ liệu cho MainActivity
            mData = cursor;
        }
    }
}