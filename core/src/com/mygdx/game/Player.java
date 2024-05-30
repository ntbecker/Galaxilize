package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends PhysicsObject{
    private static Texture playerTexture = new Texture("circleRadius10.png");

    int health;
    double fuel;
    int score;
    boolean isHooked;
    Asteroid hookedAsteroid;
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
        s.draw(playerTexture, (float) (posX-radius/2.0), (float) (posY-radius/2.0));
    }
    /**
     * Returns the current health of the player.
     * @return the health of the player.
     */
    public int getHealth(){ return(health); }

    /**
     * Sets the health of the player to the input value.
     * @param health the health of the player.
     */
    public void setHealth(int health){ this.health = health; }

    /**
     * Returns the current fuel of the player.
     * @return the fuel of the player.
     */
    public double getFuel(){ return(fuel); }

    /**
     * Sets the fuel of the player to the input value.
     * @param fuel the fuel of the player.
     */
    public void setFuel(double fuel){ this.fuel = fuel; }

    /**
     * Returns the current score of the player.
     * @return the score of the player.
     */
    public int getScore(){ return(score); }

    /**
     * Sets the score of the player to the input value.
     * @param score the score of the player.
     */
    public void setScore(int score){ this.score = score; }

    /**
     * Returns if the player is hooked to an asteroid.
     * @return a boolean holding if the player is hooked to an asteroid.
     */
    public boolean getIsHooked(){ return(isHooked); }

    /**
     * Sets if the player is hooked to an asteroid based on the input value.
     * @param isHooked a boolena holding if the player is hooked to an asteroid.
     */
    public void setIsHooked(boolean isHooked){ this.isHooked = isHooked; }

    /**
     * Returns the asteroid that the player is hooked onto.
     * @return the asteroid that the player is hooked onto.
     */
    //public Asteroid getHookedAsteroid() { return(hookedAsteroid); }

    /**
     * Sets what asteroid the player is hooked onto to the input object.
     * @param hookedAsteroid the asteroid that the player is hooked onto.
     */
    //public void setHookedAsteroid(Asteroid hookedAsteroid){ this.hookedAsteroid = hookedAsteroid; }

    /**
     * Returns a string holding the name of the ship.
     * @return a String holding the name of the ship.
     */
    public String getName() { return(name); }

    /**
     * Sets the name of the ship to the input string.
     * @param name a string the name of the ship
     */
    public void setName(String name){ this.name = name; }

    /**
     * Return a string holding the name of the player.
     * @return a string holding the name of the player.
     */
    public String getPlayerName(){ return(playerName); }

    /**
     * Sets the name of the player to the input string.
     * @param playerName a string holding the name of the player.
     */
    public void setPlayerName(String playerName){ this.playerName = playerName; }
    /*public String toString(){
        String returnString = "";
        super.toString()
    }*/
    public void updateHook(){
        if(isHooked) {
            double dist =
            double angle = Math.atan((hookedAsteroid.getPosY() - posY)/(hookedAsteroid.getPosX() - posX));
            angle += Math.PI/2;
            double tanVel = velX*Math.cos(angle) + velY*Math.sin(angle);
            double forceCent = (tanVel*tanVel)/
        }
    }
}
