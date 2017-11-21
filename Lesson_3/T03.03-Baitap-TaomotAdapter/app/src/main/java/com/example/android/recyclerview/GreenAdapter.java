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

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

// TODO (4) Từ GreenAdapter, hãy extend RecyclerView.Adapter<NumberViewHolder>
public class GreenAdapter {

    // TODO (1) Thêm biến private int và đặt tên là mNumberItems

    // TODO (2) Viết constructor cho GreenAdapter để chấp nhận một tham số kiểu int cho numberOfItems
    // TODO (3) Lưu trữ numberOfItems vào mNumberItems

    // TODO (5) Override phương thức onCreateViewHolder
    // TODO (6) Tạo và trả về NumberViewHolder trong phương thức này

    // TODO (7) Override onBindViewHolder
    // TODO (8) Trong onBindViewHolder, gọi holder.bind và chuyền vào vị trí

    // TODO (9) Override phương thức getItemCount và trả về số item hiển thị

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
