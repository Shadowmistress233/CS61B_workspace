package tetris;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TETile;
import tileengine.TERenderer;
import tileengine.Tileset;

import java.util.*;

/**
 *  提供俄罗斯方块的游戏主逻辑。
 *
 *  @author Erik Nelson, Omar Yu, Noah Adhikari, Jasmine Lin
 */

public class Tetris {

    private static int WIDTH = 10;
    private static int HEIGHT = 20;

    // 方块会在我们显示的区域上方生成，因此俄罗斯方块棋盘的高度
    // 会大于屏幕显示的实际高度。
    private static int GAME_HEIGHT = 25;

    // 包含棋盘的所有瓦片。
    private TETile[][] board;

    // 辅助处理方块的移动。
    private Movement movement;

    // 标识游戏是否结束。
    private boolean isGameOver;

    // 当前玩家可控制的方块。
    private Tetromino currentTetromino;

    // 当前游戏得分。
    private int score;

    /**
     * 根据 isGameOver 参数检查游戏是否结束。
     * @return 表示游戏是否结束的布尔值
     */
    private boolean isGameOver() {
        return isGameOver;
    }

    /**
     * 将游戏棋盘和得分渲染到屏幕上。
     */
    private void renderBoard() {
        ter.drawTiles(board);
        renderScore();
        StdDraw.show();

        if (auxFilled) {
            auxToBoard();
        } else {
            fillBoard(Tileset.NOTHING);
        }
    }

    /**
     * 创建一个新方块并相应地更新实例变量。
     * 如果棋盘顶部已被填满导致新方块无法生成，则标记游戏结束。
     */
    private void spawnPiece() {
        // 如果该格已被填满，则游戏结束
        if (board[4][19] != Tileset.NOTHING) {
            isGameOver = true;
        }

        // 否则生成一个新方块并将其位置设为生成点
        currentTetromino = Tetromino.values()[bagRandom.getValue()];
        currentTetromino.reset();
    }

    /**
     * 根据用户输入更新棋盘。根据用户的按键输入执行相应的移动或旋转。
     */
    private void updateBoard() {
        // 获取当前方块。
        Tetromino t = currentTetromino;
        if (actionDeltaTime() > 1000) {
            movement.dropDown();
            resetActionTimer();
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
            return;
        }

        // TODO: 实现交互逻辑，使玩家能够通过按键移动方块和旋转方块。
        // 你需要在这里使用一些提供的辅助方法。
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            switch (key) {
                case 'a' -> movement.tryMove(-1,0);
                case 's' -> movement.tryMove(0, -1);
                case 'd' -> movement.tryMove(1, 0);
                case 'q' -> movement.rotateLeft();
                case 'w' -> movement.rotateRight();
            }
        }

        Tetromino.draw(t, board, t.pos.x, t.pos.y);
    }

    /**
     * 根据消除的行数增加得分。
     *
     * @param linesCleared 消除的行数
     */
    private void incrementScore(int linesCleared) {
        // TODO: 根据消除的行数增加得分。

    }

    /**
     * 消除给定瓦片数组/棋盘中已水平填满的行。
     * 重复此过程以处理下落叠放效果并相应更新得分。
     * @param tiles 棋盘瓦片数组
     */
    public void clearLines(TETile[][] tiles) {
        // 记录本次清除的行数
        int linesCleared = 0;

        // TODO: 检查有多少行已被完全填满，若填满则清除这些行。

        // TODO: 根据消除的行数增加得分。

        fillAux();
    }

    /**
     * 游戏主循环逻辑所在的方法。只要游戏没有结束，就应当一直运行。
     */
    public void runGame() {
        resetActionTimer();

        // TODO: 构建你的游戏循环。游戏应当一直运行直到 game over。
        // 根据实验规范说明，在游戏循环中调用相应的辅助方法。


    }

    /**
     * 使用 StdDraw 库渲染当前得分。
     */
    private void renderScore() {
        // TODO: 使用 StdDraw 库绘制出得分。

    }

    /**
     * 启动运行俄罗斯方块的入口方法。
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        long seed = args.length > 0 ? Long.parseLong(args[0]) : (new Random()).nextLong();
        Tetris tetris = new Tetris(seed);
        tetris.runGame();
    }

    /**
     * 以下内容均为底层框架支持代码，你无需修改。
     */

    // 瓦片渲染引擎。
    private final TERenderer ter = new TERenderer();

    // 用于随机生成方块。
    private Random random;
    private BagRandomizer bagRandom;

    private long prevActionTimestamp;
    private long prevFrameTimestamp;

    // 辅助棋盘。在每个时间步中，随着方块下落，棋盘会被清空并重绘；
    // 因此我们维护一个辅助棋盘来记录截至目前已固定的方块，以辅助在更新时渲染当前游戏棋盘。
    private TETile[][] auxiliary;
    private boolean auxFilled;

    public Tetris() {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(new Random().nextLong());
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    public Tetris(long seed) {
        board = new TETile[WIDTH][GAME_HEIGHT];
        auxiliary = new TETile[WIDTH][GAME_HEIGHT];
        random = new Random(seed);
        bagRandom = new BagRandomizer(random, Tetromino.values().length);
        auxFilled = false;
        movement = new Movement(WIDTH, GAME_HEIGHT, this);

        ter.initialize(WIDTH, HEIGHT);
        fillBoard(Tileset.NOTHING);
        fillAux();
    }

    // Getter 和 Setter 方法。

    /**
     * 返回当前游戏棋盘。
     * @return 当前棋盘的瓦片数组
     */
    public TETile[][] getBoard() {
        return board;
    }

    /**
     * 返回当前得分。
     * @return 当前得分
     */
    public int getScore() {
        return score;
    }

    /**
     * 返回当前辅助棋盘。
     * @return 辅助棋盘的瓦片数组
     */
    public TETile[][] getAuxiliary() {
        return auxiliary;
    }


    /**
     * 返回当前受控方块。
     * @return 当前方块对象
     */
    public Tetromino getCurrentTetromino() {
        return currentTetromino;
    }

    /**
     * 将当前受控方块置为 null。
     */
    public void setCurrentTetromino() {
        currentTetromino = null;
    }

    /**
     * 将布尔标志 auxFilled 置为 true。
     */
    public void setAuxTrue() {
        auxFilled = true;
    }

    /**
     * 用传入的指定瓦片填满整个棋盘。
     * @param tile 用于填充的瓦片
     */
    private void fillBoard(TETile tile) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = tile;
            }
        }
    }

    /**
     * 使用 System.arraycopy 将 src 数组的内容复制到 dest 数组中。
     * @param src 源数组
     * @param dest 目标数组
     */
    private static void copyArray(TETile[][] src, TETile[][] dest) {
        for (int i = 0; i < src.length; i++) {
            System.arraycopy(src[i], 0, dest[i], 0, src[0].length);
        }
    }

    /**
     * 将游戏棋盘中的瓦片复制到辅助棋盘中。
     */
    public void fillAux() {
        copyArray(board, auxiliary);
    }

    /**
     * 将辅助棋盘中的瓦片复制回游戏棋盘中。
     */
    private void auxToBoard() {
        copyArray(auxiliary, board);
    }

    /**
     * 计算距离上一次动作经过的时间差（delta time）。
     * @return 上一次方块移动与当前时刻之间的时间间隔（毫秒）
     */
    private long actionDeltaTime() {
        return System.currentTimeMillis() - prevActionTimestamp;
    }

    /**
     * 将动作时间戳重置为当前时间的毫秒数。
     */
    private void resetActionTimer() {
        prevActionTimestamp = System.currentTimeMillis();
    }

}
