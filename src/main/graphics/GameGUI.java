package main.graphics;

import javax.swing.*;

import org.apache.commons.lang3.builder.Diff;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;

import main.core.Cell;
import main.core.MineField;
import main.enums.Difficulty;

public class GameGUI extends JFrame implements ICommon, ITrans {
    private static final long serialVersionUID = -5479701518838741039L;
    private static Difficulty level;
    private static final String TITLE = "Minesweeper";
    private static final int cellSize = 50;
    private static int FRAME_WIDTH = 500;
    private static int FRAME_HEIGHT = 500;

    private MineField mineField;
    private MineFieldPanel mineFieldPanel;

    public JPanel introPanel;
    public JButton easy,med,hard,buttonSample,undoButton,resetButton,timerButton,backButton;
    public Timer timer;
    public int spacing = 5, second;

    public GameGUI(){
        initComponent();
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

    //   easy.setBounds(0,50,100,100);
    //   med.setBounds(100+spacing,50,100,100);
    //   hard.setBounds(200+spacing*2,50,100,100);
    
    }
  
    private void addPanel()
    {
        introPanel.add(easy);
        introPanel.add(med);
        introPanel.add(hard);
        add(introPanel);
    }

    class addButtonListenerEASY implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        level = Difficulty.EASY;
        initAll(level);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerMED implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        level = Difficulty.MEDIUM;
        initAll(level);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerHARD implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        level = Difficulty.HARD;
        initAll(level);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerUNDO implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        undo();
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerRESET implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        initAll(level);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    public void setTimer(){
        timer = new Timer(1000, new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                second++;

                timerButton.setText("" + second);
            }

        });
    }

    class addButtonListenerBACK implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        FRAME_WIDTH = 500;
        FRAME_HEIGHT = 500;
        initComponent();
        createPanel();
        addPanel();
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    public void initAll(Difficulty difficulty){
        level = difficulty;
        mineField = new MineField(level);
        FRAME_HEIGHT = cellSize * difficulty.getGridHeight();
        FRAME_WIDTH = cellSize * difficulty.getGridWidth();

        initComponent();
        addComponent();
        addEvent();
    }

    @Override
    public void initComponent() {
        this.setTitle(TITLE);
        this.setSize(FRAME_WIDTH + 30 + 10, FRAME_HEIGHT + 80);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addComponent() {
        mineFieldPanel = new MineFieldPanel(level);
        mineFieldPanel.setBounds(10, 40, FRAME_WIDTH, FRAME_HEIGHT);
        add(mineFieldPanel);

        undoButton = new JButton("Undo");
        undoButton.addActionListener(new addButtonListenerUNDO());
        undoButton.setBounds(10,0,100,30);
        add(undoButton);

        resetButton = new JButton("Reset");
        resetButton.addActionListener(new addButtonListenerRESET());
        resetButton.setBounds(110 + spacing,0,100,30);
        add(resetButton);

        timerButton = new JButton();
        timerButton.setBounds(210 + spacing*2,0,100,30);
        second = 0;
        setTimer();
        timer.start();
        add(timerButton);

        backButton = new JButton("Go back");
        backButton.addActionListener(new addButtonListenerBACK());
        backButton.setBounds(310+spacing*3,0,100,30);
        add(backButton);

        mineFieldPanel.addListener(this);
    }


    @Override
    public void addEvent() {
        WindowListener wd = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            int out = JOptionPane.showConfirmDialog(GameGUI.this, "Do you really want to quit?","Quit?", JOptionPane.YES_NO_OPTION);
            if (out == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
        };
        addWindowListener(wd);
    }

    @Override
    public Cell[][] getListCell() {
        return mineField.getListCell();
    }

    @Override
    public void updateMineField(int row, int col) {
        mineField.updateMineField(row, col);
        mineFieldPanel.updateMineFieldPanel();
        // [TODO]: Check if the player has won after revealing a cell
        if (mineField.hasWon()){
            JOptionPane.showMessageDialog(GameGUI.this,"You won");
        };
        // (mineField.hasWon() == true)? //Allow no click : // allow next click
    }

    @Override
    public void setOrRemoveFlag(int row, int col) {
        mineField.setOrRemoveFlag(row, col);
        mineFieldPanel.updateMineFieldPanel();
    }

    @Override
    public void undo() {
        // [TODO]: Implement undo

    }
}

