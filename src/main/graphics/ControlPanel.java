package main.graphics;
import main.core.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.TimerTask;
public class ControlPanel extends JPanel implements ICommon{
    // [TODO]: Display remaining mines, time
    
    
    // [TODO]: Restart button
    
    // [TODO]: Leaderboard button


    // [TODO]: Select difficulty
    LeaderBoard leaderBoard = new LeaderBoard();
    private JButton btUndo, btRestart, btLeaderBoard;
    private JLabel lbTime, lbRemainingMines, lbTimer;
    private Timer timer;
    private TimerTask tTask;
    private JComboBox cbSelectedLevel;
    private ITrans listener;
    int x = 0, count = 0;
    public ControlPanel() {
        initComponent();
        addComponent();
        addEvent();
    }

    @Override
    public void initComponent() {
        setLayout(null);
    }

    @Override
    public void addComponent() {
        String levelList[]={"Easy", "Medium", "Hard"};
        cbSelectedLevel = new JComboBox<>(levelList);
        cbSelectedLevel.setBounds(10, 0, 100, 30);
        add(cbSelectedLevel);

//        Image imgUndo = new ImageIcon(getClass().getResource("/main/assets/img/undo.png")).getImage();
        btUndo = new JButton("Undo");
        btUndo.setBounds(110,0,100,30);
//        Icon iUndo = new ImageIcon(imgUndo.getScaledInstance(btRestart.getWidth(), btRestart.getHeight(), Image.SCALE_SMOOTH));
//        btUndo.setIcon(iUndo);
        add(btUndo);

//        Image imgSmile = new ImageIcon(getClass().getResource("/main/assets/img/smile.png")).getImage();
        btRestart = new JButton("Restart");
        btRestart.setBounds(210 + 5,0,100,30);
//        Icon iSmile = new ImageIcon(imgSmile.getScaledInstance(btRestart.getWidth(), btRestart.getHeight(), Image.SCALE_SMOOTH));
//        btRestart.setIcon(iSmile);
        add(btRestart);

        lbTime = new JLabel("Time");
        lbTime.setBounds(310 + 5*2,0,100,30);
        add(lbTime);

        lbTimer = new JLabel();
        lbTimer.setBounds(350 + 5*2,0,100,30);
        add(lbTimer);

    }

    @Override
    public void addEvent() {
        btRestart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.restart();
                timer.stop();
                x = 0;
                timer = new Timer(1000, new  ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        lbTimer.setText(String.valueOf(x));
                        x++;
                    }
                });
                timer.start();
            }
        });

        btUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.undo();

                //Limit undo 
                count++;
                if(count == 3){
                    btUndo.setVisible(false);
                }
            }
        });


        cbSelectedLevel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String level = cbSelectedLevel.getSelectedItem().toString();
                listener.changeLevel(level);
            }
        });
        timer = new Timer(1000, new  ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                lbTimer.setText(String.valueOf(x));
                x++;
            }
        });
        //TODO: Check first click before starting the timer
        
        
    }

    public void addListener(ITrans event) {
        listener = event;
        timer.start();
    }
}
