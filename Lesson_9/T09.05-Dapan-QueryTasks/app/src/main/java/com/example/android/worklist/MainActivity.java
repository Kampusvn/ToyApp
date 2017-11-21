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

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import com.example.android.worklist.data.TaskContract;

public class MainActivity extends AppCompatActivity implements
        LoaderManager.LoaderCallbacks<Cursor> {


    // Hằng số để ghi lại và đề cập đến một trình nạp duy nhất
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int TASK_LOADER_ID = 0;

    // Biến thành viên của adapter và RecyclerView
    private CustomCursorAdapter mAdapter;
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Đặt RecyclerView vào view tương ứng của nó
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewTasks);

        // Đặt RecyclerView là linear layout, để đo và định vị các mục trong
        // RecyclerView vào danh sách tuyến tính
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Khởi tạo adapter và gắn vào RecyclerView
        mAdapter = new CustomCursorAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

        /*
         Thêm touch helper vào RecyclerView để nhận diện hành động vuốt để xóa của người dùng.
         ItemTouchHelper cho phép thao tác cảm ứng (như vuốt và di chuyển) trên ViewHolder
         và sử dụng callbacks để báo khi người dùng thực hiện các hành động này.
         */
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            // Được gọi khi người dùng vuốt trái hoặc phải ở ViewHolder
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                // Thêm code vuốt để xóa tại đây
            }
        }).attachToRecyclerView(mRecyclerView);

        /*
         Đặt nút Floating Action Button (FAB) vào view tương ứng. Đính kèm một OnClickListener
         vào nó, để khi nó được click, một intent mới sẽ được tạo ra để khởi chạy AddTaskActivity.
         */
        FloatingActionButton fabButton = (FloatingActionButton) findViewById(R.id.fab);

        fabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo một intent mới để bắt đầu một AddTaskActivity
                Intent addTaskIntent = new Intent(MainActivity.this, AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });

        /*
         Đảm bảo loader được khởi tạo và hoạt động. Nếu loader không tồn tại thì một
         intent được tạo, nếu không thì loader cuối cùng được tạo ra sẽ được sử dụng lại.
         */
        getSupportLoaderManager().initLoader(TASK_LOADER_ID, null, this);
    }


    /**
     * Phương thức này được gọi sau khi activity bị tạm dừng hoặc khởi động lại.
     * Thường thì là sau khi dữ liệu mới đã được chèn thông qua một AddTaskActivity,
     * do đó, khởi động lại loader để truy vấn lại các dữ liệu cơ bản cho bất kỳ thay đổi nào.
     */
    @Override
    protected void onResume() {
        super.onResume();

        // truy vấn lại tất cả các task
        getSupportLoaderManager().restartLoader(TASK_LOADER_ID, null, this);
    }


    /**
     * Instantiates và trả về một AsyncTaskLoader mới với ID đã cho.
     * Loader này sẽ trả về dữ liệu task như một con trỏ hoặc null nếu xảy ra lỗi.
     *
     * Thực hiện callback yêu cầu để tải dữ liệu ở tất cả các giai đoạn tải.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int id, final Bundle loaderArgs) {

        return new AsyncTaskLoader<Cursor>(this) {

            // Initialize một con trỏ để lưu trữ các dữ liệu task
            Cursor mTaskData = null;

            // onStartLoading() được gọi khi một loader bắt đầu tải dữ liệu
            @Override
            protected void onStartLoading() {
                if (mTaskData != null) {
                    // Cung cấp bất kỳ dữ liệu đã load trước đó ngay lập tức
                    deliverResult(mTaskData);
                } else {
                    // Buộc load mới
                    forceLoad();
                }
            }

            // loadInBackground() thực hiện load dữ liệu không đồng bộ
            @Override
            public Cursor loadInBackground() {
                // Sẽ thêm để load dữ liệu

                // Hoàn thành (5) Truy vấn và tải tất cả dữ liệu task trong background; sắp xếp theo mức ưu tiên
				// [Gợi ý] Sử dụng khối try / catch để bắt lỗi trong khi tải dữ liệu

                try {
                    return getContentResolver().query(TaskContract.TaskEntry.CONTENT_URI,
                            null,
                            null,
                            null,
                            TaskContract.TaskEntry.COLUMN_PRIORITY);

                } catch (Exception e) {
                    Log.e(TAG, "Failed to asynchronously load data.");
                    e.printStackTrace();
                    return null;
                }
            }

            // deliverResult gửi kết quả của việc load, một con trỏ tới listener đã đăng ký
            public void deliverResult(Cursor data) {
                mTaskData = data;
                super.deliverResult(data);
            }
        };

    }


    /**
     * Gọi khi một loader đã tạo trước đây hoàn tất việc load.
     *
     * @param loader Loader đã load xong.
     * @param data Dữ liệu sinh ra bởi Loader.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // Cập nhật dữ liệu mà adapter sử dụng để tạo ViewHolders
        mAdapter.swapCursor(data);
    }


    /**
     * Được gọi khi loader đã tạo trước đây được reset, và do đó làm
     * dữ liệu của nó không khả dụng.
     * onLoaderReset loại bỏ bất kỳ tham chiếu nào mà activity này đã làm
     * với dữ liệu của loader.
     *
     * @param loader Loader được reset.
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
            mAdapter.swapCursor(null);
        }

}

