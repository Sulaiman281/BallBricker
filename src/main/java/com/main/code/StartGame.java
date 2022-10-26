package com.main.code;

import java.awt.*;

public class StartGame {

    public static void main(String[] args){
        EventQueue.invokeLater(() -> new GameFrame().initialize());
    }
    // use [space] to start/pause the game
    // use [←] to move the player left
    // use [→] to move the player right
    // use [esc] to enter/exit pause menu
    // use [alt+shift+f1] at any time to display debug panel
}
