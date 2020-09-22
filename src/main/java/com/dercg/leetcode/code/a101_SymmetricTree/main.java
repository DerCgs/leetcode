package com.dercg.leetcode.code.a101_SymmetricTree;

import com.dercg.leetcode.code.TreeNode;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given a binary tree, check whether it is a mirror of itself (ie, symmetric around its center).
 * <p>
 * For example, this binary tree [1,2,2,3,4,4,3] is symmetric:
 * <p>
 * 1
 * / \
 * 2   2
 * / \ / \
 * 3  4 4  3
 *  
 * <p>
 * But the following [1,2,2,null,3,null,3] is not:
 * <p>
 * 1
 * / \
 * 2   2
 * \   \
 * 3    3
 *  
 * <p>
 * Follow up: Solve it both recursively and iteratively.
 */
public class main {

    // 层序遍历法
    public boolean isSymmetric_LevelOrder(TreeNode root) {
        if (root == null) return true;
        Queue<TreeNode> q = new LinkedList<>();

        TreeNode l = root.left;
        TreeNode r = root.right;
        q.offer(l);
        q.offer(r);
        while (!q.isEmpty()) {
            l = q.poll();
            r = q.poll();
            if (l == null && r == null) {
                continue;
            }

            if ((l == null || r == null) || (l.val != r.val)) {
                return false;
            }

            q.offer(l.left);
            q.offer(r.right);

            q.offer(l.right);
            q.offer(r.left);
        }
        return true;
    }

    // 中序遍历法
    public boolean isSymmetric_inorder(TreeNode root) {
        if (root == null) return true;
        TreeNode leftRoot = root.left;
        TreeNode rightRoot = root.right;
        if (leftRoot == null && rightRoot == null) return true;
        if ((leftRoot != null && rightRoot == null) || (leftRoot == null && rightRoot != null)) return false;

        Deque<TreeNode> leftStock = new LinkedList<>();
        Deque<TreeNode> rightStock = new LinkedList<>();

        while (!leftStock.isEmpty() || !rightStock.isEmpty() || leftRoot != null || rightRoot != null) {
            while (leftRoot != null || rightRoot != null) {
                if ((leftRoot != null && rightRoot == null) || (leftRoot == null && rightRoot != null)) return false;
                if (leftRoot.val != rightRoot.val) return false;
                leftStock.push(leftRoot);
                rightStock.push(rightRoot);
                leftRoot = leftRoot.left;
                rightRoot = rightRoot.right;
            }

            leftRoot = leftStock.pop();
            rightRoot = rightStock.pop();
            leftRoot = leftRoot.right;
            rightRoot = rightRoot.left;
        }

        return true;
    }
}
