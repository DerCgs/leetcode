package com.dercg.algorithmic.one.towsumfast;

import com.dercg.algorithmic.utils.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Init_data_file {
    public static void main(String[] args) throws IOException {
        int length = 1000000;
        Set<Integer> set = new LinkedHashSet<>();
        Random r = new Random();
        for (int i = 0; i < length; i++) {
            int item = r.nextInt(length);
//            int random = r.nextInt(10);
//            if (random / 2 == 0) {
                set.add(item);
//            } else {
//                set.add(item - length);
//            }
        }

        Integer[] arr = new Integer[set.size()];

        set.toArray(arr);
//        Arrays.sort(arr);

        String filePath = Utils.getRootPath() + "/src/main/resources/datas/unsort" + length + "number.txt";
        File file = new File(filePath);
        if (!file.exists()) {
            boolean createFileResult = file.createNewFile();
            if (!createFileResult) {
                throw new IOException("创建文件失败");
            }
        }
        FileWriter fileWhitter = new FileWriter(filePath, true);

        StringBuilder sb = new StringBuilder(length);
        for (int item : arr) {
            sb.append(item).append(",");
        }
        sb.replace(sb.lastIndexOf(","), sb.length(), "");

        fileWhitter.write(sb.toString());
        fileWhitter.close();
    }


}