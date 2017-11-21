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

// Hoàn thành (4) Từ GreenAdapter, hãy extend RecyclerView.Adapter<NumberViewHolder>
public class GreenAdapter extends RecyclerView.Adapter<GreenAdapter.NumberViewHolder> {

    private static final String TAG = GreenAdapter.class.getSimpleName();

    // Hoàn thành (1) Thêm biến private int và đặt tên là mNumberItems
    private int mNumberItems;

    // Hoàn thành (2) Viết constructor cho GreenAdapter để chấp nhận một tham số kiểu int cho numberOfItems
    // Hoàn thành (3) Lưu trữ numberOfItems vào mNumberItems
    /**
     * Constructor của GreenAdapter chấp nhận một số lượng item hiển thị
     */
    public GreenAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
    }

    // Hoàn thành (5) Override phương thức onCreateViewHolder
    // Hoàn thành (6) Tạo và trả về NumberViewHolder trong phương thức này
    /**
     *
     * Phương thức này được gọi khi RecyclerView xuất hiện.
     * Một số lượng ViewHolders vừa đủ sẽ được tạo ra để lấp đầy màn hình và cho phép cuộn.
     *
     * @param viewGroup ViewGroup chứa ViewHolders.
     * @param viewType  Nếu RecyclerView có nhiều hơn một loại item (ở đây thì không), bạn có thể
     *                  sử dụng viewType này để cung cấp kiểu layout khác. Xem
     *                  {@link android.support.v7.widget.RecyclerView.Adapter#getItemViewType(int)}
     *                  để biết thêm chi tiết.
     * @return Một NumberViewHolder mới chứa View cho mỗi item
     */
    @Override
    public NumberViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.number_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    // Hoàn thành (7) Override onBindViewHolder
    // Hoàn thành (8) Trong onBindViewHolder, gọi holder.bind và chuyền vào vị trí
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

    // Hoàn thành (9) Override phương thức getItemCount và trả về số item hiển thị
    /**
     * Phương thức này chỉ đơn giản trả về số lượng các mục để hiển thị. Nó được ngầm sử dụng
     * để giúp bố trí Views và cho các animation.
     *
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

        // Will display the position in the list, ie 0 through getItemCount() - 1
        TextView listItemNumberView;

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
