package main.graphics;

import main.core.*;
import main.utils.ImageLoader;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel extends JPanel implements ICommon{
    private JButton btUndo, btRestart;
    private JLabel lbTimeIcon, lbTime, lbRemainingFlags, lbRemainingFlagsIcon;
    private Timer timer;
    private JComboBox cbSelectedLevel;

    private ITrans listener;
    private ImageLoader imageLoader;

    int time = 0, undoLimit = 3;
    public ControlPanel() {
        imageLoader = new ImageLoader();
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
        Font font = new Font("VNI", Font.PLAIN, 15);

        String levelList[]={"Easy", "Medium", "Hard"};
        cbSelectedLevel = new JComboBox<>(levelList);
        cbSelectedLevel.setFont(font);
        cbSelectedLevel.setBounds(10, 0, 100, 30);
        add(cbSelectedLevel);

        btUndo = new JButton(String.format("Undo (%d)", undoLimit));
        btUndo.setFont(font);
        btUndo.setBounds(120,0,100,30);
        btUndo.setEnabled(false);
        add(btUndo);

        btRestart = new JButton("Restart");
        btRestart.setFont(font);
        btRestart.setBounds(230,0,100,30);
        add(btRestart);

        lbTimeIcon = new JLabel();
        lbTimeIcon.setBounds(330,0,30,30);
        ImageIcon icTimer = new ImageIcon(imageLoader.getListImage().get("timer"));
        Icon renderedTimerIcon = new ImageIcon(icTimer.getImage().getScaledInstance(lbTimeIcon.getWidth(), lbTimeIcon.getHeight(), Image.SCALE_SMOOTH));
        lbTimeIcon.setIcon(renderedTimerIcon);
        add(lbTimeIcon);

        lbTime = new JLabel();
        lbTime.setFont(font);
        lbTime.setText("0");
        lbTime.setBounds(360,0,50,30);
        add(lbTime);

        lbRemainingFlagsIcon = new JLabel();
        lbRemainingFlagsIcon.setBounds(420, 0,30,30);
        ImageIcon icFlag = new ImageIcon(imageLoader.getListImage().get("flag_icon"));
        Icon renderedFlag = new ImageIcon(icFlag.getImage().getScaledInstance(lbTimeIcon.getWidth(), lbTimeIcon.getHeight(), Image.SCALE_SMOOTH));
        lbRemainingFlagsIcon.setIcon(renderedFlag);
        add(lbRemainingFlagsIcon);

        lbRemainingFlags = new JLabel();
        lbRemainingFlags.setFont(font);
        lbRemainingFlags.setBounds(445,0,30,30);
        add(lbRemainingFlags);

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
                if (time <= 999 ) {
                    lbTime.setText(String.valueOf(time));
                    time++;
                }
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

    public void setTime(String time) {
        lbTimeIcon.setText(time);
    }

    public void restartTimer() {
        timer.stop();
        this.time = 0;
        lbTime.setText(String.valueOf(time));
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

    public void setLbRemainingFlags(int nFlags) {
        lbRemainingFlags.setText(String.valueOf(nFlags));
    }
}
