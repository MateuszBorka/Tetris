package tetrisblocks;

import javax.swing.text.html.StyleSheet;
import tetris.TetrisBlock;
import java.awt.Color;

public class TShape extends TetrisBlock{
    public TShape(){
        super( new int[][]{{ 0, 1, 0 }, {1, 1, 1}}, new StyleSheet().stringToColor("purple"));
    }
}
