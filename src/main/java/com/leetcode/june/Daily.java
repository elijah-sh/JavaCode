package com.leetcode.june;

import java.util.Arrays;

public class Daily {

    public static void main(String[] args) {
        Daily daily = new Daily();
        int[] nums = new int[]{2,1,3,4,5};
        // List<Boolean> dup = daily.kidsWithCandies(candies, extraCandies);
        //int[][] matrix = {{1,2,3}, {4,5,6}, {7,8,9}};
        int[][] matrixs = {{2,3}};
        int[][] matrix = {{1,2,3,4}, {5,6,7,8}, {9,10,11,12}};
        int[][] arr1 = {{1,2}, {2, 3}, {4, 5}};

        int[] product = daily.spiralOrder(matrixs);

        for (int n : product) {
            System.out.println(n);
        }
    }

    /**
     *  面试题29. 顺时针打印矩阵
     *  输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字。
     *
     * 示例 1：
     * 输入：matrix = [[1,2,3],[4,5,6],[7,8,9]]
     * 输出：[1,2,3,6,9,8,7,4,5]
     * 示例 2：
     * 输入：matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
     * 输出：[1,2,3,4,8,12,11,10,9,5,6,7]
     *  
     * 难：[[2,5],[8,4],[0,-1]]
     * 限制：
     *
     * 0 <= matrix.length <= 100
     * 0 <= matrix[i].length <= 100
     * 注意：本题与主站 54 题相同：https://leetcode-cn.com/problems/spiral-matrix/
     *
     * 链接：https://leetcode-cn.com/problems/shun-shi-zhen-da-yin-ju-zhen-lcof
     *
     */
    public int[] spiralOrder(int[][] matrix) {
        // 四个方向的起始坐标 00 01 【top】【left】
        if(matrix.length == 0) {
            return new int[]{};
        }
        int q = 0;

        if(matrix.length == 1) {
            int[] nums = new int[matrix[0].length];

            for (int i = 0; i < matrix[0].length; i++) {
                nums[q++] = matrix[0][i];
            }
            return nums;
        }
        if(matrix[0].length == 0) {
            return new int[]{};
        }
        if(matrix[0].length == 1) {
            int[] nums = new int[matrix.length];

            for (int i = 0; i < matrix.length; i++) {
                nums[q++] = matrix[i][0];
            }
            return nums;
        }
            // 单行数据


        int left = 0, right = matrix[0].length - 1, top = 0, bottom = matrix.length - 1;
        int[] nums = new int[matrix.length * matrix[0].length];

        int p = 4;


        for (int i = 0; i < matrix.length * matrix[0].length ; i++) {
            // 奇数行 第i行  n-i 行进行遍历，偶数行 逆向遍历
            p++;
            switch (p%4){
                case 1 :
                    // 上 向右遍历
                    for (int j = left; j < right; j++) {
                        System.out.println(  matrix[top][j] + " " + q + " ： 【" + top + "】"+ " 【" + j + "】");
                        nums[q++] = matrix[top][j];
                    }
                    break;

                case 2:
                    // 左  向下遍历
                    for (int j = top; j < bottom; j++) {
                        System.out.println(  matrix[top][j] + " " + q + " ： 【" + j + "】"+ " 【" + right + "】");
                        nums[q++] = matrix[j][right];
                    }
                    break;

                case 3:
                    // 下  向左遍历
                    for (int j = right; j > left; j--) {
                        System.out.println(  matrix[top][j] + " " + q + " ： 【" + bottom + "】"+ " 【" + j + "】");
                        nums[q++] = matrix[bottom][j];
                    }
                    break;

                case 0:
                    // 右  向上遍历
                    for (int j = bottom; j > top; j--) {
                        System.out.println(  matrix[top][j] + " " + q + " ： 【" + j + "】"+ " 【" + left + "】");
                        nums[q++] = matrix[j][left];
                    }
                    top++;
                    left++;
                    right--;
                    bottom--;


                    break;

            }
            //switch (p%4){
            //    case 1 :
            //        // 上 向右遍历
            //        top++;
            //        break;
            //
            //    case 2:
            //        // 左  向下遍历
            //        right++;
            //        break;
            //
            //    case 3:
            //        // 下  向左遍历
            //        bottom++;
            //        break;
            //
            //    case 0:
            //        // 右  向上遍历
            //        left++;
            //        break;
            //
            //}
            if (left == right) {
                if (top == bottom) {
                    nums[q++] = matrix[left][right];
                    return nums;
                }
                for (int j = 0; j <= bottom - top + 1; j++) {
                    nums[q++] = matrix[top++][left];
                }
                return nums;
            }
            if (top == bottom) {
                if (left == right) {
                    nums[q++] = matrix[top][bottom];
                    return nums;
                }
                for (int j = 0; j <= right - left + 1; j++) {
                    nums[q++] = matrix[top][left++];
                }

                return nums;
            }
            //// 上  向左遍历
            //if ((i + 2)% 2 == 0) {
            //    // 偶数行正向遍历
            //    for (int j = left; j < right; j++) {
            //        nums[q++] = matrix[top][j];
            //    }
            //}
            //// 左  向下遍历
            //if ((i + 2)% 2 == 0) {
            //    for (int j = top; j < bottom; j++) {
            //        nums[q++] = matrix[j][right];
            //    }
            //}
            //
            //// 下  向右遍历
            //if ((i + 2)% 2 == 0) {
            //    for (int j = right; j < left; j--) {
            //        nums[q++] = matrix[bottom][j];
            //    }
            //}
            //
            //// 右  向上遍历
            //if ((i + 2)% 2 == 0) {
            //    for (int j = bottom; j < top; j--) {
            //        nums[q++] = matrix[j][left];
            //    }
            //}


        }

        return nums;
    }


