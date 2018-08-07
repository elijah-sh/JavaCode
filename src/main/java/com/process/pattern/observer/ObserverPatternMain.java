package com.process.pattern.observer;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public class ObserverPatternMain  {
    public static void main(String[] args) {
        Subject subject = new Subject();


        new  BinaryObserver(subject);
        new OctalObserver(subject);

        subject.setState(15);
        subject.setState(10);
    }
}
