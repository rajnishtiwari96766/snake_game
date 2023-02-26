package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class snake_game {
    public static void main(String[] args) {
//        game_frame g= new game_frame();
        new game_frame();
    }

    public static class game_frame extends JFrame {
        game_frame(){
            this.add(new game_panel()); //direct instantiation of the game panel class
            this.setTitle("Snake");
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setResizable(false);
            this.pack();
            this.setVisible(true);
            this.setLocationRelativeTo(null);
        }
    }

    public static class game_panel extends JPanel implements ActionListener {

        static final int screen_width=600;
        static final int screen_height=600;
        static final int unit_size=25;
        static final int game_units=(screen_width * screen_height)/unit_size;
        static final int delay=75;

        //dimensions of the snake is stored in the arrays...
        final int[] x=new int[game_units];
        final int[] y=new int [game_units];
        int bodyparts=6;
        int appleseaten;
        int applex;
        int appley;
        char direction='R';
        boolean running=false;
        Timer timer;
        Random random;

         game_panel(){
            random=new Random();
            this.setPreferredSize(new Dimension(screen_width,screen_height));
            this.setBackground(Color.black);
            this.setFocusable(true);
            this.addKeyListener(new mykeyadapter());
            startgame();
        }

       public void startgame(){
            newapple();
            running=true;
            timer=new Timer(delay,this);
            //this is used to call the delay method
            timer.start();
        }
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            draw(g);
        }
        public void draw(Graphics g){

            if (running) {
                //draws the grid lines that will be visible
    //            for (int i = 0; i < screen_height / unit_size; i++) {
    //                g.drawLine(i * unit_size, 0, i * unit_size, screen_height);
    //                g.drawLine(0, i * unit_size, screen_width, i * unit_size);
    //            }
                g.setColor(Color.red);
                g.fillOval(applex, appley, unit_size, unit_size);

                for (int i = 0; i < bodyparts; i++) {
                    if (i == 0) {
                        g.setColor(Color.red);
                        g.fillRect(x[i], y[i], unit_size, unit_size);
                    } else {
                        //color for the body part
                        g.setColor(new Color(45, 180, 0));
                        //below line is for random colours of the snake
                        g.setColor(new Color(random.nextInt(255),random.nextInt(255),random.nextInt(255)));
                        g.fillRect(x[i], y[i], unit_size, unit_size);
                    }
                }

                g.setColor(Color.red);
                g.setFont(new Font("Ink Free",Font.BOLD,40));
                FontMetrics Metrics=getFontMetrics(g.getFont());
                g.drawString("Score:"+appleseaten,(screen_width-Metrics.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize());

            }else {
                gameover(g);
            }
        }
        public void newapple(){
            //this fills the grid with the apple randomly
            applex=random.nextInt((int)screen_width/unit_size)*unit_size;
            appley=random.nextInt((int)screen_height/unit_size)*unit_size;
        }
        public void move(){
            for (int i=bodyparts;i>0;i--){
                x[i]=x[i-1];
                y[i]=y[i-1];
            }

            switch (direction){

                case 'u':
                    y[0]=y[0]-unit_size;
                    break;

                case 'd':
                    y[0]=y[0]+unit_size;
                    break;

                case 'l':
                    x[0]=x[0]-unit_size;
                    break;

                case 'r':
                    x[0]=x[0]+unit_size;
                    break;
            }
        }
       public void checkapple(){
            if ((x[0]==applex) && (y[0]==appley)){
                bodyparts++;
                appleseaten++;
                newapple();
            }
        }
        public void checkcollision(){
            //check if the head collides with body
            for (int i=bodyparts;i>0;i--){
                if ((x[0] == x[i]) && (y[0] == y[i])) {
                    running = false;
                    break;
                }
            }

            //check if head touches left border
            if (x[0]<0){
                running=false;
            }

            //check if the head touches right border
            if (x[0]>screen_width){
                running=false;
            }

            //check if the head touched the top border
            if (y[0]<0){
                running=false;
            }

            //check if the head touches the bottom border
            if (y[0]>screen_height){
                running=false;
            }

            if (!running){
                timer.stop();
            }
        }
        public void gameover(Graphics g){
            //score
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,40));
            FontMetrics Metrics1=getFontMetrics(g.getFont());
            g.drawString("Score:"+appleseaten,(screen_width-Metrics1.stringWidth("Score: "+appleseaten))/2,g.getFont().getSize());

            //game over text
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free",Font.BOLD,75));
            FontMetrics Metrics2=getFontMetrics(g.getFont());
            g.drawString("Game Over",(screen_width-Metrics2.stringWidth("Game Over"))/2,screen_height/2);

    //        timer.stop();
        }
        @Override
        public void actionPerformed(ActionEvent e){
            if(running){
                move();
                checkapple();
                checkcollision();
            }
            repaint();
        }

        public class mykeyadapter extends KeyAdapter {
            @Override
            public void keyPressed(KeyEvent e){
                switch (e.getKeyCode()){
                    case KeyEvent.VK_LEFT :
                        if (direction!='R'){
                            direction='L';
                        }
                        break;

                    case KeyEvent.VK_RIGHT:
                        if (direction!='L'){
                            direction='R';
                        }
                        break;

                    case KeyEvent.VK_UP:
                        if (direction!='U'){
                            direction='D';
                        }
                        break;

                    case KeyEvent.VK_DOWN:
                        if (direction!='D'){
                            direction='U';
                        }
                        break;
                }
            }
        }
    }
}