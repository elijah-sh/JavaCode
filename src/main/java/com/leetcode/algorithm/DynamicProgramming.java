package com.leetcode.algorithm;

import javax.validation.constraints.Max;
import java.util.Arrays;

/**
 * 动态规划
 *
 * @author Elijah
 * @version 1.0
 * @date 2020/8/18 11:38 上午
 */
public class DynamicProgramming {

    public static void main(String[] args) {
        DynamicProgramming dp = new DynamicProgramming();
        //int[][] grid = {{1,2,1},{1,5,1},{4,2,1}};
        //int[][] grid = {{1,2,4},{3,1,2},{1,5,1}};
        //int minPathSum = dp.minPathSum(grid);
        //System.out.println(minPathSum);

        int[] product = {-2, 3, -4};
        int maxProduct = dp.maxProduct(product);
        System.out.println(maxProduct);

    }


    /**
     * 给你一个整数数组 nums ，请你找出数组中乘积最大的连续子数组（该子数组中至少包含一个数字），并返回该子数组所对应的乘积。
     * <p>
     * 示例 1:
     * 输入: [2,3,-2,4]
     * 输出: 6
     * 解释: 子数组 [2,3] 有最大乘积 6。
     * 示例 2:
     * 输入: [-2,0,-1]
     * 输出: 0
     * 解释: 结果不能为 2, 因为 [-2,-1] 不是子数组。
     * <p>
     * [2,3,-2,4] 期结果 6
     * <p>
     *     {-2, 3, -4};   24
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/maximum-product-subarray
     */
    public int maxProduct(int[] nums) {

        int s = nums[0];
        int[] max = new int[nums.length];
        int[] min = new int[nums.length];
        max[0] = s;
        min[0] = s;
        // 使用动态规划
        for (int i = 1; i < nums.length; i++) {
            // 正数
            if (nums[i] > 0) {
                max[i] = Math.max(nums[i], max[i - 1] * nums[i]);
                min[i] = Math.min(nums[i], min[i - 1] * nums[i]);
            } else {
                // 负数要 与最小对对比
                max[i] = Math.max(nums[i], min[i - 1] * nums[i]);
                min[i] = Math.min(nums[i], max[i - 1] * nums[i]);
            }

            s = Math.max(s, max[i]);
            System.out.println(i + " --》" + s);
        }

        return s;
    }


    /**
     * 给定一个包含非负整数的 m x n 网格，请找出一条从左上角到右下角的路径，使得路径上的数字总和为最小。
     * <p>
     * 说明：每次只能向下或者向右移动一步。
     * <p>
     * 示例:
     * <p>
     * 输入:
     * [
     *   [1,3,1],
     * [1,5,1],
     * [4,2,1]
     * ]
     * 输出: 7
     * 解释: 因为路径 1→3→1→1→1 的总和最小。
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/minimum-path-sum
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public int minPathSum1(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (i == 0 && j == 0) continue;
                else if (i == 0) grid[i][j] = grid[i][j - 1] + grid[i][j];
                else if (j == 0) grid[i][j] = grid[i - 1][j] + grid[i][j];
                else grid[i][j] = Math.min(grid[i - 1][j], grid[i][j - 1]) + grid[i][j];
            }
        }
        return grid[grid.length - 1][grid[0].length - 1];

    }

    public int minPathSum(int[][] grid) {

        r = grid.length;
        if (r == 0) {       // 空的grid
            return 0;
        }
        c = grid[0].length;
        return helper(grid, 0, 0);

    }

    int r = 0;  // 行数
    int c = 0;  // 列数


    /**
     * 递归
     *
     * @param grid
     * @param x
     * @param y
     * @return
     */
    public int helper(int[][] grid, int x, int y) {
        // 步数
        int pathSum = grid[x][y];

        if (x + 1 < r && y + 1 < c) {
            // 取最小的的
            return pathSum + Math.min(helper(grid, x + 1, y), helper(grid, x, y + 1));
        }

        // 向右👉
        if (x + 1 >= r && y + 1 < c) {
            return pathSum + helper(grid, x, y + 1);
        }

        // 向下 👇
        if (x + 1 < r && y + 1 >= c) {
            return pathSum + helper(grid, x + 1, y);
        }
        return pathSum;
    }


    public int waysToStep1(int n) {
        if (n <= 2) {
            return n;
        }
        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;
        dp[3] = 4;
        for (int i = 4; i < n + 1; i++) {
            //取模，对两个较大的数之和取模再对整体取模，防止越界（这里也是有讲究的）
            //假如对三个dp[i-n]都 % 1000000007，那么也是会出现越界情况（导致溢出变为负数的问题）
            //因为如果本来三个dp[i-n]都接近 1000000007 那么取模后仍然不变，但三个相加则溢出
            //但对两个较大的dp[i-n]:dp[i-2],dp[i-3]之和mod 1000000007，那么这两个较大的数相加大于 1000000007但又不溢出
            //取模后变成一个很小的数，与dp[i-1]相加也不溢出
            //所以取模操作也需要仔细分析
            //dp[i] = (dp[i-1] + (dp[i-2] + dp[i-3]) % 1000000007) % 1000000007;
            dp[i] = dp[i - 1] + dp[i - 2] + dp[i - 3];
        }

        return dp[n];
    }

    /**
     * 递归
     *
     * @param n
     * @return
     */
    public int waysToStep(int n) {
        if (n <= 2) {
            return n;
        } else if (n == 3) {
            return 4;
        } else {
            //              //dp[i] = (dp[i-1] + (dp[i-2] + dp[i-3]) % 1000000007) % 1000000007;
            return (waysToStep(n - 1) + (waysToStep(n - 2) + waysToStep(n - 3)) % 1000000007) % 100000000;
        }

    }


}
