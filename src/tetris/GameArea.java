package tetris;

import tetrisblocks.*;
import javax.swing.JPanel;
import javax.swing.border.Border;
import java.awt.*;
import java.util.Random;
import javax.swing.BorderFactory;

public class GameArea extends JPanel {

    final private int gridRows;
    final private int gridColumns;
    final private int gridCelSize;

    private final Color[][] background;
    private TetrisBlock block;
    private TetrisBlock[] blocks;
    public GameArea(int columns){

        this.setBounds(98, 38, 300, 390);
        this.setBackground(Color.white);

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        this.setBorder(blackLine);



        gridColumns = columns;
        gridCelSize = this.getBounds().width / gridColumns;
        gridRows = this.getBounds().height / gridCelSize;

        background = new Color[gridRows][gridColumns];
        blocks = new TetrisBlock[]{  new IShape(),
                                    new JShape(),
                                    new LShape(),
                                    new OShape(),
                                    new SShape(),
                                    new TShape(),
                                    new ZShape() };

    }

    public void spawnBlock(){
        Random r = new Random();
        block = blocks[r.nextInt(blocks.length)];
        //block = blocks[2]; only L shape
        block.spawn(gridColumns);

    }

    public void correctRotate(){
        //for (int x = 0; x < block.getHeight(); x++){
        //    for (int y = 0; y < block.getWidth(); y++){
//
        //        if (x+block.getX() <= 0 || x + block.getX() >= gridRows) continue;
        //        if (y+block.getY() <= 0 || y + block.getY() >= gridColumns) continue;
        //        if (block.getShape()[x][y] == 1 && background[y+block.getY()][x+block.getX()] != null) {
        //            System.out.println("Collision!");
        //        }
        //    }
        //}

        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        boolean flag = true;
        for (int r = 0; r < h; r++){
            for (int c = 0; c < w; c++){

                if (r+yPos <= 0 || r + yPos >= gridRows) continue;
                if (c+xPos <= 0 || c + xPos >= gridColumns) continue;

                if (shape[r][c] == 1 && background[r+yPos][c+xPos]!= null)
                    flag = false;
            }
        }
        if (!flag){
            block.rotate();
            block.rotate();
            block.rotate();
        }
    }

    public boolean isBlockOutOfBounds() {
        if (block.getY() < 0){
            block = null;
            return true;
        }
        return false;

    }

    public boolean moveBlockDown(){
        if (!checkBottom()){
            return false;
        }
        block.moveDown();
        repaint();
        return true;
    }

    public void moveBlockRight(){
        if (block == null) return;
        if (!checkRight()) return;
        block.moveRight();
        repaint();
    }

    public void moveBlockLeft(){
        if (block == null) return;
        if (!checkLeft()) return;
        block.moveLeft();
        repaint();
    }

    public void dropBlock(){
        if (block == null) return;
        while ( checkBottom() ){
            block.moveDown();
        }
        repaint();
    }

    public void rotateBlock(){
        if (block == null) return;
        block.rotate();
        correctRotate();

        if (block.getLeftEdge()<0) block.setX(0);
        if (block.getRightEdge()>gridColumns) block.setX(gridColumns - block.getWidth());
        if (block.getBottomEdge()>=gridRows) block.setY(gridRows-block.getHeight());


        repaint();
    }

    public void moveBlockUp(){
        block.moveUp();
        repaint();

    }
    public boolean checkBottom(){
        if (block.getBottomEdge() == gridRows) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int col = 0; col < w; col++){
            for(int row = h-1; row >= 0; row--){
                if (shape[row][col] != 0){
                    int x = col + block.getX();
                    int y = row + block.getY()+1;
                    if(y<0) break;
                    if (background[y][x] != null) return false;
                    break;

                }
            }
        }
        return true;
    }

    private boolean checkLeft(){
        if (block.getLeftEdge() == 0) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row = 0; row < h; row++){
            for(int col = 0; col < w; col++){
                if (shape[row][col] != 0){
                    int x = col + block.getX() - 1;
                    int y = row + block.getY();
                    if(y<0) break;
                    if (background[y][x] != null) return false;
                    break;

                }
            }
        }
        return true;
    }

    private boolean checkRight(){
        if (block.getRightEdge() == gridColumns) return false;

        int[][] shape = block.getShape();
        int w = block.getWidth();
        int h = block.getHeight();

        for(int row = 0; row < h; row++){
            for(int col = w-1; col >= 0; col--){
                if (shape[row][col] != 0){
                    int x = col + block.getX() + 1;
                    int y = row + block.getY();
                    if(y<0) break;
                    if (background[y][x] != null) return false;
                    break;

                }
            }
        }
        return true;
    }

    public void clearLines(){
        boolean lineFilled;
        for (int r = gridRows-1; r >= 0; r--){
            lineFilled = true;
            for (int c = 0; c < gridColumns; c++){
                if(background[r][c] == null){
                    lineFilled = false;
                    break;
                }
            }
            if (lineFilled){
                clearLine(r);
                shiftDown(r);
                GameForm.increaseScore(1);
                clearLine(0);
                r++;
                repaint();
            }
        }
    }

    private void clearLine(int r){
        for (int i = 0; i < gridColumns; i++) {
            background[r][i] = null;
        }
    }

    private void shiftDown(int r) {
        for (int row = r; row > 0; row--){
            if (gridColumns >= 0) System.arraycopy(background[row - 1], 0, background[row], 0, gridColumns);
        }
    }
    public void moveBlockToTheBackground(){
        int[][] shape = block.getShape();
        int h = block.getHeight();
        int w = block.getWidth();

        int xPos = block.getX();
        int yPos = block.getY();

        Color color = block.getColor();

        for (int r = 0; r < h; r++){
            for (int c = 0; c < w; c++){
                if (shape[r][c] == 1)
                    background[r+yPos][c+xPos] = color;
            }
        }
    }

    private void drawBlock(Graphics g){

        int h = block.getHeight();
        int w = block.getWidth();
        Color c = block.getColor();
        int[][] shape = block.getShape();

        for(int row = 0; row < h; row++){
            for (int col = 0; col < w; col++) {
                if (shape[row][col] == 1) {

                    int x = (block.getX() + col) * gridCelSize;
                    int y = (block.getY() + row) * gridCelSize;

                    drawGridSquare(g, c, x, y);

                }
            }
        }
    }

    private void drawBackground(Graphics g){

        Color color;
        for (int r = 0; r < gridRows; r++){
            for (int c = 0; c < gridColumns; c++){
                color = background[r][c];
                if(color != null){
                    int x = c * gridCelSize;
                    int y = r * gridCelSize;
                    drawGridSquare(g, color, x, y);


                }
            }
        }
    }

    private void drawGridSquare(Graphics g, Color color, int x, int y){
        g.setColor(color);
        g.fillRect(x, y, gridCelSize, gridCelSize);
        g.setColor(Color.black);
        g.drawRect(x, y, gridCelSize, gridCelSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBlock(g);
    }
}
