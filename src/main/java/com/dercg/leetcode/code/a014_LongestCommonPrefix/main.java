package com.dercg.leetcode.code.a014_LongestCommonPrefix;

public class main {
    /**
     * 编写一个函数来查找字符串数组中的最长公共前缀。
     * <p>
     * 如果不存在公共前缀，返回空字符串 ""。
     * <p>
     * 示例 1:
     * <p>
     * 输入: ["flower","flow","flight"]
     * 输出: "fl"
     * 示例 2:
     * <p>
     * 输入: ["dog","racecar","car"]
     * 输出: ""
     * 解释: 输入不存在公共前缀。
     * 说明:
     * <p>
     * 所有输入只包含小写字母 a-z
     */
    public static void main(String[] args) {
        String[] strs = new String[]{"flower","flow","flight"};
        System.out.println(longestCommonPrefixV2(strs));
        System.out.println("str:" + "123456".indexOf("1234"));
        System.out.println("str:" + "123456".indexOf("234"));
        System.out.println("str:" + "123456".indexOf("987"));
        System.out.println("str:" + "123456".substring(0, 6));
        System.out.println("str:" + "123456".substring(0, 5));
        System.out.println("str:" + "123456".substring(0, 0));
    }

    public static String longestCommonPrefix(String[] strs) {
        String item = "";
        for (int i = 0; i < strs.length; i++) {
            if (i == 0) {
                item = strs[i];
                continue;
            }

            if (item == "") {
                return item;
            }
            String longStr, shortStr;
            if (item.length() > strs[i].length()) {
                longStr = item;
                shortStr = strs[i];
            } else {
                longStr = strs[i];
                shortStr = item;
            }

            item = "";
            for (int j = 0; j < shortStr.length(); j++) {
                if (shortStr.charAt(j) == longStr.charAt(j)) {
                    item += shortStr.charAt(j);
                    continue;
                } else {
                    break;
                }
            }
            if (item == "") {
                return item;
            }
        }
        return item;
    }

    // 水平扫描法
    public static String longestCommonPrefixV1(String[] strs) {
        if (strs.length == 0) return "";
        String prefix = strs[0];
        for (int i = 1; i < strs.length; i++)
            while (strs[i].indexOf(prefix) != 0) {
                prefix = prefix.substring(0, prefix.length() - 1);
                if (prefix.isEmpty()) return "";
            }
        return prefix;
    }

    // 水平扫描 列扫描
    public static String longestCommonPrefixV2(String[] strs) {
        if (strs.length == 0) return "";
        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);
            for (int j = 1; j < strs.length; j++) {
                if (strs[j].length() == i || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }

}
