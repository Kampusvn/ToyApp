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

import android.net.Uri;
import android.provider.BaseColumns;


public class TaskContract {

    /* Thêm content provider constants vào Contract
     Client cần biết cách truy cập dữ liệu task và công việc của bạn là cung cấp
     các URI nội dung sau cho đường dẫn đến dữ liệu đó:
        1) Quyền content,
        2) Base content URI,
        3) Đường dẫn (path) đến thư mục tasks
        4) Content URI cho dữ liệu ở lớp TaskEntry
      */

    // Phân quyền, đó là cách để code của bạn biết nó sẽ truy cập vào content provider nào
    public static final String AUTHORITY = "com.example.android.worklist";

    // Base content URI = "content://" + <authority>
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);

    // Xác định đường dẫn để truy cập dữ liệu trong contract này
    // Đây là đường dẫn cho thư mục "tasks"
    public static final String PATH_TASKS = "tasks";

    /* TaskEntry là một lớp bên trong xác định nội dung của bảng tác vụ */
    public static final class TaskEntry implements BaseColumns {

        // TaskEntry content URI = base content URI + path
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_TASKS).build();

        // Bảng task và tên cột
        public static final String TABLE_NAME = "tasks";

        // Vì TaskEntry thực hiện giao diện "BaseColumns", nó có một
        // cột "_ID" được tạo tự động bên cạnh hai khai báo dưới đây
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PRIORITY = "priority";


        /*
        Cấu trúc bảng ở trên trông giống như bảng dưới đây. Với tên của
        bảng và cột trên đầu trang, và nội dung trong các hàng

        Note: Vì ở đây implement BaseColumn, nên cột _id tự động được sinh ra

        tasks
         - - - - - - - - - - - - - - - - - - - - - -
        | _id  |    description     |    priority   |
         - - - - - - - - - - - - - - - - - - - - - -
        |  1   |    Go to work      |       1       |
         - - - - - - - - - - - - - - - - - - - - - -
        |  2   |    Go shopping     |       3       |
         - - - - - - - - - - - - - - - - - - - - - -
        .
        .
        .
         - - - - - - - - - - - - - - - - - - - - - -
        | 43   |   Learn Android     |       2       |
         - - - - - - - - - - - - - - - - - - - - - -

         */

    }
}
