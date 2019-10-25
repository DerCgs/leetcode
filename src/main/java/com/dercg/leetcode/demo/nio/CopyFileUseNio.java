package com.dercg.leetcode.demo.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFileUseNio {
    public void copyFileUserNIO(String src, String dst) throws IOException {
        // 声明源文件和目标文件
        FileInputStream fi = new FileInputStream(new File(src));
        FileOutputStream fo = new FileOutputStream(new File(dst));
        // 获得文件传输通道
        FileChannel inChannel = fi.getChannel();
        FileChannel outChannel = fo.getChannel();
        // 获得容器buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        while (true) {
            // 判断是否读完文件
            int eof = inChannel.read(buffer);
            if (eof == -1) {
                break;
            }

            // 重设一下buffer的position=0，limit=position
            buffer.flip();
            // 开始写
            outChannel.write(buffer);
            // 写完要充值buffer,重设position=0,limit=capacity
            buffer.clear();
        }

        inChannel.close();
        outChannel.close();
        fi.close();
        fo.close();
    }

    public static void main(String[] args) throws IOException {
        CopyFileUseNio copyFile = new CopyFileUseNio();
        String src = "/Users/changlu/Desktop/data.txt";
        String dst = "/Users/changlu/Desktop/data01.txt";
        copyFile.copyFileUserNIO(src, dst);
    }
}
