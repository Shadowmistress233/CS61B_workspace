import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        public K key ;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode (K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public void update (K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
    private BSTNode root;
    private int size;

    public BSTMap() {
        root = null;
        size = 0;
    }
    @Override
    public void put(K key, V value) {
        root = putHelper(root, key, value);
        size++;
    }
    private BSTNode putHelper(BSTNode node, K key, V value) {
        if (node == null) {
            return new BSTNode(key, value);
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = putHelper(node.right, key, value);
        } else if (cmp < 0) {
            node.left = putHelper(node.left, key, value);
        } else {
            node.update(key, value);
        }
        return node;
    }

    @Override
    public V get(K key) {
        return getHelper(root, key);
    }
    private V getHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp == 0) {
            return node.value;
        } else if (cmp > 0) {
            return getHelper(node.right, key);
        } else {
            return getHelper(node.left, key);
        }
    }

    @Override
    public boolean containsKey(K key) {
        return this.get(key) != null;
    }


    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }
}
