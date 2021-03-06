package com.dercg.leetcode.code.a099_RecoverBinarySearchTree;

import com.dercg.leetcode.code.TreeNode;

import java.util.*;

/**
 * Two elements of a binary search tree (BST) are swapped by mistake.
 * <p>
 * Recover the tree without changing its structure.
 * <p>
 * Example 1:
 * <p>
 * Input: [1,3,null,null,2]
 * <p>
 *    1
 *   /
 *  3
 *   \
 *    2
 * <p>
 * Output: [3,1,null,null,2]
 * <p>
 *    3
 *   /
 *  1
 *   \
 *    2
 * Example 2:
 * <p>
 * Input: [3,1,4,null,null,2]
 * <p>
 * 3
 * / \
 * 1   4
 *    /
 *   2
 * <p>
 * Output: [2,1,4,null,null,3]
 * <p>
 * 2
 * / \
 * 1   4
 *    /
 *  3
 */
public class main {
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1, new TreeNode(3, null, new TreeNode(2)), null);
        TreeNode root2 = new TreeNode(3, new TreeNode(1), new TreeNode(4, new TreeNode(2), null));
        consoleTree(root1);
        new Solution().recoverTree(root1);
        System.out.println("\n");
        consoleTree(root1);
        System.out.println("\n");
        consoleTree(root2);
        new Solution().recoverTree(root2);
        System.out.println("\n");
        consoleTree(root2);
//

    }

    public static void consoleTree(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            root = queue.poll();
            if (root == null) {
                System.out.print("null ");
                continue;
            }
            System.out.print(root.val + " ");
            queue.add(root.left);
            queue.add(root.right);
        }
    }

    static class Solution {
        public void recoverTree(TreeNode root) {
            Deque<TreeNode> stock = new LinkedList<>();
            TreeNode swapOne = null, swapTwo = null, preNode = null;

            while (!stock.isEmpty() || root != null) {
                while (root != null) {
                    stock.push(root);
                    root = root.left;
                }

                root = stock.pop();
                if (preNode != null) {
                    Integer preVal = preNode.val;
                    Integer currentVal = root.val;
                    if (preVal >= currentVal) {
                        if (swapOne == null) {
                            swapOne = preNode;
                        }
                        swapTwo = root;
                    }
                }
                preNode = root;
                root = root.right;
            }
            if (swapOne != null) {
                recover(swapOne, swapTwo);
            }
        }

        public void recover(TreeNode swapOne, TreeNode swapTwo) {
            Integer itemVal = swapOne.val;
            swapOne.val = swapTwo.val;
            swapTwo.val = itemVal;
        }
    }
}
