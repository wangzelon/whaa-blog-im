package com.whaa.blog.common.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * created by wangzelong 2021-06-17 17:14
 */
public class ThreadPoolManager {
    private final ThreadPoolExecutor mExecutor;

    public static ExecutorService getInstance() {
        return new ThreadPoolManager().mExecutor;
    }

    private ThreadPoolManager() {
        final int cpu = Runtime.getRuntime().availableProcessors();
        final int corePoolSize = cpu + 1;
        final int maximumPoolSize = cpu * 2 + 1;
        final long keepAliveTime = 3L;
        final TimeUnit timeUnit = TimeUnit.SECONDS;
        final int maxQueueNum = 128;

        mExecutor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                timeUnit,
                new LinkedBlockingQueue<>(maxQueueNum),
                new CustomThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
    }
}
