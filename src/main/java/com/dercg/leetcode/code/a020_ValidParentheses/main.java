package com.dercg.leetcode.code.a020_ValidParentheses;

import java.util.Stack;

public class main {
    /**
     * 给定一个只包括 '('，')'，'{'，'}'，'['，']' 的字符串，判断字符串是否有效。
     * <p>
     * 有效字符串需满足：
     * <p>
     * 左括号必须用相同类型的右括号闭合。
     * 左括号必须以正确的顺序闭合。
     * 注意空字符串可被认为是有效字符串。
     * <p>
     * 示例 1:
     * <p>
     * 输入: "()"
     * 输出: true
     * 示例 2:
     * <p>
     * 输入: "()[]{}"
     * 输出: true
     * 示例 3:
     * <p>
     * 输入: "(]"
     * 输出: false
     * 示例 4:
     * <p>
     * 输入: "([)]"
     * 输出: false
     * 示例 5:
     * <p>
     * 输入: "{[]}"
     * 输出: true
     */
    public static void main(String[] args) {
        System.out.println(isValid("()"));
        System.out.println(isValid("()[]{}"));
        System.out.println(isValid("(]"));
        System.out.println(isValid("([)]"));
        System.out.println(isValid("{[]}"));
    }

    public static boolean isValid(String s) {
        if (s == null || s == "") {
            return true;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            if (stack.empty()) {
                stack.push(s.charAt(i));
                continue;
            }
            char item = s.charAt(i);
            char endEle = stack.peek();
            if (item == '(' || item == '[' || item == '{') {
                stack.push(item);
                continue;
            }

            if (isPoP(endEle, item)) {
                stack.pop();
                continue;
            }

            return false;
        }

        return stack.empty();
    }

    public static boolean isPoP(char c, char b) {
        if (c == '(' && b == ')') {
            return true;
        }

        if (c == '{' && b == '}') {
            return true;
        }

        if (c == '[' && b == ']') {
            return true;
        }

        return false;
    }
}
