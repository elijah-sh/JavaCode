package com.leetcode.may;

import java.util.*;
import java.util.stream.Collectors;

public class Daily {
    public static void main(String[] args) {
        Daily daily = new Daily();
        int[] nums = new int[]{3,1,3,4,2};
        int[] A = new int[]{4,5,0,-2,-3,1};
        int K = 3;
        int[] candies = new int[]{12,1,12};
        int extraCandies = 10;
       // List<Boolean> dup = daily.kidsWithCandies(candies, extraCandies);
        System.out.println(daily.sumNums(9));
    }

    /**
     * 求1+2+…+n
     * 要求不能使用乘除法、for、while、if、else、switch、case等关键字及条件判断语句（A?B:C）。
     *
     *  示例 1：
     * 输入: n = 3
     * 输出: 6
     *  示例 2：
     * 输入: n = 9
     * 输出: 45
     */
    public int sumNums(int n) {

        // 1、等差数列         return (int) (Math.pow(n, 2) + n) >> 1;
        // 2、递归
        // 2、 高斯定理


        //int sum = n;
        //Boolean f = n > 0 && (sum += sumNums(n - 1)) > 0;
        //
        //return sum;

        // << 移动一次就是*2  两次  * 4
        System.out.println((Math.pow(n, 2) + n));
        return (int) (Math.pow(n, 2) + n) >> 1;
    }

    /**
     * 拥有最多糖果的孩子
     *
     *  给你一个数组 candies 和一个整数 extraCandies ，其中 candies[i] 代表第 i 个孩子拥有的糖果数目。
     * 对每一个孩子，检查是否存在一种方案，将额外的 extraCandies 个糖果分配给孩子们之后，此孩子有 最多 的糖果。注意，允许有多个孩子同时拥有 最多 的糖果数目。
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/kids-with-the-greatest-number-of-candies
     *
     * 2 <= candies.length <= 100
     * 1 <= candies[i] <= 100
     * 1 <= extraCandies <= 50
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        List<Boolean> an = new ArrayList<>(candies.length);
        int max = 0;
        //int m = Arrays.stream(candies).sorted().max().orElse(0);
        //Arrays.stream(candies).boxed().map(n -> n + extraCandies > m).collect(Collectors.toList());
        for (int candie : candies) {
            // 找到最大  最大的值与extraCandies的差值 与其他比较  大于为false 小于为true
            if (candie > max) {
                max = candie;
            }

        }

        for (int candie : candies) {
            // 找到最大  最大的值与extraCandies的差值 与其他比较  大于为false 小于为true
            if (candie >= max - extraCandies) {
                an.add(true);
            } else {
                an.add(false);
            }
        }

        return an;
    }

    /**
     * 给定一个整数数组 A，返回其中元素之和可被 K 整除的（连续、非空）子数组的数目。
     *
     * 示例：
     *
     * 输入：A = [4,5,0,-2,-3,1], K = 5
     * 输出：7
     * 解释：
     * 有 7 个子数组满足其元素之和可被 K = 5 整除：
     *  0  1  2   3   4  5
     * [4, 5, 0, -2, -3, 1], [5], [5, 0], [5, 0, -2, -3], [0], [0, -2, -3], [-2, -3]
     *  
     * 提示：
     * 1 <= A.length <= 30000
     * -10000 <= A[i] <= 10000
     * 2 <= K <= 10000
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/subarray-sums-divisible-by-k
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     * TODO 不会 好难
     */
    public int subarraysDivByK(int[] A, int K) {
        int n = A.length;

        if (n < 1 || n > 30000) {
            return 0;
        }
        if (K < 2 || K > 10000) {
            return 0;
        }
        int sum = 0;
        int count = 0;
        // 循环 1 - n
        for (int i = 0; i < n; i++) {
            if (A[i] < -10000 || A[i] > 10000) {
                return 0;
            }
            // 循环 i - n
            int j = i;
            while (j != n) {
                sum = sum + A[j];
                if (sum % K == 0) {
                    count++;
                    System.out.println("I：" + i + " J：" + j + " A[i]：" + A[i]  + " A[j]：" + A[j] );
                }
                j++;
            }
            sum = 0;
        }
        return count;
    }

    public int subarraysDivByKAN(int[] A, int K) {
        Map<Integer, Integer> record = new HashMap<>();
        record.put(0, 1);
        int sum = 0, ans = 0;
        for (int elem: A) {
            // 累计和 存入map
            sum += elem;
            // 注意 Java 取模的特殊性，当被除数为负数时取模结果为负数，需要纠正
            // 余数值
            int modulus = (sum % K + K) % K;
            // 匹配余数是否存在 返回余数的值
            int same = record.getOrDefault(modulus, 0);
            ans += same;
            // 存入map 余数 -》 匹配
            record.put(modulus, same + 1);
        }
        return ans;
    }
    /** 前缀优化 */
    public int subarraysDivByKAN2(int[] A, int K) {
        int res = 0;
        // 记录当前前缀和
        int preSum = 0;
        // 因为K固定，因此可以使用数组代替哈希表（mod[i] = j，代表余数为i的前缀和出现了j次）
        int[] mod = new int[K];
        // 余数为0的状况，也就是直接被整除的情况，要提前放个1，考虑比如 A = {K}
        mod[0] = 1;
        for (int value : A) {
            // 更新前缀和
            preSum = preSum + value;
            // 计算mod（java注意）
            int m = (preSum % K + K) % K;
            // 更新结果  难点
            res = res + mod[m];
            // 更新余数集记录
            ++mod[m];
        }
        return res;
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
