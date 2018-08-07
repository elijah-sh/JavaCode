package com.process.thread;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public class ThreadPool {
    public static void main(String[] args) {
        ThreadPoolExecutor executor = new ThreadPoolExecutor(5, 10,
                500, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(5));
        for(int i=0;i<15;i++){
            MyTask myTask = new MyTask(i);
            executor.execute(myTask);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("线程池中线程数目："+executor.getPoolSize()+
                    "，队列中等待执行的任务数目："+ executor.getQueue().size()+
                    "，已执行玩别的任务数目："+executor.getCompletedTaskCount()+
                    "当前时间： "+  formatter.format(new Date())

            );

        }
        executor.shutdown();
    }



}
