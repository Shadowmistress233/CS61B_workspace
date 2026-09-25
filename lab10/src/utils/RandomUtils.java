package utils;

import java.util.Random;

/**
 * 用于生成来自不同概率分布（伯努利分布、均匀分布、高斯分布、离散分布
 * 和指数分布）的伪随机数的静态方法工具库。还包括用于打乱数组以及
 * 其他你可能需要的随机相关方法。你可以自由修改此文件。
 * <p>
 * 改编自 https://introcs.cs.princeton.edu/java/22library/StdRandom.java.html
 */
public class RandomUtils {

    /**
     * 返回一个在 [0, 1) 区间内均匀分布的随机实数。
     *
     * @return [0, 1) 区间内均匀分布的随机实数
     */
    public static double uniform(Random random) {
        return random.nextDouble();
    }

    /**
     * 返回一个在 [0, n) 区间内均匀分布的随机整数。
     *
     * @param n 可能的整数个数
     * @return 在 0（包含）与 {@code n}（不包含）之间均匀分布的随机整数
     * @throws IllegalArgumentException 如果 {@code n <= 0}
     */
    public static int uniform(Random random, int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        return random.nextInt(n);
    }


    /**
     * 返回一个在 [0, n) 区间内均匀分布的随机 long 型整数。
     *
     * @param n 可能的 {@code long} 整数个数
     * @return 在 0（包含）与 {@code n}（不包含）之间均匀分布的随机 long 型整数
     * @throws IllegalArgumentException 如果 {@code n <= 0}
     */
    public static long uniform(Random random, long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }

        // https://docs.oracle.com/javase/8/docs/api/java/util/Random.html#longs-long-long-long-
        long r = random.nextLong();
        long m = n - 1;

        // 2 的幂次
        if ((n & m) == 0L) {
            return r & m;
        }

