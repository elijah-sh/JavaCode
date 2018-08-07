package com.process.pattern.observer;

/**
 * Created by ShenShuaihu on 2018/8/7.
 * 双重的
 */
public class BinaryObserver extends Observer {
    @Override
    public void update() {
        // Integer.toBinaryString 此方法返回int变量的二进制表示的字符串。
        System.out.println("二进制的: " +Integer.toBinaryString(subject.getState()));
    }
    public BinaryObserver(Subject subject){
        this.subject = subject;
        this.subject.attach(this);
    }
}
