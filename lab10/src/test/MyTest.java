package test;

import org.junit.Test;
import tetris.Tetris;
import tileengine.TETile;
import tileengine.Tileset;

public class MyTest {
    @Test
    public void deleteLineTest () {
        TETile[][] tiles = {
                {Tileset.NOTHING, Tileset.AVATAR, Tileset.AVATAR},
                {Tileset.AVATAR, Tileset.AVATAR, Tileset.AVATAR},
                {Tileset.NOTHING, Tileset.AVATAR, Tileset.NOTHING}
        };
        TETile[][] expectedTiles = {
                {Tileset.NOTHING, Tileset.NOTHING, Tileset.NOTHING},
                {Tileset.NOTHING, Tileset.AVATAR, Tileset.AVATAR},
                {Tileset.NOTHING, Tileset.AVATAR, Tileset.NOTHING}
        };
        tiles = Tetris.deleteLine(tiles, 1);
        assert(tiles).equals(expectedTiles);
    }
}
