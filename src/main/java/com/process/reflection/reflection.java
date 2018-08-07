package com.process.reflection;

import com.javatest.io.Employee;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by ShenShuaihu on 2018/8/6...
 */
public class reflection {

    public static void main(String[] args)throws Exception {
        // Class 是源头
        Class clazz = Employee.class;
        Employee employee = (Employee)clazz.newInstance();
        //1、 通过反射调用运行时类的指定属性 public 或者 private
        System.out.println(employee.toString());
        Field f1 = clazz.getField("name");
        f1.set(employee,"shen");
        Field f2 = clazz.getDeclaredField("department");
        f2.setAccessible(true);
        f2.set(employee,"中台");
        System.out.println(employee.toString());
        // 2\ 通过反射调用方法
        Method m1 = clazz.getMethod("getSa",String.class);
        m1.invoke(employee,"ssh");
    }

    public void  rzef() throws Exception{


    }
}
