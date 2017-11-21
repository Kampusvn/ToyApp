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


        // Tạo một DB helper (sẽ dùng để tạo DB nếu chạy lần đầu tiên)
        OrderlistDbHelper dbHelper = new OrderlistDbHelper(this);

        // Giữ thể hiện của mDb cho đến khi bị pause hoặc kill. Lấy writable database
        // vì bạn sẽ thêm danh sách khách mời
        mDb = dbHelper.getWritableDatabase();

        // Điền vào DB bằng dữ liệu giả
        TestUtil.insertFakeData(mDb);

        // Lấy thông tin khách từ database và lưu vào cursor
        Cursor cursor = getAllGuests();

        // TODO (10) Chuyền tất cả cursor vào adapter thay vì chỉ chuyền count
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



    /**
     * Truy vấn mDb và lấy các khách mời từ bảng orderlist
     *
     * @return Cursor lưu trữ danh sách khách mời
     */
    private Cursor getAllGuests() {
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