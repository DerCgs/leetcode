package com.dercg.algorithmic.one.towsumfast;

import com.dercg.algorithmic.one.BinarySearch;
import com.dercg.algorithmic.utils.Utils;

import java.io.IOException;

public class Sum2 {
    public static int binaryCount(int[] arr) {
        int count = 0;
        for (int value : arr) {
            if (value > 0) {
                break;
            }
            int item = -value;
            if (BinarySearch.search(item, arr) > 0) {
                count++;
            }
        }
        return count;
    }

    public static int normalCount(int[] arr) {
        int count = 0;
        for (int value : arr) {
            if (value > 0) {
                break;
            }
            int item = -value;
            if (BinarySearch.normalSearch(item, arr) > 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        int[] data = LoadDataFile.loadFile(Utils.getRootPath() + "/src/main/resources/datas/init10000number.txt");
        long start = System.currentTimeMillis();
        System.out.println("init10000number binaryCount result-->" + binaryCount(data));
        long end = System.currentTimeMillis();
        System.out.println("init10000number binaryCount time-->" + (end - start));

        start = System.currentTimeMillis();
        System.out.println("init10000number normalCount result-->" + normalCount(data));
        end = System.currentTimeMillis();
        System.out.println("init10000number normalCount time-->" + (end - start));

        System.out.println("*****************************************************************");

        data = LoadDataFile.loadFile(Utils.getRootPath() + "/src/main/resources/datas/init100000number.txt");
        start = System.currentTimeMillis();
        System.out.println("init100000number binaryCount result-->" + binaryCount(data));
        end = System.currentTimeMillis();
        System.out.println("init100000number binaryCount time-->" + (end - start));

        start = System.currentTimeMillis();
        System.out.println("init100000number normalCount result-->" + normalCount(data));
        end = System.currentTimeMillis();
        System.out.println("init100000number normalCount time-->" + (end - start));

        System.out.println("*****************************************************************");

        data = LoadDataFile.loadFile(Utils.getRootPath() + "/src/main/resources/datas/init1000000number.txt");
        start = System.currentTimeMillis();
        System.out.println("init100000number binaryCount result-->" + binaryCount(data));
        end = System.currentTimeMillis();
        System.out.println("init1000000number binaryCount time-->" + (end - start));

        start = System.currentTimeMillis();
        System.out.println("init100000number normalCount result-->" + normalCount(data));
        end = System.currentTimeMillis();
        System.out.println("init1000000number normalCount time-->" + (end - start));

        System.out.println("*****************************************************************");

        data = LoadDataFile.loadFile(Utils.getRootPath() + "/src/main/resources/datas/init10000000number.txt");
        start = System.currentTimeMillis();
        System.out.println("init100000number binaryCount result-->" + binaryCount(data));
        end = System.currentTimeMillis();
        System.out.println("init10000000number binaryCount time-->" + (end - start));

        start = System.currentTimeMillis();
        System.out.println("init100000number normalCount result-->" + normalCount(data));
        end = System.currentTimeMillis();
        System.out.println("init10000000number normalCount time-->" + (end - start));
    }
}
