package main.graphics;

import javax.swing.*;
import javax.swing.border.Border;

import main.core.Cell;
import main.enums.Difficulty;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineFieldPanel extends JPanel implements ICommon{
    private static final long serialVersionUID = -6403941308246651773L;
    private Difficulty difficulty;
    public int WIDTH;
    public int HEIGHT;
    private Label[][] lbCells;
    private ITrans listener;

    public MineFieldPanel(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.WIDTH = difficulty.getGridWidth();
        this.HEIGHT = difficulty.getGridHeight();
        initComponent();
        addComponent();
        addEvent();
    }

    public void initComponent() {
        setLayout(new GridLayout(difficulty.getGridHeight(), difficulty.getGridWidth()));
    }

    public void addComponent() {
        Border border = BorderFactory.createLineBorder(Color.GRAY,2);
        lbCells = new Label[difficulty.getGridHeight()][difficulty.getGridWidth()];

        for (int row = 0; row < difficulty.getGridHeight(); row++) {
            for (int col = 0; col < difficulty.getGridWidth(); col++) {
                lbCells[row][col] = new Label();
                lbCells[row][col].setBorder(border);
                lbCells[row][col].setHorizontalAlignment(JButton.CENTER);
                lbCells[row][col].setVerticalAlignment(JButton.CENTER);
                add(lbCells[row][col]);
                Image image = new ImageIcon(getClass().getResource("/main/assets/img/hidden.png")).getImage();
                Icon icon = new ImageIcon(image);
                lbCells[row][col].setIcon(icon);
            }
        }

    }

    public void addEvent() {
        for (int row = 0; row < difficulty.getGridHeight(); row++) {
            for (int col = 0; col < difficulty.getGridWidth(); col++) {
                lbCells[row][col].row = row;
                lbCells[row][col].col = col;
                lbCells[row][col].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseReleased(MouseEvent e) {
                        Label label = (Label) e.getComponent();
                        // Left click to reveal a cell. Right click to set/remove flag
                        if (e.getButton() == MouseEvent.BUTTON1) { // Left-click
                            listener.updateMineField(label.row, label.col);
                        } else if (e.getButton() == MouseEvent.BUTTON3) { // Right-click
                            listener.setOrRemoveFlag(label.row, label.col);
                        }
                    }
                });
            }
        }
    }

    public void addListener(ITrans event) {
        listener = event;
    }

    public void updateMineFieldPanel() {
        Cell[][] listCell = listener.getListCell();
        for (int row = 0; row < difficulty.getGridHeight(); row++) {
            for (int col = 0; col < difficulty.getGridWidth(); col++) {
                Image image;

                if (listCell[row][col].isHidden()) {
                    image = new ImageIcon(getClass().getResource("/main/assets/img/hidden.png")).getImage();
                }
                else if (listCell[row][col].isFlagged()) {
                    image = new ImageIcon(getClass().getResource("/main/assets/img/flag.png")).getImage();
                }
                else {
                    int numAdjacentMines = listCell[row][col].getNumAdjacentMines();
                    if (listCell[row][col].isMine() ) {
                        image = new ImageIcon(getClass().getResource("/main/assets/img/exploded_mine.png")).getImage();
                    }
                    else {
                        image = new ImageIcon(getClass().getResource("/main/assets/img/" + Integer.toString(numAdjacentMines) + ".png")).getImage();
                    }
                }
                Icon icon = new ImageIcon(image.getScaledInstance(lbCells[row][col].getWidth(), lbCells[row][col].getHeight(), Image.SCALE_SMOOTH));
                lbCells[row][col].setIcon(icon);
            }
        }

    }

    private class Label extends JLabel {
        private static final long serialVersionUID = 6099893043079770073L;
        private int row;
        private int col;
    }
}
