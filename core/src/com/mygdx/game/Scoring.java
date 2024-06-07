package com.mygdx.game;
// my imports
import java.io.*;
import java.util.*;

public class Scoring {
    private static final String SCORES_FILE = "highscores.txt";// The file where high scores will be stored
    private List<Double> highScores; // store high scores

    /**
     * Constructor method initializing the highScores and load files
     */
    public Scoring() {
        highScores = new ArrayList<>();
        loadScores();
    }

    /**
     * Method to add new score to the high score list
     * @param score - the score
     */
    public void addScore(double score) {
        highScores.add(score);
        highScores.sort(Collections.reverseOrder());//setting high scores in the descending order
        if (highScores.size() > 10) {
            highScores = highScores.subList(0, 10);  // Keep only top 10 scores
        }
        saveScores(); // save the updated scores
    }

    /**
     * Method to load scores from file
     */


    private void loadScores() {
        try {
            FileReader fileReader = new FileReader(SCORES_FILE);// create a file
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextDouble()) {
                highScores.add(scanner.nextDouble());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Scores file not found. Starting with an empty high scores list.");
        }
    }

    /**
     * Method to save the scores of the file
     */
    private void saveScores() {

            try {
                File file = new File(SCORES_FILE); // create a file
                PrintWriter writer = new PrintWriter(file); // using this new function known as PrintWriter which will write to the file
                for (double score : highScores) {
                    writer.println(score);
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while saving the scores." + e);



        }
    }

    /**
     * Method to diplay high scores to the file
     */

    public void displayHighScores( ) {
        System.out.println("High Scores:");
        for (int i = 0; i < highScores.size(); i++) {
            System.out.println((i + 1) + ". " + highScores.get(i));
        }
    }

    /**
     * Main Method to test everything
     * @param args
     */
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











