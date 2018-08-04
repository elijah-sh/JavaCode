package com.javatest;

import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ShenShuaihu on 2018/8/4.
 * 输入某年某月某日，给出下一天的日期？当然输入日期不合法需要做出错误提示。
 从控制台读取日期,日期输入的格式为”2015-07-26”形式.
 (不允许使用Calendar,Date,DateTime类中的函数),输入输出的形式如下:
 请输入日期: 2017-02-28
 下一天为 2017-03-1
 请输入日期: 2017-02-29
 “您输入的日期不存在”
 */
public class T2 {

    public  String nextDate(String date){
            int year =Integer.parseInt( date.substring(0,4));
            int month = Integer.parseInt( date.substring(5,7));
            int day = Integer.parseInt( date.substring(8,10));
            int newYear;
            int newMonth;
            int newDay;
          String nextDate;
            if (month==12){
                //需要判断是否为年末
                if(day==31){
                    //年末 下一天即春节
                    newYear = year+1;
                    nextDate = newYear+"-01-01";
                    return  nextDate;
                }
              newDay = day+1;
                if (newDay<10){
                    nextDate = year+"-"+month+"-0"+newDay;
                    return  nextDate;
                }
                nextDate = year+"-"+month+"-"+newDay;
                return  nextDate;
            }
        if (month==2){  // 2月
            //判断是否为闰年 1.能被4整除而不能被100整除.     2.能被400整除.
            if (year%400==0||(year%100!=0&&year%4==0)){

                    if (day==29){   // 最后一天
                        nextDate =  year+"-03-01";
                        return  nextDate;
                    }
                }
                // 平年
                if (day==28){   // 最后一天
                    nextDate =  year+"-03-01";
                    return  nextDate;
                }
            // 正常日期
       /*     newDay = day+1;
            if (newDay<10){
                nextDate = year+"-"+month+"-0"+newDay;
                return  nextDate;
            }

            nextDate = year+"-"+month+"-"+newDay;
            return  nextDate;*/
        }
        //判断是否为31的月
        if (month == 1||month == 3||month == 5||month == 7||month == 8||month == 10){
                if (day==31){
                    newMonth = month+1;
                    if (newMonth<10){
                        nextDate = year+"-0"+newMonth+"-01";
                        return  nextDate;
                    }
                     nextDate = year+"-"+newMonth+"-01";
                    return  nextDate;
                }

        }else{
            if (day==30){
                newMonth = month+1;
                if (newMonth<10){
                    nextDate = year+"-0"+month+"-01";
                    return  nextDate;
                }
                nextDate = year+"-"+month+"-01";
                return  nextDate;
            }

        }

        newDay = day+1;
        if (newDay<10){
            nextDate = year+"-"+month+"-0"+newDay;
            return  nextDate;
        }
        nextDate = year+"-"+month+"-"+newDay;
        return  nextDate;

    }

    public boolean isValidDate(String date){

        if (date.equals("")||date.equals(null)){
            return false;
        }

        if (date.length()!=10){
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        int year =Integer.parseInt( date.substring(0,4));
        int month = Integer.parseInt( date.substring(5,7));
        int day = Integer.parseInt( date.substring(8,10));
        if (month>12){
            return false;
        }
        String rexp = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";

        Pattern pat = Pattern.compile(rexp);
        Matcher mat = pat.matcher(date);
        boolean dateType = mat.matches();

        return dateType;
    }
    public static boolean leapYear(int year) {
        Boolean isLeap = false;
        if (((year % 100 == 0) && (year % 400 == 0))
                || ((year % 100 != 0) && (year % 4 == 0)))
            isLeap = true;
        return isLeap;
    }
    public static void main(String[] args) {

        System.out.println("请输入今天日期:");
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        T2 t = new T2() ;
        if(t.isValidDate(s)){
            System.out.println("明天为:"+t.nextDate(s));
        }else {
            System.out.println("您输入的日期不存在");

        }

     }
}
