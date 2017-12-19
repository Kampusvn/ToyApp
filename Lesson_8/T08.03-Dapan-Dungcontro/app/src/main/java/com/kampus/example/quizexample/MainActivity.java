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
import android.widget.TextView;

import com.kampus.example.droidtermsprovider.DroidTermsExampleContract;

/**
 * Lấy dữ liệu từ các ContentProvider và hiển thị một series các flash cards.
 */

public class MainActivity extends AppCompatActivity {

    // Dữ liệu từ DroidTermsExample content provider
    private Cursor mData;

    // Trạng thái hiện tại của ứng dụng
    private int mCurrentState;

    // Chỉ số của các định nghĩa và các cột từ trong con trỏ
    private int mDefCol, mWordCol;

    private TextView mWordTextView, mDefinitionTextView;
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
        // Hoàn thành (1) Bạn chắc chắn sẽ cần nhiều hơn là chỉ một nút
        mWordTextView = (TextView) findViewById(R.id.text_view_word);
        mDefinitionTextView = (TextView) findViewById(R.id.text_view_definition);
        mButton = (Button) findViewById(R.id.button_next);

        // Chạy database operation để lấy con trỏ ra khỏi luồng chính
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
        // Hoàn thành (3) Tìm đến từ tiếp theo trong con trỏ, hiển thị từ tiếp theo và ẩn định nghĩa
        // Chú ý là không không thực hiện việc này nếu như con trỏ chưa được thiết lập.
        // Nếu đã đi hết danh sách từ, hãy quay trở lại từ đầu.

        if (mData != null) {
            // Đến vị trí tiếp theo trong con trỏ, nếu đã ở vị trí cuối thì trở về vị trí đầu
            if (!mData.moveToNext()) {
                mData.moveToFirst();
            }
            // Ẩn TextView định nghĩa
            mDefinitionTextView.setVisibility(View.INVISIBLE);

            // Đổi chữ trên nút
            mButton.setText(getString(R.string.show_definition));

            // Lấy chữ tiếp theo
            mWordTextView.setText(mData.getString(mWordCol));
            mDefinitionTextView.setText(mData.getString(mDefCol));

            mCurrentState = STATE_HIDDEN;
        }
    }

    public void showDefinition() {
        // Hoàn thành (4) Hiển thị định nghĩa
        if (mData != null) {
            // Hiển thị TextView định nghĩa
            mDefinitionTextView.setVisibility(View.VISIBLE);

            // Đổi chữ trên nút
            mButton.setText(getString(R.string.next_word));

            mCurrentState = STATE_SHOWN;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Hoàn thành (5) Đóng con trỏ
        mData.close();
    }

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
            // Hoàn thành (2) Khởi tạo bất cứ thứ gì cần đến con trỏ, chẳng hạn như thiết lập màn hình
            // với từ đầu tiên và thiết lập bất kỳ biến thực thể nào khác

            // Thiết lập một loạt các biến thực thể dựa trên dữ liệu

            // Thiết lập dữ liệu cho MainActivity
            mData = cursor;
            // Lấy số của cột, trong con trỏ, của mỗi mảnh dữ liệu
            mDefCol = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_DEFINITION);
            mWordCol = mData.getColumnIndex(DroidTermsExampleContract.COLUMN_WORD);

            // Đặt trạng thái ban đầu
            nextWord();
        }
    }

}