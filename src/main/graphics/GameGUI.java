package main.graphics;

import javax.swing.*;

import org.apache.commons.lang3.builder.Diff;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

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
    public JButton easy,med,hard,buttonSample;
    int spacing = 5;

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
        initAll(Difficulty.EASY);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerMED implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        initAll(Difficulty.MEDIUM);
        repaint();
        printAll(getGraphics());//Extort print all content
      }
    }

    class addButtonListenerHARD implements ActionListener
    {
      public void actionPerformed(ActionEvent ae) {
        getContentPane().removeAll();
        initAll(Difficulty.HARD);
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
        mineFieldPanel.addListener(this);
    }

    @Override
    public void addEvent() {
        WindowListener wd = new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            int kq = JOptionPane.showConfirmDialog(GameGUI.this, "Bạn có muốn thoát không?","Thông báo", JOptionPane.YES_NO_OPTION);
            if (kq == JOptionPane.YES_OPTION) {
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

