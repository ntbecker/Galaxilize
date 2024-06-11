/*
 Nathan Becker, Muhammad Umar, Matthew Witherspoon
 */
package com.mygdx.game;

import java.io.*;
import java.util.*;

public class Scoring {
    private static final String SCORES_FILE = "highscores.txt";// The file where high scores will be stored
    private ArrayList<Integer> highScores; // Holds all the scores
    private ArrayList<String> scoreNames; // Holds all the names correlating with each score.
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
                File file = new File(SCORES_FILE); // Creates the file
                PrintWriter writer = new PrintWriter(file); // Uses the PrintWriter object to write the scores into a file.
                for (int i = 0; i < highScores.size(); i++){ //Adds every score with the correlating name to the file.
                    writer.println(scoreNames.get(i));
                    writer.println(highScores.get(i));
                }
                writer.close(); //Finishes writing to the file.
            } catch (FileNotFoundException e) { //Displays an error message if an error is encountered.
                System.out.println("An error occurred while saving the scores." + e);
           }
    }



    /**
     * Method to display high scores from the file.
     */
    public String displayHighScores( ) {
        String output  = "";
        output += ("Top 10 High Scores:"); //Adds a header to the message.
        int scoresToDisplay = 10; //Sets the amount of scores to display to 10.
        if(highScores.size() < 10){ //If less than 10 scores exist only display as many scores as there are.
            scoresToDisplay = highScores.size();
        }
        for (int i = 0; i < scoresToDisplay; i++) { //Loop through the scores creating a formatted row containing the score holder's name, their score and position.
            output += ("\n" + (i + 1) + ". " + scoreNames.get(i) + " - " + highScores.get(i)) + " points.";
        }
        return(output); //Returns the final output.
    }

    /**
     * Searches for a score in the file based on the name given by the user.
     * @param nameSearch the name the user is searching for.
     * @return a string containing all the scores the searched user holds.
     */
    public String searchScore(String nameSearch){
        String output; //Creates a variable to store the output.
        boolean foundName = false; //A variable that will track if anyone with the input name has been found yet.
        ArrayList<Integer> foundScores = new ArrayList<Integer>(); //Create an array list to hold all the scores found under the user's name.
        for(int i = 0; i < highScores.size(); i++){ //Loops through all high scores and check the name of the person who hold each store.
            if(nameSearch.equalsIgnoreCase(scoreNames.get(i))){ //Compares the name of the current score to the name being searched for.
                foundName = true;
                foundScores.add(highScores.get(i));
            }
        }
        if(foundName){ //Create an output of all the user's scores if they had any.
            output = nameSearch + "'s Scores:";
            for(int i = 0; i < foundScores.size(); i++){ //Loops through all the user's scores and adds them to the output.
                output += "\n" + foundScores.get(i) + "points.";
            }
        }
        else{ //Output a message if no user was found with the input name.
            output = "There was no scores found for user: " + nameSearch;
        }
        return(output);
    }
}











