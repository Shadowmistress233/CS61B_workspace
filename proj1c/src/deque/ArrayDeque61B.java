package deque;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private static final int INITIAL_CAPACITY = 8;
    private int head, tail;
    public ArrayDeque61B() {
        items = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
        head = 0;
        tail = -1;
    }
    private void resize(int number) {
        T[] temp = (T[]) new Object[number];
        head = 0;
        tail = size - 1;
        for (int i = 0; i < size; i++) {
            T x = get(i);
            temp[i] = x;
        }
        items = temp;
    }
    @Override
    public void addFirst(T x) {
        size++;
        int index = Math.floorMod(--head, items.length);
        items[index] = x;
        if (size == items.length) {
            resize(2 * items.length);
        }
    }

    @Override
    public void addLast(T x) {
        size++;
        int index = Math.floorMod(++tail, items.length);
        items[index] = x;
        if (size == items.length) {
            resize(2 * items.length);
        }
    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        for (int i = 0 ;i < size; i++) {
            T x = get(i);
            returnList.add(x);
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        if (this.isEmpty()) {
            return null;
        }
        int index = Math.floorMod(head, items.length);
        T res = items[index];
        items[index] = null;
        head++;
        size--;
        if (items.length >= 16 && 4 * size < items.length) {
            resize(items.length / 2);
        }
        return res;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        int index = Math.floorMod(tail, items.length);
        T res = items[index];
        items[index] = null;
        tail--;
        size--;
        if (items.length >= 16 && 4 * size < items.length) {
            resize(items.length / 2);
        }
        return res;
    }

    @Override
    public T get(int index) {
        if (index < 0 || index >= size) {
            return null;
        }
        index = Math.floorMod(index + head, items.length);
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    private class ArrayDequeIterator implements Iterator<T> {
        private int pos;
        public ArrayDequeIterator() {
            pos = 0;
        }
        @Override
        public boolean hasNext() {
            return pos < size;
        }

        @Override
        public T next() {
            T returnItem = get(pos);
            pos += 1;
            return returnItem;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque61B<?> other) {
            if (other.size() != this.size) {
                return false;
            }
            Iterator<T> ti = this.iterator();
            Iterator<?> oi = other.iterator();
            while (ti.hasNext()) {
                if (ti.next() != oi.next()) {
                    return false;
                }
            }
            return true;

        }
        return false;
    }
}

