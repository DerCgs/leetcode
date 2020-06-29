package com.dercg.algorithmic.one.sort;

import com.dercg.algorithmic.one.towsumfast.LoadDataFile;

import java.io.IOException;

// 选择排序跟插入数据的初始顺序没有关系，只和比较次数有关
public class Selection extends Base {
    public void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (!less(a[min], a[j])) min = j;
            }
            exch(a, i, min);
        }
    }

    public static void main(String[] args) throws IOException {
        Integer[] data = LoadDataFile.loadFile("/Users/changlu/Code/demo/leetcode/src/main/resources/datas/unsort10000number.txt");
        Selection s = new Selection();
        System.out.println(isSorted(data));
        s.sort(data);
        System.out.println(isSorted(data));
    }
}
