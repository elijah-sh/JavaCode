package com.leetcode.october;

import java.util.ArrayList;
import java.util.List;

public class Sol {

    public static void main(String[] args) {

        Sol sol = new Sol();
        //String con = "LEETCODEISHIRING";
        //String con = "0123456789";
        //String str = sol.convertAn(con, 4);
        //System.out.println(str);

        // [1,1,2]
        //int[] nums = {1,1,2};
        //System.out.println(sol.removeDuplicates(nums));

        //int[] prices = {7,1,5,3,6,4};
        //System.out.println(sol.maxProfit(prices));


        int[] nums = {1,2,3,4,5,6,7};
        sol.rotate(nums, 2);

    }





    /**
     * 旋转数组
     * 给定一个数组，将数组中的元素向右移动 k 个位置，其中 k 是非负数。
     *
     * 示例 1:
     *
     * 输入: [1,2,3,4,5,6,7] 和 k = 3
     * 输出: [5,6,7,1,2,3,4]
     * 解释:
     * 向右旋转 1 步: [7,1,2,3,4,5,6]
     * 向右旋转 2 步: [6,7,1,2,3,4,5]
     * 向右旋转 3 步: [5,6,7,1,2,3,4]
     *
     * 尽可能想出更多的解决方案，至少有三种不同的方法可以解决这个问题。
     * 要求使用空间复杂度为 O(1) 的 原地 算法。
     *
     * 作者：力扣 (LeetCode)
     * 链接：https://leetcode-cn.com/leetbook/read/top-interview-questions-easy/x2skh7/
     * 来源：力扣（LeetCode）
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums.length == 1) {
            return;
        }
        // [1,2,3] 4   [1,2] 3  循环
        if (nums.length < k) {
            k = k %  nums.length;
        }

        int[] tmps = new int[k];

        for (int i = 0; i < k; i++) {
            tmps[i] = nums[nums.length - k + i];
        }
        for (int i = nums.length - k -1; i >= 0; i--) {
            nums[i + k] = nums[i];
        }
        for (int i = 0; i < k; i++) {
            nums[i] = tmps[i];
        }

        for (int i = 0; i < nums.length; i++) {
            System.out.println(nums[i]);;
        }
    }



    /**
     * 买卖股票 利益最大  可以买卖多次
     * @param prices  [7,1,5,3,6,4]
     * @return 7
     */
    public int maxProfit(int[] prices) {
        // 动态规划DP
        // 定义最小值和最大利润
        // 初始化
        int priMin = prices[0];
        int pi = 0;

        for (int i = 1; i < prices.length; i++) {
            // 买入最小值
            priMin = Math.min(priMin, prices[i]);
            // 赚钱 卖
            if (prices[i] - priMin > 0) {
                pi = pi + prices[i] - priMin;
                // 需要重新买入
            }
            priMin = prices[i];
        }

        return pi;
    }

    // 给定 nums = [0,0,1,1,1,2,2,3,3,4],
    public int removeDuplicates(int[] nums) {
        int num = nums.length;
        if (num < 2) {
            return nums.length;
        }
        int temp = 0;
        for (int i = 1; i < nums.length; i++) {
            if (nums[temp] != nums[i]) {
                temp++;
                nums[temp] = nums[i];
            }
        }
        return temp+1;
    }
    /**
     *
     * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
     *
     * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
     *
     * L   C   I   R
     * E T O E S I I G
     * E   D   H   N
     * 之后，你的输出需要从左往右逐行读取，产生出一个新的字符串，比如："LCIRETOESIIGEDHN"。
     *
     * 请你实现这个将字符串进行指定行数变换的函数：
     *
     * string convert(string s, int numRows);
     * 示例 1:
     *
     * 输入: s = "LEETCODEISHIRING", numRows = 3
     * 输出: "LCIRETOESIIGEDHN"
     * 示例 2:
     *
     * 输入: s = "LEETCODEISHIRING", numRows = 4
     * 输出: "LDREOEIIECIHNTSG"
     * 解释:
     *
     * L     D     R
     * E   O E   I I
     * E C   I H   N
     * T     S     G
     *
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/zigzag-conversion
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    public String convert(String s, int numRows) {

        /**
         *
         * 将一个给定字符串根据给定的行数，以从上往下、从左到右进行 Z 字形排列。
         *
         * 比如输入字符串为 "LEETCODEISHIRING" 行数为 3 时，排列如下：
         *
         * L   C   I   R
         * E T O E S I I G
         * E   D   H   N
         *
         * l     d      r
         * e   o e   i  i
         * e c   i h    n
         * t     s      g
         *
         * r = 1
         * r = 2
         * r = 3
         *
         * 0     6      12        18
         * 1   5 7   11 13    17
         * 2 4   8 10   14 16
         * 3     9      15
         *
         * 对于n行的, s中的第i个字符：
         * 对余数进行判断
         * i%(2n-2) == 0 ----> row0
         * i%(2n-2) == 1 & 2n-2-1 ----> row1
         * i%(2n-2) == 2 & 2n-2-2 ----> row2
         * i%(2n-2) == n-1 ----> row(n-1)
         **/

        if (numRows == 1) return s;

        // 初始化 行数  如果行数小与字符串
        List<StringBuilder> rows = new ArrayList<>();
        for (int i = 0; i < Math.min(numRows, s.length()); i++)
            rows.add(new StringBuilder());

        // 行数
        int curRow = 0;
        // 方向标记
        boolean goingDown = false;

        for (char c : s.toCharArray()) {
            // 每行添加数据
            rows.get(curRow).append(c);
            // 如果是 0 或者与行数相等 则改变方向 每numRows行后改变方向
            if (curRow == 0 || curRow == numRows - 1)
                goingDown = !goingDown;
            // goingDown 方向改变 123212321   1234 321 234 321
            curRow += goingDown ? 1 : -1;
        }

        // 数据转化 拼接
        StringBuilder ret = new StringBuilder();
        for (StringBuilder row : rows) ret.append(row);
        return ret.toString();

    }

    /**
     *
     * 0     6      12        18
     * 1   5 7   11 13    17
     * 2 4   8 10   14 16
     * 3     9      15
     */
    public String convertAn(String s, int numRows) {
        if (s.toCharArray().length <= numRows || numRows == 1) {
            return s;
        }

        // s 字符串
        // numRows 行数
        // 行数 是 numRows 或者是小于行的字符串
        // 初始化行数
        List<StringBuffer> rows = new ArrayList<>(numRows);
        for (int i = 0; i < numRows; i++) {
            rows.add(new StringBuffer());
        }

        // 指针行号 1234321234321
        int curRow = 0;
        boolean flag = false;
        for (char str : s.toCharArray()) {
            // 拼接数据进来 rowc 上下扫描
            rows.get(curRow).append(str);

            // rowc ++ 或者 --   +1或者-1
            // 0 到 3 ++   到0 时 再更变符合
            if (curRow == 0 || curRow == numRows - 1) {
                flag = !flag;
            }
            if (flag) {
                ++curRow;
            } else {
                --curRow;
            }
        }

        // 取值
        StringBuffer stringBuffer = new StringBuffer();
        for (StringBuffer sb : rows)
            stringBuffer.append(sb);
        return stringBuffer.toString();
    }



}
