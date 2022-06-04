package main;
import main.enums.Difficulty;
import main.graphics.GameGUI;
public class Minesweeper {

    public static void main(String[] args) {
        GameGUI gui = new GameGUI(Difficulty.EASY);
        gui.setVisible(true);
    }
}