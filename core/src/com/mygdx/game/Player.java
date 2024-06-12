/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/10/2024
 * Subclass of PhysicsObject, accepts keyboard and mouse input to control the player object, also implements the grapple hook feature
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Player extends PhysicsObject{
    private static final Texture playerTexture = new Texture("Object_Textures/Spaceship_Base.png");
    private static final Texture playerOverlay = new Texture("Object_Textures/Spaceship_Colourable.png");
    private String name;
    private double score;
    private double furthestDist;
    private Scoring scoring;
    double health;
    double fuel;
    boolean isHooked;
    PhysicsObject hookedObject;

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
        furthestDist = posY;
        isHooked = false;
        //hookedObject = null;
        name = "";
        scoring = new Scoring (); // initialize scoring
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
    public Player(double posX, double posY, double velX, double velY, double mass, double radius, int health, double fuel,String name){
        this(posX,posY,velX,velY, mass, radius);
        this.health = health;
        this.fuel = fuel;
        this.name = name ;

    }

    /**
     * Reduces health by a value
     * @param dmgPoints The amount of damage the player should take
     */
    public void dealDamage(double dmgPoints){
        health -= dmgPoints;
    }
    /**
     * Increases health by a value
     * @param healPoints The amount of health the player should get
     */
    public void addHealth(double healPoints){
        health += healPoints;
        if(health > 100){
            health = 100;
        }
    }

    public void addFuel(double fuelAdded){
        fuel += fuelAdded;
        if(fuel > 100){
            fuel = 100;
        }
    }

    /**
     * Draw's the texture of this ship
     *
     * @param s The open spritebatch to draw to
     * @param shape The shapeDrawer to draw with
     */
    public void draw(SpriteBatch s, ShapeDrawer shape){
        //s.draw(playerTexture,(float)(posX-radius),(float)(posY-radius));
        //shape.circle((float)posX,(float)posY,(float)radius, 3);
        s.draw(playerTexture,(float)(posX-10),(float)(posY-10),10,10,20,20,2,2,(float)(180*Math.atan2(velY,velX)/Math.PI-90),0,0,20,20,false,false);
        s.setColor(1,0,1,1);
        s.draw(playerOverlay,(float)(posX-10),(float)(posY-10),10,10,20,20,2,2,(float)(180*Math.atan2(velY,velX)/Math.PI-90),0,0,20,20,false,false);
        s.setColor(1,1,1,1);
    }
    /**
     * Returns the current health of the player.
     * @return the health of the player.
     */
    public double getHealth(){
        return(health);
    }

    /**
     * Sets the health of the player to the input value.
     * @param health the health of the player.
     */
    public void setHealth(double health){
        this.health = health;
    }

    /**
     * Returns the current fuel of the player.
     * @return the fuel of the player.
     */
    public double getFuel(){
        return(fuel);
    }

    /**
     * Sets the fuel of the player to the input value.
     * @param fuel the fuel of the player.
     */
    public void setFuel(double fuel){
        this.fuel = fuel;
    }

    /**
     * Returns the current score of the player.
     * @return the score of the player.
     */
    public double getScore(){
    return score;
    }

    /**
     * Sets the score of the player to the input value.
     * @param score the score of the player.
     */
    public void setScore(double score){this.score = score;}

    /**
     * Returns if the player is hooked to an asteroid.
     * @return a boolean holding if the player is hooked to an asteroid.
     */
    public boolean getIsHooked(){
        return(isHooked);
    }

    /**
     * Sets if the player is hooked to an asteroid based on the input value.
     * @param isHooked a boolena holding if the player is hooked to an asteroid.
     */
    public void setIsHooked(boolean isHooked){
        this.isHooked = isHooked;
    }

    /**
     * Returns the asteroid that the player is hooked onto.
     * @return the asteroid that the player is hooked onto.
     */
    public PhysicsObject getHookedObject() {
        return(hookedObject);
    }

    /**
     * Sets what asteroid the player is hooked onto to the input object.
     * @param hookedObject the asteroid that the player is hooked onto.
     */
    public void setHookedObject(PhysicsObject hookedObject){
        this.hookedObject = hookedObject;
    }

    /**
     * Returns a string holding the name of the ship.
     * @return a String holding the name of the ship.
     */
    public String getName() {
        return(name);
    }

    /**
     * Sets the name of the ship to the input string.
     * @param name a string the name of the ship
     */
    public void setName(String name){
        this.name = name;
    }

      /*public String toString(){
        String returnString = "";
        super.toString()
    }*/

    public void updatePos(double speedFactor){
        boolean fuelSpent = false;
        if(fuel > 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.A)) {
                accX = -0.1;
                fuel -= 0.028 * speedFactor;
                fuelSpent = true;
            } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
                accX = 0.1;
                fuel -= 0.028 * speedFactor;
                fuelSpent = true;
            } else {
                accX = 0;
            }

            if (Gdx.input.isKeyPressed(Input.Keys.S)) {
                accY = -0.1;
                if (!fuelSpent) {
                    fuel -= 0.028 * speedFactor;
                }
            } else if (Gdx.input.isKeyPressed(Input.Keys.W)) {
                accY = 0.1;
                if (!fuelSpent) {
                    fuel -= 0.028 * speedFactor;
                }
            } else {
                accY = 0;
            }
        }
        else{
            accX = 0;
            accY = 0;
        }

        super.updatePos(speedFactor);

        if(posY > furthestDist){
            double vel = Math.sqrt(velX*velX + velY*velY);

            if(vel < 10){
                score += 1*speedFactor;
            }else if(vel < 20){
                score += 2*speedFactor;
            }else if(vel > 20){
                score += 3*speedFactor;
            }

            furthestDist = posY;
        }
    }

    /**
     * Updates the physics for the grappling hook.
     */
    public void updateHook(double speedFactor) {
        if (hookedObject != null && isHooked) { //Don't try to calculate physics for the grappling hook if it is not active or not connected to anything.
            double dist = Math.sqrt(Math.pow(hookedObject.getPosX() - posX, 2) + Math.pow(hookedObject.getPosY() - posY, 2)); //Calculates the distance between the center of the asteroid and the player.
            if (dist < 2*radius + hookedObject.getRadius()) { //Disconnects the asteroid from the player if they are too close.
                //isHooked = false;
                //hookedObject = null;
            }
            else{ //Calculate and apply the force to the objects.
                double angle = Math.atan2((hookedObject.getPosY() - posY), (hookedObject.getPosX() - posX)); //Calculate the angle between the position of the asteroid relative to the player.

                // Potentially use this to slowly guide player into circular motion, however it is unused for now
                double tanVel = Math.abs((hookedObject.getVelX()-velX) * Math.cos(angle + (Math.PI/2)) + (hookedObject.getVelY()-velY) * Math.sin(angle + Math.PI / 2)); //Calculate the velocity tangent to circular motion around the asteroid.
                // To slowly guide the player we could get the velocity not tangential to the velocity and decay it ( * 0.99) every frame

                // Old formula was 0.01 + (tanVel * dist * mass) / 10000
                double forceCent = (Math.sqrt(dist))/4;


                velX += (forceCent * Math.cos(angle))/mass*speedFactor;
                hookedObject.setVelX(hookedObject.getVelX() + (forceCent * Math.cos(angle+Math.PI))/hookedObject.getMass()*speedFactor);

                velY += (forceCent * Math.sin(angle))/mass*speedFactor;
                hookedObject.setVelY(hookedObject.getVelY() + (forceCent * Math.sin(angle+Math.PI))/hookedObject.getMass()*speedFactor);
            }
        }
    }
}
