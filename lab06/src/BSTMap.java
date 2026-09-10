import java.util.Iterator;
import java.util.Set;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        public final K key ;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode (K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
    }
    private BSTNode root;

    public BSTMap() {
        root = null;
    }
    @Override
    public void put(K key, V value) {
        BSTNode child = new BSTNode(key, value);
        putHelper(root, child);
    }
    private void putHelper(BSTNode father, BSTNode child) {
        if (father == null) {
            father = child;
        }

        if (child.key.compareTo(father.key) <= 0) {
            putHelper(father.left, child);
        } else {
            putHelper(father.right, child);
        }
    }
    @Override
    public V get(K key) {
        return getHelper(root, key);
    }
    private V getHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        if (node.key == key) {
            return node.value;
        }
        if (key.compareTo(node.key) <= 0) {
            getHelper(node.left, key);
        } else {
            getHelper(node.right, key);
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public int size() {
        return 0;
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
