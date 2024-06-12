/*
Matthew Witherspoon, Nathan Becker, Muhammad Umar
June 11th 2024
A class that spawns and deletes asteroids around the player randomly to generate the game area
 */
package com.mygdx.game;
import java.util.ArrayList;
public class Spawning {
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
        ArrayList<PhysicsObject> tempObjects = new ArrayList<PhysicsObject>(); //An array list that will hold all of the asteroids.
        Player player = (Player) objects.get(0); //Pulls the player from the first index of the physics objects array list.
        for (int i = 1; i < objects.size(); i++) { //Loops through the rest of the physics object array list and puts all of the asteroids into a separate array list.
            tempObjects.add(objects.get(i));
        }
        spawnAsteroids(player, tempObjects); //Spawns new asteroids.
        deleteAsteroids(player, tempObjects); //Deletes distant asteroids.
        objects.clear(); //Empty the objects array so no asteroids deleted or added aren't deleted or added to the world.
        objects.add(player); //Add the player at the first index of the array list.
        for (int i = 0; i < tempObjects.size(); i++) {
            objects.add(tempObjects.get(i));
        }
    }

    /**
     * spawns asteroids around the player's position.
     * @param player the player.
     * @param objects an arraylist of all asteroids currently in the world.
     */
    private static void spawnAsteroids(Player player, ArrayList<PhysicsObject> objects) {
        //Create variables for storing basic data about the player.
        double posX = player.getPosX();
        double posY = player.getPosY();
        for(int i = - 1; i <= 1; i++) { //Loop through 3 values spawning three clusters of asteroids just outside of each side of the screen.
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the left side of the screen.
                spawnAtPos(objects, posX - 700, posY + i * 500, 250, 0.5, 5,100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the right side of the screen.
                spawnAtPos(objects, posX + 700, posY + i * 500, 250, 0.5, 5, 100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the top side of the screen.
                spawnAtPos(objects, posX + i * 500, posY + 700, 250, 0.5, 5, 100);
            }
            for (int j = 0; j < 5; j++) { //Spawns 5 asteroids on the bottom side of the screen.
                spawnAtPos(objects, posX + i * 500, posY - 700, 250, 0.5, 5, 100);
            }
        }
    }

    /**
     * Spawns a randomly generated asteroid at a position.
     * @param objects the array list of all currently existing objects.
     * @param posX the x position to base the asteroid generation around.
     * @param posY the y position to base the asteroid generation around.
     * @param spread the maximum distance that asteroids can be spawned from the point.
     * @param maxVel the maximum velocity an asteroid can be spawned with.
     * @param minSize the minimum size an asteroid can be spawned with.
     * @param maxSize the maximum size an asteroid can be spawned with.
     */
    private static void spawnAtPos(ArrayList<PhysicsObject> objects, double posX, double posY, double spread, double maxVel, double minSize, double maxSize){
        //Finds a position to attempt to spawn an asteroid at.
        posX += (Math.random()*spread*2)-spread;
        posY += (Math.random()*spread*2)-spread;
        boolean spawn = true; //Sets the variable that determines whether an object will be spawned to true.
        for(int i = 0; i < positions.size(); i+= 2){ //Loops through every previous position that has spawned an object.
            //Checks the distance from each point to the current position that the object is attempting to be spawned at.
            double dist = Math.sqrt(Math.pow(positions.get(i) - posX, 2) + Math.pow(positions.get(i + 1) - posY, 2));
            if(dist < exclusionRadius.get(i/2)){ //Compares the distance between the radius that prevents spawning on the previously spawned positions.
                spawn = false;
            }
        }
        if(spawn) { //Spawns an asteroid if proximity does not impede it.
            //Add the position that is spawning the object to the array list.
            positions.add(posX);
            positions.add(posY);

            //Add a random exclusion zone around the object for less uniform spacing between asteroids.
            exclusionRadius.add((int)(Math.random()*200) + 200);

            if((int)(Math.random()*20) == 0){ //Roll a chance to spawn an item
                int num = (int)(Math.random()*2);
                boolean isHealth = false;
                if(num == 0){
                    isHealth = true;
                }
                objects.add(new Item(posX,posY,0,0,50,10,isHealth));
            }
            else {
                //Randomizes attributes of the asteroid.
                double velX = (Math.random() * maxVel * 2) - maxVel;
                double velY = (Math.random() * maxVel * 2) - maxVel;
                double size = (Math.random() * (maxSize - minSize)) + minSize;
                double radius = size / 2;
                double mass = size / 2;
                objects.add(new Asteroid(posX, posY, velX, velY, mass, radius)); //Spawn the object by adding it to the array.
            }
        }
    }

    /**
     * Save system resources by deleting asteroids that are too far from the player.
     * @param player the player.
     * @param objects the array lists of current existing objects.
     */
    private static void deleteAsteroids(Player player, ArrayList<PhysicsObject> objects){
        double dist;
        PhysicsObject currentObject;
        for(int i = 0; i < objects.size(); i++){ //Loop through all objects and if they are more than 2000 units from the player delete them.
            currentObject = objects.get(i);
            dist = Math.sqrt(Math.pow(currentObject.getPosX() - player.getPosX(), 2) + Math.pow(currentObject.getPosY() - player.getPosY(), 2));
            if(dist > 2000){
                objects.remove(i);
            }
        }
    }

}
