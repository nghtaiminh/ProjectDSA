package main.core;

import main.enums.Difficulty;
import main.enums.MineFieldStatus;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

public class MineSweeper {
    private static Difficulty level = Difficulty.EASY;
    private MineField board = new MineField(level);


    public static void main(String[] args) {

    }
}
