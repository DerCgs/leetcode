package com.dercg.algorithmic;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import edu.princeton.cs.algs4.Quick;

public class Test {
    public static void main(String[] args) {
        BloomFilter<String> bf = BloomFilter.create(Funnels.unencodedCharsFunnel(), 10000, 0.03);


        Integer[] a = new Integer[10];

        Quick.sort(a);
    }
}
