package main.utils;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;
import java.util.logging.Level;
public class AudioPlayer {
    //Declare audio files location
    private String gameoverPath = "src/main/assets/sound" + File.separator + "gameover.wav";
    private String explodePath = "src/main/assets/sound" + File.separator + "explode.wav";
    private String victoryPath = "src/main/assets/sound" + File.separator + "victory.wav";
    private Clip gameoverSound, explodeSound, victorySound;
    
   
    public AudioPlayer(){
            try {
                gameoverSound = AudioSystem.getClip();
                explodeSound = AudioSystem.getClip();
                victorySound = AudioSystem.getClip();
                gameoverSound.open(AudioSystem.getAudioInputStream(new File(gameoverPath).getAbsoluteFile()));
                explodeSound.open(AudioSystem.getAudioInputStream(new File(explodePath).getAbsoluteFile()));
                victorySound.open(AudioSystem.getAudioInputStream(new File(victoryPath).getAbsoluteFile()));
            }
            //Exceptions handling for audio file types
            catch (LineUnavailableException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(AudioPlayer.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    public void PlayGameOver(){
        gameoverSound.setFramePosition(0);//Implement to play the game over audio again when the method is called
        gameoverSound.start();//Play audio when the method is called
    }
    public void PlayExplode(){
        explodeSound.setFramePosition(0);//Implement to play the explode audio again when the method is called
        explodeSound.start();//Play audio when the method is called
    }
    public void PlayVictory(){
        victorySound.setFramePosition(0);//Implement to play the explode audio again when the method is called
        victorySound.start();//Play audio when the method is called
    }
}
