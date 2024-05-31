package com.mygdx.game;
import java.util.ArrayList;
public class AsteroidSpawning {
    public static void update(ArrayList<PhysicsObject> objects){
        ArrayList<Asteroid> asteroids = new ArrayList<Asteroid>();
        Player player = (Player)objects.get(0);
        for(int i = 1; i < objects.size(); i++){
            asteroids.add((Asteroid)objects.get(i));
        }
        spawnAsteroids(player,asteroids);
        deleteAsteroids(player,asteroids);
    }
    private static void spawnAsteroids(Player player, ArrayList<Asteroid> asteroids){
        double posX = player.getPosX();
        double posY = player.getPosY();
        double slope = player.getVelY()/player.getVelX();
        if(player.getVelX() > 0){
            for(int i = 0; i < 4; i++){

            }
        }
        else{

        }
    }
    private static void spawnAtPos(ArrayList<Asteroid> asteroids, double posX, double posY, double spread, double maxVel, double minRadius, double maxRadius, double minMass, double maxMass){
        posX += (Math.random()*spread*2)-1;
        posY += (Math.random()*spread*2)-1;
        double velX = (Math.random()*maxVel*2)-1;
        double velY = (Math.random()*maxVel*2)-1;
        double radius = (Math.random()*(maxRadius - minRadius)) + minRadius;
        double mass = (Math.random()*(maxMass - minMass)) + minMass;
        asteroids.add(new Asteroid(posX,posY,velX,velY,mass,radius));
    }
    private static void deleteAsteroids(Player player, ArrayList<Asteroid> asteroids){
        double dist;
        Asteroid currentAsteroid;
        for(int i = 0; i < asteroids.size(); i++){
            currentAsteroid = asteroids.get(i);
            dist = Math.sqrt(Math.pow(currentAsteroid.getPosX() - player.getPosX(), 2) + Math.pow(currentAsteroid.getPosY() - player.getPosY(), 2));
            if(dist > 2500){
                asteroids.remove(i);
            }
        }
    }
}
