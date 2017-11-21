/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();

    // Hoàn thành (8) Thêm một biến private static int là viewHolderCount để giữ tổng số ViewHolders được tạo ra
    /*
     * Số lượng ViewHolders được tạo ra. Thông thường, bạn có thể tính nên có bao nhiêu ViewHolder bằng cách xác định
	 * có bao nhiêu item có thể có trên màn hình cùng một lúc và cộng thêm 2-4 vào con số đó. Đó không phải là công thức
	 * chính xác, nhưng sẽ cho bạn biết có bao nhiêu ViewHolders  được tạo ra để hiển thị bất kỳ RecyclerView nào.
	 *
	 * Đây là mô tả để bạn dễ hình dung:
     *
     *    ViewHolders trên màn hình:
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 0 |
     *        *-----------------------------*
     *        |         ViewHolder index: 1 |
     *        *-----------------------------*
     *        |         ViewHolder index: 2 |
     *        *-----------------------------*
     *        |         ViewHolder index: 3 |
     *        *-----------------------------*
     *        |         ViewHolder index: 4 |
     *        *-----------------------------*
     *        |         ViewHolder index: 5 |
     *        *-----------------------------*
     *        |         ViewHolder index: 6 |
     *        *-----------------------------*
     *        |         ViewHolder index: 7 |
     *        *-----------------------------*
     *
     *    ViewHolders cộng thêm (nằm ngoài màn hình)
     *
     *        *-----------------------------*
     *        |         ViewHolder index: 8 |
     *        *-----------------------------*
     *        |         ViewHolder index: 9 |
     *        *-----------------------------*
     *        |         ViewHolder index: 10|
     *        *-----------------------------*
     *        |         ViewHolder index: 11|
     *        *-----------------------------*
     *
     *    Tổng số ViewHolders = 11
     */
    private static int viewHolderCount;

    private int mNumberItems;

    /**
     * Constructor của GreenAdapter chấp nhận một số lượng item hiển thị
     */
    public GreenAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
        // Hoàn thành (9) Khi GreenAdapter mới được tạo ra, đặt lại viewHolderCount bằng 0
        viewHolderCount = 0;
    }

    /**
     * Được gọi khi có một ViewHolder mới được tạo ra. Điều này xảy ra khi RecyclerView được hiển thị.
     * Các ViewHolder sẽ được tạo ra để lấy đầy màn hình và cho phép di chuyển.
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        // Hoàn thành (12) Đặt text của viewHolderIndex là "ViewHolder index: " + viewHolderCount
        viewHolder.viewHolderIndex.setText("ViewHolder index: " + viewHolderCount);

        // Hoàn thành (13) Sử dụng ColorUtils.getViewHolderBackgroundColorFromInstance để chuyền vào một Context và viewHolderCount
        int backgroundColorForViewHolder = ColorUtils
                .getViewHolderBackgroundColorFromInstance(context, viewHolderCount);
        // Hoàn thành (14) Thiết lập màu nền của viewHolder.itemView bằng màu bên trên
        viewHolder.itemView.setBackgroundColor(backgroundColorForViewHolder);

        // Hoàn thành (15) Tăng viewHolderCount và đếm giá trị của nó gằng log
        viewHolderCount++;
        Log.d(TAG, "onCreateViewHolder: number of ViewHolders created: "
                + viewHolderCount);
        return viewHolder;
    }

    /**
     * OnBindViewHolder được gọi bởi RecyclerView để hiển thị dữ liệu ở vị trí được chỉ định.
     * Trong phương thức này, chúng ta cập nhật nội dung của ViewHolder để hiển thị các chỉ số
     * chính xác trong danh sách cho vị trí cụ thể này, sử dụng đối số "position".
     *
     * @param holder   ViewHolder phải được cập nhật để đại diện cho nội dung của mục tại vị trí
     *                 đã cho trong bộ dữ liệu.
     * @param position Vị trí của item trong bộ dữ liệu của adapter.
     */
    @Override
    public void onBindViewHolder(NumberViewHolder holder, int position) {
        Log.d(TAG, "#" + position);
        holder.bind(position);
    }

    /**
     * Phương thức này chỉ đơn giản trả về số lượng các mục để hiển thị. Nó được ngầm sử dụng
     * để giúp bố trí Views và cho các animation.
     * @return Số lượng item
     */
    @Override
    public int getItemCount() {
        return mNumberItems;
    }

    /**
     * Bộ nhớ tạm của View con trong list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {

        // Hiển thị vị trí trong danh sách, từ 0 đến getItemCount() - 1
        TextView listItemNumberView;
        // Hoàn thành (10) Thêm biến TextView để hiển thị chỉ số của ViewHolder
        TextView viewHolderIndex;

        /**
         * Constructor của ViewHolder. Trong constructor này, ta có tham chiếu đến TextViews
         * và thiết lập onClickListener để lắng nghe sự kiện khi man hình được nhấn. Chúng sẽ được
         * xử lý theo phương thức onClick dưới đây.
         * @param itemView View được inflated vào
         *                 {@link GreenAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NumberViewHolder(View itemView) {
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);

            // TODO (11) Dùng itemView.findViewById để lấy tham chiếu đến tv_view_holder_instance
            viewHolderIndex = (TextView) itemView.findViewById(R.id.tv_view_holder_instance);
        }

        /**
         * Một phương thức thuận tiện được thêm vào. Phương thức này sẽ lấy một số nguyên làm
         * đầu vào và sử dụng số nguyên đó để hiển thị văn bản thích hợp trong danh sách.
         * @param listIndex Vị trí của item trong danh sách
         */
        void bind(int listIndex) {
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}