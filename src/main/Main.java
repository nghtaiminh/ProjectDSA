package main;
import main.utils.AudioPlayer;
import main.graphics.GameGUI;
public class Main implements Runnable{
    private static AudioPlayer sound = new AudioPlayer();
    GameGUI gui = new GameGUI();
    // public Main() {
    //     GameGUI board = new GameGUI();
    // }
    public static void main(String[] args) {
	// write your code here
    	new Thread(new Main()).start();
    }

    public void run(){
        while(true){
            gui.repaint();
        }
    }
}
