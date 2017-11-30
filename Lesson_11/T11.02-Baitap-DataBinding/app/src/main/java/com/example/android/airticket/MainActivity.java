package com.example.android.airticket;

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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    //TODO (3) Tạo một thể hiện ràng buộc dữ liệu tên là mBinding có kiểu ActivityMainBinding

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // TODO (4) Đặt Content View bằng cách sử dụng DataBindingUtil tới layout activity_main

        // TODO (5) Load một đối tượng BoardingPassInfo với dữ liệu giả mạo bằng FakeDataUtils

        // TODO (9) Gọi displayBoardingPassInfo và truyền vào dữ liệu giả từ thể hiện BoardingInfo

    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {

        // TODO (6) Sử dụng mBinding để thiết lập text trong tất cả các textViews sử dụng dữ liệu trong info

        // TODO (7) Sử dụng một trình định dạng SimpleDateFormat để đặt định dạng giá trị trong time text view

        // TODO (8) Sử dụng phương pháp TimeUnit để định dạng tổng số phút chờ máy bay

    }
}