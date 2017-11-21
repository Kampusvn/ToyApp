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

    // TODO (1) Tạo biến thành phần kiểu EditText là mNewGuestNameEditText và mNewPartySizeEditText

    // TODO (13) Tạo constant string là LOG_TAG và đặt giá trị bằng class.getSimpleName()

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView orderlistRecyclerView;

        // Đặt thuộc tính local cho view tương ứng
        orderlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);

        // TODO (2) Đặt Edit text với view tương ứng bằng findViewById

        // Đặt layout cho RecyclerView, và vì đó là danh sách nên ta sẽ sử dụng linear layout
        orderlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Tạo một DB helper (sẽ dùng để tạo DB nếu chạy lần đầu tiên)
        OrderlistDbHelper dbHelper = new OrderlistDbHelper(this);

        // Giữ thể hiện của mDb cho đến khi bị pause hoặc kill. Lấy writable database
        // vì bạn sẽ thêm danh sách khách mời
        mDb = dbHelper.getWritableDatabase();

        // TODO (3) Bỏ lời gọi dữ liệu giả
        TestUtil.insertFakeData(mDb);

        // Lấy thông tin khách từ database và lưu vào cursor
        Cursor cursor = getAllGuests();

        // Tạo adapter cho cursor để hiển thị dữ liệu
        mAdapter = new GuestListAdapter(this, cursor);

        // Link adapter với RecyclerView
        orderlistRecyclerView.setAdapter(mAdapter);

    }

    /**
     * Phương thức được gọi khi người dùng bấm vào nút Add to orderlist
     *
     * @param view View được gọi (button)
     */
    public void addToOrderlist(View view) {

        // TODO (9) Kiểm tra xem có EditTexts nào trống hay không, nếu có thì trả về

        // TODO (10) Tạo biến integer lưu trữ số lượng và khởi tạo nó bằng 1

        // TODO (11) Dùng Integer.parseInt để ép kiểu mNewPartySizeEditText.getText thành integer

        // TODO (12) Bao Integer.parseInt bằng khối try catch và log ngoại lệ nếu có

        // TODO (14) gọi addNewGuest với tên khách và số lượng

        // TODO (19) gọi mAdapter.swapCursor để cập nhật cursor bằng cách chuyền vào getAllGuests()

        // TODO (20) Để giao diện đẹp hơn, gọi .getText().clear() ở các EditTexts, sau đó gọi clearFocus() ở mNewPartySizeEditText

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

    // TODO (4) Tạo phương thức addGuest

    // TODO (5) Bên trong, tạo instance của ContentValues để chuyền giá trị vào insert query

    // TODO (6) gọi put để chèn giá trị tên bằng key COLUMN_GUEST_NAME

    // TODO (7) gọi put để chèn giá trị số lượng với key COLUMN_PARTY_SIZE

    // TODO (8) gọi insert để chạy insert query trên TABLE_NAME với ContentValues đã tạo



}