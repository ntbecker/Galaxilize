/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/10/2024
 * Subclass of PhysicsObject, accepts keyboard and mouse input to control the player object, also implements the grapple hook feature
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Player extends PhysicsObject{
    private static Texture playerTexture = new Texture("Object_Textures/circleRadius10.png");

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
        s.draw(playerTexture, (float) (posX-radius), (float) (posY-radius));
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
    public Asteroid getHookedAsteroid() { return(hookedAsteroid); }

    /**
     * Sets what asteroid the player is hooked onto to the input object.
     * @param hookedAsteroid the asteroid that the player is hooked onto.
     */
    public void setHookedAsteroid(Asteroid hookedAsteroid){ this.hookedAsteroid = hookedAsteroid; }

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

    public void updatePos(double speedFactor){
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            accX = -0.1;
        }else if(Gdx.input.isKeyPressed(Input.Keys.D)){
            accX = 0.1;
        }else{
            accX = 0;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            accY = -0.1;
        }else if(Gdx.input.isKeyPressed(Input.Keys.W)){
            accY = 0.1;
        }else{
            accY = 0;
        }


        super.updatePos(speedFactor);
    }

    /**
     * Updates the physics for the grappling hook.
     */
    public void updateHook(double speedFactor) {
        if (hookedAsteroid != null && isHooked) { //Don't try to calculate physics for the grappling hook if it is not active or not connected to anything.
            double dist = Math.sqrt(Math.pow(hookedAsteroid.getPosX() - posX, 2) + Math.pow(hookedAsteroid.getPosY() - posY, 2)); //Calculates the distance between the center of the asteroid and the player.
            if (dist < 2*radius + hookedAsteroid.getRadius()) { //Disconnects the asteroid from the player if they are too close.
                //isHooked = false;
                //hookedAsteroid = null;
            }
            else{ //Calculate and apply the force to the objects.
                double angle = Math.atan2((hookedAsteroid.getPosY() - posY), (hookedAsteroid.getPosX() - posX)); //Calculate the angle between the position of the asteroid relative to the player.

                // Potentially use this to slowly guide player into circular motion, however it is unused for now
                double tanVel = Math.abs((hookedAsteroid.getVelX()-velX) * Math.cos(angle + (Math.PI/2)) + (hookedAsteroid.getVelY()-velY) * Math.sin(angle + Math.PI / 2)); //Calculate the velocity tangent to circular motion around the asteroid.
                // To slowly guide the player we could get the velocity not tangential to the velocity and decay it ( * 0.99) every frame

                // Old formula was 0.01 + (tanVel * dist * mass) / 10000
                double forceCent = (Math.sqrt(dist))/4;


                velX += (forceCent * Math.cos(angle))/mass*speedFactor;
                hookedAsteroid.setVelX(hookedAsteroid.getVelX() + (forceCent * Math.cos(angle+Math.PI))/hookedAsteroid.getMass()*speedFactor);

                velY += (forceCent * Math.sin(angle))/mass*speedFactor;
                hookedAsteroid.setVelY(hookedAsteroid.getVelY() + (forceCent * Math.sin(angle+Math.PI))/hookedAsteroid.getMass()*speedFactor);
            }
        }
    }
}
