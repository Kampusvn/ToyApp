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

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * BoardingPassInfo là một đối tượng JAVA đơn giản chứa thông tin về vé máy bay. Thông thường trong
 * Java, tốt nhất là khai báo các biến thành viên private và cung cấp các getters, nhưng chúng ta
 * sẽ để public để dễ dàng sử dụng.
 */
public class BoardingPassInfo {

    public String passengerName;
    public String flightCode;
    public String originCode;
    public String destCode;

    public Timestamp boardingTime;
    public Timestamp departureTime;
    public Timestamp arrivalTime;

    public String departureTerminal;
    public String departureGate;
    public String seatNumber;

    public int barCodeImageResource;

    public long getMinutesUntilBoarding() {
        long millisUntilBoarding = boardingTime.getTime() - System.currentTimeMillis();
        return TimeUnit.MILLISECONDS.toMinutes(millisUntilBoarding);
    }
}