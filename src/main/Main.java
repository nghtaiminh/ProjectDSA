package main;
import main.enums.Difficulty;
import main.graphics.GameGUI;
public class Main {

    public static void main(String[] args) {
        GameGUI gui = new GameGUI(Difficulty.EASY);
        gui.setVisible(true);
    }
}