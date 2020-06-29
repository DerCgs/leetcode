package com.dercg.algorithmic.one.towsumfast;

import com.dercg.algorithmic.utils.Utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LoadDataFile {
    public static Integer[] loadFile(String path) throws IOException {
        BufferedReader in = new BufferedReader(new FileReader(path));
        String str;
        StringBuilder sb = new StringBuilder();
        while ((str = in.readLine()) != null) {
            sb.append(str);
        }

        String result = sb.toString();
        String[] strArr = result.split(",");

        Integer[] dataArr = new Integer[strArr.length];

        for (int i = 0; i < strArr.length; i++) {
            dataArr[i] = Integer.parseInt(strArr[i]);
        }
        return dataArr;
    }

    public static void main(String[] args) throws IOException {
        loadFile(Utils.getRootPath() + "/src/main/resources/datas/init10000number.txt");
    }
}
