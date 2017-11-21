package com.example.android.orderlist;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;

    // TODO (1) Tạo một trường local có kiểu dữ liệu là SQLiteDatabase và đặt tên là mDb

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView orderlistRecyclerView;

        // Đặt thuộc tính local cho view tương ứng
        orderlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // Đặt layout cho RecyclerView, và vì đó là danh sách nên ta sẽ sử dụng linear layout
        orderlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Tạo adapter cho con trỏ để hiển thị dữ liệu
        mAdapter = new GuestListAdapter(this);

        // TODO (2) Tạo instance của OrderlistDbHelper, chuyền "this" vào constructor như là một context

        // TODO (3) Lấy một writable database reference bằng cách sử dụng getWritableDatabase và lưu nó trong mDb

        // TODO (4) Gọi insertFakeData từ TestUtil và chuyền vào database reference mDb

        // TODO (7) Chạy getAllGuests và lưu kết quả vào biến Cursor

        // TODO (12) Chuyền kết quả cursor count vào adapter

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

    // TODO (5) Tạo một phương thức private là getAllGuests trả về một cursor (con trỏ)

    // TODO (6) Bên trong, gọi call query với mDb chuyền vào tên bảng và projection String [] order theo COLUMN_TIMESTAMP

}