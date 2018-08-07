package com.process.pattern.observer;

/**
 * Created by ShenShuaihu on 2018/8/7.
 *  八进制的
 */
public class OctalObserver extends Observer {
    @Override
    public void update() {
        System.out.println("八进制的: " +Integer.toBinaryString(subject.getState()));

    }
    public OctalObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }
}
