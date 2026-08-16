package deque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T> {
    @Override
    public Iterator<T> iterator() {
        return new LinkedListDeque61BIterator();
    }
    private class LinkedListDeque61BIterator implements Iterator<T> {
        private Node p;
        public LinkedListDeque61BIterator() {
            p = sentinel;
        }
        @Override
        public boolean hasNext() {
            return p.next != sentinel;
        }

        @Override
        public T next() {
            T returnItem = p.item;
            p = p.next;
            return returnItem;
        }
    }
    private class Node {
        T item;
        Node prev;
        Node next;

        public Node(T item) {
            this.item = item;
            this.prev = null;
            this.next = null;
        }

    }
    private Node sentinel;
    private int size;
    public LinkedListDeque61B() {
        sentinel = new Node(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T x) {
        Node newnode = new Node(x);
        newnode.next = sentinel.next;
        sentinel.next.prev = newnode;
        sentinel.next = newnode;
        newnode.prev = sentinel;
        size++;

    }

    @Override
    public void addLast(T x) {
        Node newnode = new Node(x);
        newnode.prev = sentinel.prev;
        sentinel.prev.next = newnode;
        newnode.next = sentinel;
        sentinel.prev = newnode;
        size++;

    }

    @Override
    public List<T> toList() {
        List<T> returnList = new ArrayList<>();
        Node p = sentinel.next;
        while (p != sentinel) {
            returnList.add(p.item);
            p = p.next;
        }
        return returnList;
    }

    @Override
    public boolean isEmpty() {
        return (sentinel.next == sentinel && sentinel.prev == sentinel);
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
        T res = sentinel.next.item;
        sentinel.next = sentinel.next.next;
        sentinel.next.prev = sentinel;
        size--;
        return res;
    }

    @Override
    public T removeLast() {
        if (this.isEmpty()) {
            return null;
        }
        T res = sentinel.prev.item;
        sentinel.prev = sentinel.prev.prev;
        sentinel.prev.next = sentinel;
        size--;
        return res;
    }

    @Override
    public T get(int index) {
        if (this.isEmpty() || index < 0 || index >= size) {
            return null;
        }
        Node p = sentinel.next;
        while (index > 0) {
            p = p.next;
            index--;
        }
        return p.item;
    }
    private  T getRecursiveHelper(int index, Node p) {
        if (index == 0) {
            return p.item;
        }
        return getRecursiveHelper(index - 1, p.next);
    }
    @Override
    public T getRecursive(int index) {
        if (this.isEmpty() || index < 0 || index >= size) {
            return null;
        }
        return getRecursiveHelper(index, sentinel.next);
    }
}