    /**
     * 238. 除自身以外数组的乘积
     * 给你一个长度为 n 的整数数组 nums，其中 n > 1，返回输出数组 output ，其中 output[i] 等于 nums 中除 nums[i] 之外其余各元素的乘积。
     *
     * 示例:
     * 输入: [1,2,3,4]
     * 输出: [24,12,8,6]
     *
     * 提示：题目数据保证数组之中任意元素的全部前缀元素和后缀（甚至是整个数组）的乘积都在 32 位整数范围内。
     * 说明: 请不要使用除法，且在 O(n) 时间复杂度内完成此题。
     *
     * 链接：https://leetcode-cn.com/problems/product-of-array-except-self
     */
    public int[] productExceptSelf(int[] nums) {

        int multi[] = new int[nums.length];
        // 给每一个元素填充1
        Arrays.fill(multi, 1);
        int left = 1, right = 1, n = nums.length;

        for (int i = 0; i < nums.length; i++) {
            //求前缀乘积  前i个元素的乘积
            multi[i] = multi[i] * left;
            // 左侧元素相乘
            left = left * nums[i];

            //求后缀乘积 i 后边元素的乘积
            multi[n - i - 1] =  multi[n - i - 1] * right;
            right = right * nums[n - i - 1];
        }
        return multi;
    }









//        int[] nums = new int[]{2,1,3,4,5};
    public int[] productExceptSelfAm(int[] nums) {

        int multi[] = new int[nums.length];
        // 给每一个元素填充1
        Arrays.fill(multi, 1);
        int left = 1, right = 1, n = nums.length;

        for (int i = 0; i < nums.length; i++) {
            // i 元素 左右两边乘积
            multi[i] = multi[i] * left;
            left = left * nums[i];

            multi[n - i - 1] = multi[n - i - 1] * right;
            right = right * nums[n - i - 1];

            System.out.println("I:" + i + " multi[i] " + multi[i] + " left " + left+ "  (n - i - 1)" + (n - i - 1) +" - multi[n - i - 1] " + multi[n - i - 1] + " right " + right);
        }
        return multi;
    }
}
