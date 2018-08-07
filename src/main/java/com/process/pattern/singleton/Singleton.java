package com.process.pattern.singleton;

import java.util.Date;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public enum Singleton {
    INSTANCE;
    public void whateverMethod() {
        System.out.println(new Date());
    }
}
