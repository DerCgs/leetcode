package com.dercg.leetcode.code.a095_UniqueBinarySearchTreesII;

import java.util.ArrayList;
import java.util.List;

public class main {
    /**
     * Given an integer n, generate all structurally unique BST's (binary search trees) that store values 1 ... n.
     * <p>
     * Example:
     * <p>
     * Input: 3
     * Output:
     * [
     * [1,null,3,2],
     * [3,2,null,1],
     * [3,1,null,null,2],
     * [2,1,3],
     * [1,null,2,null,3]
     * ]
     * Explanation:
     * The above output corresponds to the 5 unique BST's shown below:
     * <p>
     * 1         3     3      2      1
     * \       /     /      / \      \
     * 3     2     1      1   3      2
     * /     /       \                 \
     * 2     1         2                 3
     */

    public static void main(String[] args) {
        System.out.println("start");
        List<TreeNode> list = new main().generateTrees(3);
        for (TreeNode item : list) {
            binaryRecursive(item);
            System.out.println("");
        }
    }

    public static void binaryRecursive(TreeNode node) {
        if (node == null) {
            System.out.printf("null,");
            return;
        }
        System.out.printf(node.val + ",");
        binaryRecursive(node.left);
        binaryRecursive(node.right);
    }

    // 递归法
    public List<TreeNode> generateTrees(int n) {
        if (n <= 0) {
            return new ArrayList<>();
        }
        return generateTrees(1, n);
    }

    public List<TreeNode> generateTrees(int start, int end) {
        List<TreeNode> list = new ArrayList<>();

        if (start > end) {
            list.add(null);
            return list;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> leftList = generateTrees(start, i - 1);
            List<TreeNode> rightList = generateTrees(i + 1, end);

            for (TreeNode left : leftList) {
                for (TreeNode right : rightList) {
                    TreeNode node_i = new TreeNode(i, left, right);
                    list.add(node_i);
                }
            }
        }

        return list;
    }

    class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        public TreeNode() {

        }

        public TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
