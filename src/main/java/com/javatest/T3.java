package com.javatest;

import java.util.*;

/**
 * Created by ShenShuaihu on 2018/8/4.
 * 随机生成50个小于100的整数,放入List中,将List中的数据除以10,
 * 以结果的整数值作为key放入Map中,得到如{1=>[11,10,12],2=>[21,24,23]}的Map,
 * 再将Map中key对应的数组进行排序,得到如{1=>[10,11,12],2=>[21,23,24]}
 输出的形式如下:
 随机生成50个小于100的数,分别为: xxx,xxxx,xxxx
 Map中的数据为: {1=>[11,10,12],2=>[21,24,23]}
 排序后的Map为: {1=>[10,11,12],2=>[21,23,24]}

 */
public class T3 {
    public static void main(String[] args) {
        List<Integer> list=new ArrayList<>();
/*
        List<Integer> listSub=new ArrayList<>();
*/
        Map<Integer,List<Integer>>  map = new HashMap<>();
        Random rand = new Random();
        for(int i=0; i<50; i++) {
           int ii= rand.nextInt(100);
            list.add(ii);
        }
        System.out.println("list: "+list.toString());
        // list分组 存 map
        for (int j = 0; j<5;j++){
                List<Integer> listSub=new ArrayList<>();
                for(int q=0; q<10; q++) {
                    listSub.add(q,list.get(j*10+q));
                }
                map.put(j,listSub);
        }
        System.out.println("map: "+map.toString());


        // map排序
        Map<Integer,List<Integer>>  mapSort = new HashMap<>();

        for (int j = 0; j<5;j++){
           List<Integer> tmp = new ArrayList<>();
           tmp = map.get(j);
            Integer[] arr = new Integer[tmp.size()];
            tmp.toArray(arr);
            Arrays.sort(arr);//使用java.util.Arrays对象的sort方法

            List<Integer> listSort=new ArrayList<>();
            for(int i=0;i<arr.length;i++){
              //  System.out.println(arr[i]);
                listSort.add(i,arr[i]);
            }
            mapSort.put(j,listSort);
        }

        System.out.println("mapSort: "+mapSort.toString());

    }
}
