package com.example.android.orderlist.data;

import android.provider.BaseColumns;

public class OrderlistContract {

    public static final class OrderlistEntry implements BaseColumns {
        public static final String TABLE_NAME = "orderlist";
        public static final String COLUMN_GUEST_NAME = "guestName";
        public static final String COLUMN_PARTY_SIZE = "partySize";
        public static final String COLUMN_TIMESTAMP = "timestamp";
    }

}
