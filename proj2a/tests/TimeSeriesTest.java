import ngrams.TimeSeries;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.fail;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testMyTs() {
        TimeSeries ts = new TimeSeries();
        for (int i = 200; i <= 300; i++) {
            ts.put(i, i + 30.0);
        }

        TimeSeries tsTemp = new TimeSeries(ts, 200, 250);
        for (int i = 200; i <= 250; i++) {
            assertThat(ts.get(i)).isEqualTo(tsTemp.get(i));
        }
        try {
            TimeSeries tsOutIndex = new TimeSeries(ts, -1, 2000);
            Assertions.fail("应该产生错误");
        } catch (IndexOutOfBoundsException e) {
            System.out.println("成功捕获错误");
        }

        /**
         * 测试years()方法
         */
        List<Integer> expectedList = new ArrayList<>();
        for (int i = ts.firstKey(); i <= ts.lastKey(); i++) {
            expectedList.add(i);
        }
        assertThat(ts.years()).containsExactlyElementsIn(expectedList);

        /**
         * 测试date()方法
         */
        List<Double> expectedDate = new ArrayList<>();
        for (int key : ts.years()) {
            expectedDate.add(ts.get(key));
        }
        assertThat(ts.data()).containsExactlyElementsIn(expectedDate);

        /**
         * 测试plus()函数
         */
        TimeSeries tsPlus = new TimeSeries();
        assertThat(tsPlus.plus(tsPlus).isEmpty()).isEqualTo(true);
        tsPlus.put(1999, 250.0);
        tsPlus.put(1992, 240.0);
        TimeSeries expectedTs = new TimeSeries();
        expectedTs.put(1999, 500.0);
        expectedTs.put(1992, 480.0);
        assertThat(tsPlus.plus(tsPlus).data()).containsExactlyElementsIn(expectedTs.data());
        /**
         * 测试 dividedBy方法
         */
        TimeSeries expectedDividedBy = new TimeSeries();
        expectedDividedBy.put(1999, 1.0);
        expectedDividedBy.put(1992, 1.0);
        assertThat(tsPlus.dividedBy(tsPlus).data()).containsExactlyElementsIn(expectedDividedBy.data());
    }
} 