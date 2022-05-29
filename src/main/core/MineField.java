package main.core;

import main.enums.MineFieldStatus;
import main.enums.Difficulty;
import main.enums.CellStatus;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import org.apache.commons.lang3.SerializationUtils;

public class MineField implements Serializable {
    public final int GRID_WIDTH;
    public final int GRID_HEIGHT;
    private final int numMines;
    private int remainingFlags;
    private boolean firstClick = true;

    private Cell[][] mineField;
    private Cell[][] prevMineField;

    private MineFieldStatus mineFieldStatus = MineFieldStatus.NOT_CLEARED;
    private final Difficulty difficulty;

    private int[][] directions = new int[][] {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{1,1},{-1,1},{1,-1}};

    public MineField(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.GRID_WIDTH = difficulty.getGridWidth();
        this.GRID_HEIGHT = difficulty.getGridHeight();
        this.numMines = difficulty.getNumberOfMines();
        this.remainingFlags = difficulty.getNumberOfMines();
        this.mineField = new Cell[GRID_HEIGHT][GRID_WIDTH];

        createEmptyMineField();
    }

    /**
     * Initialize the minefield with an empty grid
     * */
    public void createEmptyMineField() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                mineField[row][col] = new Cell(row, col);
            }
        }
    }

    /**
     * Select random cells as mine except the first click
     */
    public void generateMines(int clickedRow, int clickedCol) {
        int countMines = 0;
        Random rand = new Random();
        while (countMines < numMines) {
            int col = rand.nextInt(GRID_WIDTH);
            int row = rand.nextInt(GRID_HEIGHT);

            if (!mineField[row][col].isMine() && !(col == clickedCol && row == clickedRow)) {
                mineField[row][col].setMine(true);
                countMines++;
            }
        }
    }

    /**
     *  Count all the mines in the 8 adjacent cells
     */
    public void generateMineNumber() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {

                if (!mineField[row][col].isMine()) {
                    int countMines = 0;

                    for (int[] dir : directions) {
                        int nextRow = row + dir[0];
                        int nextCol = col + dir[1];

                        if (isValidCell(nextRow, nextCol) && mineField[nextRow][nextCol].isMine()) {
                            countMines++;
                        }
                    }
                    mineField[row][col].setNumAdjacentMines(countMines);
                }
            }
        }
    }

    /**
     * Update the minefield when click on a cell. If click on an empty cell, apply BFS to open all the neighbor empty cells
     * @param clickedRow
     * @param clickedCol
     * @return
     */
    public int[] updateMineField(int clickedRow, int clickedCol) {
        // Generate mines after the first click
        if(firstClick){
            generateMines(clickedRow, clickedCol);
            generateMineNumber();
            firstClick = false;
            updateMineField(clickedRow, clickedCol);
        }
        // Store the current state of minefield
        prevMineField = SerializationUtils.clone(mineField);

        if (mineField[clickedRow][clickedCol].isFlagged())
            return new int[] {clickedRow, clickedCol};

        if (mineField[clickedRow][clickedCol].isMine() && mineField[clickedRow][clickedCol].isHidden()) {
            revealAllMines();
            mineFieldStatus = MineFieldStatus.EXPLODED;
            return new int[] {clickedRow, clickedCol};
        }

        Queue<int[]> queue = new LinkedList<>();
        boolean[][] visitedCells = new boolean[GRID_HEIGHT][GRID_WIDTH];

        queue.offer(new int[]{clickedRow, clickedCol});
        visitedCells[clickedRow][clickedCol] = true;

        while(!queue.isEmpty()) {
            int[] curCell = queue.poll();
            int curRow = curCell[0];
            int curCol = curCell[1];

            if(mineField[curRow][curCol].getNumAdjacentMines() == 0) {
                mineField[curRow][curCol].setCellStatus(CellStatus.OPENED);
                // Visit all the neighbors of the current cell
                for (int[] dir : directions) {
                    int nextRow = curRow + dir[0];
                    int nextCol = curCol + dir[1];

                    if (isValidCell(nextRow, nextCol) && !visitedCells[nextRow][nextCol] && mineField[nextRow][nextCol].isHidden() && !mineField[nextRow][nextCol].isMine()) {
                        queue.offer(new int[] {nextRow,nextCol});
                        visitedCells[nextRow][nextCol] = true;
                    }
                }
            } else {
                mineField[curRow][curCol].setCellStatus(CellStatus.OPENED);
            }
        }
        return new int[] {clickedRow, clickedCol};
    }


    public void revealAllCells() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                mineField[row][col].setCellStatus(CellStatus.OPENED);
            }
        }
    }

    public void revealAllMines() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                if (mineField[row][col].isMine())
                    mineField[row][col].setCellStatus(CellStatus.OPENED);
            }
        }
    }

    public boolean hasWon() {
        boolean isWon = true;
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                if (!mineField[row][col].isMine() && mineField[row][col].isHidden())
                    isWon = false;
            }
        }

        if(isWon)
            mineFieldStatus = MineFieldStatus.CLEARED;

        return isWon;
    }

    public void undo() {
        if (!firstClick){
            mineField = SerializationUtils.clone(prevMineField);
            mineFieldStatus = MineFieldStatus.NOT_CLEARED;
        }
    }

    public void setMineFieldStatus(MineFieldStatus status) {
        this.mineFieldStatus = status;
    }

    public boolean isValidCell(int row, int col) {
        return (row >= 0) && (row < GRID_HEIGHT) && (col >= 0) && (col < GRID_WIDTH);
    }

    /**
     * Print the minefield on terminal, mainly for testing
     */
    public void displayMineField() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                if (mineField[row][col].isHidden()) {
                    System.out.print("H");
                }
                else if (mineField[row][col].isFlagged()) {
                    System.out.print("F");
                }
                else {
                    if (mineField[row][col].isMine()) {
                        System.out.print("M");
                    }
                    else {
                        if (mineField[row][col].getNumAdjacentMines() > 0) {
                            System.out.print(mineField[row][col].getNumAdjacentMines());
                        }
                        else {
                            System.out.print(" ");
                        }
                    }
                }
                System.out.print(" | ");
            }
            System.out.println();
        }
    }

    public void setOrRemoveFlag(int row, int col) {
        if (mineField[row][col].isHidden() && remainingFlags > 0) {
            mineField[row][col].setCellStatus(CellStatus.FLAGGED);
            remainingFlags--;
        } else if (mineField[row][col].isFlagged()) {
            mineField[row][col].setCellStatus(CellStatus.HIDDEN);
            remainingFlags++;
        }
    }

    public MineFieldStatus getMineFieldStatus() {
       return mineFieldStatus;
    }

    public int getRemainingFlags() {
        return remainingFlags;
    }

    public Cell[][] getListCell() {
        return mineField;
    }
    public boolean getFirstClick(){
        return firstClick;
    }
}
