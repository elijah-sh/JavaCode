package com.process.thread.multithreading.call;

/**
 * 实现Runnable接口实现多线程
 *
 * @author xianglong.zhang@hand-china.com
 * @version 1.0
 * @date 2018/7/30 21:39
 */
public class MyRunnable implements Runnable {
    String name=null;
    public MyRunnable(String name){
        this.name=name;
    }

    @Override
    public synchronized void run() {
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"-"+this.name+"-"+i);
            try {
                if("myRunnable1".equals(this.name)){
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
