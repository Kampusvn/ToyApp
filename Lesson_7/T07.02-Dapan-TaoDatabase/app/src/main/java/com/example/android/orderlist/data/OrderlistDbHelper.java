package com.example.android.orderlist.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.orderlist.data.OrderlistContract.*;

// Hoàn thành (1) extend từ lớp SQLiteOpenHelper
public class OrderlistDbHelper extends SQLiteOpenHelper {

    // Hoàn thành (2) Tạo static final String DATABASE_NAME và đặt giá trị "orderlist.db"
    // Tên db
    private static final String DATABASE_NAME = "orderlist.db";

    // Hoàn thành (3) Tạo static final int DATABASE_VERSION và đặt giá trị 1
    // Nếu bạn thay đổi lược đồ db, bạn phải tăng phiên bản db
    private static final int DATABASE_VERSION = 1;

    // Hoàn thành (4) Viết Constructor nhận tham số context và gọi đến constructor cha
    // Constructor
    public OrderlistDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Hoàn thành (5) Override phương thức onCreate
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Hoàn thành (6) Bên trong, tạo String truy vấn tên là SQL_CREATE_WAITLIST_TABLE để tạo bảng
        // Tạo bảng để lưu trữ dữ liệu orderlist
        final String SQL_CREATE_ORDERLIST_TABLE = "CREATE TABLE " + OrderlistEntry.TABLE_NAME + " (" +
                OrderlistEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                OrderlistEntry.COLUMN_GUEST_NAME + " TEXT NOT NULL, " +
                OrderlistEntry.COLUMN_PARTY_SIZE + " INTEGER NOT NULL, " +
                OrderlistEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        // Hoàn thành (7) Thực thi query bằng cách gọi execSQL trong sqLiteDatabase và chuyền vào string query SQL_CREATE_WAITLIST_TABLE
        sqLiteDatabase.execSQL(SQL_CREATE_ORDERLIST_TABLE);
    }

    // Hoàn thành (8) Override phương thức onUpgrade
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // Lúc này ta chỉ đơn giản drop bảng và tạo bảng mới. Nghĩa là nếu bạn thay đổi
        // DATABASE_VERSION thì bảng sẽ bị drop.
        // Trong ứng dụng bình thường, phương pháp này có thể được sửa đổi để
        // ALTER bảng thay vì xóa nó, để dữ liệu hiện có không bị xóa.
        // Hoàn thành (9) Bên trong, thực thi lệnh truy vấn drop table, và sau đó gọi onCreate để tạo lại nó
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + OrderlistEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}