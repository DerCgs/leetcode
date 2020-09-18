package com.dercg.leetcode.code.a096_UniqueBinarySearchTrees;

import java.util.ArrayList;
import java.util.List;

/**
 * Given n, how many structurally unique BST's (binary search trees) that store values 1 ... n?
 * <p>
 * Example:
 * <p>
 * Input: 3
 * Output: 5
 * Explanation:
 * Given n = 3, there are a total of 5 unique BST's:
 * <p>
 * 1         3     3      2      1
 * \       /     /      / \      \
 * 3     2     1      1   3      2
 * /     /       \                 \
 * 2     1         2                 3
 *  
 * <p>
 * Constraints:
 * <p>
 * 1 <= n <= 19
 */
public class main {
    public static void main(String[] args) {
        System.out.println((new main()).numTree(10));
    }

    public int numTree(int n) {
        return generate(1, n).size();
    }

    // 构造出BST，然后计算个数，但是执行时间不能在系统指定时间内完成，效率不高
    private static List<TreeNode> generate(int start, int end) {
        List<TreeNode> result = new ArrayList<>();
        if (start > end) {
            result.add(null);
            return result;
        }

        for (int i = start; i <= end; i++) {
            List<TreeNode> left = generate(start, i - 1);
            List<TreeNode> right = generate(i + 1, end);

            for (TreeNode leftItem : left) {
                for (TreeNode rightItem : right) {
                    TreeNode item = new TreeNode(leftItem, rightItem, i);
                    result.add(item);
                }
            }
        }
        return result;
    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        public TreeNode(TreeNode left, TreeNode right, int val) {
            this.left = left;
            this.right = right;
            this.val = val;
        }
    }
}
