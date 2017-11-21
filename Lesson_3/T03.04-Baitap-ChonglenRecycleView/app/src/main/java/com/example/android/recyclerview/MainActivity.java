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
package com.example.android.recyclerview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // TODO (1) Khai báo biến private static final int tên là NUM_LIST_ITEMS và gán giá trị 100

    // TODO (2) Tạo biến GreenAdapter là mAdapter
    // TODO (3) Tạo biến RecyclerView là mNumbersList

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO (4) Sử dụng findViewById để lưu trữ một tham chiếu đến RecyclerView trong mNumbersList

        // TODO (5) Tạo một biến LinearLayoutManager là layoutManager
        // TODO (6) Sử dụng setLayoutManager trong mNumbersList với LinearLayoutManager ở trên

        // TODO (7) Sử dụng setHasFixedSize(true) để chỉ ra rằng nội dung của RecyclerView sẽ không thay đổi kích thước của item

        // TODO (8) Lưu trữ một GreenAdapter mới trong Adapter và chuyền vào NUM_LIST_ITEMS

        // TODO (9) Đặt GreenAdapter vừa tạo ra vào mNumbersList
    }
}
