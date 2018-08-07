package com.process.thread.multithreading;

import com.process.thread.multithreading.call.MyRunnable;
/**
 * Created by ALEX on 2018/7/30.
 */
public class RunnableMain {

    public static void main(String[] args) {
        MyRunnable myRunnable1=new MyRunnable("myRunnable1");
        MyRunnable myRunnable2=new MyRunnable("myRunnable2");
        MyRunnable myRunnable3=new MyRunnable("myRunnable3");

        new Thread(myRunnable1).start();
        new Thread(myRunnable2).start();
        new Thread(myRunnable3).start();
    }
}
