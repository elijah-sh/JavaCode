package com.leetcode.may;


import java.util.*;

/**
 *
 * 五月刷题
 *
 * @author ELijah
 * @version 1.0
 * @date 2020/5/6 5:36 下午
 *         and regexp_like(ccct.CLAIM_TYPE_CODE, '^NPDT(.+*)+|^TEDT(.+*)+|^TFNT(.+*)+|^TENT(.+*)+|^APDT(.+*)')
 *         AND (CCCT.CLAIM_TYPE_CODE LIKE CONCAT('NPDT', '%') OR CCCT.CLAIM_TYPE_CODE LIKE CONCAT('TEDT', '%'))
 */
public class Solution {

    public static void main(String[] args) {
        Solution solution = new Solution();
        // "pwwkew"
        String  s = "MCMXCIV";
        //int[] nums1 = new int[]{1, 2};
        //int[] nums2 = new int[]{3, 4};
        int mid = solution.romanToInt(s);
        System.out.println(mid);
    }


    /**
     * 罗马数字转整数
     *
     * 罗马数字包含以下七种字符: I， V， X， L，C，D 和 M。
     *
     * 字符          数值
     * I             1
     * V             5
     * X             10
     * L             50
     * C             100
     * D             500
     * M             1000
     * 例如， 罗马数字 2 写做 II ，即为两个并列的 1。12 写做 XII ，即为 X + II 。 27 写做  XXVII, 即为 XX + V + II 。
     *
     * 通常情况下，罗马数字中小的数字在大的数字的右边。但也存在特例，例如 4 不写做 IIII，而是 IV。数字 1 在数字 5 的左边，所表示的数等于大数 5 减小数 1 得到的数值 4 。同样地，数字 9 表示为 IX。这个特殊的规则只适用于以下六种情况：
     *
     * I 可以放在 V (5) 和 X (10) 的左边，来表示 4 和 9。  IV 4  / IX 9
     * X 可以放在 L (50) 和 C (100) 的左边，来表示 40 和 90。
     * C 可以放在 D (500) 和 M (1000) 的左边，来表示 400 和 900。
     * 给定一个罗马数字，将其转换成整数。输入确保在 1到 3999 的范围内。
     *
     * 链接：https://leetcode-cn.com/problems/roman-to-integer
     */
    public int romanToInt(String s) {

        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            char str = s.charAt(i);

            switch (str) {
                case 'I':
                    if (i+1 < s.length() && s.charAt(i+1) == 'V') {
                        result = result + 4;
                        i++;
                    } else if (i+1 < s.length() && s.charAt(i+1) == 'X') {
                        result = result + 9;
                        i++;
                    } else {
                        result = result + 1;
                    }
                    break;
                case 'V':
                    result = result + 5;
                    break;
                case 'X':
                    if (i+1 < s.length() && s.charAt(i+1) == 'L') {
                        result = result + 40;
                        i++;
                    } else if (i+1 < s.length() && s.charAt(i+1) == 'C') {
                        result = result + 90;
                        i++;
                    } else {
                        result = result + 10;
                    }
                    break;
                case 'L':
                    result = result + 50;
                    break;
                case 'C':
                    if (i+1 < s.length() && s.charAt(i+1) == 'D') {
                        result = result + 400;
                        i++;
                    } else if (i+1 < s.length() && s.charAt(i+1) == 'M') {
                        result = result + 900;
                        i++;
                    } else {
                        result = result + 100;
                    }
                    break;
                case 'D':
                    result = result + 500;
                    break;
                case 'M':
                    result = result + 1000;
                    break;
                default:
                    System.out.println("input string error");
                    break;
            }
        }
        return result;
    }



    /**
     * 给定两个大小为 m 和 n 的正序（从小到大）数组nums1 和nums2。
     * 请你找出这两个正序数组的中位数，并且要求算法的时间复杂度为O(log(m + n))。
     * 你可以假设nums1和nums2不会同时为空。
     *
     * 示例 1:
     * nums1 = [1, 3]  nums2 = [2]  则中位数是 2.0
     * 示例 2:
     * nums1 = [1, 2] nums2 = [3, 4] 则中位数是 (2 + 3)/2 = 2.5
     *
     * @来源： 力扣（LeetCode） 链接：https://leetcode-cn.com/problems/median-of-two-sorted-arrays
     * @完成情况  完全参考答案
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {

        int m = nums1.length;
        int n = nums2.length;
        int[] nums = new int[m + n];

        // 1、其中一个元素是NULL
        if (m == 0) {
            // 取余之后为 奇数 直接返回中间数
            if (n % 2 == 1) {
                return nums2[n / 2];
            } else {
                return (nums2[n / 2 - 1] + nums2[n / 2]) / 2.0;
            }
        }
        if (n == 0) {
            // 取余之后为 奇数 直接返回中间数
            if (m % 2 == 1) {
                return nums1[m / 2];
            } else {
                return (nums1[m / 2 - 1] + nums1[m / 2]) / 2.0;
            }
        }

        // 2、两个都有值

        // 定义传入两个元素的指针，用于赋值
        int i = 0;
        int j = 0;
        // 定义需要便利数据的最大值
        int count = 0;
        while (count != (m + n)) {

            // 2、1 其中一个数据变遍历完了，直接把另外一个添加上 就跳出循环
            if (i == m) {
                while (j != n) {
                    nums[count++] = nums2[j++];
                }
                break;
            }
            if (j == n) {
                while (i != m) {
                    nums[count++] = nums1[i++];
                }
                break;
            }

            // 2.2 比较两个元素的大小 选取小的赋值
            if (nums1[i] < nums2[j]) {
                nums[count++] = nums1[i++];
            } else {
                nums[count++] = nums2[j++];
            }
        }

        for (int k = 0; k < count; k++) {
            System.out.println(nums[k]);
        }
        // 3 找到合并之后的中位数
        if (count % 2 == 1) {
            return nums[count / 2];
        } else {
            return (nums[count / 2 - 1] + nums[count / 2]) / 2.0;
        }
    }

    /**
     * 无重复字符的最长子串
     *
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度。
     *
     * 示例 1:
     * 输入: "abcabcbb"
     * 输出: 3
     * 解释: 因为无重复字符的最长子串是 "abc"，所以其长度为 3。
     * 示例 2:
     *
     * 输入: "bbbbb"
     * 解释: 因为无重复字符的最长子串是 "b"，所以其长度为 1。
     * 示例 3:
     *
     * 输入: "pwwkew"
     * 解释: 因为无重复字符的最长子串是 "wke"，所以其长度为 3。
     *      请注意，你的答案必须是 子串 的长度，"pwke" 是一个子序列，不是子串。
     *
     * 移动窗口的解决方法
     * @param s "pwwkew" "aabaab!bb"
     * @return
     */
    public static int lengthOfLongestSubstring(String s) {
//"aabaab!bb"
        String s1[] = s.split("");

        List<String> maxList = new ArrayList<>();
        List<String> tempList = new ArrayList<>();
        String temp = "";
        Set<String> set = new HashSet<>();
        for (String str: s1) {

            if ("".equals(str)) {
                continue;
            }
            int len = set.size();
            set.add(str);
            // 出现重复 找到原来存放的位置
            if (len == set.size()) {
                // 有重复 需要移除重复的  移动窗口 匹配到某值  仅仅清除上个重复的以及之前的
                //  难点
                //for (String st : temp.split("")) {
                //    //if (tempList.contains(str)) {
                //    if (st.equals(str) ) {
                //        temp = String.valueOf(tempList);
                //        break;
                //    }
                //    tempList.remove(st);
                //    set.remove(st);
                //}
                // 包含位置  并删除在这之前的
                int reIndex = tempList.indexOf(str);
                // 删除tempStr中的重复字节及其之前的字符
                for(int j=0;j<=reIndex;j++){
                    tempList.remove(0);
                    set.remove(0);
                }
                tempList.add(str);
                // 字符串和set 处理
            } else {
                tempList.add(str);
                temp = temp + str;
            }
            if (tempList.size() > maxList.size()) {
                maxList.clear();
                maxList.addAll(tempList);
            }

        }
        System.out.println(maxList.size());
        System.out.println(maxList);
        return 0;
    }
    public int lengthOfLongestSubstringAnswer(String s) {
        //如果s为空，length不大于0，是一个空串，就没有向下执行的必要了
        if(s != null && s.length() > 0 && s != ""){
            return 0;
        }

        //String -> char[]
        char[] strChar = s.toCharArray();
        // 存储最长字串 key:char值，value:index下标
        ArrayList<String> maxStr = new ArrayList<>();
        //临时的字串存储空间
        ArrayList<String> tempStr = new ArrayList<>();
        //循环
        for(int i=0; i<strChar.length; i++){
            //char -> String
            String str = new String(new char[]{strChar[i]});
            //判断str是否存在于tempStr中
            if(tempStr.contains(str)){
                //先判断tempStr的长度是否大于等于maxStr的长度,大于，才能将最长字串覆盖
                if(tempStr.size() > maxStr.size()){
                    maxStr = new ArrayList<>(tempStr);
                }
                //存储重复字符
                int reIndex = tempStr.indexOf(str);
                // 删除tempStr中的重复字节及其之前的字符
                for(int j=0;j<=reIndex;j++){
                    tempStr.remove(0);
                }
            }
            //将当前字符存入tempStr中
            tempStr.add(str);
        }
        //最终判断
        if(tempStr.size() > maxStr.size()){
            maxStr = tempStr;
        }
        //返回最长字串的长度
        return maxStr.size();

    }
}
