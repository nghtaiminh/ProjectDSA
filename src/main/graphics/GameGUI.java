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
    private static final String TITLE = "Minesweeper";

    private static final int margin = 10;
    private static int FRAME_WIDTH;
    private static int FRAME_HEIGHT;

    private MineField mineField;
    private MineFieldPanel mineFieldPanel;
    private ControlPanel controlPanel;
    private static AudioPlayer audio = new AudioPlayer();
    private Difficulty level;

    public GameGUI(Difficulty level){
        this.mineField = new MineField(level);
        this.mineFieldPanel = new MineFieldPanel(level);
        this.controlPanel = new ControlPanel(mineFieldPanel.WIDTH);
        this.level = level;

        FRAME_WIDTH = mineFieldPanel.WIDTH +  margin*3 + 4;
        FRAME_HEIGHT = mineFieldPanel.HEIGHT + controlPanel.HEIGHT + margin*5 +4;

        initComponent();
        addComponent();
        addEvent();
    }


    @Override
    public void initComponent() {
        this.setTitle(TITLE);
        this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        this.setLocationRelativeTo(null);
        this.setResizable(true);
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
        controlPanel.setBounds(margin, margin, controlPanel.WIDTH, controlPanel.HEIGHT);
        controlPanel.setLbRemainingFlags(mineField.getRemainingFlags());
        controlPanel.setTime("0");
        controlPanel.restartUndoLimit();
        add(controlPanel);
        controlPanel.addListener(this);

        mineFieldPanel.setBounds(margin, controlPanel.HEIGHT + margin, mineFieldPanel.WIDTH, mineFieldPanel.HEIGHT);
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
            else setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);//Return to the game interface
        }
        };
        addWindowListener(wd);
    }

    @Override
    public Cell[][] getListCell() {
        return mineField.getListCell();
    }

    @Override
    public void updateMineField(int row, int col) throws InterruptedException {
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
            Thread.sleep(672);//Delay 0.672s before playing the game over audio
            audio.PlayVictory();
            JOptionPane.showMessageDialog(GameGUI.this,"You won!","Minesweeper",
            JOptionPane.INFORMATION_MESSAGE);
        }
        else if (mineField.getMineFieldStatus() == MineFieldStatus.EXPLODED) {
            controlPanel.stopTimer();
            audio.PlayExplode();//Invoke explode audio when a mine cell is clicked
            if (controlPanel.getUndoLimit() > 0){
                controlPanel.setBtUndoVisible(true);
            }
            Thread.sleep(672);//Delay 0.672s before playing the game over audio
            audio.PlayGameOver();//Invoke game over audio after the you lose message
            JOptionPane.showMessageDialog(GameGUI.this,"You lose!","Minesweeper",
            JOptionPane.INFORMATION_MESSAGE);
        }
    }

    @Override
    public void setOrRemoveFlag(int row, int col) {
        mineField.setOrRemoveFlag(row, col);
        mineFieldPanel.updateMineFieldPanel();
        controlPanel.setLbRemainingFlags(mineField.getRemainingFlags());
    }

    @Override
    public void restart() {
        mineField = new MineField(level);
        controlPanel.restartTimer();
        controlPanel.restartUndoLimit();
        controlPanel.setTime("0");
        controlPanel.setLbRemainingFlags(mineField.getRemainingFlags());
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
            dispose();
            initAll(Difficulty.EASY);
        }
        else if (level.equals("Medium")) {
            dispose();
            initAll(Difficulty.MEDIUM);
        }
        else if (level.equals("Hard")) {
            dispose();
            initAll(Difficulty.HARD);
        }

        setVisible(true);
        controlPanel.setTime("0");
        controlPanel.setLbRemainingFlags(mineField.getRemainingFlags());
    }

    public void initAll(Difficulty level){
        this.level = level;
        mineField = new MineField(level);
        mineFieldPanel = new MineFieldPanel(level);
        controlPanel = new ControlPanel(mineFieldPanel.WIDTH);

        FRAME_WIDTH = mineFieldPanel.WIDTH +  margin*3 + 4;
        FRAME_HEIGHT = mineFieldPanel.HEIGHT + controlPanel.HEIGHT + margin*5 + 4;

        initComponent();
        addComponent();
        addEvent();
    }
}

