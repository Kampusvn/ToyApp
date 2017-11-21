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

    private Cursor mCursor;
    private Context mContext;

    /**
     * Hàm khởi tạo, sử dụng context và con trỏ db
     *
     * @param context context/activity được gọi
     * @param cursor cursor với dữ liệu orderlist để hiển thị
     */
    public GuestListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
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
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return;

        // Cập nhật view holder với thông tin cần hiển thị
        String name = mCursor.getString(mCursor.getColumnIndex(OrderlistContract.OrderlistEntry.COLUMN_GUEST_NAME));
        int partySize = mCursor.getInt(mCursor.getColumnIndex(OrderlistContract.OrderlistEntry.COLUMN_PARTY_SIZE));

        // Hiển thị tên khách
        holder.nameTextView.setText(name);
        // Hiển thị số lượng
        holder.partySizeTextView.setText(String.valueOf(partySize));
    }


    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    // TODO (15) Tạo hàm swapCursor lấy cursor mới và trả về void

    // TODO (16) Bên trong, kiểm tra cursor nếu không bằng null, và kết thúc nếu đúng

    // TODO (17) Cập nhật mCursor bằng newCursor

    // TODO (18) Bên trong, kiểm tra newCursor nếu không bằng null, nếu đúng thì gọi this.notifyDataSetChanged()

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