        // 拒绝过采样的候选值（拒绝采样法）
        long u = r >>> 1;
        while (u + m - (r = u % n) < 0L) {
            u = random.nextLong() >>> 1;
        }
        return r;
    }

    ///////////////////////////////////////////////////////////////////////////
    //  以下静态方法仅通过上述静态方法间接依赖 JAVA.UTIL.RANDOM。
    ///////////////////////////////////////////////////////////////////////////


    /**
     * 返回一个在 [a, b) 区间内均匀分布的随机整数。
     *
     * @param a 左端点
     * @param b 右端点
     * @return [a, b) 区间内均匀分布的随机整数
     * @throws IllegalArgumentException 如果 {@code b <= a}
     * @throws IllegalArgumentException 如果 {@code b - a >= Integer.MAX_VALUE}
     */
    public static int uniform(Random random, int a, int b) {
        if ((b <= a) || ((long) b - a >= Integer.MAX_VALUE)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random, b - a);
    }

    /**
     * 返回一个在 [a, b) 区间内均匀分布的随机实数。
     *
     * @param a 左端点
     * @param b 右端点
     * @return [a, b) 区间内均匀分布的随机实数
     * @throws IllegalArgumentException 除非 {@code a < b}
     */
    public static double uniform(Random random, double a, double b) {
        if (!(a < b)) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(random) * (b - a);
    }

    /**
     * 返回一个来自成功概率为 <em>p</em> 的伯努利分布的随机布尔值。
     *
     * @param p 返回 {@code true} 的概率
     * @return 以概率 {@code p} 返回 {@code true}，
     * 以概率 {@code 1 - p} 返回 {@code false}
     * @throws IllegalArgumentException 除非 {@code 0} &le; {@code p} &le; {@code 1.0}
     */
    public static boolean bernoulli(Random random, double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        return uniform(random) < p;
    }

    /**
     * 返回一个来自成功概率为 1/2 的伯努利分布的随机布尔值。
     *
     * @return 以 1/2 的概率返回 {@code true}，以 1/2 的概率返回 {@code false}
     */
    public static boolean bernoulli(Random random) {
        return bernoulli(random, 0.5);
    }

    /**
     * 返回一个来自标准高斯分布（正态分布）的随机实数。
     *
     * @return 来自标准高斯分布（均值为 0，标准差为 1）的随机实数。
     */
    public static double gaussian(Random random) {
        // 使用 Box-Muller 变换的极坐标形式
        double r, x, y;
        do {
            x = uniform(random, -1.0, 1.0);
            y = uniform(random, -1.0, 1.0);
            r = x * x + y * y;
        } while (r >= 1 || r == 0);
        return x * Math.sqrt(-2 * Math.log(r) / r);

        // 备注：y * Math.sqrt(-2 * Math.log(r) / r)
        // 是另一个独立的随机高斯变量
    }

    /**
     * 返回一个来自均值为 &mu;、标准差为 &sigma; 的高斯分布的随机实数。
     *
     * @param mu    均值
     * @param sigma 标准差
     * @return 服从均值为 {@code mu}、标准差为 {@code sigma} 的高斯分布的实数
     */
    public static double gaussian(Random random, double mu, double sigma) {
        return mu + sigma * gaussian(random);
    }

    /**
     * 返回一个来自成功概率为 <em>p</em> 的几何分布的随机整数。
     *
     * @param p 几何分布的参数
     * @return 来自成功概率为 {@code p} 的几何分布的随机整数；
     * 如果 {@code p} （几乎）等于 {@code 1.0}，则返回 {@code Integer.MAX_VALUE}。
     * @throws IllegalArgumentException 除非 {@code p >= 0.0} 且 {@code p <= 1.0}
     */
    public static int geometric(Random random, double p) {
        if (!(p >= 0.0 && p <= 1.0)) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        // 使用高德纳（Knuth）给出的算法
        return (int) Math.ceil(Math.log(uniform(random)) / Math.log(1.0 - p));
    }

    /**
     * 返回一个来自均值为 &lambda; 的泊松分布的随机整数。
     *
     * @param lambda 泊松分布的均值
     * @return 来自均值为 {@code lambda} 的泊松分布的随机整数
     * @throws IllegalArgumentException 除非 {@code lambda > 0.0} 且不为无穷大
     */
    public static int poisson(Random random, double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        }
        // 使用高德纳（Knuth）给出的算法
        // 参见 http://en.wikipedia.org/wiki/Poisson_distribution
        int k = 0;
        double p = 1.0;
        double expLambda = Math.exp(-lambda);
        do {
            k++;
            p *= uniform(random);
        } while (p >= expLambda);
        return k - 1;
    }

    /**
     * 返回一个来自标准帕累托（Pareto）分布的随机实数。
     *
     * @return 来自标准帕累托分布的随机实数
     */
    public static double pareto(Random random) {
        return pareto(random, 1.0);
    }

    /**
     * 返回一个来自形状参数为 &alpha; 的帕累托（Pareto）分布的随机实数。
     *
     * @param alpha 形状参数
     * @return 来自形状参数为 {@code alpha} 的帕累托分布的随机实数
     * @throws IllegalArgumentException 除非 {@code alpha > 0.0}
     */
    public static double pareto(Random random, double alpha) {
        if (!(alpha > 0.0)) {
            throw new IllegalArgumentException("alpha must be positive: " + alpha);
        }
        return Math.pow(1 - uniform(random), -1.0 / alpha) - 1.0;
    }

    /**
     * 返回一个来自柯西（Cauchy）分布的随机实数。
     *
     * @return 来自柯西分布的随机实数。
     */
    public static double cauchy(Random random) {
        return Math.tan(Math.PI * (uniform(random) - 0.5));
    }

    /**
     * 返回一个来自指定离散分布的随机整数。
     *
     * @param probabilities 每个整数出现的概率数组
     * @return 来自离散分布的随机整数：以概率 {@code probabilities[i]} 返回 {@code i}
     * @throws IllegalArgumentException 如果 {@code probabilities} 为 {@code null}
     * @throws IllegalArgumentException 如果数组元素之和不（非常接近）等于 1.0
     * @throws IllegalArgumentException 除非对每个索引 i 都有 {@code probabilities[i] >= 0.0}
     */
    public static int discrete(Random random, double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        double eps = 1E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; i++) {
            if (!(probabilities[i] >= 0.0)) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: "
                                                   + probabilities[i]);
            }
            sum += probabilities[i];
        }
        if (sum > 1.0 + eps || sum < 1.0 - eps) {
            throw new IllegalArgumentException("sum of array entries does not approximately "
                                               + "equal 1.0: " + sum);
        }

        // 当 r （几乎）为 1.0 且累加和由于浮点舍入误差而小于 1.0 时，for 循环可能不会返回值
        while (true) {
            double r = uniform(random);
            sum = 0.0;
            for (int i = 0; i < probabilities.length; i++) {
                sum = sum + probabilities[i];
                if (sum > r) {
                    return i;
                }
            }
        }
    }

    /**
     * 返回一个来自指定离散分布的随机整数。
     *
     * @param frequencies 每个整数出现的频数数组
     * @return 来自离散分布的随机整数：以正比于 frequencies[i] 的概率返回 i
     * @throws IllegalArgumentException 如果 frequencies 为 null
     * @throws IllegalArgumentException 如果所有数组元素均为 0
     * @throws IllegalArgumentException 如果存在任意索引 i 使得 frequencies[i] 为负数
     * @throws IllegalArgumentException 如果频数总和超过 Integer.MAX_VALUE (2^31 - 1)
     */
    public static int discrete(Random random, int[] frequencies) {
        if (frequencies == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        long sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] < 0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: "
                                                   + frequencies[i]);
            }
            sum += frequencies[i];
        }
        if (sum == 0) {
            throw new IllegalArgumentException("at least one array entry must be positive");
        }
        if (sum >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("sum of frequencies overflows an int");
        }

        // 以正比于频数的概率选取索引 i
        double r = uniform(random, (int) sum);
        sum = 0;
        for (int i = 0; i < frequencies.length; i++) {
            sum += frequencies[i];
            if (sum > r) {
                return i;
            }
        }

        // 正常情况下不会执行到此处
        assert false;
        return -1;
    }

    /**
     * 返回一个来自比率为 &lambda; 的指数分布的随机实数。
     *
     * @param lambda 指数分布的比率参数（rate）
     * @return 来自比率为 {@code lambda} 的指数分布的随机实数
     * @throws IllegalArgumentException 除非 {@code lambda > 0.0}
     */
    public static double exp(Random random, double lambda) {
        if (!(lambda > 0.0)) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        return -Math.log(1 - uniform(random)) / lambda;
    }

    /**
     * 将指定数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a 要打乱的数组
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     */
    public static void shuffle(Random random, Object[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // 位于 i 和 n-1 之间
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a 要打乱的数组
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     */
    public static void shuffle(Random random, double[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // 位于 i 和 n-1 之间
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a 要打乱的数组
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     */
    public static void shuffle(Random random, int[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // 位于 i 和 n-1 之间
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a 要打乱的数组
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     */
    public static void shuffle(Random random, char[] a) {
        validateNotNull(a);
        int n = a.length;
        for (int i = 0; i < n; i++) {
            int r = i + uniform(random, n - i);     // 位于 i 和 n-1 之间
            char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定子数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a  要打乱的数组
     * @param lo 左端点（包含）
     * @param hi 右端点（不包含）
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     * @throws IllegalArgumentException 除非 {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, Object[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // 位于 i 和 hi-1 之间
            Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定子数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a  要打乱的数组
     * @param lo 左端点（包含）
     * @param hi 右端点（不包含）
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     * @throws IllegalArgumentException 除非 {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, double[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // 位于 i 和 hi-1 之间
            double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 将指定子数组的元素以均匀随机的顺序重新排列（洗牌）。
     *
     * @param a  要打乱的数组
     * @param lo 左端点（包含）
     * @param hi 右端点（不包含）
     * @throws IllegalArgumentException 如果 {@code a} 为 {@code null}
     * @throws IllegalArgumentException 除非 {@code (0 <= lo) && (lo < hi) && (hi <= a.length)}
     */
    public static void shuffle(Random random, int[] a, int lo, int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);

        for (int i = lo; i < hi; i++) {
            int r = i + uniform(random, hi - i);     // 位于 i 和 hi-1 之间
            int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    /**
     * 返回 <em>n</em> 个元素的均匀随机排列。
     *
     * @param n 元素个数
     * @return 长度为 {@code n} 的数组，包含 {@code 0}, {@code 1}, ..., {@code n-1} 的均匀随机全排列
     * @throws IllegalArgumentException 如果 {@code n} 为负数
     */
    public static int[] permutation(Random random, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        int[] perm = new int[n];
        for (int i = 0; i < n; i++) {
            perm[i] = i;
        }
        shuffle(random, perm);
        return perm;
    }

    /**
     * 返回从 <em>n</em> 个元素中选取 <em>k</em> 个元素的均匀随机排列。
     *
     * @param n 元素总数
     * @param k 选取的元素个数
     * @return 长度为 {@code k} 的数组，包含从 {@code 0}, {@code 1}, ..., {@code n-1} 中选取的 {@code k} 个元素的均匀随机排列
     * @throws IllegalArgumentException 如果 {@code n} 为负数
     * @throws IllegalArgumentException 除非 {@code 0 <= k <= n}
     */
    public static int[] permutation(Random random, int n, int k) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("k must be between 0 and n");
        }
        int[] perm = new int[k];
        for (int i = 0; i < k; i++) {
            int r = uniform(random, i + 1);    // 位于 0 和 i 之间
            perm[i] = perm[r];
            perm[r] = i;
        }
        for (int i = k; i < n; i++) {
            int r = uniform(random, i + 1);    // 位于 0 和 i 之间
            if (r < k) {
                perm[r] = i;
            }
        }
        return perm;
    }

    // 如果 x 为 null 则抛出 IllegalArgumentException
    // （x 可以是 Object[], double[], int[] 等类型）
    private static void validateNotNull(Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }

    // 除非 0 <= lo <= hi <= length，否则抛出异常
    private static void validateSubarrayIndices(int lo, int hi, int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", "
                                               + hi + ")");
        }
    }
}
