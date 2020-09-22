package com.dercg.leetcode.code.a102_BinaryTreeLevelOrderTraversal;

import com.dercg.leetcode.code.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the level order traversal of its nodes' values. (ie, from left to right, level by level).
 * <p>
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * return its level order traversal as:
 * [
 * [3],
 * [9,20],
 * [15,7]
 * ]
 */
public class main {
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();

        if (root == null) {
            return new ArrayList<>();
        }
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = q.size();

            for (int i = 0; i < size; i++) {
                root = q.poll();
                temp.add(root.val);
                if (root.left != null) {
                    q.offer(root.left);
                }
                if (root.right != null) {
                    q.offer(root.right);
                }
            }
            result.add(temp);
        }
        return result;
    }
}
