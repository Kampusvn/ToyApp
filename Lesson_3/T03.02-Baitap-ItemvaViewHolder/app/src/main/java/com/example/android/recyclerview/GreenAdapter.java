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

    // TODO (1) Tạo layout number_list_item.xml trong res/layout/

    // Làm các bước 2 - 11 trong number_list_item.xml
    // TODO (2) Đặt layout gốc là FrameLayout
    // TODO (3) Width của layout sẽ là match_parent và height là wrap_content
    // TODO (4) Đặt padding 16dp
    // TODO (5) Thêm một TextView là con của FrameLayout
    // TODO (6) Gán ID cho TextView là "@+id/tv_item_number"
    // TODO (7) Đặt height và width là wrap_content cho TextView
    // TODO (8) Đặt TextView về vị trí gốc của view cha
    // TODO (9) Căn giữa TextView theo chiều dọc
    // TODO (10) Đặt font là monospace
    // TODO (11) Đặt text size bằng 42sp

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

    // TODO (12) Viết lớp NumberViewHolder extends RecyclerView.ViewHolder

    // TODO (13) Trong NumberViewHolder, tạo biến TextView tên là listItemNumberView

    // TODO (14) Viết constructor cho NumberViewHolder để chấp nhận một View gọi là itemView làm tham số
    // TODO (15) Trong constructor, gọi super(itemView) và tìm listItemNumberView theo ID

    // TODO (16) Trong lớp NumberViewHolder, tạo một phương thức void tên là bind, và nhận một tham số int tên là listIndex
    // TODO (17) Trong phương thức bind, đặt text của listItemNumberView là listIndex
    // TODO (18) Hãy cẩn thận khi lấy đại diện String của listIndex, vì ta sẽ dùng setText với một số nguyên

    }
}
