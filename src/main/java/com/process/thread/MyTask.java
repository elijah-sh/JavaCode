package com.process.thread;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ShenShuaihu on 2018/8/7.
 * 任务
 */
public class MyTask implements Runnable {
    private  int taskNum;


    public  MyTask(int taskNum){
        this.taskNum = taskNum;
    }

    @Override
    public void  run() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 可以获取当前线程的引用   synchronized (this)
        {
            System.out.println("正在执行的任务：" + taskNum+  " 当前时间： "+ formatter.format(new Date()));

            try {
                Thread.currentThread().sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("任务：" + taskNum + " 执行完毕 "+  " 当前时间： "+ formatter.format(new Date()));
        }
    }
}
