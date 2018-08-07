package com.process.thread.multithreading;

import com.process.thread.multithreading.call.MyRunnable;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 使用线程池实现多线程
 *
 * @author xianglong.zhang@hand-china.com
 * @version 1.0
 * @date 2018/7/31 15:32
 */
public class ThreadPoolMain {
    public static void main(String[] args) {
        /**
         * 创建线程池
         * corePoolSize 核心线程数
         * maximumPoolSize 最大线程数
         * keepAliveTime 线程等待销毁时间
         * TimeUnit 线程等待销毁时间单位
         * Queue 等待队列
         */
        ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(5,10,200, TimeUnit.MILLISECONDS,new ArrayBlockingQueue<Runnable>(5));

        for(int i=0;i<15;i++){
            MyRunnable myRunnable=new MyRunnable(" myRunnable: "+i);
            threadPoolExecutor.execute(myRunnable);
            System.out.println("线程池中线程数目："+threadPoolExecutor.getPoolSize()+",队列中等待的任务数目："+threadPoolExecutor.getQueue().size()
                    +",已执行完成的任务数目："+threadPoolExecutor.getCompletedTaskCount());
        }
        threadPoolExecutor.shutdown();


        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("线程池中线程数目："+threadPoolExecutor.getPoolSize()+
                ",队列中等待的任务数目："+threadPoolExecutor.getQueue().size()
                +",已执行完成的任务数目："+threadPoolExecutor.getCompletedTaskCount());
    }
}
