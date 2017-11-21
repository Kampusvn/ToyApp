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
package com.example.android.favoritetoys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView mToysListTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Sử dụng findViewById, chúng ta có được một tham chiếu đến TextView từ xml.
         * Việc này cho phép chúng ta thiết lập văn bản của TextView.
         */
        mToysListTextView = (TextView) findViewById(R.id.tv_toy_names);

        // Chuỗi này chứa tên các món đồ chơi
        String[] toyNames = ToyBox.getToyNames();

        // Lặp qua mảng và thêm chữ vào TextView. Ta thêm \n\n\n để tách biệt giữa mỗi String
        // trong TextView. Trong các bài sau ta sẽ học cách tốt hơn để làm việc này
        for (String toyName : toyNames) {
            mToysListTextView.append(toyName + "\n\n\n");
        }

    }
}