package main.graphics;

import javax.swing.*;

import main.enums.MineFieldStatus;
import org.apache.commons.lang3.builder.Diff;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.Timer;

import main.core.Cell;
import main.core.MineField;
import main.enums.Difficulty;
import main.utils.AudioPlayer;

public class GameGUI extends JFrame implements ICommon, ITrans {
    private static final long serialVersionUID = -5479701518838741039L;
    private static Difficulty level = Difficulty.EASY;
    private static final String TITLE = "Minesweeper";
    private static final int cellSize = 50;
    private static final int border = 2;

    private static final int margin = 10;
    private static int FRAME_WIDTH = cellSize * level.getGridWidth() + margin*2 + level.getGridWidth()*border*2;
    private static int FRAME_HEIGHT = cellSize * level.getGridHeight() + margin*3 + level.getGridHeight()*border*2 + 60;

    private MineField mineField;
    private MineFieldPanel mineFieldPanel;
    private ControlPanel controlPanel;
    private static AudioPlayer audio = new AudioPlayer();

    public GameGUI(){
        mineField = new MineField(level);
        initComponent();
        addComponent();
        addEvent();
    }


    @Override
    public void initComponent() {
        this.setTitle(TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
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
        controlPanel = new ControlPanel();
        controlPanel.setBounds(margin, margin, FRAME_WIDTH - margin*2, 60);
        add(controlPanel);
        controlPanel.addListener(this);

        mineFieldPanel = new MineFieldPanel(level);
        mineFieldPanel.setBounds(margin, margin*2 + 60, FRAME_WIDTH - margin*2, FRAME_HEIGHT - margin*3 - 60);
        add(mineFieldPanel);
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
        if (mineField.getMineFieldStatus() == MineFieldStatus.NOT_CLEARED) {
            // Start timer after first click
            if (mineField.getFirstClick()) {
                controlPanel.startTimer();
            }

            mineField.updateMineField(row, col);
            mineFieldPanel.updateMineFieldPanel();
        }

        if (mineField.getMineFieldStatus() == MineFieldStatus.CLEARED){
            controlPanel.stopTimer();
            JOptionPane.showMessageDialog(GameGUI.this,"You won!");
        }
        else if (mineField.getMineFieldStatus() == MineFieldStatus.EXPLODED) {
            controlPanel.stopTimer();
            audio.PlayExplode();//Invoke explode audio when a mine cell is clicked
            if (controlPanel.getUndoLimit() > 0){
                controlPanel.setBtUndoVisible(true);
            }
            audio.PlayGameOver();//Invoke game over audio after the you lose message
            JOptionPane.showMessageDialog(GameGUI.this,"You lose!");
        }
    }

    @Override
    public void setOrRemoveFlag(int row, int col) {
        mineField.setOrRemoveFlag(row, col);
        mineFieldPanel.updateMineFieldPanel();
    }

    @Override
    public void restart() {
        mineField = new MineField(level);
        controlPanel.restartTimer();
        controlPanel.restartUndoLimit();
        mineFieldPanel.updateMineFieldPanel();
        
    }

    @Override
    public void undo() {
        if (mineField.getMineFieldStatus() == MineFieldStatus.EXPLODED) {
            mineField.undo();
            controlPanel.startTimer();
            controlPanel.updateUndoCount();
            controlPanel.setBtUndoVisible(false);
            mineFieldPanel.updateMineFieldPanel();
        }
    }

    @Override
    public void changeLevel(String level) {
        getContentPane().removeAll();
        if (level.equals("Easy")) {
            getContentPane().removeAll();
            mineField = new MineField(Difficulty.EASY);
            initAll(Difficulty.EASY);
        }
        else if (level.equals("Medium")) {
            getContentPane().removeAll();
            mineField = new MineField(Difficulty.MEDIUM);
            initAll(Difficulty.MEDIUM);
        }
        else if (level.equals("Hard")) {
            getContentPane().removeAll();
            mineField = new MineField(Difficulty.HARD);
            initAll(Difficulty.HARD);
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
}

