package com.process.thread.multithreading;


import com.process.thread.multithreading.call.MyThread;

/**
 * Created by ALEX on 2018/7/30.
 */
public class ThreadMain {

    public static void main(String[] args) {
        MyThread myThread1 =new MyThread("thread1");
        MyThread myThread2 =new MyThread("thread2");
        MyThread myThread3 =new MyThread("thread3");

        myThread1.start();
        myThread2.start();
        myThread3.start();

    }

}
