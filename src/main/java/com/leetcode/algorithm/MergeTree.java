package com.leetcode.algorithm;

import com.common.TreeNode;

import java.util.*;

import static javax.swing.UIManager.getInt;

public class MergeTree {

    /**
     * 深度优先搜索
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        //如果两个节点都为空，直接返回空就行了
        if (t1 == null && t2 == null)
            return null;
        //如果t1节点为空，就返回t2节点
        if (t1 == null)
            return t2;
        //如果t2节点为空，就返回t1节点
        if (t2 == null)
            return t1;

        TreeNode newNode = new TreeNode(t1.val + t2.val);
        newNode.left = mergeTrees(t1.left, t2.left);
        newNode.right = mergeTrees(t1.right, t2.right);
        return newNode;


    }


    /**
     * 给定一个有相同值的二叉搜索树（BST），找出 BST 中的所有众数（出现频率最高的元素）。
     *
     * 假定 BST 有如下定义：
     *
     * 结点左子树中所含结点的值小于等于当前结点的值
     * 结点右子树中所含结点的值大于等于当前结点的值
     * 左子树和右子树都是二叉搜索树
     * 例如：
     * 给定 BST [1,null,2,2],
     *
     *    1
     *     \
     *      2
     *     /
     *    2
     * 返回[2].
     *
     * 提示：如果众数超过1个，不需考虑输出顺序
     *
     * 进阶：你可以不使用额外的空间吗？（假设由递归产生的隐式调用栈的开销不被计算在内）
     *
     * 来源：力扣（LeetCode）
     * 链接：https://leetcode-cn.com/problems/find-mode-in-binary-search-tree
     * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
     */
    Map<Integer,Integer> map = new HashMap<>();

    public int[] findMode3(TreeNode root) {
        int[] re = {};
        Map<Integer,Integer> map = new HashMap<>();

        traversal(root, map);

        map = sortMapByValue(map);
        map.get(0);

        int a = map.get(0);
        int i = 0;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {

            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());

            if (entry.getValue() < a) {
                re[i++] = entry.getValue();
            } else {
                break;
            }
        }


            return re;
    }

    public Map<Integer, Integer> sortMapByValue(Map<Integer, Integer> oriMap) {
        Map<Integer, Integer> sortedMap = new LinkedHashMap<>();
        if (oriMap != null && !oriMap.isEmpty()) {
            List<Map.Entry<Integer, Integer>> entryList = new ArrayList<Map.Entry<Integer, Integer>>(oriMap.entrySet());
            Collections.sort(entryList,
                    new Comparator<Map.Entry<Integer, Integer>>() {
                        public int compare(Map.Entry<Integer, Integer> entry1,
                                           Map.Entry<Integer, Integer> entry2) {
                            int value1 = 0, value2 = 0;
                            try {
                                value1 = getInt(entry1.getValue());
                                value2 = getInt(entry2.getValue());
                            } catch (NumberFormatException e) {
                                value1 = 0;
                                value2 = 0;
                            }
                            return value2 - value1;
                        }
                    });
            Iterator<Map.Entry<Integer, Integer>> iter = entryList.iterator();
            Map.Entry<Integer, Integer> tmpEntry = null;
            while (iter.hasNext()) {
                tmpEntry = iter.next();
                sortedMap.put(tmpEntry.getKey(), tmpEntry.getValue());
            }
        }
        return sortedMap;
    }


    public void traversal(TreeNode root, Map<Integer,Integer> map) {

        if (map.get(root.val) == null) {
            map.put(root.val, 1);
        } else {
            map.put(root.val,map.get(root.val)+1);
        }
        if (root.left != null) {
            traversal(root.left, map);
        }
        if (root.right != null) {
            traversal(root.right, map);
        }
    }

        int preVal = 0, curTimes = 0, maxTimes = 0;
    ArrayList<Integer> list = new ArrayList<Integer>();

    //二叉搜索树中序遍历是递增顺序
    public void traversal(TreeNode root){
        if(root != null){
            traversal(root.left);
            //判断当前值与上一个值的关系, 更新 curTimes 和 preVal
            if(preVal == root.val){
                curTimes++;
            }else{
                preVal = root.val;
                curTimes = 1;
            }
            //判断当前数量与最大数量的关系, 更新 list 和 maxTimes
            if(curTimes == maxTimes){
                list.add(root.val);
            }else if(curTimes > maxTimes){
                list.clear();
                list.add(root.val);
                maxTimes = curTimes;
            }
            traversal(root.right);
        }
    }

    private TreeNode pre = null;    // 前驱节点
    private int[] result;   // 结果数组
    private int resultCount = 0;    // 结果个数
    private int maxCount = 0;   // 众数数量
    private int currCount = 0;  // 当前重复的数的数量

    public int[] findMode(TreeNode root) {
        findAndFill(root);  // 第一轮，查询 “众数个数”

        // 复位
        this.pre = null;
        this.result = new int[this.resultCount];    // 初始化数组
        this.resultCount = 0;
        this.currCount = 0;

        findAndFill(root);  // 第二轮，填充 众数
        return this.result;
    }

    /**
     * 中根序 遍历 目标二叉树<br/>
     *
     */
    private void findAndFill(TreeNode root) {
        if (root == null) {
            return;
        }
        findAndFill(root.left); // 递归遍历 左子树

        if (this.pre != null && this.pre.val == root.val) { // 与前一个节点的值相等
            this.currCount++;
        } else {
            this.currCount = 1;  // 若 不相等，则 刷新currCount
        }

        if (this.currCount > this.maxCount) {   // 当前最大数 > 最大众数数
            this.maxCount = this.currCount;
            this.resultCount = 1;
        } else if (this.currCount == this.maxCount) {   // 当前最大数 == 最大众数数
            if (this.result != null) {
                this.result[this.resultCount] = root.val;
            }
            this.resultCount++;  // 使 指针向后移动，便于下次录入
        }

        // 进行下轮遍历
        this.pre = root;
        findAndFill(root.right);    // 递归遍历 右子树
    }

    public void traversalTn(TreeNode root){
        if (root == null) {
            return;
        }

        // 遍历左
        traversalTn(root.left);
        if (this.pre != null && this.pre.val == root.val) {
            this.currCount ++;
        } else {
            this.currCount = 1;
        }
        if (this.currCount > this.maxCount) {
            this.maxCount = this.currCount;
            this.resultCount = 1;
        }
        if(root != null){
            traversal(root.left);
            //判断当前值与上一个值的关系, 更新 curTimes 和 preVal
            if(preVal == root.val){
                curTimes++;
            }else{
                preVal = root.val;
                curTimes = 1;
            }
            //判断当前数量与最大数量的关系, 更新 list 和 maxTimes
            if(curTimes == maxTimes){
                list.add(root.val);
            }else if(curTimes > maxTimes){
                list.clear();
                list.add(root.val);
                maxTimes = curTimes;
            }
            traversal(root.right);
        }
    }

}


