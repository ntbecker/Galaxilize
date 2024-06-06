package com.mygdx.game;
import java.io.*;
import java.util.*;

public class Scoring {
    private static final String SCORES_FILE = "highscores.txt";
    private List<Double> highScores;

    public Scoring() {
        highScores = new ArrayList<>();
        loadScores();
    }

    public void addScore(double score) {
        highScores.add(score);
        highScores.sort(Collections.reverseOrder());//setting high scores in the descending order
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);  // Keep only top 10 scores
        }
        saveScores();
    }



    private void loadScores() {
        try {
            FileReader fileReader = new FileReader(SCORES_FILE);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextDouble()) {
                highScores.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Scores file not found. Starting with an empty high scores list.");
        }
    }

    private void saveScores() {
        try {
            FileReader fileReader = new FileReader(SCORES_FILE);
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextDouble()) {
                highScores.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Scores file not found. Starting with an empty high scores list.");
        }
    }

    public void displayHighScores() {
        System.out.println("High Scores:");
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println((i + 1) + ". " + highScores.get(i));
        }
    }

    public static void main(String[] args) {
        Scoring scoring = new Scoring();

        // Simulate adding scores
        scoring.addScore(100);
        scoring.addScore(200);
        scoring.addScore(150);

        // Display high scores
        scoring.displayHighScores();
    }
}











