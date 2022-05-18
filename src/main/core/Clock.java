package main.core;
import javax.swing.*;
import java.awt.*;

public class Clock extends JLabel {
    private boolean go;
    private Runnable task;

    public Clock() {
        super("0", SwingConstants.CENTER);
        setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
        setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        task = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    // do nothing, this is fine
                }
                while (go) {
                    setText(String.valueOf(Integer.valueOf(getText()) + 1));
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        // do nothing, this is fine
                    }

                }
            }
        };
        go = false;
    }

    /**
     * Starts the timer
     */
    public void start() {
        go = true;
        new Thread(task).start();
    }

    /**
     * Stops the timer
     */
    public void stop() {
        go = false;
    }

    /**
     * Resets the timer
     */
    public void reset() {
        go = false;
        setText("0");
    }
}
