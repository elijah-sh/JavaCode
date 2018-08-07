package com.process.pattern.singleton;

import java.util.Date;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public class SingleObject {

    //创建 SingleObject 的一个对象   instance 例子
    private  static SingleObject instance ;
    //让构造函数为 private，这样该类就不会被实例化
    private  SingleObject(){}
    //获取唯一可用的对象
    public static synchronized  SingleObject  getInstance(){

        if (instance == null) {
            instance = new SingleObject();
        }
        return instance;
    }

    public void showMessage(){
        System.out.println("Hello World!"+new Date());
    }


}
