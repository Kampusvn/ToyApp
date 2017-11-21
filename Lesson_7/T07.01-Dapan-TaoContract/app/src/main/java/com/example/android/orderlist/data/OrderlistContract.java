package com.example.android.orderlist.data;

import android.provider.BaseColumns;

public class OrderlistContract {

    // Hoàn thành (1) Tạo inner class có tên OrderlistEntry và implement BaseColumns interface
    public static final class OrderlistEntry implements BaseColumns {
        // Hoàn thành (2) Ở bên trong, tạo các biến thành viên kiểu static final string cho table name và mỗi cột cho db
        public static final String TABLE_NAME = "orderlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}