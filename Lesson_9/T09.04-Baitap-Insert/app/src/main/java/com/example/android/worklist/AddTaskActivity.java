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

package com.example.android.worklist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;


public class AddTaskActivity extends AppCompatActivity {

    // Khai báo một biến thành viên để theo dõi mPriority của một task
    private int mPriority;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        // Khởi tạo mPriority cao nhất theo mặc định (mPriority = 1)
        ((RadioButton) findViewById(R.id.radButton1)).setChecked(true);
        mPriority = 1;
    }


    /**
     * onClickAddTask được gọi khi bấm nút "ADD".
     * Nó lấy dữ liệu đầu vào của người dùng nhập và chèn dữ liệu task
     * mới vào cơ sở dữ liệu nằm bên dưới.
     */
    public void onClickAddTask(View view) {
		// Thêm trong các bài sau//

        // TODO (6) Kiểm tra EditText có rỗng không, nếu không, truy xuất input và lưu trữ trong đối tượng ContentValues

        // TODO (7) Insert dữ liệu task mới thông qua ContentResolver

        // TODO (8) Hiển thị URI được trả về bằng Toast
        // [Gợi ý] Gọi finish() để trở lại MainActivity sau khi hoàn tất insert
        // Thêm trong các bài sau
    }


    /**
     * onPrioritySelected được gọi khi bấm các nút priority.
     * Nó sẽ thay đổi giá trị của mPriority dựa vào nút được chọn.
     */
    public void onPrioritySelected(View view) {
        if (((RadioButton) findViewById(R.id.radButton1)).isChecked()) {
            mPriority = 1;
        } else if (((RadioButton) findViewById(R.id.radButton2)).isChecked()) {
            mPriority = 2;
        } else if (((RadioButton) findViewById(R.id.radButton3)).isChecked()) {
            mPriority = 3;
        }
    }
}
