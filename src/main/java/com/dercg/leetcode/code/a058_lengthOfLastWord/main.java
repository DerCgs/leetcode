package com.dercg.leetcode.code.a058_lengthOfLastWord;

import java.util.IllegalFormatCodePointException;

public class main {
    /**
     * 给定一个仅包含大小写字母和空格 ' ' 的字符串 s，返回其最后一个单词的长度。
     * <p>
     * 如果字符串从左向右滚动显示，那么最后一个单词就是最后出现的单词。
     * <p>
     * 如果不存在最后一个单词，请返回 0 。
     * <p>
     * 说明：一个单词是指仅由字母组成、不包含任何空格的 最大子字符串。
     * <p>
     *  
     * <p>
     * 示例:
     * <p>
     * 输入: "Hello World"
     * 输出: 5
     */

    public static void main(String[] args) {
        System.out.println("Hello World--->:" + lengthOfLastWordOfficial("Hello World"));
        System.out.println("a --->:" + lengthOfLastWordOfficial("a "));
    }

    public static int lengthOfLastWord(String s) {
        String[] arr = s.split(" ");
        if (arr.length == 0) {
            return 0;
        }

        return arr[arr.length - 1].length();
    }

    public static int lengthOfLastWordV2(String s) {
        if(s.length()==0){
            return 0;
        }
        char[] arr = s.toCharArray();
        int i = arr.length;
        int wordCount = 0;
        while (i > 0) {
            i--;
            if (' ' == arr[i]) {
                if (wordCount > 0) {
                    return wordCount;
                }
            } else {
                wordCount++;
            }
        }
        return wordCount;
    }

    public static int lengthOfLastWordOfficial(String s) {
        if(s.length()==0){
            return 0;
        }
        int i = s.length();
        int end = s.length() - 1;
        int start = 0;

        while (i > 0) {
            i--;
            if (s.charAt(i) == ' ') {
                if (start != 0) {
                    return end - start + 1;
                }
                end--;
            } else {
                start = i;
            }
        }
        return s.charAt(i) == ' ' ? start : end - start + 1;
    }
}
