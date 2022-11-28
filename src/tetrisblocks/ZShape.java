package tetrisblocks;

import tetris.TetrisBlock;
import java.awt.Color;

public class ZShape extends TetrisBlock{
    public ZShape(){
        super( new int[][]{{0, 1},{1, 1}, {1, 0}}, Color.red);
    }
}