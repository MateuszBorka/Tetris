package tetrisblocks;

import tetris.TetrisBlock;
import java.awt.Color;

public class LShape extends TetrisBlock{
    public LShape(){
        super( new int[][]{{1, 0}, {1, 0}, {1, 1}}, Color.orange);
    }
}
