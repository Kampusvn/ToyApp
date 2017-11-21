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
package com.example.android.datafrominternet.utilities;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Những tiện ích này sẽ được sử dụng để giao tiếp với mạng
 */
public class NetworkUtils {

    final static String GITHUB_BASE_URL =
            "https://api.github.com/search/repositories";

    final static String PARAM_QUERY = "q";

    /*
     * Trường (field) dùng để sắp xếp
     * Mặc định: Kết quả được sắp xếp theo sự so khớp tốt nhất nếu không có trường nào được chỉ định.
     */
    final static String PARAM_SORT = "sort";
    final static String sortBy = "stars";

    /**
     * Build URL dùng để truy vấn trên Github.
     *
     * @param githubSearchQuery Từ khóa dùng để truy vấn.
     * @return URL sử dụng để truy vấn Github
     */
    public static URL buildUrl(String githubSearchQuery) {
        Uri builtUri = Uri.parse(GITHUB_BASE_URL).buildUpon()
                .appendQueryParameter(PARAM_QUERY, githubSearchQuery)
                .appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Phương thức này trả về toàn bộ kết quả từ phản hồi HTTP.
     *
     * @param url URL để tìm nạp phản hồi HTTP.
     * @return Nội dung của phản hồi HTTP.
     * @throws IOException Liên quan đến mạng (network) và đọc luồng dữ liệu (stream)
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }
}