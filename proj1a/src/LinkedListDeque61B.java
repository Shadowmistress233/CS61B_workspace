import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;
        private Node(T value) {
            item = value;
            next = null;
            prev = null;
        }
    }

    private Node<T> sentinel;
    private int size;
    private Node<T> head;
    public LinkedListDeque61B() {
        sentinel = new Node<>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        head = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T x) {
        Node<T> newnode = new Node<>(x);
        head.next = newnode;
        newnode.prev = head;
        newnode.next =sentinel;
    }

    @Override
    public void addLast(T x) {

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
        return 0;
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
