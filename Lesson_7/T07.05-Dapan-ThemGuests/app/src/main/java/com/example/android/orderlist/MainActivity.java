package com.example.android.orderlist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.android.orderlist.data.OrderlistContract;
import com.example.android.orderlist.data.OrderlistDbHelper;


public class MainActivity extends AppCompatActivity {

    private GuestListAdapter mAdapter;
    private SQLiteDatabase mDb;
    // Hoàn thành (1) Tạo biến thành phần kiểu EditText là mNewGuestNameEditText và mNewPartySizeEditText
    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;

    // Hoàn thành (13) Tạo constant string là LOG_TAG và đặt giá trị bằng class.getSimpleName()
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView orderlistRecyclerView;

        // Đặt thuộc tính local cho view tương ứng
        orderlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        // Hoàn thành (2) Đặt Edit text với view tương ứng bằng findViewById
        mNewGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = (EditText) this.findViewById(R.id.party_count_edit_text);

        // Đặt layout cho RecyclerView, và vì đó là danh sách nên ta sẽ sử dụng linear layout
        orderlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Tạo một DB helper (sẽ dùng để tạo DB nếu chạy lần đầu tiên)
        OrderlistDbHelper dbHelper = new OrderlistDbHelper(this);

        // Giữ thể hiện của mDb cho đến khi bị pause hoặc kill. Lấy writable database
        // vì bạn sẽ thêm danh sách khách mời
        mDb = dbHelper.getWritableDatabase();

        // Hoàn thành (3) Bỏ lời gọi dữ liệu giả

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
        // Hoàn thành (9) Kiểm tra xem có EditTexts nào trống hay không, nếu có thì trả về
        if (mNewGuestNameEditText.getText().length() == 0 ||
                mNewPartySizeEditText.getText().length() == 0) {
            return;
        }
        // Hoàn thành (10) Tạo biến integer lưu trữ số lượng và khởi tạo nó bằng 1
        //default party size to 1
        int partySize = 1;
        // Hoàn thành (11) Dùng Integer.parseInt để ép kiểu mNewPartySizeEditText.getText thành integer
        try {
            //mNewPartyCountEditText inputType="number"
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            // Hoàn thành (12) Bao Integer.parseInt bằng khối try catch và log ngoại lệ nếu có
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        // Hoàn thành (14) gọi addNewGuest với tên khách và số lượng
        // Thêm thông tin khách vào mDb
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

        // Hoàn thành (19) gọi mAdapter.swapCursor để cập nhật cursor bằng cách chuyền vào getAllGuests()
        // Cập nhật cursor trong adapter để giao diện cập nhật danh sách mới
        mAdapter.swapCursor(getAllGuests());

        // Hoàn thành (20) Để giao diện đẹp hơn, gọi .getText().clear() ở các EditTexts, sau đó gọi clearFocus() ở mNewPartySizeEditText
        // Xóa sạch các trường text ở giao diện
        mNewPartySizeEditText.clearFocus();
        mNewGuestNameEditText.getText().clear();
        mNewPartySizeEditText.getText().clear();
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

    // Hoàn thành (4) Tạo phương thức addGuest
    /**
     * Thêm khách mới vào mDb, bao gồm cả số người và timestamp hiện tại
     *
     * @param name  Tên khách
     * @param partySize Số người
     * @return id của bản ghi mới thêm
     */
    private long addNewGuest(String name, int partySize) {
        // Hoàn thành (5) Bên trong, tạo instance của ContentValues để chuyền giá trị vào insert query
        ContentValues cv = new ContentValues();
        // Hoàn thành (6) gọi put để chèn giá trị tên bằng key COLUMN_GUEST_NAME
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, name);
        // Hoàn thành (7) gọi put để chèn giá trị số lượng với key COLUMN_PARTY_SIZE
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, partySize);
        // Hoàn thành (8) gọi insert để chạy insert query trên TABLE_NAME với ContentValues đã tạo
        return mDb.insert(OrderlistContract.OrderlistEntry.TABLE_NAME, null, cv);
    }


}