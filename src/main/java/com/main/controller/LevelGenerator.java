package com.main.controller;

import com.main.Settings;

import java.util.Random;

public class LevelGenerator {

    private int complexity = 4;

    private char[] bricks = {' ', 'P', 'S', 'B'};

    public char[][] generateLevel() {
        char[][] level = new char[Settings.BOX_COLS][Settings.BOX_ROWS];

        for (int i = 0; i < Settings.BOX_COLS; i++) {
            for (int j = 0; j < Settings.BOX_ROWS; j++) {
                level[i][j] = ' ';
            }
        }

        for (int i = 0; i < complexity; i++) {
            for (int j = 0; j < complexity; j++) {
                char ch = bricks[(new Random().nextInt(bricks.length-1))+1];
                level[new Random().nextInt(Settings.BOX_COLS)][new Random().nextInt(Settings.BOX_ROWS)] = ch;
            }
        }
        return level;
    }

    public int getComplexity() {
        return complexity;
    }

    public void setComplexity(int complexity) {
        this.complexity = complexity;
    }
}
