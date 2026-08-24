package ngrams;

import edu.princeton.cs.algs4.In;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import static ngrams.TimeSeries.MAX_YEAR;
import static ngrams.TimeSeries.MIN_YEAR;

/**
 * 一个对象，提供用于查询的实用方法
 * Google NGrams 数据集（或其子集）。
 *
 * NGramMap 存储来自“单词文件”和“计数”的相关数据
 * 文件”。它不是严格意义上的地图，但它确实提供了额外的信息
 * 功能。
 *
 * @author Josh Hug
 */
public class NGramMap {

    // TODO: 添加任何必要的静态/实例变量。
    private Map<String, Word> words;
    private TimeSeries totalYears;
    private class Word {
        private  String name;
        private TimeSeries ts;

        public Word(String name) {
            this.name = name;
            ts = new TimeSeries();
        }

        public String name() {
            return name;
        }
        public TimeSeries ts() {
            return ts;
        }
        public void add(int year, double count) {
            ts.put(year, count);
        }
    }
    /**
     * 从 WORDSFILENAME 和 COUNTSFILENAME 构造 NGramMap。
     */
    public NGramMap(String wordsFilename, String countsFilename) {
        // TODO: 填写这个构造函数。请参阅规范的“NGramMap Tips”部分以获取帮助。
        In wordIn =  new In(wordsFilename);
        In countIn = new In(countsFilename);
        words = new HashMap<>();
        totalYears = new TimeSeries();
        while (wordIn.hasNextLine()) {
            String line = wordIn.readLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split("\t");
            String word = tokens[0];
            int year = Integer.parseInt(tokens[1]);
            double count = Double.parseDouble(tokens[2]);
            if (words.containsKey(word)) {
                words.get(word).add(year, count);
            } else {
                Word w = new Word(word);
                w.add(year, count);
                words.put(word, w);
            }
        }

        while (countIn.hasNextLine()) {
            String line = countIn.readLine();
            if (line.isEmpty()) {
                continue;
            }
            String[] tokens = line.split(",");
            int year = Integer.parseInt(tokens[0]);
            double count = Double.parseDouble(tokens[1]);
            totalYears.put(year, count);
        }
    }
    private boolean isValid(String word) {
        return words.containsKey(word);
    }
    /**
     * 提供 STARTYEAR 和 ENYEAR 之间（包括两端）的 WORD 历史记录。的
     * 返回的 TimeSeries 应该是一个副本，而不是指向此 NGramMap 的 TimeSeries 的链接。在其他方面
     * 换句话说，对此函数返回的对象所做的更改不应也影响
     * NGramMap。这也称为“防御性副本”。如果该词不在数据文件中，
     * 返回一个空的时间序列。
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        if(!isValid(word)) {
            return new TimeSeries();
        }
        return new TimeSeries(words.get(word).ts(), startYear, endYear);
    }

    /**
     * 提供 WORD 的历史。返回的 TimeSeries 应该是副本，而不是指向此的链接
     * NGramMap 的时间序列。换句话说，对此函数返回的对象进行的更改
     * 不应也影响 NGramMap。这也称为“防御性副本”。如果这个词
     * 不在数据文件中，返回空的 TimeSeries。
     */
    public TimeSeries countHistory(String word) {
        // TODO: Fill in this method.
        if (!isValid(word)) {
            return new TimeSeries();
        }
        return words.get(word).ts().copy();
    }

    /**
     * 返回所有卷中每年记录的总字数的防御副本。
     */
    public TimeSeries totalCountHistory() {
        // TODO: Fill in this method.
        return totalYears.copy();
    }
    /**
     * 提供一个 TimeSeries，其中包含 STARTYEAR 之间每年 WORD 的相对频率
     * 和 ENDYEAR，包括两端。如果该单词不在数据文件中，则返回空
     * 时间序列。
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        // TODO: Fill in this method.
        if (!isValid(word)) {
            return new TimeSeries();
        }
        TimeSeries totalTs = new TimeSeries(totalYears, startYear, endYear);
        TimeSeries wordTs = new TimeSeries(words.get(word).ts(), startYear, endYear);
        return wordTs.dividedBy(totalTs);
    }

    /**
     * 提供一个 TimeSeries，其中包含 WORD 与所有年份相比的相对频率
     * 当年记录的字词。如果该单词不在数据文件中，则返回空
     * 时间序列。
     */
    public TimeSeries weightHistory(String word) {
        // TODO: Fill in this method.
        if (!isValid(word)) {
            return new TimeSeries();
        }
        return words.get(word).ts().dividedBy(totalYears);
    }

    /**
     * 提供 WORDS 中 STARTYEAR 和 之间所有单词每年相对频率的总和
     * ENDYEAR，包括两端。如果某个单词在此时间范围内不存在，则忽略它
     * 而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            TimeSeries tempTs = new TimeSeries(this.words.get(word).ts(), startYear, endYear);
            ts = ts.plus(tempTs);
        }
        return ts.dividedBy(totalYears);
    }

    /**
     * 返回 WORDS 中所有单词每年的相对频率总和。如果一个词没有
     * 存在于这个时间范围内，忽略它而不是抛出异常。
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        // TODO: Fill in this method.
        TimeSeries ts = new TimeSeries();
        for (String word : words) {
            ts.plus(this.words.get(word).ts());
        }
        return ts.dividedBy(totalYears);
    }

    // TODO: Add any private helper methods.
    // TODO: Remove all TODO comments before submitting.
}
