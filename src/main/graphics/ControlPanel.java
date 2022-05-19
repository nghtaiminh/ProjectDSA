package main.graphics;
import main.core.*;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.TimerTask;
public class ControlPanel extends JPanel implements ICommon{
    // [TODO]: Display remaining mines, time
    // [TODO]: Leaderboard button
    LeaderBoard leaderBoard = new LeaderBoard();
    private JButton btUndo, btRestart, btLeaderBoard;
    private JLabel lbTime, lbRemainingMines, lbTimer;
    private Timer timer;
    private TimerTask tTask;
    private JComboBox cbSelectedLevel;
    private ITrans listener;
    int time = 0, undoLimit = 3;
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
        btUndo = new JButton(String.format("Undo (%d)", undoLimit));
        btUndo.setBounds(110,0,100,30);
//        Icon iUndo = new ImageIcon(imgUndo.getScaledInstance(btRestart.getWidth(), btRestart.getHeight(), Image.SCALE_SMOOTH));
//        btUndo.setIcon(iUndo);
        btUndo.setEnabled(false);
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
            }
        });

        btUndo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                listener.undo();

                if(undoLimit == 0){
                    btUndo.setEnabled(false);
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
                lbTimer.setText(String.valueOf(time));
                time++;
            }
        });
    }

    public void addListener(ITrans event) {
        listener = event;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer() {
        timer.stop();
    }

    public int getTime() {
        return time;
    }

    public void restartTimer() {
        timer.stop();
        this.time = 0;
        lbTimer.setText(String.valueOf(time));
    }

    public void restartUndoLimit() {
        this.undoLimit = 3;
        btUndo.setText(String.format("Undo (%d)", undoLimit));
    }

    public void updateUndoCount() {
        undoLimit--;
        btUndo.setText(String.format("Undo (%d)", undoLimit));
    }

    public int getUndoLimit() {
        return undoLimit;
    }

    public void setBtUndoVisible(Boolean bool) {
        btUndo.setEnabled(bool);
    }
}
