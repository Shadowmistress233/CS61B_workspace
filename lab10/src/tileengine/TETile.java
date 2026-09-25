package tileengine;

import java.awt.Color;
import java.util.Arrays;
import java.util.Random;

import edu.princeton.cs.algs4.StdDraw;
import utils.RandomUtils;

/**
 * TETile 对象用于表示世界中的单个瓦片。瓦片的二维数组组成棋盘，
 * 并可以使用 TERenderer 类将其绘制到屏幕上。
 *
 * 所有 TETile 对象都必须具有字符、文本颜色和背景颜色，以便在绘制到屏幕时表示该瓦片。
 * 你也可以选择提供合适大小（16x16）的图像文件路径，以替代 Unicode 字符进行绘制。
 * 如果找不到提供的图像路径，draw 方法将自动回退到使用提供的字符和颜色表示，
 * 因此你可以在自己的电脑上自由使用图像瓦片。
 *
 * 提供的 TETile 类是不可变的（immutable），即其所有实例变量都不能被更改。
 * 如果你愿意，也可以将你的 TETile 类修改为可变类。
 */

public class TETile {
    private final char character; // 请勿重命名 character，否则会导致 autograder 报错。
    private final Color textColor;
    private final Color backgroundColor;
    private final String description;
    private final String filepath;
    private final int id;

    /**
     * TETile 对象的完整构造函数。
     * @param character 显示在屏幕上的字符。
     * @param textColor 字符本身的颜色。
     * @param backgroundColor 字符后方绘制的背景颜色。
     * @param description 瓦片的描述，在 GUI 中鼠标悬停在瓦片上时显示。
     * @param filepath 用于该瓦片的图像的完整路径。尺寸必须正确（16x16）。
     * @param id 瓦片的唯一 ID。
     */
    public TETile(char character, Color textColor, Color backgroundColor, String description,
                  String filepath, int id) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = filepath;
        this.id = id;
    }

    /**
     * 不带 filepath 的构造函数。在这种情况下，filepath 将为 null，因此绘制时
     * 根本不会尝试绘制图像，而是直接使用提供的字符和颜色。
     * @param character 显示在屏幕上的字符。
     * @param textColor 字符本身的颜色。
     * @param backgroundColor 字符后方绘制的背景颜色。
     * @param description 瓦片的描述，在 GUI 中鼠标悬停在瓦片上时显示。
     * @param id 瓦片的唯一 ID。
     */
    public TETile(char character, Color textColor, Color backgroundColor, String description, int id) {
        this.character = character;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
        this.description = description;
        this.filepath = null;
        this.id = id;
    }

    /**
     * 创建 TETile t 的副本，但使用给定的 textColor。
     * @param t 要复制的瓦片
     * @param textColor 副本的前景色
     */
    public TETile(TETile t, Color textColor) {
        this(t.character, textColor, t.backgroundColor, t.description, t.filepath, t.id);
    }

    /**
     * 创建 TETile t 的副本，但使用给定的字符 character。
     * @param t 要复制的瓦片
     * @param c 副本的字符
     */
    public TETile(TETile t, char c) {
        this(c, t.textColor, t.backgroundColor, t.description, t.filepath, t.id);
    }


    /**
     * 在位置 x, y 处将瓦片绘制到屏幕上。如果提供了有效的 filepath，
     * 我们将该路径处的图像绘制到屏幕上。否则，我们将回退到使用瓦片的字符和颜色表示。
     *
     * 注意：提供的图像尺寸必须正确（16x16），系统不会自动缩放或裁剪。
     * @param x X 坐标
     * @param y Y 坐标
     */
    public void draw(double x, double y) {
        if (filepath != null) {
            try {
                StdDraw.picture(x + 0.5, y + 0.5, filepath);
                return;
            } catch (IllegalArgumentException e) {
                // 找不到文件时会抛出异常。这种情况下静默失败，
                // 直接使用该瓦片的字符和背景颜色进行绘制。
            }
        }

        StdDraw.setPenColor(backgroundColor);
        StdDraw.filledSquare(x + 0.5, y + 0.5, 0.5);
        StdDraw.setPenColor(textColor);
        StdDraw.text(x + 0.5, y + 0.5, Character.toString(character()));
    }

    /** 瓦片的字符表示。用于文本模式下的绘制。
     * @return 字符表示
     */
    public char character() {
        return character;
    }

    /**
     * 瓦片的描述。可用于显示鼠标悬停文本，或测试两个瓦片是否代表同一种事物。
     * @return 瓦片的描述
     */
    public String description() {
        return description;
    }

    /**
     * 瓦片的 ID 编号。用于相等性比较。
     * @return 瓦片的 ID
     */
    public int id() {
        return id;
    }

    /**
     * 创建给定瓦片的副本，但文本颜色略有不同。新颜色的红色分量与当前红色分量的
     * 差值在 dr 以内，绿色分量和蓝色分量同样在 dg 和 db 以内。
     * @param t 要复制的瓦片
     * @param dr 红色分量的最大差值
     * @param dg 绿色分量的最大差值
     * @param db 蓝色分量的最大差值
     * @param r 使用的随机数生成器
     * @return 带有颜色微调的新瓦片副本
     */
    public static TETile colorVariant(TETile t, int dr, int dg, int db, Random r) {
        Color oldColor = t.textColor;
        int newRed = newColorValue(oldColor.getRed(), dr, r);
        int newGreen = newColorValue(oldColor.getGreen(), dg, r);
        int newBlue = newColorValue(oldColor.getBlue(), db, r);

        Color c = new Color(newRed, newGreen, newBlue);

        return new TETile(t, c);
    }

    private static int newColorValue(int v, int dv, Random r) {
        int rawNewValue = v + RandomUtils.uniform(r, -dv, dv + 1);

        // 确保颜色数值不会超出 0 到 255 的范围。
        int newValue = Math.min(255, Math.max(0, rawNewValue));
        return newValue;
    }

    /**
     * 将给定的二维数组转换为字符串。便于调试。
     * 注意：使用瓦片渲染引擎绘制时，y = 0 实际上是世界的底部，
     * 因此该打印方法必须按照看似相反的顺序打印（使得第 0 行最后被打印出来）。
     * @param world 要打印的二维世界数组
     * @return 世界的字符串表示形式
     */
    public static String toString(TETile[][] world) {
        int width = world.length;
        int height = world[0].length;
        StringBuilder sb = new StringBuilder();

        for (int y = height - 1; y >= 0; y -= 1) {
            for (int x = 0; x < width; x += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                sb.append(world[x][y].character());
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * 创建给定二维瓦片数组的副本。
     * @param tiles 要复制的二维数组
     * @return 瓦片二维数组的深拷贝副本
     **/
    public static TETile[][] copyOf(TETile[][] tiles) {
        if (tiles == null) {
            return null;
        }

        TETile[][] copy = new TETile[tiles.length][];

        int i = 0;
        for (TETile[] column : tiles) {
            copy[i] = Arrays.copyOf(column, column.length);
            i += 1;
        }

        return copy;
    }

    /**
     * 通过比较 ID 来判断两个瓦片是否相等。
     * @param o 要比较的对象
     * @return 表示是否相等的布尔值
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        return (o instanceof TETile otherTile && otherTile.id == this.id);
    }
}
