/*
Matthew Witherspoon, Nathan Becker, Muhammad Umar
June 11th 2024
A class that spawns and deletes asteroids around the player randomly to generate the game area
 */
package com.mygdx.game;
import java.util.ArrayList;
public class AsteroidSpawning {
    private static ArrayList<Double> positions = new ArrayList<Double>(); //A variable to hold all positions that have previously spawned asteroids from.
    private static ArrayList<Integer> exclusionRadius = new ArrayList<Integer>();

    /**
     * Empties the two arrays when called, used to fully reset the game after a loss or returning to the main menu
     */
    public static void reset(){
        positions.clear();
        exclusionRadius.clear();
    }

    /**
     * Updates the spawning algorithm by activating the methods that spawn and delete asteroids. Also seperates asteroids and players into different variables for later manipulation.
     * @param objects all of the physics objects in the game world.
     */
    public static void update(ArrayList<PhysicsObject> objects) {
        ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>(); //An array list that will hold all of the asteroids.
        Player player = (Player) objects.get(0); //Pulls the player from the first index of the physics objects array list.
        for (int i = 1; i < objects.size(); i++) { //Loops through the rest of the physics object array list and puts all of the asteroids into a separate array list.
            asteroids.add((Asteroid) objects.get(i));
        }
        spawnAsteroids(player, asteroids); //Spawns new asteroids.
        deleteAsteroids(player, asteroids); //Deletes distant asteroids.
        objects.clear(); //Empty the objects array so no asteroids deleted or added aren't deleted or added to the world.
        objects.add(player); //Add the player at the first index of the array list.
        for (int i = 0; i < asteroids.size(); i++) {
            objects.add(asteroids.get(i));
        }
    }

    /**
     * spawns asteroids around the player's position.
     * @param player the player.
     * @param asteroids an arraylist of all asteroids currently in the world.
     */
    private static void spawnAsteroids(Player player, ArrayList<Asteroid> asteroids) {
        //Create variables for storing basic data about the player.
        double posX = player.getPosX();
        double posY = player.getPosY();
        for(int i = - 1; i <= 1; i++) { //Loop through 3 values spawning three clusters of asteroids just outside of each side of the screen.
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the left side of the screen.
                spawnAtPos(asteroids, posX - 700, posY + i * 500, 250, 0.5, 5,100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the right side of the screen.
                spawnAtPos(asteroids, posX + 700, posY + i * 500, 250, 0.5, 5, 100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the top side of the screen.
                spawnAtPos(asteroids, posX + i * 500, posY + 700, 250, 0.5, 5, 100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the bottom side of the screen.
                spawnAtPos(asteroids, posX + i * 500, posY - 700, 250, 0.5, 5, 100);
            }
        }
    }

    /**
     * Spawns a randomly generated asteroid at a position.
     * @param asteroids the array list of all currently existing asteorids.
     * @param posX the x position to base the asteroid generation around.
     * @param posY the y position to base the asteroid generation around.
     * @param spread the maximum distance that asteroids can be spawned from the point.
     * @param maxVel the maximum velocity an asteroid can be spawned with.
     * @param minSize the minimum size an asteroid can be spawned with.
     * @param maxSize the maximum size an asteroid can be spawned with.
     */
    private static void spawnAtPos(ArrayList<Asteroid> asteroids, double posX, double posY, double spread, double maxVel, double minSize, double maxSize){
        //Finds a position to attempt to spawn an asteroid at.
        posX += (Math.random()*spread*2)-spread;
        posY += (Math.random()*spread*2)-spread;
        boolean spawn = true; //Sets the variable that determines whether an asteroid will be spawned to true.
        for(int i = 0; i < positions.size(); i+= 2){ //Loops through every previous position that has spawned an asteroid.
            //Checks the distance from each point to the current position that the asteroid is attmepting to be spawned at.
            double dist = Math.sqrt(Math.pow(positions.get(i) - posX, 2) + Math.pow(positions.get(i + 1) - posY, 2));
            if(dist < exclusionRadius.get(i/2)){ //Compares the distance between the radius that prevents spawning on the previously spawned positions.
                spawn = false;
            }
        }
        if(spawn) { //Spawns an asteroid if proximity does not impede it.
            //Add the position that is spawning the asteroid to the array list.
            positions.add(posX);
            positions.add(posY);
            //Add a random exclusion zone around the asteroid for less uniform spacing between asteroids.
            exclusionRadius.add((int)(Math.random()*200) + 200);
            //Randomizes attributes of the asteroid.
            double velX = (Math.random() * maxVel * 2) - maxVel;
            double velY = (Math.random() * maxVel * 2) - maxVel;
            double size = (Math.random() * (maxSize - minSize)) + minSize;
            double radius = size/2;
            double mass =  size/2;
            asteroids.add(new Asteroid(posX, posY, velX, velY, mass, radius)); //Spawn the asteroid by adding it to the array.
        }
    }

    /**
     * Save system resources by deleting asteroids that are too far from the player.
     * @param player the player.
     * @param asteroids the array lists of current existing asteroids.
     */
    private static void deleteAsteroids(Player player, ArrayList<Asteroid> asteroids){
        double dist;
        Asteroid currentAsteroid;
        for(int i = 0; i < asteroids.size(); i++){ //Loop through all asteroids and if they are more than 2000 units from the player delete them.
            currentAsteroid = asteroids.get(i);
            dist = Math.sqrt(Math.pow(currentAsteroid.getPosX() - player.getPosX(), 2) + Math.pow(currentAsteroid.getPosY() - player.getPosY(), 2));
            if(dist > 2000){
                asteroids.remove(i);
            }
        }
    }

}
