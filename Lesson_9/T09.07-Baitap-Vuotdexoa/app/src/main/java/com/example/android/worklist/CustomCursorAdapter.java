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

import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.worklist.data.TaskContract;


/**
 * CustomCursorAdapter tạo và ràng buộc ViewHolders, giữ mô tả và mức độ ưu tiên
 * (priority) của một task, tới một RecyclerView để hiển thị dữ liệu một cách hiệu quả.
 */
public class CustomCursorAdapter extends RecyclerView.Adapter<CustomCursorAdapter.TaskViewHolder> {

    // Các biến của lớp cho Cursor chứa dữ liệu task và Context
    private Cursor mCursor;
    private Context mContext;


    /**
     * Constructor của CustomCursorAdapter để khởi tạo Context.
     *
     * @param mContext Context hiện tại
     */
    public CustomCursorAdapter(Context mContext) {
        this.mContext = mContext;
    }


    /**
     * Được gọi khi ViewHolders được tạo ra để lấp đầy RecyclerView.
     *
     * @return TaskViewHolder mới chứa view của các task
     */
    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // Inflate task_layout vào một view
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.task_layout, parent, false);

        return new TaskViewHolder(view);
    }


    /**
     * Được gọi bởi RecyclerView để hiển thị dữ liệu ở vị trí chỉ định trong con trỏ.
     *
     * @param holder ViewHolder
     * @param position TVị trí trong con trỏ
     */
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        // Chỉ mục cho các cột _id, mô tả và ưu tiên
        int idIndex = mCursor.getColumnIndex(TaskContract.TaskEntry._ID);
        int descriptionIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_DESCRIPTION);
        int priorityIndex = mCursor.getColumnIndex(TaskContract.TaskEntry.COLUMN_PRIORITY);

        mCursor.moveToPosition(position); // đến đúng vị trí trong con trỏ

        // Xác định các giá trị của dữ liệu muốn lấy
        final int id = mCursor.getInt(idIndex);
        String description = mCursor.getString(descriptionIndex);
        int priority = mCursor.getInt(priorityIndex);

        // Đặt giá trị
        holder.itemView.setTag(id);
        holder.taskDescriptionView.setText(description);

        // Đặt text và màu sắc cho priority TextView
        String priorityString = "" + priority; // chuyển int thành String
        holder.priorityView.setText(priorityString);

        GradientDrawable priorityCircle = (GradientDrawable) holder.priorityView.getBackground();
        // Lấy màu nền thích hợp dựa trên mức độ ưu tiên
        int priorityColor = getPriorityColor(priority);
        priorityCircle.setColor(priorityColor);

    }


    /*
    Phương thức trợ giúp để chọn màu sắc vòng tròn ưu tiên chính xác.
    P1 = đỏ, P2 = cam, P3 = vàng
    */
    private int getPriorityColor(int priority) {
        int priorityColor = 0;

        switch(priority) {
            case 1: priorityColor = ContextCompat.getColor(mContext, R.color.materialRed);
                break;
            case 2: priorityColor = ContextCompat.getColor(mContext, R.color.materialOrange);
                break;
            case 3: priorityColor = ContextCompat.getColor(mContext, R.color.materialYellow);
                break;
            default: break;
        }
        return priorityColor;
    }


    /**
     * Trả về số lượng item hiển thị
     */
    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }


    /**
     * Khi dữ liệu thay đổi và truy vấn lại xảy ra, chức năng này
     * hoán đổi Cursor cũ với Cursor (Cursor c) vừa được cập nhật.
     */
    public Cursor swapCursor(Cursor c) {
        // kiểm tra xem con trỏ này có giống như con trỏ trước đó (mCursor)
        if (mCursor == c) {
            return null; // vì không có gì thay đổi
        }
        Cursor temp = mCursor;
        this.mCursor = c; // giá trị con trỏ mới được gán

        // kiểm tra xem đây có phải là con trỏ hợp lệ không, sau đó cập nhật con trỏ
        if (c != null) {
            this.notifyDataSetChanged();
        }
        return temp;
    }


    // Inner class tạo ViewHolders
    class TaskViewHolder extends RecyclerView.ViewHolder {

        // Các biến lớp cho mô tả tác vụ và TextViews của độ ưu tiên
        TextView taskDescriptionView;
        TextView priorityView;

        /**
         * Constructor của TaskViewHolders.
         *
         * @param itemView View được inflated vào onCreateViewHolder
         */
        public TaskViewHolder(View itemView) {
            super(itemView);

            taskDescriptionView = (TextView) itemView.findViewById(R.id.taskDescription);
            priorityView = (TextView) itemView.findViewById(R.id.priorityTextView);
        }
    }
}