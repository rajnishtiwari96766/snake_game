package com.company;
import javax.swing.*;

public class Game_Frame extends JFrame {
    Game_Frame(){
        this.add(new Game_panel());
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}