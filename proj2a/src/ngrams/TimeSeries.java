package ngrams;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

   /** 如果它有助于加速你的代码，你可以假设你的 NGramMap 的年份参数
     * 介于 1400 和 2100 之间。我们将这些值存储为常量
     * 此处为 MIN_YEAR 和 MAX_YEAR。 */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * 构造一个新的空TimeSeries。
     */
    public TimeSeries() {
        super();
    }

    /**
     * 创建 TS 的副本，但仅限于 STARTYEAR 和 ENDYEAR 之间，
     * 包括两个端点。
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        if (startYear < ts.firstKey() || endYear > ts.lastKey()) {
            throw new IndexOutOfBoundsException();
        }

        for (int i = startYear; i <= endYear; i++) {
            this.put(i, ts.get(i));
        }

    }

    /**
     * 返回此 TimeSeries 的所有年份（按任意顺序）。
     */
    public List<Integer> years() {
        List<Integer> returnList = new ArrayList<>();
        returnList.addAll(this.keySet());
        return returnList;
    }

    /**
     * 返回此 TimeSeries 的所有数据（按任意顺序）。
     * 必须与years() 的顺序相同。
     */
    public List<Double> data() {
        List<Double> returnList = new ArrayList<>();
        for (int key : years()) {
            returnList.add(this.get(key));
        }
        return returnList;
    }

    /**
     * 返回此 TimeSeries 与给定 TS 的逐年总和。换句话说，对于
     * 每年，将此 TimeSeries 的数据与 TS 的数据相加。应该返回一个
     * 新的TimeSeries（不修改此TimeSeries）。
     *
     * 如果两个 TimeSeries 都不包含任何年份，则返回空 TimeSeries。
     * 如果一个 TimeSeries 包含另一个 TimeSeries 不包含的年份，则返回 TimeSeries
     * 应存储包含该年份的 TimeSeries 中的值。
     */
    public TimeSeries plus(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        if (this.isEmpty() && ts.isEmpty()) {
            return returnTs;
        }

        for (int key : this.keySet()) {
            returnTs.put(key, this.get(key));
        }
        for (int key : ts.keySet()) {
            if (returnTs.containsKey(key)) {
                returnTs.put(key, this.get(key) + ts.get(key));
            } else {
                returnTs.put(key, ts.get(key));
            }

        }
        return returnTs;
    }

    /**
     * 返回此 TimeSeries 每年的值除以
     * 同年的 TS 值。应该返回一个新的 TimeSeries（不修改此
     *时间序列）。
     *
     * 如果 TS 缺少此 TimeSeries 中存在的年份，则抛出一个
     * 非法参数异常。
     * 如果 TS 有一个年份不在这个 TimeSeries 中，则忽略它。
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries returnTs = new TimeSeries();
        for (int key : this.keySet()) {
            if (!ts.containsKey(key)) {
                throw new IllegalArgumentException();
            }
            double value = this.get(key) / ts.get(key);
            returnTs.put(key, value);
        }
        return returnTs;
    }
    public TimeSeries copy() {
        TimeSeries returnTs = new TimeSeries(this, firstKey(), lastKey());
        return returnTs;
    }
}
