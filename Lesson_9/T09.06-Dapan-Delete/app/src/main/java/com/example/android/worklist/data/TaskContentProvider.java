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
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;

import static com.example.android.worklist.data.TaskContract.TaskEntry.TABLE_NAME;

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
        // Truy cập task database (để ghi dữ liệu mới)
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        // Viết code URI matching để match thư mục task
        int match = sUriMatcher.match(uri);
        Uri returnUri; // URI trả về

        switch (match) {
            case TASKS:
                // Insert giá trị mới vào database
                // Thêm giá trị vào bảng task
                long id = db.insert(TABLE_NAME, null, values);
                if ( id > 0 ) {
                    returnUri = ContentUris.withAppendedId(TaskContract.TaskEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            // Đặt giá trị cho returnUri và viết trường hợp mặc định cho URI không xác định
            // Trường hợp default ném ra ngoại lệ UnsupportedOperationException
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Thông báo cho resolver nếu uri đã được thay đổi và trả lại URI vừa được insert
        getContext().getContentResolver().notifyChange(uri, null);

        // Trả về uri đã xây dựng (trỏ vào hàng mới chèn dữ liệu)
        return returnUri;
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Truy cập vào cơ sở dữ liệu cơ bản (chế độ chỉ đọc cho truy vấn)
        final SQLiteDatabase db = mTaskDbHelper.getReadableDatabase();

        // Viết code match URI và đặt biến để trả về một con trỏ
        int match = sUriMatcher.match(uri);
        Cursor retCursor;

        // Truy vấn thư mục task và viết cho trường hợp mặc định
        switch (match) {
            // Truy vấn đến thư mục tasks
            case TASKS:
                retCursor =  db.query(TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            // Ngoại lệ mặc định
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Đặt URI thông báo trên con trỏ và trả về con trỏ đó
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        // Trả về con trỏ
        return retCursor;
    }


    // Thêm hàm xóa để xóa một hàng dữ liệu
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        // Hoàn thành (1) Truy cập vào database và viết code cho URI matching để nhận diện một item đơn lẻ
        final SQLiteDatabase db = mTaskDbHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);
        // Theo dõi số lượng task đã xóa
        int tasksDeleted; // bắt đầu bằng 0

        // Hoàn thành (2) Viết code để xóa một hàng dữ liệu
        // [Gợi ý] dùng selection để xóa item dựa vào ID của hàng
        switch (match) {
            // Xử lý trường hợp item duy nhất, được nhận dạng bởi ID có trong đường dẫn URI
            case TASK_WITH_ID:
                // Lấy task ID từ URI path
                String id = uri.getPathSegments().get(1);
                // Dùng selections/selectionArgs để lọc ID
                tasksDeleted = db.delete(TABLE_NAME, "_id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Hoàn thành (3) Thông báo thay đổi cho resolver và trả lại số lượng item đã bị xóa
        if (tasksDeleted != 0) {
            // Thông báo về việc xóa một task
            getContext().getContentResolver().notifyChange(uri, null);
        }

        // Trả về số lượng task đã xóa
        return tasksDeleted;
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