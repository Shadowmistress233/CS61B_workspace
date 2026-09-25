package tileengine;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;

/**
 * 用于渲染瓦片的工具类。你不需要修改此文件。
 * 当然如果你想修改也可以，但请务必小心。我们强烈建议在尝试诸如屏幕滚动、
 * 追踪角色等高级功能之前，先确保其他所有功能正常工作。
 */
public class TERenderer {
    private static final int TILE_SIZE = 16;
    private int width;
    private int height;
    private int xOffset;
    private int yOffset;

    /**
     * 功能与另一个初始化方法相同。唯一的区别是 xOff 和 yOff 参数会改变
     * renderFrame 方法开始绘制的起始位置。例如，若设置 w = 60, h = 30, xOff = 3, yOff = 4，
     * 然后使用 TETile[50][25] 数组调用 renderFrame，渲染器将在左侧留出 3 个瓦片空白，
     * 右侧留出 7 个瓦片空白，底部留出 4 个瓦片空白，顶部留出 1 个瓦片空白。
     * @param w 窗口宽度（单位：瓦片数）
     * @param h 窗口高度（单位：瓦片数）
     * @param xOff X 方向的偏移量（单位：瓦片数）
     * @param yOff Y 方向的偏移量（单位：瓦片数）
     */
    public void initialize(int w, int h, int xOff, int yOff) {
        this.width = w;
        this.height = h;
        this.xOffset = xOff;
        this.yOffset = yOff;
        StdDraw.setCanvasSize(width * TILE_SIZE, height * TILE_SIZE);
        resetFont();
        StdDraw.setXscale(0, width);
        StdDraw.setYscale(0, height);

        StdDraw.clear(new Color(0, 0, 0));

        StdDraw.enableDoubleBuffering();
        StdDraw.show();
    }

    /**
     * 初始化 StdDraw 参数并启动 StdDraw 窗口。w 和 h 是世界的宽度和高度（单位：瓦片数）。
     * 如果传给 renderFrame 的 TETile[][] 数组小于此尺寸，则画面的右侧和顶端将留出多余的空白空间。
     * 例如，若选择 w = 60 且 h = 30，该方法将创建一个 60 瓦片宽、30 瓦片高的窗口。
     * 若随后使用 TETile[50][25] 数组调用 renderFrame，右侧将留出 10 个瓦片空白，
     * 顶部留出 5 个瓦片空白。如果你希望在左侧或底部留出额外空间，请使用另一个初始化方法。
     * @param w 窗口宽度（单位：瓦片数）
     * @param h 窗口高度（单位：瓦片数）
     */
    public void initialize(int w, int h) {
        initialize(w, h, 0, 0);
    }

    /**
     * 接收 TETile 对象的二维数组，并从 xOffset 和 yOffset 起始位置将其渲染到屏幕上。
     *
     * 若该数组是 N×M 大小，则显示在各位置的元素对应关系如下（以瓦片为单位）：
     *
     *                位置坐标   xOffset |xOffset+1|xOffset+2| .... |xOffset+world.length
     *
     * startY+world[0].length   [0][M-1] | [1][M-1] | [2][M-1] | .... | [N-1][M-1]
     *                    ...    ......  |  ......  |  ......  | .... | ......
     *               startY+2    [0][2]  |  [1][2]  |  [2][2]  | .... | [N-1][2]
     *               startY+1    [0][1]  |  [1][1]  |  [2][1]  | .... | [N-1][1]
     *                 startY    [0][0]  |  [1][0]  |  [2][0]  | .... | [N-1][0]
     *
     * 通过在初始化时调整 xOffset、yOffset 以及屏幕尺寸，你可以在不同位置留出空白空间，
     * 以便放置其他信息（如 GUI 界面）。
     * 该方法假定 xScale 和 yScale 已正确设置，使得最大 X 值为以瓦片为单位的屏幕宽度，
     * 最大 Y 值为以瓦片为单位的屏幕高度。
     * @param world 要渲染的二维 TETile[][] 数组
     */
    public void renderFrame(TETile[][] world) {
        StdDraw.clear(new Color(0, 0, 0));
        drawTiles(world);
        StdDraw.show();
    }

    /**
     * 绘制所有世界瓦片，但不清空画布，也不调用 show() 显示。
     * @param world 要渲染的二维 TETile[][] 数组
     */
    public void drawTiles(TETile[][] world) {
        int numXTiles = world.length;
        int numYTiles = world[0].length;
        for (int x = 0; x < numXTiles; x += 1) {
            for (int y = 0; y < numYTiles; y += 1) {
                if (world[x][y] == null) {
                    throw new IllegalArgumentException("Tile at position x=" + x + ", y=" + y
                            + " is null.");
                }
                world[x][y].draw(x + xOffset, y + yOffset);
            }
        }
    }

    /**
     * 将字体重置为默认设置。如果修改了画笔设置，在绘制任何瓦片之前应调用此方法。
     */
    public void resetFont() {
        Font font = new Font("Monaco", Font.BOLD, TILE_SIZE - 2);
        StdDraw.setFont(font);
    }
}
