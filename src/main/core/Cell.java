package main.core;

import main.enums.CellStatus;

public class Cell {
    private boolean isMine;
    private CellStatus cellStatus;
    private int numAdjacentMines = 0;

    public Cell() {
        isMine = false;
        cellStatus = CellStatus.HIDDEN;
    }

    public boolean isMine() {
        return isMine;
    }

    public boolean isHidden() {
        return cellStatus == CellStatus.HIDDEN;
    }

    public boolean isFlagged() {
        return cellStatus == CellStatus.FLAGGED;
    }

    public void setCellStatus(CellStatus status) {
        this.cellStatus = status;
    }

    public void setMine(boolean bool) {
        this.isMine = bool;
    }

    public void setNumAdjacentMines(int num) {
        this.numAdjacentMines = num;
    }

    public int getNumAdjacentMines() {
        return this.numAdjacentMines;
    }
}
