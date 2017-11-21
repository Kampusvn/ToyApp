package com.example.android.orderlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class GuestListAdapter extends RecyclerView.Adapter<GuestListAdapter.GuestViewHolder> {

    private Context mContext;
    // TODO (8) Thêm biến local là mCount để lưu trữ số lượng item hiển thị trên recycler view

    /**
     * Hàm khởi tạo, sử dụng context và con trỏ db
     *
     * @param context context/activity được gọi
     */
    // TODO (9) Update Adapter constructor để nó nhận một tham số nguyên
    public GuestListAdapter(Context context) {
        this.mContext = context;
        // TODO (10) Đặt mCount = count
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

    }


    // TODO (11) Sửa getItemCount để nó trả về giá trị của mCount thay vì 0
    @Override
    public int getItemCount() {
        return 0;
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