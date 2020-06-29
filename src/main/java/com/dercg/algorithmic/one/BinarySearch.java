package com.dercg.algorithmic.one;

import com.dercg.algorithmic.one.towsumfast.LoadDataFile;

import java.io.IOException;

public class BinarySearch {
    public static int search(int key, Integer[] a) {
        int lo = 0;
        int hi = a.length - 1;
        int count = 0;
        while (lo <= hi) {
            int mid = (lo + hi) / 2;
            if (key < a[mid]) {
                hi = mid - 1;
            } else if (key > a[mid]) {
                lo = mid + 1;
            } else {
                count++;
//                System.out.println("count:" + count);
                return mid;
            }
            count++;
        }
//        System.out.println("count:" + count);
        return -1;
    }

    public static int normalSearch(int key, Integer[] a) {
        for (int i = 0; i < a.length; i++) {
            if (key == a[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) throws IOException {
        Integer[] data = LoadDataFile.loadFile("/Users/changlu/Code/demo/leetcode/src/main/resources/datas/init10000number.txt");
        long start = System.currentTimeMillis();
        System.out.println(search(-9975, data));
        long end = System.currentTimeMillis();
        System.out.println((end - start));

        start = System.currentTimeMillis();
        System.out.println(normalSearch(-9975, data));
        end = System.currentTimeMillis();
        System.out.println((end - start));
    }
}
