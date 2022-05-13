package main.graphics;

import javax.swing.*;

import main.core.Cell;
import main.core.MineField;
import main.enums.Difficulty;

public class GameGUI extends JFrame implements ICommon, ITrans {
    private static final long serialVersionUID = -5479701518838741039L;
    private static Difficulty level;
    private static final String TITLE = "Minesweeper";
    private static final int cellSize = 50;
    private static int FRAME_WIDTH;
    private static int FRAME_HEIGHT;

    private MineField mineField;
    private MineFieldPanel mineFieldPanel;

    public GameGUI(Difficulty difficulty){
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

