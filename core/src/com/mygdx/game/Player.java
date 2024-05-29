package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends PhysicsObject{
    private static Texture playerTexture = new Texture("circleRadius10.jpg");

    int health;
    double fuel;
    int score;
    boolean isHooked;
    //Asteroid hookedAsteroid;
    String name;
    String playerName;

    /**
     * Partially filled constructor for the Player class
     * @param posX The X position of the player
     * @param posY The Y position of the player
     * @param velX The X velocity of the player
     * @param velY The Y velocity of the player
     * @param mass The mass of the player
     * @param radius The radius of the player's collisions
     */
    public Player(double posX, double posY, double velX, double velY, double mass, double radius){
        super(posX,posY,velX,velY,mass,radius);
        health = 100;
        fuel = 100;
        score = 0;
        isHooked = false;
        //hookedAsteroid = null;
        name = "";
        playerName = "";
    }

    /**
     * A fully filled constructor for the Player class
     * @param posX The X position of the player
     * @param posY The Y position of the player
     * @param velX The X velocity of the player
     * @param velY The Y velocity of the player
     * @param mass The mass of the player
     * @param radius The radius of the player's collisions
     * @param health The health of the player
     * @param fuel The fuel of the player
     */
    public Player(double posX, double posY, double velX, double velY, double mass, double radius, int health, double fuel){
        this(posX,posY,velX,velY, mass, radius);
        this.health = health;
        this.fuel = fuel;
    }

    /**
     * Draw's the texture of this ship
     * @param s The open spritebatch to draw to
     */
    public void draw(SpriteBatch s){
        s.draw(playerTexture, (float) posX, (float) posY);
    }
}
