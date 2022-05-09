package main.core;

import main.enums.MineFieldStatus;
import main.enums.Difficulty;
import main.enums.CellStatus;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class MineField {
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final int numMines;
    private int remainingMines;
    private boolean firstClick = true;

    private Cell[][] mineField;

    private MineFieldStatus mineFieldStatus = MineFieldStatus.NOT_CLEARED;
    private final Difficulty difficulty;

    private int[][] directions = new int[][] {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{1,1},{-1,1},{1,-1}};

    public MineField(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.GRID_WIDTH = difficulty.getGridWidth();
        this.GRID_HEIGHT = difficulty.getGridHeight();
        this.numMines = difficulty.getNumberOfMines();
        this.remainingMines = difficulty.getNumberOfMines();
        mineField = new Cell[GRID_HEIGHT][GRID_WIDTH];

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
        if(firstClick){
            generateMines(clickedRow, clickedCol);
            generateMineNumber();
            firstClick = false;
            updateMineField(clickedRow, clickedCol);
        }

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

                    if (isValidCell(nextCol, nextRow) && !visitedCells[nextRow][nextCol] && mineField[nextRow][nextCol].isHidden() && !mineField[nextRow][nextCol].isMine()) {
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

    public void setMineFieldStatus(MineFieldStatus status) {
        this.mineFieldStatus = status;
    }

    public boolean isValidCell(int x, int y) {
        return (y >= 0) && (y < GRID_HEIGHT) && (x >= 0) && (x < GRID_WIDTH);
    }

    /**
     * Print the minefield on terminal, mainly for testing
     */
//    public void displayMineField() {
//        for (int row = 0; row < GRID_HEIGHT; row++) {
//            for (int col = 0; col < GRID_WIDTH; col++) {
//                if (mineField[row][col].isHidden()) {
//                    System.out.print("H");
//                }
//                else if (mineField[row][col].isFlagged()) {
//                    System.out.print("F");
//                }
//                else {
//                    if (mineField[row][col].isMine()) {
//                        System.out.print("M");
//                    }
//                    else {
//                        if (mineField[row][col].getNumAdjacentMines() > 0) {
//                            System.out.print(mineField[row][col].getNumAdjacentMines());
//                        }
//                        else {
//                            System.out.print(" ");
//                        }
//                    }
//                }
//                System.out.print(" | ");
//            }
//            System.out.println();
//        }
//    }

    public void setFlag(int row, int col) {
        if (mineField[row][col].isHidden() && !mineField[row][col].isFlagged()) {
            mineField[row][col].setCellStatus(CellStatus.FLAGGED);
            remainingMines--;
        }
    }

    public void removeFlag(int row, int col) {
        if (mineField[row][col].isFlagged()) {
            mineField[row][col].setCellStatus(CellStatus.HIDDEN);
            remainingMines++;
        }
    }

    public MineFieldStatus getMineFieldStatus() {
       return mineFieldStatus;
    }

    public int getRemainingMines() {
        return remainingMines;
    }
}
