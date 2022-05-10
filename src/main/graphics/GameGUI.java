package main.graphics;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import main.enums.Difficulty;

public class GameGUI extends JFrame {

    int spacing = 5;

    public GameGUI(){
        this.setTitle("Minesweeper");
        this.setSize(1286,829);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        Board board = new Board();
        this.setContentPane(board);
    }

    public class Board extends JPanel{
        public void paintComponent (Graphics g){
            g.setColor(Color.darkGray);
            g.fillRect(0, 0, 1280, 800);
            g.setColor(Color.gray);
            for(int i = 0; i < Difficulty.HARD.getGridWidth(); i ++){
                for(int j = 0; j < Difficulty.HARD.getGridHeight(); j++){
                    g.fillRect(spacing+i*80, spacing+j*80+80, 80-2*spacing, 80-2*spacing);
                }
            }
        }
    }

    // public static void main(String[] args) {
    //     new GameGUI();
    // }

}
