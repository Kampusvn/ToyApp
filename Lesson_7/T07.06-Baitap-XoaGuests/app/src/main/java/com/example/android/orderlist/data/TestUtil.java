package com.example.android.orderlist.data;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class TestUtil {

    public static void insertFakeData(SQLiteDatabase db){
        if(db == null){
            return;
        }
        //tạo danh sách khách mời giả
        List<ContentValues> list = new ArrayList<ContentValues>();

        ContentValues cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, "Viet");
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, 12);
        list.add(cv);

        cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, "Hieu");
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, 2);
        list.add(cv);

        cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, "Thu");
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, 99);
        list.add(cv);

        cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, "Trang");
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, 1);
        list.add(cv);

        cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, "Nghia");
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, 45);
        list.add(cv);

        //chèn tất cả các khách trong một transaction
        try
        {
            db.beginTransaction();
            //xóa sạch bảng
            db.delete (OrderlistContract.OrderlistEntry.TABLE_NAME,null,null);
            //duyệt qua danh sách và thêm từng người một
            for(ContentValues c:list){
                db.insert(OrderlistContract.OrderlistEntry.TABLE_NAME, null, c);
            }
            db.setTransactionSuccessful();
        }
        catch (SQLException e) {
        }
        finally
        {
            db.endTransaction();
        }

    }
}