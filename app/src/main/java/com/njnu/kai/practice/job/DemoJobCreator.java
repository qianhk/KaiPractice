package com.njnu.kai.practice.job;

import com.evernote.android.job.Job;
import com.evernote.android.job.JobCreator;

/**
 * @author hongkai.qian
 * @version 1.0.0
 * @since 17/3/27
 */
public class DemoJobCreator implements JobCreator {

    @Override
    public Job create(String tag) {
        switch (tag) {
            case DemoSyncJob.TAG:
                return new DemoSyncJob();
            default:
                return null;
        }
    }
}
