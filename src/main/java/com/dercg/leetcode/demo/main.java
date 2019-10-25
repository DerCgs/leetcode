package com.dercg.leetcode.demo;

import org.joda.time.DateTime;

public class main {
    public static void main(String[] args) {
        System.out.println(DateTime.now().year().get());
        System.out.println(DateTime.now().monthOfYear().get());
        System.out.println("123456".indexOf(""));
        System.out.println("123456".indexOf("1"));
        System.out.println("123456".indexOf("3"));
        System.out.println("123456".indexOf("9"));

        System.out.println("123456789".substring(7));
        String s = "12345".intern();
        StringBuffer sb = new StringBuffer();
        StringBuilder stringBuilder = new StringBuilder();
    }
}
