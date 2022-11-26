package tetrisblocks;

import tetris.TetrisBlock;
import java.awt.Color;

public class LShape extends TetrisBlock{
    public LShape(){
        super( new int[][]{{1, 0}, {1, 0}, {1, 1}}, Color.orange);
        //super( new int[][]{{0, 0, 1, 1, 1, 0, 0}, {0, 0, 1, 0, 1, 0, 0}, {0, 0, 1, 0, 1, 0, 0},  DONT UNCOMMENT
        // {0, 0, 1, 0, 1, 0, 0},{0, 0, 1, 0, 1, 0, 0},{0, 0, 1, 0, 1, 0, 0},{0, 0, 1, 0, 1, 0, 0},
        // {1, 1, 1, 0, 1, 1, 1}, {1, 0, 1, 1, 1, 0, 1}, {1, 1, 1, 0, 1, 1, 1}}, Color.orange);
    }
}
