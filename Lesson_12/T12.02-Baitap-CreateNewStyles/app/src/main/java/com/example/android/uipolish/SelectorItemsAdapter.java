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
package com.example.android.uipolish;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectorItemsAdapter extends RecyclerView.Adapter<SelectorItemsAdapter.ArticleViewHolder> {

    private static final String TAG = SelectorItemsAdapter.class.getSimpleName();

    /*
     * Trình xử lý chạm được định nghĩa để giúp activity giao tiếp với RecycleView dễ dàng
     */
    final private ListItemClickListener mOnClickListener;


    /**
     * Giao diện nhận các tin nhắn onClick.
     */
    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    /**
     * Constructor cho Adapter; bao gồm một list item click listener
     *
     * @param listener Listener
     */
    public SelectorItemsAdapter(ListItemClickListener listener) {
        mOnClickListener = listener;
    }

    /**
     *
     * Được gọi khi mỗi ViewHolder mới được tạo ra. Điều này xảy ra khi RecyclerView được đặt ra.
     * Các ViewHolders sẽ được tạo ra để lấp đầy màn hình và cho phép di chuyển.
     *
     * @param viewGroup ViewGroup chứa các ViewHolders.
     * @param viewType  Nếu RecycleView có nhiều loại item (ở đây thì không), bạn có thể sử dụng số
     *                  viewType này để biểu diễn layout khác.
     * @return ArticleViewHolder chứa View cho mỗi list item
     */
    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.selector_list_item;

        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);

        return viewHolder;
    }

    /**
     * OnBindViewHolder được gọi bởi RecyclerView để hiển thị dữ liệu ở vị trí nhất định.
     * Trong phương thức này, chúng ta cập nhật nội dung của ViewHolder để hiển thị các
     * chỉ số chính xác trong danh sách cho vị trí cụ thể này, sử dụng đối số "position"
     * được truyền vào.
     *
     * @param holder   ViewHolder cần được cập nhật để đại diện cho nội dung của mục ở vị
     *                trí nhất định trong tập dữ liệu.
     * @param position Vị trí của item trong bộ dữ liệu của adapter.
     */
    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        Log.d(TAG, "#" + position);

        // Đặt các giá trị nếu được đưa ra từ cơ sở dữ liệu - lúc này chúng là các placeholder
        // trong xml list_item, do đó việc này sẽ không được thực hiện
    }


    /**
     * Phương thức trả về 10 item để hiển thị. Tất cả đều xảy ra "bên ngoài màn hình", giúp bố trí
     * các View và animation.
     *
     * @return số item có trong dự báo
     */
    @Override
    public int getItemCount() {
        return 10;
    }


    /**
     * Lưu trữ cache các view con cho list item.
     */
    class ArticleViewHolder extends RecyclerView.ViewHolder
            implements OnClickListener {

        ImageView icon;
        TextView firstName;
        TextView lastName;


        public ArticleViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.personIcon);
            firstName = (TextView) itemView.findViewById(R.id.firstName);
            lastName = (TextView) itemView.findViewById(R.id.lastName);

            itemView.setOnClickListener(this);
        }


        /**
         * Được gọi khi người dùng bấm vào một view
         * @param v View được bấm
         */
        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
