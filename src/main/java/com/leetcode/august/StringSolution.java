package com.leetcode.august;

import java.util.*;

 public class StringSolution {

    public static void main(String[] args) {
        StringSolution solution = new StringSolution();
        String s = "abcba";
        String an = solution.longestPalindrome(s);
        long d = new Date().getTime();
        int way = solution.waysToStep(42);
        long d1 = new Date().getTime();

        System.out.println(d1 - d);

        int way1 = solution.waysToStep1(42);
        long d2 = new Date().getTime();

        System.out.println(d2 - d1);


        System.out.println(an);
        System.out.println(way);
        System.out.println(way1);

    }
    /**
     * 最长回文子串
     * <p>
     * 给定一个字符串 s，找到 s 中最长的回文子串。你可以假设 s 的最大长度为 1000。
     * <p>
     * 示例 1：
     * <p>
     * 输入: "babad"
     * 输出: "bab"
     * 注意: "aba" 也是一个有效答案。
     * 示例 2：
     * <p>
     * 输入: "cbbd"
     * 输出: "bb"
     * <p>
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring
     *
     * @param s cbbd babad a ab abcba
     * @return
     */
    public String longestPalindrome1(String s) {
        //如果s为空，length不大于0，是一个空串，就没有向下执行的必要了
        if (s == null || s.length() < 1 || s == "") {
            return "";
        }

        //String -> char[]
        char[] strChar = s.toCharArray();
        // 存储最长字串 key:char值，value:index下标
        ArrayList<String> maxStr = new ArrayList<>();
        //临时的字串存储空间
        ArrayList<String> tempStr = new ArrayList<>();
        //循环
        for (int i = 0; i < strChar.length; i++) {
            //char -> String
            String str = new String(new char[]{strChar[i]});
            //判断str是否存在于tempStr中  形成一个循环
            if (tempStr.contains(str)) {
                //先判断tempStr的长度是否大于等于maxStr的长度,大于，才能将最长字串覆盖
                if (tempStr.size() > maxStr.size()) {
                    maxStr = new ArrayList<>(tempStr);
                }
                //存储重复字符
                int reIndex = tempStr.indexOf(str);
                // 删除tempStr中的重复字节及其之前的字符
                for (int j = 0; j <= reIndex - 1; j++) {
                    tempStr.remove(0);
                }
                // 最后匹配的就是答案
                //maxStr = new ArrayList<>(tempStr);
                //maxStr.add(str);
            }
            //将当前字符存入tempStr中
            tempStr.add(str);

        }
        //最终判断
        if (maxStr.size() == 0) {
            maxStr.add(new String(new char[]{strChar[0]}));
        }
        //返回最长字串的长度
       // return maxStr.size();
       // maxStr.remove(maxStr.size()-1);
        return String.valueOf(maxStr)
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
                .replace(",", "");

    }


    /**
     * 根据回文子串的定义，枚举所有长度大于等于 22 的子串，依次判断它们是否是回文；
     * 在具体实现时，可以只针对大于“当前得到的最长回文子串长度”的子串进行“回文验证”；
     * 在记录最长回文子串的时候，可以只记录“当前子串的起始位置”和“子串长度”，不必做截取。这一步我们放在后面的方法中实现。
     *
     * 作者：liweiwei1419
     * 链接：https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zhong-xin-kuo-san-dong-tai-gui-hua-by-liweiwei1419/
     * 来源：力扣（LeetCode）
     * 著作权归作者所有。商业转载请联系作者获得授权，非商业转载请注明出处。
     *
     * @param s
     * @return
     */
    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // s.charAt(i) 每次都会检查数组下标越界，因此先转换成字符数组
        char[] charArray = s.toCharArray();

        // 枚举所有长度大于 1 的子串 charArray[i..j]
        for (int i = 0; i < len - 1; i++) {
            for (int j = i + 1; j < len; j++) {
                if (j - i + 1 > maxLen && validPalindromic(charArray, i, j)) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }

    /**
     * 验证子串 s[left..right] 是否为回文串
     */
    private boolean validPalindromic(char[] charArray, int left, int right) {
        while (left < right) {
            if (charArray[left] != charArray[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }


     public int waysToStep1(int n) {
         if (n <= 2) {
             return n;
         }
         int[] dp = new int[n+1];
         dp[1] = 1;
         dp[2] = 2;
         dp[3] = 4;
         for (int i = 4; i < n+1; i++) {
             //取模，对两个较大的数之和取模再对整体取模，防止越界（这里也是有讲究的）
             //假如对三个dp[i-n]都 % 1000000007，那么也是会出现越界情况（导致溢出变为负数的问题）
             //因为如果本来三个dp[i-n]都接近 1000000007 那么取模后仍然不变，但三个相加则溢出
             //但对两个较大的dp[i-n]:dp[i-2],dp[i-3]之和mod 1000000007，那么这两个较大的数相加大于 1000000007但又不溢出
             //取模后变成一个很小的数，与dp[i-1]相加也不溢出
             //所以取模操作也需要仔细分析
             //dp[i] = (dp[i-1] + (dp[i-2] + dp[i-3]) % 1000000007) % 1000000007;
             dp[i] = dp[i-1] + dp[i-2] + dp[i-3];
         }

         return dp[n];
     }

     /**
      * 递归
      * @param n
      * @return
      */
     public int waysToStep(int n) {
         if (n <= 2) {
             return n;
         } else if (n == 3) {
             return 4;
         } else  {
             //              //dp[i] = (dp[i-1] + (dp[i-2] + dp[i-3]) % 1000000007) % 1000000007;
             return (waysToStep(n -1) + (waysToStep(n - 2) + waysToStep(n - 3)) % 1000000007) % 100000000;
         }

     }


}
