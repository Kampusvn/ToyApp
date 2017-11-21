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
    private EditText mNewGuestNameEditText;
    private EditText mNewPartySizeEditText;
    private final static String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView orderlistRecyclerView;

        // Đặt thuộc tính local cho view tương ứng
        orderlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
        mNewGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
        mNewPartySizeEditText = (EditText) this.findViewById(R.id.party_count_edit_text);

        // Đặt layout cho RecyclerView, và vì đó là danh sách nên ta sẽ sử dụng linear layout
        orderlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Tạo một DB helper (sẽ dùng để tạo DB nếu chạy lần đầu tiên)
        OrderlistDbHelper dbHelper = new OrderlistDbHelper(this);

        // Giữ thể hiện của mDb cho đến khi bị pause hoặc kill. Lấy writable database
        // vì bạn sẽ thêm danh sách khách mời
        mDb = dbHelper.getWritableDatabase();

        // Lấy thông tin khách từ database và lưu vào cursor
        Cursor cursor = getAllGuests();

        // Tạo adapter cho cursor để hiển thị dữ liệu
        mAdapter = new GuestListAdapter(this, cursor);

        // Link adapter với RecyclerView
        orderlistRecyclerView.setAdapter(mAdapter);


        //TODO (3) Viết ItemTouchHelper với SimpleCallback quản lí input vuốt hai hướng trái phải

        // TODO (4) Override onMove and và trả về false

        // TODO (5) Override onSwiped

        // TODO (8) Bên trong, lấy tag itemView của viewHolder và lưu trữ trong biến id kiểu long
        // TODO (9) gọi removeGuest và chuyền vào id đó
        // TODO (10) gọi swapCursor trên mAdapter chuyền vào getAllGuests() như một đối số

        //TODO (11) gắn ItemTouchHelper với orderlistRecyclerView

    }

    /**
     * Phương thức được gọi khi người dùng bấm vào nút Add to orderlist
     *
     * @param view View được gọi (button)
     */
    public void addToOrderlist(View view) {
        if (mNewGuestNameEditText.getText().length() == 0 ||
                mNewPartySizeEditText.getText().length() == 0) {
            return;
        }
        //default party size to 1
        int partySize = 1;
        try {
            //mNewPartyCountEditText inputType="number"
            partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
        } catch (NumberFormatException ex) {
            Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
        }

        // Thêm thông tin khách vào mDb
        addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

        // Cập nhật cursor trong adapter để giao diện cập nhật danh sách mới
        mAdapter.swapCursor(getAllGuests());

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

    /**
     * Thêm khách mới vào mDb, bao gồm cả số người và timestamp hiện tại
     *
     * @param name  Tên khách
     * @param partySize Số người
     * @return id của bản ghi mới thêm
     */
    private long addNewGuest(String name, int partySize) {
        ContentValues cv = new ContentValues();
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME, name);
        cv.put(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE, partySize);
        return mDb.insert(OrderlistContract.OrderlistEntry.TABLE_NAME, null, cv);
    }


    // TODO (1) Viết phương thức removeGuest lấy tham số long id và trả về giá trị boolean

    // TODO (2) Bên trong phương thức đó, gọi mDb.delete để chuyền vào TABLE_NAME và điều kiện (OrderlistEntry._ID bằng id)


}