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

    private int mNumberItems;

    /**
     * Constructor của GreenAdapter chấp nhận một số lượng item hiển thị
     */
    public GreenAdapter(int numberOfItems) {
        mNumberItems = numberOfItems;
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

    // Hoàn thành (12) Viết lớp NumberViewHolder extends RecyclerView.ViewHolder
    /**
     * Bộ nhớ tạm của View con trong list item.
     */
    class NumberViewHolder extends RecyclerView.ViewHolder {

        // Hoàn thành (13) Trong NumberViewHolder, tạo biến TextView tên là listItemNumberView
        // Hiển thị vị trí trong danh sách, từ 0 đến getItemCount() - 1
        TextView listItemNumberView;

        // Hoàn thành (14) Viết constructor cho NumberViewHolder để chấp nhận một View gọi là itemView làm tham số
        /**
         * Constructor của ViewHolder. Trong constructor này, ta có tham chiếu đến TextViews
         * và thiết lập onClickListener để lắng nghe sự kiện khi man hình được nhấn. Chúng sẽ được
         * xử lý theo phương thức onClick dưới đây.
         * @param itemView View được inflated vào
         *                 {@link GreenAdapter#onCreateViewHolder(ViewGroup, int)}
         */
        public NumberViewHolder(View itemView) {
            // Hoàn thành (15) Trong constructor, gọi super(itemView) và tìm listItemNumberView theo ID
            super(itemView);

            listItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_number);
        }

        // Hoàn thành (16) Trong lớp NumberViewHolder, tạo một phương thức void tên là bind, và nhận một tham số int tên là listIndex
        /**
         * Một phương thức thuận tiện được thêm vào. Phương thức này sẽ lấy một số nguyên làm
         * đầu vào và sử dụng số nguyên đó để hiển thị văn bản thích hợp trong danh sách.
         * @param listIndex Vị trí của item trong danh sách
         */
        void bind(int listIndex) {
            // Hoàn thành (17) Trong phương thức bind, đặt text của listItemNumberView là listIndex
            // Hoàn thành (18) Hãy cẩn thận khi lấy đại diện String của listIndex, vì ta sẽ dùng setText với một số nguyên
            listItemNumberView.setText(String.valueOf(listIndex));
        }
    }
}
