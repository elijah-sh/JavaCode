package com.process.pattern.observer;


/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public abstract class Observer {
    protected Subject subject;
    public abstract void update();
}
