package com.dercg.leetcode.code.a066_PlusOne;

import java.util.Arrays;

public class main {
    /**
     * 给定一个由整数组成的非空数组所表示的非负整数，在该数的基础上加一。
     * <p>
     * 最高位数字存放在数组的首位， 数组中每个元素只存储单个数字。
     * <p>
     * 你可以假设除了整数 0 之外，这个整数不会以零开头。
     * <p>
     * 示例 1:
     * <p>
     * 输入: [1,2,3]
     * 输出: [1,2,4]
     * 解释: 输入数组表示数字 123。
     * <p>
     * 示例 2:
     * <p>
     * 输入: [4,3,2,1]
     * 输出: [4,3,2,2]
     * 解释: 输入数组表示数字 4321。
     */
    public static void main(String[] args) {
        int[] a = new int[]{4, 3, 2, 1};
        System.out.println(Arrays.toString(plusOne(a)));
        int[] b = new int[]{1, 2, 3};
        System.out.println(Arrays.toString(plusOne(b)));
    }

    /**
     * +1 需要考虑 非首位为9 ，首位为9需要进位 的两种情况
     * @param digits
     * @return
     */
    public static int[] plusOne(int[] digits) {
        int i = digits.length;
        while (i > 0) {
            i--;
            int item = digits[i] + 1;
            if (item < 10) {
                digits[i] = item;
                return digits;
            }
            digits[i] = 0;
        }

        int[] result = new int[digits.length + 1];
        result[0] = 1;
        return result;
    }
}
