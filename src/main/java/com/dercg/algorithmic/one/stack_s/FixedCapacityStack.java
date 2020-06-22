package com.dercg.algorithmic.one.stack_s;

import java.util.Iterator;

// 自定义定量容器栈
public class FixedCapacityStack<T> implements Iterable<T> {
    private T[] a;
    private int size;

    public static void main(String[] args) {
        FixedCapacityStack<Integer> intStack = new FixedCapacityStack<>(4);

        intStack.push(2);
        intStack.push(21);
        intStack.push(23);
        intStack.push(25);
        intStack.push(12);

        System.out.println(intStack.pop());
        Iterator iterator = intStack.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }

    public FixedCapacityStack(int capacity) {
        a = (T[]) new Object[capacity];
        size = 0;
    }

    public void push(T item) {
        if (size == a.length) {
            resize(2 * size);
        }
        a[size++] = item;
    }

    public T pop() {
        return a[--size];
    }

    private void resize(int max) {
        T[] temp = (T[]) new Object[max];
        for (int i = 0; i < size; i++) {
            temp[i] = a[i];
        }
        a = temp;
    }

    @Override
    public Iterator<T> iterator() {
        return new ReverseArrayIterator();
    }

    private class ReverseArrayIterator implements Iterator<T> {
        int i = size;

        @Override
        public boolean hasNext() {
            return i > 0;
        }

        @Override
        public T next() {
            return a[--i];
        }
    }
}
