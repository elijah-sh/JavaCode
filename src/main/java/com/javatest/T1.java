package com.javatest;

import java.util.Scanner;

/**
 * Created by ShenShuaihu on 2018/8/4.
 * 计算个人所得税
 */
public class T1 {
    public double tax(int salary) {

        double tax = 0;
        if (salary < 1500) {
            tax = salary * 0.03;
        } else if (salary < 4500) {
            tax = 1500 * 0.03;
            tax = tax + (salary - 1500) * 0.1;
        } else if (salary < 9000) {
            tax = 1500 * 0.03;
            tax = tax + (4500 - 1500) * 0.1;
            tax = tax + (salary - 4500) * 0.2;
            System.out.println(tax);
        } else if (salary < 35000) {
            tax = 1500 * 0.03;
            tax = tax + (4500 - 1500) * 0.1;
            tax = tax + (9000 - 4500) * 0.2;
            tax = tax + (salary - 9000) * 0.25;
        } else if (salary < 55000) {
            tax = 1500 * 0.03;
            tax = tax + (4500 - 1500) * 0.1;
            tax = tax + (9000 - 4500) * 0.2;
            tax = tax + (35000 - 9000) * 0.25;
            tax = tax + (salary - 35000) * 0.3;
        } else if (salary < 80000) {
            tax = 1500 * 0.03;
            tax = tax + (4500 - 1500) * 0.1;
            tax = tax + (9000 - 4500) * 0.2;
            tax = tax + (35000 - 9000) * 0.25;
            tax = tax + (55000 - 35000) * 0.3;
            tax = tax + (salary - 55000) * 0.35;

        } else if (salary > 80000) {
            tax = 1500 * 0.03;
            tax = tax + (4500 - 1500) * 0.1;
            tax = tax + (9000 - 4500) * 0.2;
            tax = tax + (35000 - 9000) * 0.25;
            tax = tax + (55000 - 35000) * 0.3;
            tax = tax + (80000 - 55000) * 0.35;
            tax = tax + (salary - 80000) * 0.45;

        }

        return tax;
    }

    public static void main(String[] args) {
        System.out.println("请输入工资:");
        Scanner scanner = new Scanner(System.in);
        int s = scanner.nextInt();
        T1 t = new T1() ;
        System.out.println("所需要缴纳的税费为:"+t.tax(s));
    }
}