package com.dercg.leetcode.code.a283_MoveZeroes;
//给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
//
// 示例:
//
// 输入: [0,1,0,3,12]
//输出: [1,3,12,0,0]
//
// 说明:
//
//
// 必须在原数组上操作，不能拷贝额外的数组。
// 尽量减少操作次数。
//
// Related Topics 数组 双指针
// 👍 797 👎 0
public class main {
    class Solution {

        // 双指针双循化
        public void moveZeroes_1(int[] nums) {
            if (nums == null || nums.length == 0) return;
            int j = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    nums[j++] = nums[i];
                }
            }

            while (j < nums.length) {
                nums[j++] = 0;
            }
        }

        // 双指针法
        public void moveZeroes(int[] nums) {
            if (nums == null || nums.length == 0) return;
            int j = 0;
            for (int i = 0; i < nums.length; i++) {
                if (nums[i] != 0) {
                    int temp = nums[j];
                    nums[j++] = nums[i];
                    nums[i] = temp;
                }
            }
        }
    }
}
