package com.dercg.leetcode.code.a100_SameTree;

import com.dercg.leetcode.code.TreeNode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Given two binary trees, write a function to check if they are the same or not.
 * <p>
 * Two binary trees are considered the same if they are structurally identical and the nodes have the same value.
 * <p>
 * Example 1:
 * <p>
 * Input:     1         1
 * / \       / \
 * 2   3     2   3
 * <p>
 * [1,2,3],   [1,2,3]
 * <p>
 * Output: true
 * Example 2:
 * <p>
 * Input:     1         1
 * /           \
 * 2             2
 * <p>
 * [1,2],     [1,null,2]
 * <p>
 * Output: false
 * Example 3:
 * <p>
 * Input:     1         1
 * / \       / \
 * 2   1     1   2
 * <p>
 * [1,2,1],   [1,1,2]
 * <p>
 * Output: false
 */
public class main {
    public static void main(String[] args) {
        TreeNode root1 = new TreeNode(1, new TreeNode(3, null, new TreeNode(2)), null);
        TreeNode root2 = new TreeNode(3, new TreeNode(1), new TreeNode(4, new TreeNode(2), null));
        System.out.println(isSameTree_Deep(root1, root1));
        System.out.println(isSameTree_Deep(root1, root2));
        System.out.println(isSameTree_Deep(root2, root2));
        System.out.println(isSameTree_Width(root1, root1));
        System.out.println(isSameTree_Width(root1, root2));
        System.out.println(isSameTree_Width(root2, root2));
    }

    // 广度优先 层序遍历
    public static boolean isSameTree_Width(TreeNode p, TreeNode q) {
        Queue<TreeNode> p_queue = new LinkedList<>();
        Queue<TreeNode> q_queue = new LinkedList<>();

        p_queue.add(p);
        q_queue.add(q);

        while (!p_queue.isEmpty() || !q_queue.isEmpty()) {
            if (p_queue.size() != q_queue.size()) {
                return false;
            }
            p = p_queue.poll();
            q = q_queue.poll();
            if (p == null && q == null) {
                continue;
            }
            if ((p == null && q != null) || (p != null && q == null)) {
                return false;
            }
            if (p.val != q.val) {
                return false;
            }
            p_queue.add(p.left);
            p_queue.add(p.right);
            q_queue.add(q.left);
            q_queue.add(q.right);

        }
        return true;
    }

    // 深度优先 中序遍历
    public static boolean isSameTree_Deep(TreeNode p, TreeNode q) {
        Deque<TreeNode> p_stock = new ArrayDeque<>();
        Deque<TreeNode> q_stock = new ArrayDeque<>();
        while (!p_stock.isEmpty() || !q_stock.isEmpty() || p != null || q != null) {
            if (p_stock.size() != q_stock.size()) {
                return false;
            }

            while (p != null || q != null) {
                if (p != null) {
                    p_stock.push(p);
                    p = p.left;
                }
                if (q != null) {
                    q_stock.push(q);
                    q = q.left;
                }
            }

            if (p_stock.size() != q_stock.size()) {
                return false;
            }

            q = q_stock.pop();
            p = p_stock.pop();
            if (q.val != p.val) {
                return false;
            }
            q = q.right;
            p = p.right;
        }
        return true;
    }
}
