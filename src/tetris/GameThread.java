package tetris;

import java.util.logging.Level;
import java.util.logging.Logger;

public class GameThread extends Thread{

    private final GameArea ga;
    static private int pause = 1000;
    static private int speedUpPerLevel = 100;
    public GameThread(GameArea ga){
        this.ga = ga;
    }
    @Override
    public void run(){

        while (true){

            ga.spawnBlock();
            while (ga.moveBlockDown()) {

                try {
                    Thread.sleep(pause);
                } catch (InterruptedException ex) {
                    Logger.getLogger(GameThread.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            if(ga.isBlockOutOfBounds()){
                System.out.println("Game over");
                break;
            }

            ga.moveBlockToTheBackground();
            ga.clearLines();
        }
    }

    public static void decreasePause(){
        pause -= speedUpPerLevel;
    }
}
