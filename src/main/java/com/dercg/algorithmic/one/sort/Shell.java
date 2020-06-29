package com.dercg.algorithmic.one.sort;

import com.dercg.algorithmic.one.towsumfast.LoadDataFile;

import java.io.IOException;

public class Shell extends Base {
    @Override
    public void sort(Comparable[] a) {
        int n = a.length;
        int h = 1;
        while (h < n / 3) {
            h = 3 * h + 1;
        }

        while (h >= 1) {
            for (int i = h; i < n; i++) {
                for (int j = i; j >= h && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    public static void main(String[] args) throws IOException {
        Integer[] data = LoadDataFile.loadFile("/Users/changlu/Code/demo/leetcode/src/main/resources/datas/unsort1000000number.txt");
        Shell s = new Shell();
        long start = System.currentTimeMillis();
        System.out.println(isSorted(data));
        s.sort(data);
        long end = System.currentTimeMillis();
        System.out.println(isSorted(data));
        System.out.println(end - start);
    }

}
