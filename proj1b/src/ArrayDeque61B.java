import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int INIT = 8;
    private int head, tail;
    private int length;
    public ArrayDeque61B() {
        items = (T[]) new Object[INIT];
        size = 0;
        head = 0;
        tail = -1;
        length = INIT;
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
        length = number;
    }
    @Override
    public void addFirst(T x) {
        size++;
        int index = Math.floorMod(--head, length);
        items[index] = x;
        if (size == length) {
            resize(2 * length);
        }
    }

    @Override
    public void addLast(T x) {
        size++;
        int index = Math.floorMod(++tail, length);
        items[index] = x;
        if (size == length) {
            resize(2 * length);
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
        int index = Math.floorMod(head, length);
        T res = items[index];
        items[index] = null;
        head++;
        size--;
        if (4 * size < length) {
            resize(length / 2 + 1);
        }
        return res;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        int index = Math.floorMod(tail, length);
        T res = items[index];
        items[index] = null;
        tail--;
        size--;
        if (4 * size < length) {
            resize(length / 2 + 1);
        }
        return res;
    }

    @Override
    public T get(int index) {
        index = Math.floorMod(index + head, length);
        return items[index];
    }

    @Override
    public T getRecursive(int index) {
        throw new UnsupportedOperationException("No need to implement getRecursive for proj 1b");
    }
}
