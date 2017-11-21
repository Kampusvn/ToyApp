package com.example.android.orderlist;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.orderlist.data.TestUtil;
import com.example.android.orderlist.data.OrderlistContract;
import com.example.android.orderlist.data.OrderlistDbHelper;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    // Hoàn thành (1) Tạo một trường local có kiểu dữ liệu là SQLiteDatabase và đặt tên là mDb
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView orderlistRecyclerView;

        // Đặt thuộc tính local cho view tương ứng
        orderlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // Đặt layout cho RecyclerView, và vì đó là danh sách nên ta sẽ sử dụng linear layout
        orderlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Hoàn thành (2) Tạo instance của OrderlistDbHelper, chuyền "this" vào constructor như là một context
        // Tạo một DB helper (sẽ dùng để tạo DB nếu chạy lần đầu tiên)
        OrderlistDbHelper dbHelper = new OrderlistDbHelper(this);

        // Hoàn thành (3) Lấy một writable database reference bằng cách sử dụng getWritableDatabase và lưu nó trong mDb
        // Giữ thể hiện của mDb cho đến khi bị pause hoặc kill. Lấy writable database
        // vì bạn sẽ thêm danh sách khách mời
        mDb = dbHelper.getWritableDatabase();

        // Hoàn thành (4) Gọi insertFakeData từ TestUtil và chuyền vào database reference mDb
        // Điền vào DB bằng dữ liệu giả
        TestUtil.insertFakeData(mDb);

        // Hoàn thành (7) Chạy getAllGuests và lưu kết quả vào biến Cursor
        Cursor cursor = getAllGuests();

        // Hoàn thành (12) Chuyền kết quả cursor count vào adapter
        // Tạo adapter cho cursor để hiển thị dữ liệu
        mAdapter = new GuestListAdapter(this, cursor.getCount());

        // Link adapter với RecyclerView
        orderlistRecyclerView.setAdapter(mAdapter);

    }

    /**
     * Phương thức được gọi khi người dùng bấm vào nút Add to orderlist
     *
     * @param view View được gọi (button)
     */
    public void addToOrderlist(View view) {

    }


    // Hoàn thành (5) Tạo một phương thức private là getAllGuests trả về một cursor (con trỏ)
    /**
     * Truy vấn mDb và lấy các khách mời từ bảng orderlist
     *
     * @return Cursor lưu trữ danh sách khách mời
     */
    private Cursor getAllGuests() {
        // Hoàn thành (6) Bên trong, gọi call query với mDb chuyền vào tên bảng và projection String [] order theo COLUMN_TIMESTAMP
        return mDb.query(
                OrderlistContract.OrderlistEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                OrderlistContract.OrderlistEntry.COLUMN_TIMESTAMP
        );
    }


}