package main.graphics;

import main.core.Cell;

public interface ITrans {
    Cell[][] getListCell();

    void updateMineField(int row, int col);
    void setOrRemoveFlag(int row, int col);
    void undo();
}
