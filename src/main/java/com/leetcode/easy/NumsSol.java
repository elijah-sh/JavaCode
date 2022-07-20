package com.leetcode.easy;

import java.util.*;

/**
 * 初级算法 数组
 */
public class NumsSol {
    public static void main(String[] args) {
        NumsSol numsSol = new NumsSol();
        //int[] nums = {1,2,3,1};
        //boolean cd = numsSol.containsDuplicate(nums);
        //System.out.println(cd);
        //int[] nums = {4,1,2,1,2};
        //int sn = numsSol.singleNumber(nums);
        //System.out.println(sn);


        int[] nums1 = {1,2};
        int[] nums2 = {2,2};
        int[] sn = numsSol.intersect(nums1, nums2);
        System.out.println(sn);


    }

    /**
     * 加一
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     *
     * 示例 1:
     *
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     * 示例 2:
     *
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     *
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2cv1c/
     */
    public int[] plusOne(int[] digits) {

        return null;
    }

    /**
     * 两个数组的交集 II
     * 给定两个数组，编写一个函数来计算它们的交集。
     *
     * 示例 1：
     * 输入：nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出：[2,2]
     * 示例 2:
     * 输入：nums1 = [4,9,5], nums2 = [9,4,9,8,4]
     * 输出：[4,9]
     *
     * 说明：
     * 输出结果中每个元素出现的次数，应与元素在两个数组中出现次数的最小值一致。
     * 我们可以不考虑输出结果的顺序。
     * 进阶：
     * 如果给定的数组已经排好序呢？你将如何优化你的算法？
     * 如果 nums1 的大小比 nums2 小很多，哪种方法更优？
     * 如果 nums2 的元素存储在磁盘上，内存是有限的，并且你不能一次加载所有的元素到内存中，你该怎么办？
     *
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2y0c2/
     */
       public int[] intersect(int[] nums1, int[] nums2) {
        if (nums1.length > nums2.length)
            return intersect(nums2, nums1);

        // key 数组  value 数量
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for (int num : nums1) {
            int count = map.getOrDefault(num, 0) + 1;
            map.put(num, count);
        }

        int[] intersection = new int[nums1.length];
        // 存值张度
        int index = 0;

        // 循环比较长的
        for (int num : nums2) {
            int count = map.getOrDefault(num, 0);
            if (count > 0) {
                intersection[index++] = num;
            }
            count--;
            if (count > 0) {
                map.put(num, count);
            } else {
                map.remove(num);
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }

    public int[] intersect1(int[] nums1, int[] nums2) {
        // 这个地方很值得借鉴  代码剪辑
        if (nums1.length > nums2.length) {
            return intersect(nums2, nums1);
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        // 值 数量
        for (int num : nums1) {
            int count = map.getOrDefault(num, 0) + 1;
            map.put(num, count);
        }
        int[] intersection = new int[nums1.length];
        // 存值张度
        int index = 0;
        // 循环长的
        for (int num : nums2) {
            int count = map.getOrDefault(num, 0);
            // 大于0 有重复的数据
            if (count > 0) {
                // 存起来
                intersection[index++] = num;
                // 把原来的移出去
                count--;
                // 大于0 更新 小于0 就删了
                if (count > 0) {
                    map.put(num, count);
                } else {
                    map.remove(num);
                }
            }
        }
        return Arrays.copyOfRange(intersection, 0, index);
    }


    /**
     * 只出现一次的数字
     * 给定一个非空整数数组，除了某个元素只出现一次以外，其余每个元素均出现两次。找出那个只出现了一次的元素。
     * 说明：
     * 你的算法应该具有线性时间复杂度。 你可以不使用额外空间来实现吗？
     * 示例 1:
     * 输入: [2,2,1]
     * 输出: 1
     * 示例 2:
     * 输入: [4,1,2,1,2]
     * 输出: 4
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x21ib6/
     */
    public int singleNumberan(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int count = 0;
        for (int num : nums) {

            if (set.contains(num))
                set.remove(num);
            else
                set.add(num);
        }

        return (int)set.toArray()[0];
    }

    /**
     * 异或运算  1^=1  == 0  1^=0 == 1  a^=a == 0 a^=b == 1
     */
    public int singleNumber(int[] nums) {
        int single = 0;
        for (int num : nums) {
            single ^= num;
        }
        return single;
    }


    /**
     * 存在重复元素
     * 给定一个整数数组，判断是否存在重复元素。
     *
     * 如果任意一值在数组中出现至少两次，函数返回 true 。如果数组中每个元素都不相同，则返回 false 。
     *  
     * 示例 1:
     * 输入: [1,2,3,1]
     * 输出: true
     * 示例 2:
     * 输入: [1,2,3,4]
     * 输出: false
     *
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x248f5/
     * 来源：力扣（LeetCode）
     * 解决 排序
     */
    public boolean containsDuplicate(int[] nums) {
        Set<Integer> set = new HashSet<>();
        int count = 0;
        for (int num : nums) {
            set.add(num);
            //   if (set.contains(num)) return true;
            if (set.size() != ++count) {
                return true;
            }
        }
        return false;
    }
}
