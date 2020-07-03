package com.dercg.algorithmic.one.sort;

import com.dercg.algorithmic.one.towsumfast.LoadDataFile;

import java.io.IOException;

/**
 * 改进：小数组时使用插入排序 10~15%
 */
public class Merge extends Base {
    Comparable[] aux;

    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, a.length - 1);
    }

    private void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = (lo + hi) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);
        merge(a, lo, mid, hi);
    }

    public void merge(Comparable[] a, int lo, int mid, int hi) {
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) throws IOException {
        Integer[] data = LoadDataFile.loadFile("/Users/changlu/Code/demo/leetcode/src/main/resources/datas/unsort" + 10000000 + "number.txt");
        Merge s = new Merge();
        System.out.println(isSorted(data));
        long start = System.currentTimeMillis();
        s.sort(data);
        long end = System.currentTimeMillis();
        System.out.println(isSorted(data));
        System.out.println(end - start);
    }
}
