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
        System.out.println((new main()).numTreeCatalan(10));
        System.out.println((new main()).numTreeDP(10));
        System.out.println((new main()).numTreeRecursive(10));
    }

    // 卡塔兰数 C(0)=1,C(n+1)=2(2n+1)/(n+2)C(n)
    public int numTreeCatalan(int n) {
        long c = 1;
        for (int i = 1; i < n; i++) {
            c = c*2*(2*i+1)/(i+2);
        }
        return (int)c;
    }


    // 动态规划 DP
    // 函数推导出公式
    // G(n),F(i,n)
    public int numTreeDP(int n) {
        int[] G = new int[n + 1];
        G[0] = 1;
        G[1] = 1;

        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                G[i] += G[j - 1] * G[i - j];
            }
        }

        return G[n];
    }

    public int numTreeRecursive(int n) {
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
