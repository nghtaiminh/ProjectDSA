package main.core;

import main.enums.MineFieldStatus;
import main.enums.Difficulty;
import main.enums.CellStatus;

import java.util.Random;

public class MineField {
    private final int GRID_WIDTH;
    private final int GRID_HEIGHT;
    private final int numMines;

    private Cell[][] mineField;
    private boolean visited[][];

    private MineFieldStatus mineFieldStatus = MineFieldStatus.NOT_CLEARED;
    private final Difficulty difficulty;

    int[][] directions = new int[][] {{-1,0},{1,0},{0,-1},{0,1},{-1,-1},{1,1},{-1,1},{1,-1}};

    public MineField(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.GRID_WIDTH = difficulty.getGridWidth();
        this.GRID_HEIGHT = difficulty.getGridHeight();
        this.numMines = difficulty.getNumberOfMines();
        mineField = new Cell[GRID_HEIGHT][GRID_WIDTH];

        createMineField();
        generateMines();
        generateData();
    }

    /**
     * Initialize the minefield with an empty grid
     * */
    public void createMineField() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                mineField[row][col] = new Cell();
            }
        }
    }

    /**
     * Select random cells as mine
     */
    public void generateMines() {
        int countMines = 0;
        Random rand = new Random();
        while (countMines < numMines) {
            int col = rand.nextInt(GRID_HEIGHT);
            int row = rand.nextInt(GRID_WIDTH);

            if (!mineField[row][col].isMine()) {
                mineField[row][col].setMine(true);
                countMines++;
            }
        }
    }

    /**
     *  Count all the mines in the 8 adjacent cells
     */
    public void generateData() {
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

    public int[] updateMineFiled(int clickedRow, int clickedCol) {
        if (mineField[clickedRow][clickedCol].isMine() && mineField[clickedRow][clickedCol].isHidden()) {
            revealAllCells();
            mineFieldStatus = MineFieldStatus.EXPLODED;
            return new int[] {clickedRow, clickedCol};
        }
        visited = new boolean[GRID_HEIGHT][GRID_WIDTH];
        reveal(clickedRow, clickedCol);
        return new int[] {clickedRow, clickedCol};
    }

    public void reveal(int row, int col) {

        if (!(isValidCell(row, col) && !visited[row][col])) {
            return;
        }
        visited[row][col] = true;

        if (mineField[row][col].getNumAdjacentMines() > 0) {
            mineField[row][col].setCellStatus(CellStatus.OPENED);
            return;
        }

        for (int[] dir : directions) {
            reveal(row + dir[0], col + dir[1]);
        }
        mineField[row][col].setCellStatus(CellStatus.OPENED);
    }

    public void revealAllCells() {
        for (int row = 0; row < GRID_HEIGHT; row++) {
            for (int col = 0; col < GRID_WIDTH; col++) {
                mineField[row][col].setCellStatus(CellStatus.OPENED);
            }
        }
    }

    public boolean isValidCell(int x, int y) {
        return (y >= 0) && (y < GRID_HEIGHT) && (x >= 0) && (x < GRID_WIDTH);
    }

    /**
     * Print the minefield on matrix, mainly for testing
     */
    public void displayMindField() {
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



    public void setFlag(int row, int col) {
        if (mineField[row][col].isHidden()) {
            mineField[row][col].setCellStatus(CellStatus.FLAGGED);
        }
    }

    public void removeFlag(int row, int col) {
        if (mineField[row][col].isFlagged()) {
            mineField[row][col].setCellStatus(CellStatus.HIDDEN);
        }
    }
}
