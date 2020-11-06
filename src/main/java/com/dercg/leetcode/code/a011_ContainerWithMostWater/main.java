package com.dercg.leetcode.code.a011_ContainerWithMostWater;

// 给你 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
// 在坐标内画 n 条垂直线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0) 。
// 找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水
// 说明：你不能倾斜容器。
public class main {
    public int maxArea(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int max = 0;
        int l = 0;
        int r = height.length - 1;
        while (l < r) {
            int temp = Math.min(height[l], height[r]) * (r - l);
            max = Math.max(max, temp);
            if (height[l] > height[r]) {
                r--;
            } else {
                l++;
            }
        }
        return max;
    }
}
