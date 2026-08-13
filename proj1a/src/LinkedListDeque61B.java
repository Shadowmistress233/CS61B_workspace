import java.util.List;

public class LinkedListDeque61B<T> implements Deque61B<T>{
    private static class Node<T> {
        T item;
        Node<T> prev;
        Node<T> next;

        public Node(T item) {
           this.item = item;
           this.prev = null;
           this.next = null;
        }

    }
    Node<T> sentinel;
    private int size;
    public LinkedListDeque61B() {
        sentinel = new Node<>(null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }
    @Override
    public void addFirst(T x) {
        Node<T> newnode = new Node<>(x);
        newnode.next = sentinel.next;
        sentinel.next.prev = newnode;
        sentinel.next = newnode;
        newnode.prev = sentinel;
        size++;

    }

    @Override
    public void addLast(T x) {
        Node<T> newnode = new Node<>(x);
        newnode.prev = sentinel.prev;
        sentinel.prev.next = newnode;
        newnode.next = sentinel;
        sentinel.prev = newnode;
        size++;

    }

    @Override
    public List<T> toList() {

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
