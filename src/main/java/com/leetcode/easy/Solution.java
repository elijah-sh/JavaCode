package com.leetcode.easy;

import com.common.ListNode;

import java.util.HashMap;

public class Solution {


    public static void main(String[] args) {
        // [3,2,4]
        //6
        int[] nums = new int[]{3,2,4};
        int target = 6;
        int[] num = twoSum(nums, target);
        //System.out.println(num);

        //[1,8]
        //[0]
        ListNode l1 = new ListNode(2);
        l1.next = new ListNode(4);
        l1.next.next = new ListNode(3);

        ListNode l2 = new ListNode(0);
        //l2.next = new ListNode(6);
        //l2.next.next = new ListNode(4);

        addTwoNumbers(l1, l2);

        boolean a = isPalindrome(123321);
        System.out.println(a);
    }

    /**
     *
     * 给出两个 非空 的链表用来表示两个非负的整数。其中，它们各自的位数是按照 逆序 的方式存储的，并且它们的每个节点只能存储 一位 数字。
     * 如果，我们将这两个数相加起来，则会返回一个新的链表来表示它们的和。
     * 您可以假设除了数字 0 之外，这两个数都不会以 0 开头
     * 输入：(2 -> 4 -> 3) + (5 -> 6 -> 4)
     * 输出：7 -> 0 -> 8
     * 原因：342 + 465 = 807
     * @param l1
     * @param l2
     * @return
     */
    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode dummyHead = new ListNode(0);
        ListNode p = l1, q = l2, curr = dummyHead;

        int carry = 0;
       // p + q
        while (l1 != null || l2 != null) {

            int m,n;
            if (l1 == null) {
                m = 0;
            } else {
                m = l1.val;
            }
            if (l2 == null) {
                n = 0;
            } else {
                n = l2.val;
            }
            int sun = m + n + carry;
            carry = sun / 10;
            // 取余
            curr.next = new ListNode(sun % 10);
            curr = curr.next;

            if(l1 != null) l1 = l1.next;
            if(l2 != null) l2 = l2.next;
        }

        if (carry > 0) {
            curr.next = new ListNode(carry);
        }

        System.out.println(dummyHead.toString());
        return dummyHead;

    }

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
                //System.out.println(indexs[0] + " - " + indexs[1]);
                return indexs;
            }
            // 将数据存入 key为补数 ，value为下标
            integerHashMap.put(target - nums[i], i);
        }
        return new int[-1];
    }

    /**
     * 7. 整数反转
     *
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。
     *
     * 示例 1:
     *
     * 输入: 123
     * 输出: 321
     *  示例 2:
     *
     * 输入: -123
     * 输出: -321
     * 示例 3:
     *
     * 输入: 120
     * 输出: 21
     *
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/reverse-integer
     *
     * x 除10 取 余 既作为最大的n
     * x除10 循环
     *
     */
    public static int reverse(int x) {

        long n = 0;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }
        return (int) n == n ? (int) n : 0 ;
    }


    /**
     *
     *
     *  判断一个整数是否是回文数。回文数是指正序（从左向右）和倒序（从右向左）读都是一样的整数。
     *
     * 示例 1:
     *
     * 输入: 121
     * 输出: true
     * 示例 2:
     *
     * 输入: -121
     * 输出: false
     * 解释: 从左向右读, 为 -121 。 从右向左读, 为 121- 。因此它不是一个回文数。
     * 示例 3:
     *
     * 输入: 10
     * 输出: false
     * 解释: 从右向左读, 为 01 。因此它不是一个回文数。
     * 进阶:
     * 你能不将整数转为字符串来解决这个问题吗？
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/palindrome-number
     */
    public static boolean isPalindrome(int x) {
        if (x < 0) {
            return false;
        }
        long n = 0;
        long xx = x;
        while (x != 0) {
            n = n * 10 + x % 10;
            x = x / 10;
        }
        return xx == n;
    }

}
