package com.dercg.leetcode.demo.nio;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    public static void main(String[] args) throws Exception {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);

        System.out.println("limit:" + byteBuffer.limit());
        System.out.println("初始时-->position--->" + byteBuffer.position());
        System.out.println("初始时-->capacity--->" + byteBuffer.capacity());
        System.out.println("初始时-->mark--->" + byteBuffer.mark());

        System.out.println("--------------------------------------");
        // 添加一些数据到缓冲区中
        String s = "Java3y";
        byteBuffer.put(s.getBytes());
        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->" + byteBuffer.limit());
        System.out.println("put完之后-->position--->" + byteBuffer.position());
        System.out.println("put完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());

        byteBuffer.put("JJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJJ"
                .getBytes());
        System.out.println("--------------------------------------");
        // 看一下初始时4个核心变量的值
        System.out.println("put完之后-->limit--->" + byteBuffer.limit());
        System.out.println("put完之后-->position--->" + byteBuffer.position());
        System.out.println("put完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("put完之后-->mark--->" + byteBuffer.mark());

        // 当调用完filp()时：limit是限制读到哪里，而position是从哪里读: 一般我们称filp()为“切换成读模式”
        byteBuffer.flip();
        System.out.println("--------------------------------------");
        // 看一下初始时4个核心变量的值
        System.out.println("flip完之后-->limit--->" + byteBuffer.limit());
        System.out.println("flip完之后-->position--->" + byteBuffer.position());
        System.out.println("flip完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("flip完之后-->mark--->" + byteBuffer.mark());
        System.out.println("--------------------------------------");
        // 创建一个limit()大小的字节数组读取数据
        byte[] bytes = new byte[byteBuffer.limit()];
        // 将数据读取到byte字节数组中
        byteBuffer.get(bytes,0,100);
        //输出数据
        System.out.println(new String(bytes));
        System.out.println("get 100完之后-->limit--->" + byteBuffer.limit());
        System.out.println("get 100完之后-->position--->" + byteBuffer.position());
        System.out.println("get 100完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("get 100完之后-->mark--->" + byteBuffer.mark());

        // compact 切换为写模式，从未读取部分之后开始写
        byteBuffer.compact();
        System.out.println("--------------------------------------");
        // 看一下初始时4个核心变量的值
        System.out.println("compact完之后-->limit--->" + byteBuffer.limit());
        System.out.println("compact完之后-->position--->" + byteBuffer.position());
        System.out.println("compact完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("compact完之后-->mark--->" + byteBuffer.mark());
        // 读完我们还想写数据到缓冲区，那就使用clear()函数，这个函数会“清空”缓冲区：数据没有真正被清空，只是被遗忘掉了
        // 核心变量切换到写模式
        byteBuffer.clear();
        System.out.println("--------------------------------------");
        // 看一下初始时4个核心变量的值
        System.out.println("clear完之后-->limit--->" + byteBuffer.limit());
        System.out.println("clear完之后-->position--->" + byteBuffer.position());
        System.out.println("clear完之后-->capacity--->" + byteBuffer.capacity());
        System.out.println("clear完之后-->mark--->" + byteBuffer.mark());
        System.out.println("--------------------------------------");
    }

    public void demo1() throws Exception {
        Selector selector = Selector.open();
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);
        SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
        selectionKey.channel();

        ByteBuffer bf = ByteBuffer.allocate(1024);


        /**
         * 一旦调用了select()方法，它就会返回一个数值，表示一个或多个通道已经就绪，
         * 然后你就可以通过调用selector.selectedKeys()方法返回的SelectionKey集合来获得就绪的Channel
         */
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterable = selectionKeys.iterator();
        while (keyIterable.hasNext()) {
            SelectionKey key = keyIterable.next();
            if (key.isAcceptable()) {

            } else if (key.isConnectable()) {

            } else if (key.isReadable()) {

            } else if (key.isWritable()) {

            }
            /**
             * 请注意循环中最后的keyIterator.remove()方法。Selector对象并不会从自己的selected key集合中自动移除SelectionKey实例。
             * 我们需要在处理完一个Channel的时候自己去移除。当下一次Channel就绪的时候，Selector会再次把它添加到selected key集合中v
             */
            keyIterable.remove();
        }
    }
}
