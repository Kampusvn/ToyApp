package com.example.android.orderlist;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.orderlist.data.OrderlistContract;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    // Hoàn thành (1) Thay mCount bằng trường Cursor mới tên là mCursor
    private Cursor mCursor;
    private Context mContext;

    // Hoàn thành (2) Sửa constructor để nó nhận cursor thay vì integer
    /**
     * Hàm khởi tạo, sử dụng context và con trỏ db
     *
     * @param context context/activity được gọi
     * @param cursor cursor với dữ liệu orderlist để hiển thị
     */
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        // Hoàn thành (3) Đặt mCursor bằng cursor
        this.mCursor = cursor;
    }

    @Override
    public GuestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Lấy RecyclerView item layout
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.guest_list_item, parent, false);
        return new GuestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GuestViewHolder holder, int position) {
        // Hoàn thành (5) Di chuyển cursor đến vị trí đã được chuyển, trả về nếu moveToPosition trả về false
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;
        // Hoàn thành (6) Gọi getString trên cursor để lấy tên khách
        String name = mCursor.getString(mCursor.getColumnIndex(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME));
        // Hoàn thành (7) Gọi getInt trên cursor để lấy số lượng khách
        int partySize = mCursor.getInt(mCursor.getColumnIndex(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE));
        // Hoàn thành (8) Đặt text của holder.nameTextView là tên khách
        // Hiển thị tên khách
        holder.nameTextView.setText(name);
        // Hoàn thành (9) Đặt text của holder.partySizeTextView là số lượng khách
        // Hiển thị số lượng
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }

    @Override
    public int getItemCount() {
        // COMPLETED (4) Update the getItemCount to return the getCount of the cursor
        return mCursor.getCount();
    }

    /**
     * Inner class giữ các view cần để hiển thị một item trong recycler-view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Hiển thị tên khách
        TextView nameTextView;
        // Hiển thị số lượng
        TextView partySizeTextView;

        /**
         * Constructor của ViewHolder. Trong constructor này, ta lấy reference của TextViews
         *
         * @param itemView View được inflated vào
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}