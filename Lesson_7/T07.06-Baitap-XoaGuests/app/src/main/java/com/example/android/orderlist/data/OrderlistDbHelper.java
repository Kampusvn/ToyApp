package com.example.android.orderlist.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.orderlist.data.OrderlistContract.*;

public class OrderlistDbHelper extends SQLiteOpenHelper {

    // Tên db
    private static final String DATABASE_NAME = "orderlist.db";

    // Nếu bạn thay đổi lược đồ db, bạn phải tăng phiên bản db
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public OrderlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Tạo bảng để lưu trữ dữ liệu orderlist
        final String SQL_CREATE_ORDERLIST_TABLE = "CREATE TABLE " + OrderlistEntry.TABLE_NAME + " (" +
                OrderlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                OrderlistEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
                OrderlistEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL, " +
                OrderlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_ORDERLIST_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Lúc này ta chỉ đơn giản drop bảng và tạo bảng mới. Nghĩa là nếu bạn thay đổi
        // DATABASE_VERSION thì bảng sẽ bị drop.
        // Trong ứng dụng bình thường, phương pháp này có thể được sửa đổi để
        // ALTER bảng thay vì xóa nó, để dữ liệu hiện có không bị xóa.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrderlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}