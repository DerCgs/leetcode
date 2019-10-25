package com.dercg.leetcode.demo.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class FileChannleDemo {
    public void demo(String src, String dst) throws Exception {
        // 通过本地IO的方式获取通道
        FileInputStream fileInputStream = new FileInputStream(dst);
        // 得到文件的输入通道
        FileChannel inChannel = fileInputStream.getChannel();

        /****************************************************/
        //jdk 1.7后，通过静态方法open()获取通道
        FileChannel.open(Paths.get(dst), StandardOpenOption.WRITE);

    }

    // 使用FileChannel配合缓冲区实现文件复制功能
    public void demo1(String src, String dst) {
        FileInputStream fis = null;
        FileOutputStream fos = null;

        // 获取通道
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream(src);
            fos = new FileOutputStream(dst);

            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            // 分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            // 将通道中的数据存入缓冲区中
            while (inChannel.read(buf) != -1) {
                // 切换到读取数据的模式
                buf.flip();
                // 将缓冲区中的数据写入通道中
                outChannel.write(buf);
                buf.clear();
            }
        } catch (Exception e) {
            try {
                if (inChannel != null) {
                    inChannel.close();
                }
                if (outChannel != null) {
                    outChannel.close();
                }
                if (fis != null) {
                    fis.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception ex) {

            }
        }
    }

    // 使用内存映射文件的方式实现文件复制的功能(直接操作缓冲区)
    public void demo2(String src, String dst) throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(dst)
                , StandardOpenOption.WRITE
                , StandardOpenOption.CREATE
                , StandardOpenOption.READ);

        // 内存映射文件
        MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对缓冲区进行数据的读写操作
        byte[] dstByte = new byte[inMappedBuf.limit()];
        inMappedBuf.get(dstByte);
        outMappedBuf.put(dstByte);
    }

    // 通道之间通过transfer() 实现数据的传输(直接操作缓冲区)
    public void demo3(String src, String dst) throws Exception {
        FileChannel inChannel = FileChannel.open(Paths.get(src), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get(dst)
                , StandardOpenOption.WRITE
                , StandardOpenOption.CREATE
                , StandardOpenOption.READ);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        inChannel.close();
        outChannel.close();
    }

    // 分散读取（分散读取(scatter)：将一个通道中的数据分散读取到多个缓冲区中）
    // 聚集写入（* 聚集写入(gather)：将多个缓冲区中的数据集中写入到一个通道中）
    public void demo4(String src, String dst) throws Exception {
        FileInputStream fis = new FileInputStream(src);
        FileChannel channel1 = fis.getChannel();
        // 分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(1000);
        ByteBuffer buf2 = ByteBuffer.allocate(2000);
        // 分散读取
        ByteBuffer[] bufs = {buf1, buf2};
        channel1.read(bufs);

        for (ByteBuffer byteBuffer : bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(), 0, bufs[0].limit()));
        System.out.println(new String(bufs[1].array(), 0, bufs[1].limit()));

        // 聚集写入
        RandomAccessFile raf2 = new RandomAccessFile(dst, "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);
    }

    //
    public static void main(String[] args) throws Exception {
        new FileChannleDemo().demo3("/Users/changlu/Desktop/ubuntu-14.04.4-server-amd64.iso", "/Users/changlu/Desktop/ios.demo");
    }
}
