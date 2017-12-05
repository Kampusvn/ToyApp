package com.example.android.uipolish;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


public class SelectorsActivity extends AppCompatActivity
        implements SelectorItemsAdapter.ListItemClickListener{

    private static final String TAG = SelectorsActivity.class.getSimpleName();

    // recycler view và adapter
    RecyclerView mRecyclerView;
    SelectorItemsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectors_activity);

        // Tham chiếu đến recycler view với lời gọi findViewById
        mRecyclerView = (RecyclerView) findViewById(R.id.mainRecyclerView);

        // Trình quản lý linear layout sẽ đặt list item trong danh sách dọc
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        // Adapter chịu trách nhiệm hiển thị mỗi item trong danh sách
        mAdapter = new SelectorItemsAdapter(this);
        mRecyclerView.setAdapter(mAdapter);

    }

    /**
     * Nơi nhận callback từ clicklistener trong adapter. Sẽ được gọi khi bấm vào item trong danh sách
     *
     * @param clickedItemIndex Chỉ số của item
     */
    @Override
    public void onListItemClick(int clickedItemIndex) {

        // Xử lý khi bấm vào một item
        // Trong trường hợp này, chỉ hiển thị chỉ số của item được bấm trong log

        Log.v(TAG, "List item clicked at index: " + clickedItemIndex);

    }

}