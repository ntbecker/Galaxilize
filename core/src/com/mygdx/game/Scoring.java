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
            quickSort(highScores, scoreNames,0 , highScores.size()-1);
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Scores file not found. Starting with an empty high scores list.");
        }
    }

    /**
     * Quick sort algorithm, sorts a list recursively, in ascending order
     * @param scoreList The list to sort by (a list of integers)
     * @param nameList The list of names that correspond to scores
     * @param start The start of indices to sort
     * @param end The end of indices to sort
     */
    private static void quickSort(ArrayList<Integer> scoreList, ArrayList<String> nameList, int start, int end){
        // Base Case
        if(start >= end) {
            return;
        }

        // Last element is set as the pivot value
        int pivot = scoreList.get(end);

        // Index to put elements that are higher than the pivot (i.e. After sorted portion of the list)
        int highIndex = start;

        // Go up to 'end' exclusive, 'end' is the index of the pivot value
        for(int i = start; i < end; i++){
            /* If current value is larger than the pivot, we swap it with value at highIndex
             * e.g. [35,23,30,10,19,|50|,20] -> [35,23,30,|50|,19,10,20]
             * 50 is swapped with 10 because it is higher than the pivot (20) */
            if(scoreList.get(i) > pivot){
                // Swap items
                int temp = scoreList.get(i);
                scoreList.set(i,scoreList.get(highIndex));
                scoreList.set(highIndex,temp);

                // Swap names
                String sTemp = nameList.get(i);
                nameList.set(i,nameList.get(highIndex));
                nameList.set(highIndex,sTemp);

                // highIndex gets incremented to place next item one further to the right
                highIndex++;
            }
        }
        /* Move pivot to end of highIndex portion of the array
         * e.g. [23,35,30,50,19,10,|20|] -> [23,35,30,50,|20|,10,19] */
        int temp = scoreList.get(highIndex);
        scoreList.set(highIndex,pivot);
        scoreList.set(end,temp);

        // Swap names
        String sTemp = nameList.get(highIndex);
        nameList.set(highIndex,nameList.get(end));
        nameList.set(end,sTemp);

        // Recursive calls (Sort everything in the current portion but the pivot)
        quickSort(scoreList, nameList,start,highIndex-1);
        quickSort(scoreList,nameList,highIndex+1,end);
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











