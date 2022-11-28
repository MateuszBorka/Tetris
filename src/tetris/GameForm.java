package tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameForm extends JFrame {

    private final GameArea ga;

    static private final JLabel score = new JLabel();
    static private int scoreNumber;

    static private final JLabel level = new JLabel();
    static private int levelNumber;

    final static private int scorePerLevel = 3;
     public GameForm() {

         this.getContentPane().setLayout(null); //tetris.GameArea is no more connected to the tetris.GameForm
         ga =  new GameArea(10);


         this.add( ga );

         initControls();

         startGame();
     }

     private void initControls(){
         InputMap im = this.getRootPane().getInputMap();
         ActionMap am = this.getRootPane().getActionMap();

         im.put(KeyStroke.getKeyStroke("RIGHT"), "right");
         im.put(KeyStroke.getKeyStroke("LEFT"), "left");
         im.put(KeyStroke.getKeyStroke("UP"), "up");
         im.put(KeyStroke.getKeyStroke("DOWN"), "down");
         im.put(KeyStroke.getKeyStroke("X"), "x");

         am.put("right", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                ga.moveBlockRight();
             }
         });
         am.put("left", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ga.moveBlockLeft();
             }
         });
         am.put("up", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ga.rotateBlock();
             }
         });
         am.put("down", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ga.dropBlock();
             }
         });
         am.put("x", new AbstractAction() {
             @Override
             public void actionPerformed(ActionEvent e) {
                 ga.moveBlockUp();
             }
         });

     }
     public void startGame(){
         new GameThread(ga).start();
     }

     static private void initScore(GameForm frame){
         scoreNumber = 0;

         score.setText("Score: 0");
         score.setBounds(440, 55, 100, 100);
         score.setFont(new Font("Monaco", Font.PLAIN, 20));
         frame.add(score);
     }

     static private void initLevel(GameForm frame){
         levelNumber = 1;
         level.setText("Level: 1");
         level.setBounds(440, 105, 100, 100);
         level.setFont(new Font("Monaco", Font.PLAIN, 20));
         frame.add(level);

     }

     public static void increaseScore(int point){
         scoreNumber += point;
         score.setText("Score: " + scoreNumber);
         if (scoreNumber % scorePerLevel == 0){
             increaseLevel(1);
         }
     }

    public static void increaseLevel(int point){
        levelNumber += point;
        level.setText("Level: " + levelNumber);
        GameThread.decreasePause();
    }

     public static void main(String[] args){
     java.awt.EventQueue.invokeLater(new Runnable() {
             public void run() {

                 GameForm frame = new GameForm();
                 initScore(frame);
                 initLevel(frame);
                 frame.setVisible(true);
                 frame.setBounds(0, 0, 700, 700);
                 frame.setLocationRelativeTo(null);


             }
         });
    }
}
