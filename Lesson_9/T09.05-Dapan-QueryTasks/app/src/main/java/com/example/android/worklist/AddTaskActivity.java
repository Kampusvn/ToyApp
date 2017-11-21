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

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.android.worklist.data.TaskContract;


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
        // Sẽ thêm tiếp trong các bài sau
        // Kiểm tra xem EditText có rỗng hay không, nếu không, lấy input và lưu trữ trong đối tượng ContentValues
		// Nếu input từ EditText rỗng -> không tạo entry
        String input = ((EditText) findViewById(R.id.editTextTaskDescription)).getText().toString();
        if (input.length() == 0) {
            return;
        }

        // Insert dữ liệu task mới bằng ContentResolver
        // Tạo đối tượng ContentValues rỗng
        ContentValues contentValues = new ContentValues();
        // Đặt mô tả task và mPriority đã chọn vào ContentValues
        contentValues.put(TaskContract.TaskEntry.COLUMN_DESCRIPTION, input);
        contentValues.put(TaskContract.TaskEntry.COLUMN_PRIORITY, mPriority);
        // Chèn các giá trị content thông qua ContentResolver
        Uri uri = getContentResolver().insert(TaskContract.TaskEntry.CONTENT_URI, contentValues);

        // Hiển thị URI được trả lại về qua Toast
        // [Gợi ý] Gọi finish() để trả về MainActivity sau khi việc chèn hoàn tất
        if(uri != null) {
            Toast.makeText(getBaseContext(), uri.toString(), Toast.LENGTH_LONG).show();
        }

        // Kết thúc activity (trả về MainActivity)
        finish();

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
