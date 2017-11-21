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

package com.example.android.worklist.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

// Xác minh rằng TaskContentProvider mở rộng từ ContentProvider và thực hiện các phương pháp cần thiết
public class TaskContentProvider extends ContentProvider {

    // Xác định hằng số nguyên cuối cùng cho thư mục các task và một item đơn lẻ.
	// Sử dụng 100, 200, 300, vv... là quy ước cho các thư mục, và các số nguyên liên quan (101, 102, ..)
	// là cho các mục trong thư mục đó.
    public static final int TASKS = 100;
    public static final int TASK_WITH_ID = 101;

    // Khai báo một biến static cho hàm matcher Uri mà bạn xây dựng
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    // Xác định một phương thức static buildUriMatcher kết hợp URI với đối tượng int của chúng
    /**
     Khởi tạo một đối tượng matcher mới mà chưa có bất kỳ kết hợp nào,
	 sau đó sử dụng .addURI (String authority, String path, int match) để thêm các match
     */
    public static UriMatcher buildUriMatcher() {

        // Khởi tạo UriMatcher mà chưa có match bằng cách truyền NO_MATCH tới constructor
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        /*
          All paths added to the UriMatcher have a corresponding int.
		  Tất cả các đường dẫn được thêm vào UriMatcher đều có một số nguyên (int) tương ứng.
		  Đối với mỗi loại uri bạn muốn truy cập, hãy thêm match tương ứng với addURI.
		  Hai lời gọi dưới đây là để thêm match cho thư mục task và một item đơn lẻ bằng ID.
         */
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS, TASKS);
        uriMatcher.addURI(TaskContract.AUTHORITY, TaskContract.PATH_TASKS + "/#", TASK_WITH_ID);

        return uriMatcher;
    }

    // Biến thành viên cho một TaskDbHelper được khởi tạo trong phương thức onCreate()
    private TaskDbHelper mTaskDbHelper;


    /* onCreate() là nơi khởi tạo bất cứ thứ gì bạn cần để thiết lập nguồn dữ liệu cơ bản
     Trong trường hợp này ta đang làm việc với cơ sở dữ liệu SQLite,
     vì vậy bạn cần phải khởi tạo một DbHelper để có thể truy cập nó.
     */
    @Override
    public boolean onCreate() {
        // Hoàn thành onCreate() và khởi tạo một TaskDbhelper khi khởi động
        // [Gợi ý] Khai báo DbHelper là biến toàn cục
        Context context = getContext();
        mTaskDbHelper = new TaskDbHelper(context);
        return true;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        // TODO (1) Truy cập task database (để ghi dữ liệu mới)

        // TODO (2) Viết code URI matching để match thư mục task

        // TODO (3) Insert giá trị mới vào database
        // TODO (4) Đặt giá trị cho returnUri và viết trường hợp mặc định cho URI không xác định

        // TODO (5) Thông báo cho resolver nếu uri đã được thay đổi và trả lại URI vừa được insert

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public String getType(@NonNull Uri uri) {

        throw new UnsupportedOperationException("Not yet implemented");
    }

}
