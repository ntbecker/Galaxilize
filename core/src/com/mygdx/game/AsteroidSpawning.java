package com.mygdx.game;
import java.util.ArrayList;
public class AsteroidSpawning {
    private static ArrayList<Double> positions = new ArrayList<Double>(); //A variable to hold all positions that have previously spawned asteroids from.
    private static ArrayList<Integer> exclusionRadius = new ArrayList<Integer>();
    public static void update(ArrayList<PhysicsObject> objects) {
        ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
        Player player = (Player) objects.get(0);
        for (int i = 1; i < objects.size(); i++) {
            asteroids.add((Asteroid) objects.get(i));
        }
        spawnAsteroids(player, asteroids);
        deleteAsteroids(player, asteroids);
        objects.clear();
        objects.add(player);
        for (int i = 0; i < asteroids.size(); i++) {
            objects.add(asteroids.get(i));
        }
    }

    private static void spawnAsteroids(Player player, ArrayList<Asteroid> asteroids) {
        double posX = player.getPosX();
        double posY = player.getPosY();
        double slope = player.getVelY() / player.getVelX();
        double multi = 1;
        double dist = 0;
        Asteroid tempAst;
        boolean detected = false;
        if (player.getVelX() > 0) {
            multi = 1;
        } else {
            multi = -1;
        }
        for(int i = - 1; i <= 1; i++) {
            for (int j = 0; j < 5; j++) {
                spawnAtPos(asteroids, posX - 650, posY + i * 500, 250, 0.5, 5, 50, 3, 30);
            }
            for (int j = 0; j < 5; j++) {
                spawnAtPos(asteroids, posX + 650, posY + i * 500, 250, 0.5, 5, 50, 3, 30);
            }
            for (int j = 0; j < 5; j++) {
                spawnAtPos(asteroids, posX + i * 500, posY + 650, 250, 0.5, 5, 50, 3, 30);
            }
            for (int j = 0; j < 5; j++) {
                spawnAtPos(asteroids, posX + i * 500, posY - 650, 250, 0.5, 5, 50, 3, 30);
            }
        }
    }
    private static void spawnAtPos(ArrayList<Asteroid> asteroids, double posX, double posY, double spread, double maxVel, double minRadius, double maxRadius, double minMass, double maxMass){
        posX += (Math.random()*spread*2)-spread;
        posY += (Math.random()*spread*2)-spread;
        boolean spawn = true;
        for(int i = 0; i < positions.size(); i+= 2){
            double dist = Math.sqrt(Math.pow(positions.get(i) - posX, 2) + Math.pow(positions.get(i + 1) - posY, 2));
            if(dist < exclusionRadius.get(i/2)){
                spawn = false;
            }
        }
        if(spawn) {
            positions.add(posX);
            positions.add(posY);
            exclusionRadius.add((int)(Math.random()*200) + 200);
            double velX = (Math.random() * maxVel * 2) - maxVel;
            double velY = (Math.random() * maxVel * 2) - maxVel;
            double radius = (Math.random() * (maxRadius - minRadius)) + minRadius;
            double mass = (Math.random() * (maxMass - minMass)) + minMass;
            asteroids.add(new Asteroid(posX, posY, velX, velY, mass, radius));
        }
    }
    private static void deleteAsteroids(Player player, ArrayList<Asteroid> asteroids){
        double dist;
        Asteroid currentAsteroid;
        for(int i = 0; i < asteroids.size(); i++){
            currentAsteroid = asteroids.get(i);
            dist = Math.sqrt(Math.pow(currentAsteroid.getPosX() - player.getPosX(), 2) + Math.pow(currentAsteroid.getPosY() - player.getPosY(), 2));
            if(dist > 2000){
                asteroids.remove(i);
            }
        }
    }

}
