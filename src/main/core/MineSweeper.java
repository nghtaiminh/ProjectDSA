package main.core;
import main.enums.Difficulty;
import java.util.Stack;
public class MineSweeper {
    private static Difficulty level = Difficulty.EASY;
    private MineField board = new MineField(level);
    private Stack<MineField> undoStack = new Stack<>();
    //undoStack.push(board) ?????
    public void undo() {
        if (!undoStack.empty()) {
            this.board = undoStack.pop();
        }
    }

    public static void main(String[] args) {
    }
}
