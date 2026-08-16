package deque;

import java.util.Comparator;

public class MaxArrayDeque61B<T> extends ArrayDeque61B<T> {

    private final Comparator<T> defalutComparator;
    public MaxArrayDeque61B(Comparator<T> c) {
        defalutComparator = c;
    }

    public T max() {
        return max(defalutComparator);
    }

    public T max(Comparator<T> c) {
        if (this.isEmpty()) {
            return null;
        }
        T result = this.get(0);
        for (T i : this) {
            if (c.compare(result, i) < 0) {
                result = i;
            }
        }
        return result;

    }


}
