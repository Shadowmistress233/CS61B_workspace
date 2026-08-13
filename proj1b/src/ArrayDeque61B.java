import java.util.List;
import java.lang.Math;

public class ArrayDeque61B<T> implements Deque61B<T> {
    private T[] items;
    private int size;
    private int INIT = 8;
    private int head, tail;
    private int getIndex(int idx) {
        return Math.floorMod(idx, size);
    }
    public ArrayDeque61B() {
        items = (T[]) new Object[INIT];
        size = 0;
        head = 0;
        tail = 0;
    }
    @Override
    public void addFirst(T x) {
        int idx = getIndex(--head);
        items[idx] = x;
        size++;
    }

    @Override
    public void addLast(T x) {
        int idx = getIndex(++tail);
        items[idx] = x;
        size++;
    }

    @Override
    public List<T> toList() {
        return List.of();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T removeFirst() {
        return null;
    }

    @Override
    public T removeLast() {
        return null;
    }

    @Override
    public T get(int index) {
        return null;
    }

    @Override
    public T getRecursive(int index) {
        return null;
    }
}
