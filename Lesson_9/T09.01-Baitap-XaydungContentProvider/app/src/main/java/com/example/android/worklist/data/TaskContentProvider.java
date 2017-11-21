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
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

// TODO (1) Xác minh rằng TaskContentProvider mở rộng từ ContentProvider và thực hiện các phương pháp cần thiết
public class TaskContentProvider extends ContentProvider {


    /* onCreate() là nơi khởi tạo bất cứ thứ gì bạn cần để thiết lập nguồn dữ liệu cơ bản
     Trong trường hợp này ta đang làm việc với cơ sở dữ liệu SQLite,
     vì vậy bạn cần phải khởi tạo một DbHelper để có thể truy cập nó.
     */
    @Override
    public boolean onCreate() {
        // TODO (2) Hoàn thành onCreate() và khởi tạo một TaskDbhelper khi khởi động
        // [Gợi ý] Khai báo DbHelper là biến toàn cục

        return false;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {

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
