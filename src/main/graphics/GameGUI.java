package main.graphics;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

import main.enums.Difficulty;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.*;

import javax.swing.*;
import java.util.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Timer;

public class GameGUI extends JFrame{
    public JPanel introPanel;
    public JButton easy,med,hard,buttonSample;
    int spacing = 5;
  
    GameGUI(){
      this.setTitle("Minesweeper");
      this.setSize(1286,829);
      this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.setVisible(true);
      this.setResizable(false);
      createPanel();
      addPanel();
    }
  
    private void createPanel()
    {
      introPanel = new JPanel();
      introPanel.setBounds(0,0,500,500);
      buttonSample = new JButton("Hello");
      buttonSample.addActionListener(new addButtonListener());
      buttonSample.setBounds(50, 90, 190, 30);
  
    }
  
    private void addPanel()
    {
        introPanel.add(buttonSample);
        add(introPanel);
    }
  
    class addButtonListener implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) 
      {
          getContentPane().removeAll();
          Board board = new Board();
          setContentPane(board);//Adding to content pane, not to Frame
          repaint();
          printAll(getGraphics());//Extort print all content
      }
    }
    public class Board extends JPanel{
      public void paintComponent (Graphics g){
          g.setColor(Color.darkGray);
          g.fillRect(0, 0, 1280, 800);
          g.setColor(Color.gray);
          for(int i = 0; i < Difficulty.HARD.getGridWidth(); i ++){
              for(int j = 0; j < Difficulty.HARD.getGridHeight(); j++){
                  g.fillRect(spacing+i*40, spacing+j*40+40, 40-2*spacing, 40-2*spacing);
              }
          }
      }
  }
  
    public static void main(String[] args) {
      new GameGUI();
    }
  }