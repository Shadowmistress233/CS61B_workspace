import deque.Deque61B;
import deque.LinkedListDeque61B;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;

import java.util.Comparator;
import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;
import java.util.Iterator;

public class LinkedListDeque61BTest {
    @Test
    public void iteratorTest() {
        Deque61B<Integer> lld1 = new LinkedListDeque61B<>();
        for (int i = 1; i <= 100; i = i + 2) {
            lld1.addLast(i);
        }
        Iterator<Integer> it = lld1.iterator();
        int pos = 0;
        while (it.hasNext()) {
            assertThat(it.next()).isEqualTo(lld1.get(pos++));
        }
    }
    @Test
    public void testEqualDeques61B() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();
        Deque61B<String> lld2 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        lld2.addLast("front");
        lld2.addLast("middle");
        lld2.addLast("back");

        assertThat(lld1).isEqualTo(lld2);
    }

    @Test
    public void toStringTest() {
        Deque61B<String> lld1 = new LinkedListDeque61B<>();

        lld1.addLast("front");
        lld1.addLast("middle");
        lld1.addLast("back");

        System.out.println(lld1);
    }
}
