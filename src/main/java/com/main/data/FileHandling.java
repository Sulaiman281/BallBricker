package com.main.data;

import com.main.controller.Singleton;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandling {

    /**
     * save the highscore of player in highscore file.
     */
    public static void save_highScore() {
        if(!Singleton.getInstance().highScore.isEmpty()){
            int lastScore = Integer.parseInt(Singleton.getInstance().highScore.split(":")[1]);
            if(Singleton.getInstance().active_player.getScore() < lastScore)
                return;
        }
        try {
            String path = System.getProperty("user.home");

            File file = new File(path.concat("/brick_ball"));
            file.mkdirs();

            file = new File(file.getPath().concat("/highscore.txt"));
            FileWriter writer = new FileWriter(file);
            if (file.exists()) {
                writer.append(Singleton.getInstance().active_player.getName()).
                        append(": ").append(String.valueOf(Singleton.getInstance().active_player.getScore())).append("\n");
                writer.flush();
                writer.close();
                System.out.println("Updated HighScore Successfully");
            } else {
                file.createNewFile();
                writer.write(Singleton.getInstance().active_player.getName() + ": " + Singleton.getInstance().active_player.getScore() + "\n");
                writer.flush();
                writer.close();
                System.out.println("Created HighScore Successfully");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * load the highscore file. and return text in String.
     *
     * @return
     */
    public static String load_highScore() {
        StringBuilder str = new StringBuilder();
        try {
            String path = System.getProperty("user.home");
            File file = new File(path.concat("/brick_ball/highscore.txt"));
            if (file.exists()) {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) {
                    str.append(scanner.nextLine());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return str.toString();
    }
}
