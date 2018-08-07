package com.process.pattern.factory;

/**
 * Created by ShenShuaihu on 2018/8/7.
 * 长方形
 */
public class Rectangle  implements Shape{

    @Override
    public void draw() {
        System.out.println("Inside Rectangle::draw() method.");
    }
}
