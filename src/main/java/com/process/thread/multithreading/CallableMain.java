package com.process.thread.multithreading;


import com.process.thread.multithreading.call.MyCallable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by ALEX on 2018/7/31.
 */
public class CallableMain {
    public static void main(String[] args) {
        MyCallable myCallable1=new MyCallable("myCallable1");
        MyCallable myCallable2=new MyCallable("myCallable2");
        MyCallable myCallable3=new MyCallable("myCallable3");

        // FutureTask关系到分支线程的返回和异常，与主线程关联起来，.get方法等待线程的执行完毕，
        // 同时分支线程的异常也会被主线程捕获到
        FutureTask<String> futureTask1=new FutureTask<String>(myCallable1);
        FutureTask<String> futureTask2=new FutureTask<String>(myCallable2);
        FutureTask<String> futureTask3=new FutureTask<String>(myCallable3);

        // start和run的区别，start会创建一个新的线程执行run方法，run不会创建新的线程，
        // 只会在主线程执行run方法体，没有并发
        new Thread(futureTask1).start();
        new Thread(futureTask2).start();
        new Thread(futureTask3).start();

        try {
            System.out.println(futureTask1.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        try {
            System.out.println(futureTask2.get());
            System.out.println(futureTask3.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }
}
