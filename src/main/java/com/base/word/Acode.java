package com.base.word;

import com.common.Cat;
import com.common.Person;

import java.util.*;

public class Acode {
    public static void main(String[] args) {

        // 集合
        List<Object> list = new ArrayList<>();
        List<Object> linkedList = new LinkedList<>();
        Set<Object> set = new HashSet<>();
        Set<Object> linkedHashSet = new LinkedHashSet<>();
        Set<Object> treeSet = new TreeSet<>();
        Map map = new HashMap();
        map.keySet();
        Map hashtable = new Hashtable();

        // 新建Person对象，
        Person p1 = new Person("eee", 100);
        Person p2 = new Person("eee", 100);
        Person p3 = new Person("aaa", 200);



        // 新建HashSet对象
        //HashMap map = new HashMap();
        map.put(p1,"woshi---p1");
        map.put(p2,"woshi---p2");
        map.put(p3,"我是P3");

        System.out.println(map.get(p1)+"---------------------");
        System.out.println(map.get(p2)+"---------------------");
        System.out.println(map.get(p3));


        // 比较p1 和 p2， 并打印它们的hashCode()
        // p1.equals(p2) 对象直接比较 引用不同  不相等
        System.out.printf("p1.equals(p2) : %s; p1(%d) p2(%d)\n", p1.equals(p2), p1.hashCode(), p2.hashCode());



        // hashCode()相等即两个键值对的哈希值相等，然而哈希值相等，并不一定能得出键值对相等
        String str1 = "通话";
        String str2 = "重地";
        System.out.println(String.format("str1：%d | str2：%d", str1.hashCode(), str2.hashCode()));
        System.out.println(str1.equals(str2));



        Cat c1 = new Cat("王磊");
        Cat c2 = new Cat("王磊");
        System.out.println(c1.equals(c2)); // false


        String s1 = new String("老王");
        String s2 = new String("老王");
        System.out.println(s1.equals(s2)); // true


        String x = "string";
        String y = "string";
        String z = new String("string");
        // 值比较
        System.out.println(x==y); // true
        // 引用比较
        System.out.println(x==z); // false
        System.out.println(x.equals(y)); // true
        System.out.println(x.equals(z)); // true
    }
}
