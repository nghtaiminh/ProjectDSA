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
    public int width,height;
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
    //   buttonSample = new JButton("Hello");
    //   buttonSample.addActionListener(new addButtonListener());
    //   buttonSample.setBounds(50, 90, 190, 30);

      easy = new JButton("Easy");
      med = new JButton("Medium");
      hard = new JButton("Hard");

      easy.addActionListener(new addButtonListenerEASY());
      med.addActionListener(new addButtonListenerMED());
      hard.addActionListener(new addButtonListenerHARD());

      easy.setBounds(0,50,100,100);
      med.setBounds(100+spacing,50,100,100);
      hard.setBounds(200+spacing*2,50,100,100);
    
    }
  
    private void addPanel()
    {
        introPanel.add(easy);
        introPanel.add(med);
        introPanel.add(hard);
        add(introPanel);
    }
  
    // class addButtonListener implements ActionListener
    // {
    //   public void actionPerformed(ActionEvent ae) 
    //   {
    //       getContentPane().removeAll();
    //       Board board = new Board();
    //       setContentPane(board);//Adding to content pane, not to Frame
    //       repaint();
    //       printAll(getGraphics());//Extort print all content
    //   }
    // }

    class addButtonListenerEASY implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) 
      {
          getContentPane().removeAll();
          width = Difficulty.EASY.getGridWidth();
          height = Difficulty.EASY.getGridHeight();
          Board board = new Board();
          setContentPane(board);//Adding to content pane, not to Frame
          repaint();
          printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerMED implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) 
      {
          getContentPane().removeAll();
          width = Difficulty.MEDIUM.getGridWidth();
          height = Difficulty.MEDIUM.getGridHeight();
          Board board = new Board();
          setContentPane(board);//Adding to content pane, not to Frame
          repaint();
          printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerHARD implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) 
      {
          getContentPane().removeAll();
          width = Difficulty.HARD.getGridWidth();
          height = Difficulty.HARD.getGridHeight();
          Board board = new Board();
          setContentPane(board);//Adding to content pane, not to Frame
          repaint();
          printAll(getGraphics());//Extort print all content
      }
    }

    public class Board  extends JPanel{
      public void paintComponent (Graphics g){
          g.setColor(Color.darkGray);
          g.fillRect(0, 0, 1280, 800);
          g.setColor(Color.gray);
          for(int i = 0; i < width; i ++){
              for(int j = 0; j < height; j++){
                  g.fillRect(spacing+i*40, spacing+j*40+40, 40-2*spacing, 40-2*spacing);
              }
          }
      }
  }
  
    public static void main(String[] args) {
      new GameGUI();
    }
  }