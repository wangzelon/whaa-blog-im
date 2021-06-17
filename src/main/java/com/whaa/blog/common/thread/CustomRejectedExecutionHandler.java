package com.whaa.blog.common.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 自定义拒绝策略
 * created by wangzelong 2021-06-17 17:12
 */
public class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
    private CustomRejectedExecutionHandler() {
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

    }
}
