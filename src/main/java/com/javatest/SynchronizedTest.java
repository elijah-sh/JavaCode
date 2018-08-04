package com.javatest;

import java.util.Date;

/**
 * Created by ShenShuaihu on 2018/8/4.
 */
public class SynchronizedTest implements Runnable {
    private String name;

    @Override
    public  void   run() {
            for (int i = 0 ;i<10;i++){
               name = this.save(name);
                System.out.println(name+" : "+ i);
        }
    }

    public   SynchronizedTest (String name){
        this.name = name;
    }

    public String save (String name){
         return "";
    }

    public static void main(String[] args) {

        System.out.println("start");
        SynchronizedTest s1 =new SynchronizedTest("s1");
        SynchronizedTest s2 =new SynchronizedTest("s2");
        Thread t1 = new Thread(s1);
        Thread t2 = new Thread(s2);
        t1.start();
        t2.start();
    }

}
