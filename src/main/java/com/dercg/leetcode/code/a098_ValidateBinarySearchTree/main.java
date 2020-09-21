package com.dercg.leetcode.code.a098_ValidateBinarySearchTree;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Given a binary tree, determine if it is a valid binary search tree (BST).
 * <p>
 * Assume a BST is defined as follows:
 * <p>
 * The left subtree of a node contains only nodes with keys less than the node's key.
 * The right subtree of a node contains only nodes with keys greater than the node's key.
 * Both the left and right subtrees must also be binary search trees.
 *  
 * <p>
 * Example 1:
 * <p>
 * 2
 * / \
 * 1   3
 * <p>
 * Input: [2,1,3]
 * Output: true
 * Example 2:
 * <p>
 * 5
 * / \
 * 1   4
 *      / \
 *     3   6
 * <p>
 * Input: [5,1,4,null,null,3,6]
 * Output: false
 * Explanation: The root node's value is 5 but its right child's value is 4.
 */
public class main {

    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(2, new TreeNode(1), new TreeNode(3));
        TreeNode root2 = new TreeNode(5, new TreeNode(1), new TreeNode(4, new TreeNode(3), new TreeNode(6)));
        TreeNode root3 = new TreeNode(1, new TreeNode(1), null);
        TreeNode root4 = new TreeNode(10, new TreeNode(5), new TreeNode(15, new TreeNode(6), new TreeNode(20)));
        TreeNode root5 = new TreeNode(10, new TreeNode(5, new TreeNode(2), new TreeNode(8)), new TreeNode(16, new TreeNode(14), new TreeNode(20)));
        // [1,1]
        // [10,5,15,null,null,6,20]
//        System.out.println(new Solution().isValidBST_Recursive(root1, null, null));
//        System.out.println(new Solution().isValidBST_Recursive(root2, null, null));
//        System.out.println(new Solution().isValidBST_Recursive(root3, null, null));
//        System.out.println(new Solution().isValidBST_Recursive(root4, null, null));
        new Solution().isValidBST_inOrder(root1);
        System.out.println("\n");
        new Solution().isValidBST_inOrder(root2);
        System.out.println("\n");
        new Solution().isValidBST_inOrder(root3);
        System.out.println("\n");
        new Solution().isValidBST_inOrder(root4);
        System.out.println("\n");
        new Solution().isValidBST_inOrder(root5);
    }

    static class Solution {
        // 中序遍历，BST 的特性是，中序遍历为递增数列，因此只需检查中序遍历的后一个数是否比前一个数大
        public boolean isValidBST_inOrder(TreeNode root) {
            Deque<TreeNode> stack = new LinkedList<>();
            double inorder = -Double.MAX_VALUE;

            while (!stack.isEmpty() || root != null) {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }

                root = stack.pop();
                // 如果中序遍历得到的节点小于等于前一个 inorder，说明不是二叉搜索树
                if (root.val <= inorder) {
                    return false;
                }

                inorder = root.val;
                System.out.println(inorder);
                root = root.right;
            }
            return true;
        }

        // 递归解法
        public boolean isValidBST_Recursive(TreeNode root, Integer lower, Integer upper) {
            if (root == null) {
                return true;
            }

            if (lower != null && lower >= root.val) {
                return false;
            }
            if (upper != null && upper <= root.val) {
                return false;
            }

            if (!isValidBST_Recursive(root.left, lower, root.val)) {
                return false;
            }
            if (!isValidBST_Recursive(root.right, root.val, upper)) {
                return false;
            }
            return true;
        }
    }

    static class TreeNode {
        TreeNode left;
        TreeNode right;
        int val;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
