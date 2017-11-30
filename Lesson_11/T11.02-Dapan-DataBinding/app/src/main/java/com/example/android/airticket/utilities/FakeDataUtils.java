package com.example.android.airticket.utilities;

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

import com.example.android.airticket.BoardingPassInfo;
import com.example.android.airticket.R;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Lớp này sinh dữ liệu giả để hiển thị trong ứng dụng
 */
public class FakeDataUtils {

    /**
     * Sinh dữ liệu vé để hiển thị lên màn hình.
     */
    public static BoardingPassInfo generateFakeBoardingPassInfo() {

        BoardingPassInfo bpi = new BoardingPassInfo();

        bpi.passengerName = "MR. NGHIA";
        bpi.flightCode = "KA 678";
        bpi.originCode = "HAN";
        bpi.destCode = "SGO";

        long now = System.currentTimeMillis();

        // Từ 0 đến <30 phút
        long randomMinutesUntilBoarding = (long) (Math.random() * 30);
        long totalBoardingMinutes = 40;
        long randomFlightLengthHours = (long) (Math.random() * 5 + 1);

        long boardingMillis = now + minutesToMillis(randomMinutesUntilBoarding);
        long departure = boardingMillis + minutesToMillis(totalBoardingMinutes);
        long arrival = departure + hoursToMillis(randomFlightLengthHours);

        bpi.boardingTime = new Timestamp(boardingMillis);
        bpi.departureTime = new Timestamp(departure);
        bpi.arrivalTime = new Timestamp(arrival);
        bpi.departureTerminal = "3A";
        bpi.departureGate = "33C";
        bpi.seatNumber = "1A";
        bpi.barCodeImageResource = R.drawable.art_plane;

        return bpi;
    }

    private static long minutesToMillis(long minutes) {
        return TimeUnit.MINUTES.toMillis(minutes);
    }

    private static long hoursToMillis(long hours) {
        return TimeUnit.HOURS.toMillis(hours);
    }
}