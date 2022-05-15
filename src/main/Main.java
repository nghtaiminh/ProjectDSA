
package main;
import main.enums.Difficulty;
import main.utils.AudioPlayer;
import main.graphics.GameGUI;
public class Main {
    private static AudioPlayer sound = new AudioPlayer();

    public static void main(String[] args) {
        GameGUI gui = new GameGUI();
        gui.setVisible(true);
    }
}