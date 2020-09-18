package com.dercg.interview._20200912_net_easy;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 用java代码实现LRU，因为java中LinkedHashMap已经实现了LRU缓存淘汰算法，故不能使用LinkedHashMap
 */
public class LRU {
    // 双向链表 + HashMap
    class LRUCache_DLinkedHashMap<k, v> {
        private final int capacity;
        private final HashMap<k, DLinkedNode<k, v>> data = new HashMap<>();
        private final DLinkedNode<k, v> head;
        private final DLinkedNode<k, v> end;

        public LRUCache_DLinkedHashMap(int capacity) {
            this.capacity = capacity;
            head = new DLinkedNode<>();
            end = new DLinkedNode<>();
            head.nextNode = end;
            end.preNode = head;
        }

        public v get(k key) {
            if (data.containsKey(key)) {
                moveHead(head);
                return data.get(key).value;
            }
            return null;
        }

        public void set(k key, v value) {
            if (data.containsKey(key)) {
                DLinkedNode<k, v> dataNode = data.get(key);
                if (dataNode.value.equals(value)) {
                    return;
                }

                dataNode.value = value;
                moveHead(dataNode);
                return;
            }

            if (isFull()) {
                removeEnd();
            }

            addNode(key, value);
            return;
        }

        private void addNode(k key, v value) {
            DLinkedNode dataNode = new DLinkedNode(key, value);
            dataNode.preNode = head;
            dataNode.nextNode = head.nextNode;
            head.nextNode.preNode = dataNode;
            head.nextNode = dataNode;
            data.put(key, dataNode);
        }

        private void moveHead(DLinkedNode node) {
            node.nextNode.preNode = node.preNode;
            node.preNode.nextNode = node.nextNode;

            node.nextNode = head.nextNode;
            node.preNode = head;

            head.nextNode.preNode = node;
            head.nextNode = node;

        }

        private void removeEnd() {
            DLinkedNode endNode = end.preNode;
            data.remove(endNode.key);
            DLinkedNode endPreNode = endNode.preNode;
            end.preNode = endPreNode;
            endPreNode.nextNode = end;
        }

        private boolean isEmpty() {
            return head.nextNode.equals(end);
        }

        private boolean isFull() {
            return data.size() == capacity;
        }

        class DLinkedNode<k, v> {
            private DLinkedNode<k, v> preNode;
            private DLinkedNode<k, v> nextNode;
            private k key;
            private v value;

            public DLinkedNode() {
            }

            public DLinkedNode(k key, v value) {
                this.key = key;
                this.value = value;
            }

        }
    }

    // JAVA 自带的实现
    class LRUCache_Java<k, v> extends LinkedHashMap<k, Optional<v>> {
        private final int capacity;

        // loadFactor 访问负载 默认 0.75 （扩容时的阈值因子，阈值为：capacity*0.75，超过该阈值则扩容）
        // accessOrder 是否基于访问排序，默认为false（即false时为基于插入顺序排序）
        public LRUCache_Java(int capacity) {
            super(capacity, 0.75F, true);
            this.capacity = capacity;
        }

        @Override
        protected boolean removeEldestEntry(Map.Entry<k, Optional<v>> eldest) {
            return size() > capacity;
        }
    }

}

