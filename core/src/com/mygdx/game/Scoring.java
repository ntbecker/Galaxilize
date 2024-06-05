package com.mygdx.game;
import java.io.*;
import java.util.*;

public class Scoring {
public static final String SCORES_FILE =  "highscores.txt";
private List <Double> highScores;

public Scoring (){
    highScores = new ArrayList<>();

}
    public void addScore(double score) {
       highScores.add(score);
       // Collections.sort(highScores,Collection.reverseOrder());
        if (highScores.size() > 10) { // This will only store 10 scores
            highScores = highScores.subList(0, 10);
        }

    }
    public List<Double> getHighScores() {
        return highScores;
    }
      private void loadScores (){
          try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
              String line;
              while ((line = reader.readLine()) != null) {
                  highScores.add(Double.parseDouble(line));
              }
              Collections.sort(highScores, Collections.reverseOrder());
          } catch (IOException e) {
              System.out.println("Could not load scores: " + e.getMessage());
          }
      }

}












