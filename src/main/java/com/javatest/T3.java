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
        List<Integer> listSub1=new ArrayList<>();
        List<Integer> listSub2=new ArrayList<>();
        List<Integer> listSub3=new ArrayList<>();
        List<Integer> listSub4=new ArrayList<>();
        List<Integer> listSub5=new ArrayList<>();
        List<Integer> listSub6=new ArrayList<>();
        List<Integer> listSub7=new ArrayList<>();
        List<Integer> listSub8=new ArrayList<>();
        List<Integer> listSub9=new ArrayList<>();
        List<Integer> listSub0=new ArrayList<>();
        for (int j = 0; j<50;j++){

            String  s = String.valueOf(list.get(j)); // 把int转换成String
           //
            String  ss =   String.format("%02d", list.get(j));
            int cc = Integer.parseInt(ss.substring(0,1));
            System.out.print(cc+" ");
                switch (cc){
                    case 0 :  listSub0.add(list.get(j)) ; break;
                    case 1 :  listSub1.add(list.get(j)); break;
                    case 2 :  listSub2.add(list.get(j)); break;
                    case 3 :  listSub3.add(list.get(j)); break;
                    case 4 :  listSub4.add(list.get(j)); break;
                    case 5 :  listSub5.add(list.get(j)); break;
                    case 6 :  listSub6.add(list.get(j)); break;
                    case 7 :  listSub7.add(list.get(j)); break;
                    case 8 :  listSub8.add(list.get(j)); break;
                    case 9 :  listSub9.add(list.get(j)); break;

                }

                map.put(0,listSub0);
                map.put(1,listSub1);
                map.put(2,listSub2);
                map.put(3,listSub3);
                map.put(4,listSub4);
                map.put(5,listSub5);
                map.put(6,listSub6);
                map.put(7,listSub7);
                map.put(8,listSub8);
                map.put(9,listSub9);
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
