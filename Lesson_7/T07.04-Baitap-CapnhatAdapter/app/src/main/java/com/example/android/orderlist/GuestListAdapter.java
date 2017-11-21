package com.example.android.orderlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    // TODO (1) Thay mCount bằng một trường Cursor tên là mCursor
    private int mCount;

    /**
     * Hàm khởi tạo, sử dụng context và con trỏ db
     *
     * @param context context/activity được gọi
     */
    // TODO (2) Sửa constructor để chấp nhận một cursor làm tham chiếu thay vì integer
    public GuestListAdapter(Context context, int count) {
        this.mContext = context;
        // TODO (3) Đặt mCursor bằng cursor
        mCount = count;
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
        // TODO (5) Di chuyển cursor đến vị trí đã được chuyển, trả về nếu moveToPosition trả về false

        // TODO (6) Gọi getString trên cursor để lấy tên khách

        // TODO (7) Gọi getInt trên cursor để lấy số lượng khách

        // TODO (8) Đặt text của holder.nameTextView là tên khách

        // TODO (9) Đặt text của holder.partySizeTextView là số lượng khách
    }

    @Override
    public int getItemCount() {
        // TODO (4) Cập nhật getItemCount để trả về getCount của mCursor
        return mCount;
    }


    /**
     * Inner class giữ các view cần thiết để hiển thị một mục duy nhất trong recycler view
     */
    class GuestViewHolder extends RecyclerView.ViewHolder {

        // Hiển thị tên khách mời
        TextView nameTextView;
        // Hiển thị số lượng khách mời
        TextView partySizeTextView;

        /**
         * Constructor của ViewHolder. Trong constructor này, ta lấy reference của
         * TextViews
         *
         * @param itemView View được inflated
         *                 {@link GuestListAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public GuestViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            partySizeTextView = (TextView) itemView.findViewById(R.id.party_size_text_view);
        }

    }
}