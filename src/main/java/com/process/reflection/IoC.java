package com.process.reflection;
import java.io.*;
import java.util.*;
/**
 * Created by ShenShuaihu on 2018/8/7.
 */
public class IoC {
}


interface fruit{
    public abstract void eat();
}

class Apple implements fruit{
    public void eat(){
        System.out.println("Apple");
    }
}

class Orange implements fruit{
    public void eat(){
        System.out.println("Orange");
    }
}
//操作属性文件类
class init{
    public static Properties getPro() throws FileNotFoundException, IOException{
        Properties pro=new Properties();
        FileInputStream f=new FileInputStream("/application.properties");
       InputStream in = new BufferedInputStream (f); //读取属性文件a.properties

        pro.load(in);     ///加载属性列表
         Iterator<String> it=pro.stringPropertyNames().iterator();
          while(it.hasNext()){
             String key=it.next();
             System.out.println(key+":"+pro.getProperty(key));
          }
         //  in.close();


        if(1==1){
            pro.load(in);
        }else{
            ///加载属性列表
            pro.setProperty("apple", "Reflect.Apple");
            pro.setProperty("orange", "Reflect.Orange");
        }
        return pro;
    }
}

class Factory{
    public static fruit getInstance(String ClassName){
        fruit f=null;
        try{
            f=(fruit)Class.forName(ClassName).newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}

class hello{
    public static void main(String[] a) throws FileNotFoundException, IOException{
        Properties pro=init.getPro();
        fruit f=Factory.getInstance(pro.getProperty("orange"));
        if(f!=null){
            f.eat();
        }
    }
}
//【运行结果】：Apple