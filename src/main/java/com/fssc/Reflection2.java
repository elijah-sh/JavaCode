package com.fssc;

import com.sun.xml.internal.bind.v2.model.core.PropertyInfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;


/*
 * 通过反射调用TestClass的构造函数和一般函数
 */
public class Reflection2 {

    public static void main(String[] args) {
        /*
         * 在Reflection 动态机制中有两种作法，一个针对“无自变量ctor”， 一个针对“带参数ctor”。
         */
        //Type type = new sun.jvm.hotspot.types.Type()
        //PropertyInfo[] propertyInfo = type.GetP

        System.out.println("Invoke the default ctor:");
        try {
            Class testClass1 = Class.forName("test2.TestClass");

            // 调用缺省构造函数，直接用testClass调用就可
            Object obj = testClass1.newInstance();

            System.out.println(obj);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("********************************");
        System.out.println("Invoke the ctor with parameters:");

        try {
            Class testClass2 = Class.forName("test2.TestClass");

            // 首先准备一个Class[]做为ctor的参数类型（本例指定为一个int和一个string），
            Class[] pType = new Class[]
                    {int.class, Class.forName("java.lang.String")};

            // 调用pType为变量的getConstructor()，获得一个专属ctor
            Constructor ctor = testClass2.getConstructor(pType);

            // 准备一个Object[] 做为ctor实参值
            Object[] obj = new Object[]{2, "anybody"};

            // 调用上述专属ctor的newInstance()
            Object result = ctor.newInstance(obj);
            System.out.println(result);

        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        System.out.println("********************************");
        System.out.println("Invoke a function of TestClass, eg setName:");

        try {
            Class testClass3 = Class.forName("test2.TestClass");

            //准备一个Class[]做为调用函数的参数类型（本例指定为String）
            //如果参数多个一个，需要声明为Class[]
            Class pTypes = Class.forName("java.lang.String");

            //以pTypes为自变量调用getMethod()，获得特定的Method object
            //在getMethod中指定了要调用的函数名称setName和参数类型pTypes
            Method method = testClass3.getMethod("setName", pTypes);

            //准备一个Object放置自变量，或者直接声明为String aName = new String("Bao");
            Object aName = "BaoBao";

            //这里声明一个TestClass的对象，是因为setName需要修改一个对象的数据成员
            //如果没有这个对象的话，setName中将修改谁的name呢？
            //aStudent是id为1,name为somebody的默认值
            TestClass aStudent = new TestClass();

            //用上述所得之特定Method object的invoke()，就是TestClass中的setName()
            method.invoke(aStudent, aName);

            System.out.println(aStudent);

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
