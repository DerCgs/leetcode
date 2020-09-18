package com.dercg.leetcode.code.a094_BinaryTreeInorderTraversal;

import java.util.*;

/**
 * Given the root of a binary tree, return the inorder traversal of its nodes' values.
 * <p>
 * Follow up: Recursive solution is trivial, could you do it iteratively?
 * <p>
 * Example 1:
 * Input: root = [1,null,2,3]
 * Output: [1,3,2]
 * <p>
 * Constraints:
 * The number of nodes in the tree is in the range [0, 100].
 * -100 <= Node.val <= 100
 * <p>
 * Definition for a binary tree node.
 * public class TreeNode {
 * int val;
 * TreeNode left;
 * TreeNode right;
 * TreeNode() {}
 * TreeNode(int val) { this.val = val; }
 * TreeNode(int val, TreeNode left, TreeNode right) {
 * this.val = val;
 * this.left = left;
 * this.right = right;
 * }
 * }
 */
public class main {
    public static void main(String[] args) {
        TreeNode root = new TreeNode(3, new TreeNode(6, new TreeNode(5, null, new TreeNode(2))
                , new TreeNode(4, new TreeNode(9), null)), new TreeNode(8, new TreeNode(12), new TreeNode(17)));

        List<Integer> result = Solution.inorderRecursive(root);
        System.out.println(Arrays.toString(result.toArray()));

        result = Solution.inorderTraversal(root);
        System.out.println(Arrays.toString(result.toArray()));
    }

    static class Solution {
        // Morris 中序遍历(暂时理解不了)
        // 迭代法 (显式的使用栈)
        public static List<Integer> inorderTraversal(TreeNode root) {
            List<Integer> result = new ArrayList<>();
            Deque<TreeNode> stock = new LinkedList<>();
            while (root != null || !stock.isEmpty()) {
                while (root != null) {
                    stock.push(root);
                    root = root.left;
                }
                root = stock.pop();
                result.add(root.val);
                root = root.right;
            }
            return result;
        }

        // 递归法 中序遍历
        private static final List<Integer> resultRecursive = new ArrayList<>();
        public static List<Integer> inorderRecursive(TreeNode root) {
            if (root.left != null) {
                inorderRecursive(root.left);
            }
            resultRecursive.add(root.val);
            if (root.right != null) {
                inorderRecursive(root.right);
            }
            return resultRecursive;
        }
    }

    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        public TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
