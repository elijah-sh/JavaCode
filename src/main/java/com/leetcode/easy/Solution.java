package com.leetcode.easy;

import java.util.HashMap;

public class Solution {

    /**
     * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
     * 你可以假设每种输入只会对应一个答案。但是，数组中同一个元素不能使用两遍。
     *  
     * 示例:
     * 给定 nums = [2, 7, 11, 15], target = 9
     *
     * 因为 nums[0] + nums[1] = 2 + 7 = 9
     * 所以返回 [0, 1]
     *
     * @param nums
     * @param target
     * @return
     */
    public static int[] twoSum(int[] nums, int target) {
        //for (int i = 0; i < nums.length; i++) {
        //    for (int j = i; j < nums.length; j++) {
        //        if (nums[i] + nums[j] == target && i != j) {
        //            return new int[]{i, j};
        //        }
        //    }
        //}

        int[] indexs = new int[2];
        // 建立k-v ，一一对应的哈希表 下标与值
        HashMap<Integer, Integer> integerHashMap = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            // map 中是否包含
            if (integerHashMap.containsKey(nums[i])) {
                // 返回 与补数相匹配的值
                indexs[0] = integerHashMap.get(nums[i]);
                indexs[1] = i;
                System.out.println(indexs[0] + " - " + indexs[1]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            integerHashMap.put(target - nums[i], i);
        }
        return new int[-1];
    }

    public static void main(String[] args) {
        // [3,2,4]
        //6
        int[] nums = new int[]{3,2,4};
        int target = 6;
        int[] num = twoSum(nums, target);
        System.out.println(num);
    }
}
