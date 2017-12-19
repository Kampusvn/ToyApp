package com.kampus.example.droidtermsprovider;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Hợp đồng giữa DroidTermsExample provider và các ứng dụng khác.
 * Chứa các định nghĩa cho các URI và cột được hỗ trợ.
 */
public class DroidTermsExampleContract implements BaseColumns {

    /**
     * Chứng nhận cho phép nội dung cho DroidTermsExample provider.
     */
    public static final String CONTENT_AUTHORITY = "com.kampus.example.droidtermexample";

    /**
     * Đây là {@link Uri} trên tất cả các Uri DroidTermsExample khác được xây dựng.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    /**
     * Đường dẫn cho các thuật ngữ
     */
    public static final String PATH_TERMS = "terms";

    /**
     * Đây là {@link Uri} được sử dụng để có danh sách đầy đủ các thuật ngữ và định nghĩa.
     */
    public static final Uri CONTENT_URI =
            BASE_CONTENT_URI.buildUpon().appendPath(PATH_TERMS).build();


    /**
     * Đây là một kiểu String biểu thị Uri tham khảo một danh sách hoặc thư mục.
     */
    public static final String CONTENT_TYPE =
            ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;

    /**
     * Đây là một loại String biểu thị Uri tham chiếu một mục.
     */
    public static final String CONTENT_ITEM_TYPE =
            ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_TERMS;


    // Khai báo các hằng làm cho mã dễ đọc hơn.
    // Nó cũng trông giống như SQL.

    /**
     * Phiên bản cơ sở dữ liệu cho {@link android.database.sqlite.SQLiteOpenHelper}.
     */
    public static final int DATABASE_VERSION = 1;

    /**
     * Đây là tên của bảng SQL cho các thuật ngữ.
     */
    public static final String TERMS_TABLE = "term_entries";
    /**
     * Đây là tên cơ sở dữ liệu SQL cho các thuật ngữ.
     */
    public static final String DATABASE_NAME = "terms";

    /**
     * Tên cột trong SQLiteDatabase cho từ.
     */
    public static final String COLUMN_WORD = "word";
    /**
     * Tên cột trong SQLiteDatabase cho định nghĩa.
     */
    public static final String COLUMN_DEFINITION = "definition";

    /**
     * Mảng chứa tất cả các tiêu đề cột trong bảng thuật ngữ.
     */
    public static final String[] COLUMNS =
            {_ID, COLUMN_WORD, COLUMN_DEFINITION};

    /**
     * Đây là chỉ mục của ID trong bảng thuật ngữ
     */
    public static final int COLUMN_INDEX_ID = 0;
    /**
     * Đây là chỉ mục của từ trong bảng thuật ngữ
     */
    public static final int COLUMN_INDEX_WORD = 1;
    /**
     * Đây là chỉ mục của định nghĩa trong bảng thuật ngữ
     */
    public static final int COLUMN_INDEX_DEFINITION = 2;

    /**
     * Phương thức này tạo {@link Uri} cho một thuật ngữ, được tham chiếu bởi id.
     * @param id Các id của thuật ngữ.
     * @ Uri với id được nối.
     */
    public static Uri buildTermUriWithId(long id) {
        return ContentUris.withAppendedId(CONTENT_URI, id);
    }
}