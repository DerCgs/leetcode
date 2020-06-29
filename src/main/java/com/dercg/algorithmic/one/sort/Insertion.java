package com.dercg.algorithmic.one.sort;

import com.dercg.algorithmic.one.towsumfast.LoadDataFile;

import java.io.IOException;

public class Insertion extends Base {
    @Override
    public void sort(Comparable[] a) {
        int n = a.length;
        for (int i = 1; i < n; i++) {
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j, j - 1);
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Integer[] data = LoadDataFile.loadFile("/Users/changlu/Code/demo/leetcode/src/main/resources/datas/unsort10000number.txt");
        Insertion s = new Insertion();
        System.out.println(isSorted(data));
        s.sort(data);
        System.out.println(isSorted(data));
        show(data);
    }
}
