package main.graphics;

import main.core.Cell;
import main.enums.Difficulty;

public interface ITrans {
    Cell[][] getListCell();

    void updateMineField(int row, int col);
    void setOrRemoveFlag(int row, int col);
    void restart();
    void undo();
    void changeLevel(String level);
}
