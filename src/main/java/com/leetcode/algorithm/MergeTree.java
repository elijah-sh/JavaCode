package com.leetcode.algorithm;

import com.common.TreeNode;

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
}


