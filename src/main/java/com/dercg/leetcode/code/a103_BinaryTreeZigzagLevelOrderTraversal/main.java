package com.dercg.leetcode.code.a103_BinaryTreeZigzagLevelOrderTraversal;

import com.dercg.leetcode.code.TreeNode;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Given a binary tree, return the zigzag level order traversal of its nodes' values. (ie, from left to right, then right to left for the next level and alternate between).
 * <p>
 * For example:
 * Given binary tree [3,9,20,null,null,15,7],
 * 3
 * / \
 * 9  20
 * /  \
 * 15   7
 * return its zigzag level order traversal as:
 * [
 * [3],
 * [20,9],
 * [15,7]
 * ]
 */
public class main {
    public static void main(String[] args) {
        //[3,9,20,null,null,15,7]
        TreeNode root = new TreeNode(3, new TreeNode(9), new TreeNode(20, new TreeNode(15), new TreeNode(7)));

        System.out.println((new main()).zigzagLevelOrder(root));
    }

    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) return result;
        Queue<TreeNode> q = new LinkedList<>();

        q.offer(root);
        boolean direction = true;

        while (!q.isEmpty()) {
            List<Integer> temp = new ArrayList<>();
            int size = q.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = q.poll();
                temp.add(node.val);
                if (node.left != null) {
                    q.offer(node.left);
                }

                if (node.right != null) {
                    q.offer(node.right);
                }
            }
            if (direction) {
                result.add(temp);
                direction = false;
            } else {
                result.add(reverse(temp));
                direction = true;
            }


        }
        return result;
    }

    public List<Integer> reverse(List<Integer> list) {
        List<Integer> result = new ArrayList<>();
        for (int i = (list.size() - 1); i >= 0; i--) {
            result.add(list.get(i));
        }
        return result;
    }
}
