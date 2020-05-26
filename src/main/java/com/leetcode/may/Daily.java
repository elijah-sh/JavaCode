package com.leetcode.may;

import java.util.HashMap;
import java.util.HashSet;

public class Daily {
    public static void main(String[] args) {
        Daily daily = new Daily();
        int[] nums = new int[]{3,1,3,4,2};
        int dup = daily.findDuplicate(nums);
        System.out.println(dup);
    }

    /**
     * 给定一个包含 n + 1 个整数的数组 nums，其数字都在 1 到 n 之间（包括 1 和 n），可知至少存在一个重复的整数。假设只有一个重复的整数，找出这个重复的数。
     *
     * 示例 1:
     * 输入: [1,3,4,2,2]
     * 输出: 2
     *
     * 示例 2:
     * 输入: [3,1,3,4,2]
     * 输出: 3
     *
     * 不能更改原数组（假设数组是只读的）。
     * 只能使用额外的 O(1) 的空间。
     * 时间复杂度小于 O(n2) 。
     * 数组中只有一个重复的数字，但它可能不止重复出现一次。
     *
     * 链接：https://leetcode-cn.com/problems/find-the-duplicate-number
     */

    public int findDuplicate(int[] nums) {

        int len = nums.length;
        int left = 1;
        int right = len - 1;

        while (left < right) {
            int mid = (left + right) >>> 1;

        }


        HashSet<Integer> set = new HashSet<>();
        int l = 0;


        for (int i = 0; i < nums.length; i++) {
            l = set.size();
            set.add(nums[i]);
            if (l == set.size()) {
                return nums[i];
            }

        }
        return 0;
    }
}
