package com.leetcode.algorithm;



/**
 * 给定一个整数数组  nums，求出数组从索引 i 到 j  (i ≤ j) 范围内元素的总和，包含 i,  j 两点。
 *
 * 示例：
 *
 * 给定 nums = [-2, 0, 3, -5, 2, -1]，求和函数为 sumRange()
 *
 * sumRange(0, 2) -> 1
 * sumRange(2, 5) -> -1
 * sumRange(0, 5) -> -3
 * 说明:
 *
 * 你可以假设数组不可变。
 * 会多次调用 sumRange 方法。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/range-sum-query-immutable
 */
public class NumArray {

    private static int[] nums = {-2, 0, 3, -5, 2, -1};

    // sum数组中存的是和，sum[i]存储了nums中前i个元素的和，sum[0] = 0;
    // sum[i]存储的实际是nums[0....i-1]的和，所以初始化长度要是nums.length+1
    private int[] sum ;

    public NumArray(int[] nums) {
        sum = new int[nums.length + 1];
        sum[0] = 0;
        for(int i = 1; i < sum.length; i++){
            // 转移方程，前i个元素和=前i-1个元素和加上第i个元素，注意sum和nums索引有一个偏移
            sum[i] = sum[i - 1] + nums[i - 1];
        }

    }

    public int sumRange(int i, int j) {
        // 由于计算和是闭区间，所以要取到sum[j+1]表示nums中前j+1个数，即索引为j那个数和它之前的数的和,减去sum[i]，即是nums[i]到nums[j]的和。
        return sum[j + 1] - sum[i];
    }

    public static void main(String[] args) {

        /**
         * Your NumArray object will be instantiated and called as such:
         * NumArray obj = new NumArray(nums);
         * int param_1 = obj.sumRange(i,j);
         */
        NumArray obj = new NumArray(nums);
        int param_1 = obj.sumRange(0,5);
        System.out.println(param_1);
    }
}
