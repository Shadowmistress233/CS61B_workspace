import org.antlr.v4.runtime.tree.Tree;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
    }
    private BSTNode putHelper(BSTNode node, K key, V value) {
        if (node == null) {
            size++;
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
        return containHelper(root, key);
    }
    private  boolean containHelper(BSTNode node, K key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            return containHelper(node.right, key);
        } else if (cmp < 0) {
            return containHelper(node.left, key);
        } else {
            return true;
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }
    public void printInOrder() {

    }

    @Override
    public Set<K> keySet() {
        return keySetHelper(root);
    }
    private Set<K> keySetHelper(BSTNode node) {
        Set<K> returnSet = new TreeSet<>();
        if (node == null) {
            return new TreeSet<>();
        }
        returnSet.addAll(keySetHelper(node.right));
        returnSet.add(node.key);
        returnSet.addAll(keySetHelper(node.left));
        return returnSet;
    }

    @Override
    public V remove(K key) {
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return null;
    }

    public class MapIterator<K> implements Iterator<K> {

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public K next() {
            return null;
        }
    }
}
