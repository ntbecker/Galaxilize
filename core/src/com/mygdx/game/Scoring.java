package com.mygdx.game;
// my imports
import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class Scoring {
    private static final String SCORES_FILE = "highscores.txt";// The file where high scores will be stored
    private ArrayList<Integer> highScores; // store high scores
    private ArrayList<String> scoreNames;
    /**
     * Constructor method initializing the highScores and load files
     */
    public Scoring() {
        highScores = new ArrayList<>();
        scoreNames = new ArrayList<>();
        loadScores();
    }

    /**
     * Method to add new score to the high score list
     * @param score - the score
     */
    public void addScore(int score, String playerName) {
        highScores.add(score);
        scoreNames.add(playerName);
        saveScores();
    }

    /**
     * Method to load scores from file
     */


    private void loadScores() {
        try {
            File file = new File(SCORES_FILE);// create a file
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                scoreNames.add(scanner.nextLine());
                highScores.add(Integer.parseInt(scanner.nextLine()));
            }
            highScores = dscMergeSort(highScores);
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
                for (int i = 0; i < highScores.size(); i++){
                    writer.println(scoreNames.get(i));
                    writer.println(highScores.get(i));
                }
                writer.close();
            } catch (FileNotFoundException e) {
                System.out.println("An error occurred while saving the scores." + e);
           }
    }
    public ArrayList<Integer> dscMerge(ArrayList<Integer> highScores){
        return(highScores);
    }
    public ArrayList<Integer> dscMergeSort(ArrayList<Integer> highScores){
        return(highScores);
    }
    /**
     * Method to display high scores from the file.
     */
    public String displayHighScores( ) {
        String output  = "";

        output += ("Top 10 High Scores:");
        int scoresToDisplay = 10;
        if(highScores.size() < 10){
            scoresToDisplay = highScores.size();
        }
        for (int i = 0; i < scoresToDisplay; i++) {
            output += ("\n" + (i + 1) + ". " + scoreNames.get(i) + " - " + highScores.get(i)) + " points.";
        }
        return(output);
    }
    public String searchScore(String nameSearch){
        String output;
        boolean foundName = false;
        ArrayList<Integer> foundScores = new ArrayList<Integer>();
        for(int i = 0; i < highScores.size(); i++){
            if(nameSearch.equalsIgnoreCase(scoreNames.get(i))){
                foundName = true;
                foundScores.add(highScores.get(i));
            }
        }
        if(foundName){
            output = nameSearch + "'s Scores:";
            for(int i = 0; i < foundScores.size(); i++){
                output += "\n" + foundScores.get(i);
            }
        }
        else{
            output = "There was no scores found for user: " + nameSearch;
        }
        return(output);
    }
//    /**
//     * Main Method to test everything
//     * @param args
//     */
//    public static void main(String[] args) {
//        Scoring scoring = new Scoring();
//
//        // Simulate adding scores
//        scoring.addScore(100);
//        scoring.addScore(200);
//        scoring.addScore(150);
//
//        // Display high scores
//        scoring.displayHighScores();
//    }
}











