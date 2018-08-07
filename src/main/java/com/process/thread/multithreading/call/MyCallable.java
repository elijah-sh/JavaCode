package com.process.thread.multithreading.call;

import java.util.concurrent.Callable;

/**
 * Created by ALEX on 2018/7/31.
 */
public class MyCallable implements Callable<String> {
    String name=null;
    public MyCallable(String name){
        this.name=name;
    }

    @Override
    public synchronized String call() throws Exception {
        if("myCallable1".equals(this.name)){
            throw new Exception("testException");
        }
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"-"+name+"-"+i);
            if("myCallable3".equals(this.name)){
                Thread.sleep(100);
            }
        }
        return this.name;
    }
}
