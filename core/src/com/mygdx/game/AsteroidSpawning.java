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
        for(int i = 0; i < objects.size(); i++){
            objects.remove(0);
        }
        objects.add(player);
        for(int i = 0; i < asteroids.size(); i++){
            objects.add(asteroids.get(i));
        }
    }
    private static void spawnAsteroids(Player player, ArrayList<Asteroid> asteroids){
        double posX = player.getPosX();
        double posY = player.getPosY();
        double slope = player.getVelY()/player.getVelX();
        double multi = 1;
        if(player.getVelX() > 0){
            multi = 1;
        }
        else{
            multi = -1;
        }
        posX += 625* multi;
        posY += slope*625*multi;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 84; j++){
                AsteroidSpawning.spawnAtPos(asteroids,posX,posY,250, 0.5,10,10,5,20);
            }
        }
    }
    private static void spawnAtPos(ArrayList<Asteroid> asteroids, double posX, double posY, double spread, double maxVel, double minRadius, double maxRadius, double minMass, double maxMass){
        posX += (Math.random()*spread*2)-spread;
        posY += (Math.random()*spread*2)-spread;
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
            if(dist > 5000){
                asteroids.remove(i);
            }
        }
    }
}
