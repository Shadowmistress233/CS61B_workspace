import java.util.Arrays;

public class UnionFind {
    private int[] items;

    /* Creates a UnionFind data structure holding N items. Initially, all
       items are in disjoint sets. */
    public UnionFind(int N) {
        items = new int[N];
        Arrays.fill(items, -1);
    }
    private void validate(int v) {
        if (v >= items.length || v < 0) {
            throw new IllegalArgumentException("Index out of bounds: " + v);
        }
    }
    /* Returns the size of the set V belongs to. */
    public int sizeOf(int v) {
        return -items[find(v)];
    }

    /* Returns the parent of V. If V is the root of a tree, returns the
       negative size of the tree for which V is the root. */
    public int parent(int v) {
        validate(v);
        return items[v];
    }

    /* Returns true if nodes/vertices V1 and V2 are connected. */
    public boolean connected(int v1, int v2) {
        return find(v1) == find(v2);
    }

    /* Returns the root of the set V belongs to. Path-compression is employed
       allowing for fast search-time. If invalid items are passed into this
       function, throw an IllegalArgumentException. */
    public int find(int v) {
        validate(v);
        if (parent(v) < 0) {
            return v;
        }
        items[v] = find(items[v]);
        return items[v];

    }

    /* Connects two items V1 and V2 together by connecting their respective
       sets. V1 and V2 can be any element, and a union-by-size heuristic is
       used. If the sizes of the sets are equal, tie break by connecting V1's
       root to V2's root. Union-ing an item with itself or items that are
       already connected should not change the structure. */
    public void union(int v1, int v2) {
        int v1Root = find(v1);
        int v2Root = find(v2);
        if (v1Root == v2Root) {
            return;
        }
        if (-items[v1Root] > -items[v2Root]) {
            int temp = v1Root;
            v1Root = v2Root;
            v2Root = temp;
        }
        items[v2Root] += items[v1Root];
        items[v1Root] = v2Root;
    }

}
