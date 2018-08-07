package com.javatest.io;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * Created by ShenShuaihu on 2018/8/6.
 */
public class rmRepeat {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("ss");
        list.add("s2");
        list.add("ss");
        System.out.println(list.toString());
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
        System.out.println(list.toString());
    }
    private static void removeDuplicate(List<String> list) {
        LinkedHashSet<String> set = new LinkedHashSet<String>(list.size());
        set.addAll(list);
        list.clear();
        list.addAll(set);
    }
}
