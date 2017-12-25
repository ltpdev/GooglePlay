package com.gdcp.asus_.googleplay.manager;


import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by asus- on 2017/12/24.
 */

public class ThreadManager {
    private static ThreadPool threadPool;

    public static ThreadPool getThreadPool() {
        if (threadPool == null) {
            synchronized (ThreadManager.class) {
                if (threadPool == null) {
                    threadPool = new ThreadPool(10,10,1L);
                }
            }
        }
        return threadPool;
    }

    //线程池
    public static class ThreadPool {
        //核心线程数
        private int corePoolSize;
        private int maxNumPoolSize;
        private long keepAliveTime;//休息时间
        private ThreadPoolExecutor threadPoolExecutor;

        private ThreadPool(int corePoolSize, int maxNumPoolSize, long keepAliveTime) {
            this.corePoolSize = corePoolSize;
            this.maxNumPoolSize = maxNumPoolSize;
            this.keepAliveTime = keepAliveTime;
        }
//执行任务
        public void execute(Runnable runnable) {
            if (threadPoolExecutor == null) {
                threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maxNumPoolSize, keepAliveTime,
                        TimeUnit.SECONDS, new LinkedBlockingDeque<Runnable>(), Executors.defaultThreadFactory(),
                        new ThreadPoolExecutor.AbortPolicy());
            }
            threadPoolExecutor.execute(runnable);
        }
        //取消任务
        public void cancle(Runnable runnable) {
               if (threadPoolExecutor!=null){
                   //从线程队列中移除下载任务
                   threadPoolExecutor.getQueue().remove(runnable);
               }
        }
    }
}
