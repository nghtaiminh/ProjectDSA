package main.graphics;

import javax.swing.*;
import javax.swing.border.Border;

import main.core.Cell;
import main.enums.Difficulty;
import main.utils.ImageLoader;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MineFieldPanel extends JPanel implements ICommon{
    private static final long serialVersionUID = -6403941308246651773L;

    public int WIDTH;
    public int HEIGHT;
    private static final int cellSize = 35;
    private static final int border = 2;

    private Difficulty difficulty;
    private ImageLoader imageLoader;
    private Label[][] lbCells;
    private ITrans listener;

    public MineFieldPanel(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.WIDTH = difficulty.getGridWidth() * cellSize + difficulty.getGridWidth()*border*2;
        this.HEIGHT = difficulty.getGridHeight() * cellSize + difficulty.getGridHeight()*border*2;

        imageLoader = new ImageLoader();
        initComponent();
        addComponent();
        addEvent();
    }

    public void initComponent() {
        setLayout(new GridLayout(difficulty.getGridHeight(), difficulty.getGridWidth()));
    }

    public void addComponent() {
        Border border = BorderFactory.createLineBorder(Color.GRAY,1);
        lbCells = new Label[difficulty.getGridHeight()][difficulty.getGridWidth()];

        for (int row = 0; row < difficulty.getGridHeight(); row++) {
            for (int col = 0; col < difficulty.getGridWidth(); col++) {
                lbCells[row][col] = new Label();
                lbCells[row][col].setBorder(border);
                lbCells[row][col].setHorizontalAlignment(JButton.CENTER);
                lbCells[row][col].setVerticalAlignment(JButton.CENTER);
                add(lbCells[row][col]);
                ImageIcon icon = new ImageIcon(imageLoader.getListImage().get("hidden"));
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
                            try {
                                listener.updateMineField(label.row, label.col);
                            } catch (InterruptedException e1) {
                                // TODO Auto-generated catch block
                                e1.printStackTrace();
                            }
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
                ImageIcon icon;

                if (listCell[row][col].isHidden()) {
                    icon = new ImageIcon(imageLoader.getListImage().get("hidden"));
                }
                else if (listCell[row][col].isFlagged()) {
                    icon = new ImageIcon(imageLoader.getListImage().get("flag"));
                }
                else {
                    int numAdjacentMines = listCell[row][col].getNumAdjacentMines();
                    if (listCell[row][col].isMine() ) {
                        icon = new ImageIcon(imageLoader.getListImage().get("exploded_mine"));
                    }
                    else {
                        icon = new ImageIcon(imageLoader.getListImage().get(Integer.toString(numAdjacentMines)));
                    }
                }
                Icon renderedIcon = new ImageIcon(icon.getImage().getScaledInstance(lbCells[row][col].getWidth(), lbCells[row][col].getHeight(), Image.SCALE_SMOOTH));
                lbCells[row][col].setIcon(renderedIcon);
            }
        }
    }

    private class Label extends JLabel {
        private static final long serialVersionUID = 6099893043079770073L;
        private int row;
        private int col;
    }
}
