package com.leetcode.june;

import java.util.Arrays;

public class Daily {

    public static void main(String[] args) {
        Daily daily = new Daily();
        int[] nums = new int[]{2,1,3,4,5};
        // List<Boolean> dup = daily.kidsWithCandies(candies, extraCandies);
        int[] product = daily.productExceptSelfAm(nums);

        for (int n : product) {
            System.out.println(n);
        }
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
