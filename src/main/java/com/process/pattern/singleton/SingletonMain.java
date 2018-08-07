package com.process.pattern.singleton;

/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public class SingletonMain {
    public static void main(String[] args) {
        //不合法的构造函数
        //编译时错误：构造函数 SingleObject() 是不可见的
        //SingleObject singleObject = new SingleObject();
        //获取唯一可用的对象
       // SingleObject singleObject = SingleObject.getInstance();
      //  singleObject.showMessage();
        Singleton singleton = Singleton.INSTANCE;
        singleton.whateverMethod();
    }
}

