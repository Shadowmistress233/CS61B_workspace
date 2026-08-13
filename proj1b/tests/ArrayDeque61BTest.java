import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

    @Test
    @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
    void noNonTrivialFields() {
        List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
                .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
                .toList();

        assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
    }

    @Test
    public void addTest() {
        Deque61B<Integer> al = new ArrayDeque61B<>();
        assertThat(al.isEmpty()).isTrue();
        assertThat(al.size()).isEqualTo(0);
        al.addFirst(1);
        assertThat(al.size()).isEqualTo(1);
        al.addFirst(2);
        al.addLast(3);
        al.addLast(5);
        // [2, 1, 3, 5]
        assertThat(al.isEmpty()).isFalse();
        assertThat(al.size()).isEqualTo(4);
        assertThat(al.toList()).containsExactly(2, 1, 3, 5).inOrder();
        assertThat(al.get(0)).isEqualTo(al.removeFirst());
        assertThat(al.toList()).containsExactly(1, 3, 5).inOrder();
        assertThat(al.get(al.size() - 1)).isEqualTo(al.removeLast());
        assertThat(al.toList()).containsExactly(1, 3).inOrder();
        al.removeLast();
        al.removeLast();
        assertThat(al.isEmpty()).isTrue();
        al.addLast(3);
        al.addFirst(5);
        assertThat(al.toList()).containsExactly(5, 3).inOrder();
        for (int i = 0 ;i < 100;i++) {
            al.addFirst(i);
        }
        for (int i = 0 ;i < 100;i++) {
            al.removeLast();
        }
        al.removeFirst();
        al.removeLast();
        assertThat(al.removeLast()).isEqualTo(null);
        for (int i = 0;i < 1000;i++) {
            al.addLast(i);
        }

    }

}
