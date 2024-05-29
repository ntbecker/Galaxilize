package com.mygdx.game;

public class Player extends PhysicsObject{
    int health;
    double fuel;
    int score;
    boolean isHooked;
    Asteroid hookedAsteroid;
    String name;
    String playerName;
    public Player(double posX, double posY, double velX, double velY){
        super(posX,posY,velX,velY,1,100);
        health = 100;
        fuel = 100;
        score = 0;
        isHooked = false;
        hookedAsteroid = null;
        name = "The Skeld";
        playerName = "John Doe";
    }
    public Player(double posX, double posY, double velX, double velY, int health, double fuel){
        this(posX,posY,velX,velY);
        this.health = health;
        this.fuel = fuel;
    }
}
