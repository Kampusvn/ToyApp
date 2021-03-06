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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private static final int NUM_LIST_ITEMS = 100;

    /*
     * Tham chiếu đến RecyclerView và Adapter để đặt lại danh sách sang trạng thái tốt hơn
     * khi người dùng thiết lập lại menu item.
     */
    private GreenAdapter mAdapter;
    private RecyclerView mNumbersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * Ta lấy thể hiện của RecyclerView từ xml bằng findViewById. Điều này cho phép ta làm những
         * việc như thiết lập adapter của RecyclerView và ẩn hiện nó.
         */
        mNumbersList = (RecyclerView) findViewById(R.id.rv_numbers);

        /*
         * LinearLayoutManager chịu trách nhiệm đo đạc và định vị các item trong RecyclerView vào
         * danh sách tuyến tính. Điều này có nghĩa là nó có thể tạo ra một danh sách ngang hoặc dọc
         * tùy thuộc vào tham số mà bạn chuyền vào constructor LinearLayoutManager. Theo mặc định,
         * nếu bạn không thiết lập hướng, bạn sẽ có một danh sách theo chiều dọc.
         * Trong trường hợp này, chúng ta muốn có một danh sách theo chiều dọc, vì vậy ta không cần
         * thiết lập gì cả. Ta cũng có thể sử dụng các LayoutManagers có sẵn khác để hiển thị
         * dữ liệu theo các dạng lưới và nhiều dạng khác.
         * Hãy xem tài liệu dành cho nhà phát triển để biết thêm chi tiết.
         */
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mNumbersList.setLayoutManager(layoutManager);

        /*
         * Sử dụng cài đặt này để cải thiện hiệu suất nếu bạn biết rằng những thay đổi trong
         * nội dung không thay đổi kích thước bố cục con trong RecyclerView
         */
        mNumbersList.setHasFixedSize(true);

        /*
         * GreenAdapter chịu trách nhiệm hiển thị mỗi item trong danh sách.
         */
        mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
        mNumbersList.setAdapter(mAdapter);
    }

    // Hoàn thành (7) Override onCreateOptionsMenu
    // Hoàn thành (8) Sử dụng getMenuInflater().inflate để inflate menu
    // Hoàn thành (9) Trả về true để hiển thị menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // Hoàn thành (10) Override onOptionsItemSelected
    // Hoàn thành (11) Trong phương thức này, lấy ID từ MenuItem
    // Hoàn thành (12) Nếu ID bằng R.id.action_refresh, tạo mới và thiết lập adapter trong RecyclerView và trả về true
    // Hoàn thành (13) Với các ID khác, trả về super.onOptionsItemSelected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemId = item.getItemId();

        switch (itemId) {
            /*
             * Khi bấm vào reset, ta sẽ thiết lập lại tất cả để màu sắc hiển thị
             * như lúc đầu. Có nhiều cách để làm việc này, nhưng cách này là đơn giản nhất
			 * (ít nhất là tôi thấy như vậy =)) )
             */
            case R.id.action_refresh:
                mAdapter = new GreenAdapter(NUM_LIST_ITEMS);
                mNumbersList.setAdapter(mAdapter);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}