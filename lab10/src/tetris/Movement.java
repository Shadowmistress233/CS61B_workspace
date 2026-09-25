package tetris;

import tileengine.TETile;
import tileengine.Tileset;

/**
 *  提供俄罗斯方块移动的逻辑。
 *
 *  @author Erik Nelson, Omar Yu, and Jasmine Lin
 */

public class Movement {

    private int WIDTH;

    private int GAME_HEIGHT;

    Tetris tetris;

    public Movement (int width, int game_height, Tetris tetris) {
        this.WIDTH = width;
        this.GAME_HEIGHT = game_height;
        this.tetris = tetris;
    }

    /**
     * 将当前方块向右顺时针旋转 90 度。
     */
    public void rotateRight() {
        rotate(Rotation.RIGHT);
    }

    /**
     * 将当前方块向左逆时针旋转 90 度。
     */
    public void rotateLeft() {
        rotate(Rotation.LEFT);
    }

    /**
     * 尝试将当前方块平移 deltaX 和 deltaY。
     * 如果方块无法移动并将与边界或已有方块碰撞，
     * 则将其固定在当前位置并置空，以便生成新的方块。
     * @param deltaX X 轴位移量
     * @param deltaY Y 轴位移量
     */
    public void tryMove(int deltaX, int deltaY) {
        Tetromino t = tetris.getCurrentTetromino();

        if (canMove(deltaX, deltaY)) {
            t.pos.x += deltaX;
            t.pos.y += deltaY;
        } else {
            if (deltaY < 0) {
                TETile[][] board = tetris.getBoard();
                Tetromino.draw(t, board, t.pos.x, t.pos.y);
                tetris.fillAux();

                tetris.setAuxTrue();
                tetris.setCurrentTetromino();
            }
        }
    }

    /**
     * 检查将当前方块平移 deltaX 和 deltaY 是否合法，
     * 即是否在边界内且不与其他方块发生碰撞。
     * @param deltaX X 轴位移量
     * @param deltaY Y 轴位移量
     * @return 表示移动是否可行的布尔值
     */
    public boolean canMove(int deltaX, int deltaY) {
        Tetromino t = tetris.getCurrentTetromino();

        for (int tx = 0; tx < t.width; tx++){
            for (int ty = 0; ty < t.height; ty++){
                if (t.shape[tx][ty]) {

                    // 越界检查
                    if (t.pos.x + tx + deltaX >= WIDTH ||
                            t.pos.x + tx + deltaX < 0 ||
                            t.pos.y + ty + deltaY >= GAME_HEIGHT ||
                            t.pos.y + ty + deltaY < 0) {
                        return false;
                    }

                    // 棋盘占用检查
                    TETile[][] board = tetris.getBoard();
                    if (board[t.pos.x + tx + deltaX][t.pos.y + ty + deltaY] != Tileset.NOTHING) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    /**
     * 将当前方块向下移动一格；如果无法下落，
     * 则将方块固定在原位并允许生成新的方块。
     */
    public void dropDown() {
        Tetromino t = tetris.getCurrentTetromino();

        if (canMove(0, -1)) {
            t.pos.y -= 1;
        } else {
            TETile[][] board = tetris.getBoard();
            Tetromino.draw(t, board, t.pos.x, t.pos.y);
            tetris.fillAux();

            tetris.setAuxTrue();
            tetris.setCurrentTetromino();
        }
    }

    /**
     * 检查旋转当前方块是否合法，
     * 即旋转后是否仍处于边界内且不会旋转/碰撞到其他方块。
     * @param newShape 旋转后的新形状数组
     * @return 表示旋转是否可行的布尔值
     */
    public boolean canRotate(boolean[][] newShape) {
        Tetromino t = tetris.getCurrentTetromino();
        boolean valid = true;
        for (int tx = 0; tx < newShape.length; tx++) {
            for (int ty = 0; ty < newShape[0].length; ty++) {
                if (newShape[tx][ty]) {
                    if (t.pos.x + tx < 0 || t.pos.y + ty < 0
                            || t.pos.x + tx >= tetris.getAuxiliary().length
                            || t.pos.y + ty >= tetris.getAuxiliary()[0].length
                            || tetris.getAuxiliary()[t.pos.x + tx][t.pos.y + ty] != Tileset.NOTHING) {
                        valid = false;
                    }
                }
            }
        }
        return valid;
    }

    /**
     * 用于区分向左和向右旋转的枚举。
     */
    public enum Rotation {
        RIGHT, LEFT
    }

    /**
     * 尝试按给定方向 r（左或右）旋转当前方块。
     * 如果方块无法旋转，则保持当前朝向不变。
     * @param r 旋转方向
     */
    public void rotate(Rotation r) {
        Tetromino t = tetris.getCurrentTetromino();
        int h = t.shape.length;
        int w = t.shape[0].length;
        boolean[][] newShape = new boolean[h][w];
        if (r == Rotation.LEFT) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++){
                    newShape[i][j] = t.shape[j][h - i - 1];
                }
            }
        } else if (r == Rotation.RIGHT) {
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {
                    newShape[i][j] = t.shape[w - j - 1][i];
                }
            }
        }

        if (canRotate(newShape)) {
            t.shape = newShape;
            t.height = t.shape[0].length;
            t.width = t.shape.length;
        }
    }
}
