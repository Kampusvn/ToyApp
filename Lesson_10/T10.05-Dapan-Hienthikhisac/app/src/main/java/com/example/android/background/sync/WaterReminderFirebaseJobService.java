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
package com.example.android.background.sync;

import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import com.firebase.jobdispatcher.RetryStrategy;

public class WaterReminderFirebaseJobService extends JobService {

    private AsyncTask mBackgroundTask;

    /**
     * Khởi đầu công việc. Việc triển khai cần phải được thực hiện ngay khi có thể.
     *
     * Code này được gọi bởi Job Dispatcher để cho biết ta nên bắt đầu công việc. Lưu ý rằng phương thức
     * này được chạy trên main thread của ứng dụng, vì vậy chúng ta cần phải chạy công việc trong background.
     */
    @Override
    public boolean onStartJob(final JobParameters jobParameters) {

        mBackgroundTask = new AsyncTask() {

            @Override
            protected Object doInBackground(Object[] params) {
                Context context = WaterReminderFirebaseJobService.this;
                ReminderTasks.executeTask(context, ReminderTasks.ACTION_CHARGING_REMINDER);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                /*
                 * Khi AsyncTask kết thúc, công việc đã hoàn tất. Để thông báo cho JobManager rằng công việc
				 * đã hoàn tất, bạn gọi jobFinished với jobParamters đã được truyền đến công việc của bạn và
				 * một boolean đại diện cho việc công việc cần phải được lên kế hoạch lại. Việc này xảy ra khi
				 * có sự cố và bạn muốn công việc được chạy lại
                 */

                jobFinished(jobParameters, false);
            }
        };

        mBackgroundTask.execute();
        return true;
    }

    /**
     * Được gọi khi công cụ lập lịch đã quyết định làm gián đoạn việc thực hiện một công việc đang chạy,
     * có thể bởi vì các runtime constraints gắn với công việc không còn nữa.
     *
     * @return khi công việc nên được thử lại
     * @see Job.Builder#setRetryStrategy(RetryStrategy)
     * @see RetryStrategy
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if (mBackgroundTask != null) mBackgroundTask.cancel(true);
        return true;
    }
}