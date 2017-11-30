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

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.android.airticket.databinding.ActivityMainBinding;
import com.example.android.airticket.utilities.FakeDataUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Hoàn thành (3) Tạo một thể hiện ràng buộc dữ liệu tên là mBinding có kiểu ActivityMainBinding
    ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Hoàn thành (4) Đặt Content View bằng cách sử dụng DataBindingUtil tới layout activity_main
        /*
         * DataBindUtil.setContentView thay thế lời gọi thông thường đến view setContent.
		 * DataBindingUtil cũng tạo ra ActivityMainBinding mà sẽ được sử dụng để hiển thị tất cả dữ liệu.
         */
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        // Hoàn thành (5) Load một đối tượng BoardingPassInfo với dữ liệu giả mạo bằng FakeDataUtils
        BoardingPassInfo fakeBoardingInfo = FakeDataUtils.generateFakeBoardingPassInfo();
        // Hoàn thành (9) Gọi displayBoardingPassInfo và truyền vào dữ liệu giả từ thể hiện BoardingInfo
        displayBoardingPassInfo(fakeBoardingInfo);
    }

    private void displayBoardingPassInfo(BoardingPassInfo info) {

        // Hoàn thành (6) Sử dụng mBinding để thiết lập text trong tất cả các textViews sử dụng dữ liệu trong info
        mBinding.textViewPassengerName.setText(info.passengerName);
        mBinding.textViewOriginAirport.setText(info.originCode);
        mBinding.textViewFlightCode.setText(info.flightCode);
        mBinding.textViewDestinationAirport.setText(info.destCode);

        // Hoàn thành (7) Sử dụng một trình định dạng SimpleDateFormat để đặt định dạng giá trị trong time text view
        SimpleDateFormat formatter = new SimpleDateFormat(getString(R.string.timeFormat), Locale.getDefault());
        String boardingTime = formatter.format(info.boardingTime);
        String departureTime = formatter.format(info.departureTime);
        String arrivalTime = formatter.format(info.arrivalTime);

        mBinding.textViewBoardingTime.setText(boardingTime);
        mBinding.textViewDepartureTime.setText(departureTime);
        mBinding.textViewArrivalTime.setText(arrivalTime);

        // Hoàn thành (8) Sử dụng phương pháp TimeUnit để định dạng tổng số phút chờ máy bay
        long totalMinutesUntilBoarding = info.getMinutesUntilBoarding();
        long hoursUntilBoarding = TimeUnit.MINUTES.toHours(totalMinutesUntilBoarding);
        long minutesLessHoursUntilBoarding =
                totalMinutesUntilBoarding - TimeUnit.HOURS.toMinutes(hoursUntilBoarding);

        String hoursAndMinutesUntilBoarding = getString(R.string.countDownFormat,
                hoursUntilBoarding,
                minutesLessHoursUntilBoarding);

        mBinding.textViewBoardingInCountdown.setText(hoursAndMinutesUntilBoarding);
        mBinding.textViewTerminal.setText(info.departureTerminal);
        mBinding.textViewGate.setText(info.departureGate);
        mBinding.textViewSeat.setText(info.seatNumber);
    }
}