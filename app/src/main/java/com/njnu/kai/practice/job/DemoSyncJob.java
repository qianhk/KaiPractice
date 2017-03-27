package com.njnu.kai.practice.job;

import android.support.annotation.NonNull;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobRequest;
import com.njnu.kai.support.LogUtils;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/27
 */
public class DemoSyncJob extends Job {

    public static final String TAG = "job_demo_tag";

    @Override
    @NonNull
    protected Result onRunJob(Params params) {
        LogUtils.d(TAG, "onRunJob tid=%d", Thread.currentThread().getId());
        return Result.SUCCESS;
    }

    public static int scheduleJob() {
        LogUtils.d(TAG, "scheduleJob tid=%d", Thread.currentThread().getId());
        return new JobRequest.Builder(DemoSyncJob.TAG)
                .setExecutionWindow(30_000L, 40_000L)
                .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
//                .setRequiresCharging(true)
//                .setRequiresDeviceIdle(true)
                .build()
                .schedule();
    }
}
