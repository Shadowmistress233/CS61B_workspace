import org.antlr.v4.runtime.tree.Tree;

import java.util.*;

public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class BSTNode {
        public K key;
        public V value;
        public BSTNode left;
        public BSTNode right;

        public BSTNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.left = null;
            this.right = null;
        }
        public void update(K key, V value) {
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
        Set<K> returnSet = new TreeSet<>();
        for (K elem : this) {
            returnSet.add(elem);
        }
        return returnSet;
    }


    @Override
    public V remove(K key) {
        if (!containsKey(key)) {
            return null;
        }
        V value = get(key);
        root = removeHelper(root, key);
        size--;
        return value;
    }
    private BSTNode removeHelper(BSTNode node, K key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.key);
        if (cmp > 0) {
            node.right = removeHelper(node.right, key);
        } else if (cmp < 0) {
            node.left = removeHelper(node.left, key);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            BSTNode tempNode = node.right;
            while (tempNode.left != null) {
                tempNode = tempNode.left;
            }
            node.key = tempNode.key;
            node.value = tempNode.value;
            node.right = removeHelper(node.right, tempNode.key);
        }
        return node;
    }
    @Override
    public Iterator<K> iterator() {
        return new MapIterator();
    }

    public class MapIterator implements Iterator<K> {
        private Stack<BSTNode> nodes;
        public MapIterator() {
            nodes = new Stack<>();
            if (root == null) {
                return;
            }
            BSTNode node = root;
            while (node != null) {
                nodes.push(node);
                node = node.left;
            }
        }
        @Override
        public boolean hasNext() {
            return !nodes.isEmpty();
        }

        @Override
        public K next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BSTNode node = nodes.pop();
            if (node.right != null) {
                BSTNode tempNode = node.right;
                while (tempNode != null) {
                    nodes.push(tempNode);
                    tempNode = tempNode.left;
                }
            }

            return node.key;
        }
    }
}
