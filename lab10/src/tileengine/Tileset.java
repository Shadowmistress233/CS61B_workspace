package tileengine;

import java.awt.Color;

/**
 * 包含常量瓦片对象，以避免在代码的不同部分重复创建相同的瓦片。
 *
 * 你可以自由地（并且我们鼓励你）创建并添加自己的瓦片到此文件中。该文件将与其他代码一起提交。
 *
 * 示例：
 *      world[x][y] = Tileset.FLOOR;
 *
 * 当你尝试对此文件进行代码风格检查（style check）时，代码风格检查器可能会因为 Unicode 字符而崩溃。
 * 这是正常的，无需担心。
 */

public class Tileset {
    public static final TETile AVATAR = new TETile('@', Color.white, Color.black, "you", 0);
    public static final TETile WALL = new TETile('#', new Color(216, 128, 128), Color.darkGray,
            "wall", 1);
    public static final TETile FLOOR = new TETile('·', new Color(128, 192, 128), Color.black, "floor", 2);
    public static final TETile NOTHING = new TETile(' ', Color.black, Color.black, "nothing", 3);
    public static final TETile GRASS = new TETile('"', Color.green, Color.black, "grass", 4);
    public static final TETile WATER = new TETile('≈', Color.blue, Color.black, "water", 5);
    public static final TETile FLOWER = new TETile('❀', Color.magenta, Color.pink, "flower", 6);
    public static final TETile LOCKED_DOOR = new TETile('█', Color.orange, Color.black,
            "locked door", 7);
    public static final TETile UNLOCKED_DOOR = new TETile('▢', Color.orange, Color.black,
            "unlocked door", 8);
    public static final TETile SAND = new TETile('▒', Color.yellow, Color.black, "sand", 9);
    public static final TETile MOUNTAIN = new TETile('▲', Color.gray, Color.black, "mountain", 10);
    public static final TETile TREE = new TETile('♠', Color.green, Color.black, "tree", 11);
}


