package com.process.thread.multithreading.call;

/**
 * 继承Thread实现多线程
 *
 * @author xianglong.zhang@hand-china.com
 * @version 1.0
 * @date 2018/7/30 21:12
 */
public class MyThread extends Thread {
    String name=null;

    public MyThread(String name){
        this.name=name;
    }

    @Override
    public synchronized void run(){
        for(int i=0;i<10;i++){
            System.out.println(Thread.currentThread().getName()+"  --   " +this.name+i);

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
