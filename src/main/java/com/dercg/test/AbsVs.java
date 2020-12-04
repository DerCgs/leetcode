package com.dercg.test;

public class AbsVs {
    public static void main(String[] args) {
        int j = 1;
        while (j < 10) {
            System.out.println(j + "次：");
            long start = System.currentTimeMillis();
            int a = 0;
            for (int i = 200000000*j; i > -200000000*j; i--) {
                a = i & 0x7FFFFFFF;
            }


            long end = System.currentTimeMillis();
            System.out.println(end - start);
            start = System.currentTimeMillis();
            for (int i = 200000000*j; i > -200000000*j; i--) {
                Math.abs(i);
            }
            end = System.currentTimeMillis();
            System.out.println(end - start);
            j++;
        }
    }
}
