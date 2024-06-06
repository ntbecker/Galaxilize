/* Nathan Becker, Muhammad Umar, Matthew Witherspoon
 * 6/11/2024
 * Subclass of PhysicsObject, acts as an obstacle for the player
 */
package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Asteroid extends PhysicsObject{
    private static Texture asteroidTexture = new Texture("Object_Textures/Circle_Radius_10.png");
    boolean containsUpgrade;

    /**
     * Primary constructor
     * @param posX Start position x coordinate
     * @param posY Start position y coordinate
     * @param velX Start x velocity
     * @param velY Start y velocity
     * @param mass Mass of the asteroid
     * @param radius Radius of the asteroid
     */
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius){
        super(posX,posY,velX,velY,mass,radius);
        containsUpgrade = false;
    }
    /**
     * Secondary constructor, for an asteroid with an item
     * @param posX Start position x coordinate
     * @param posY Start position y coordinate
     * @param velX Start x velocity
     * @param velY Start y velocity
     * @param mass Mass of the asteroid
     * @param radius Radius of the asteroid
     * @param containsUpgrade If the asteroid has an item
     */
    Asteroid(double posX, double posY, double velX, double velY, double mass, double radius, boolean containsUpgrade){
        this(posX,posY,velX,velY,mass,radius);
        this.containsUpgrade = containsUpgrade;
    }

    /**
     * Finds the distance from this asteroid to the mouse
     * @return The distance from the asteroid to the mouse
     */
    public double mouseDist(double mouseX, double mouseY){
        return Math.sqrt(Math.pow((posX-mouseX),2.0) + Math.pow((posY-mouseY),2.0));
    }

    /**
     * Draws the asteroid to the Galaxilize window
     * @param s The spritebatch defined in Galaxilize class
     */
    public void draw(SpriteBatch s, ShapeDrawer shape){
        //s.draw(asteroidTexture, (float) (posX-radius), (float) (posY-radius));
        shape.circle((float)posX,(float)posY,(float)radius, 3);
    }

    /**
     * Returns if the asteroid contains an upgrade.
     * @return if the asteroid contains an upgrade.
     */
    public boolean getContainsUpgrade(){ return(containsUpgrade); }

    /**
     * Sets the asteroid to contain or not contain an upgrade based on input.
     * @param containsUpgrade if the asteroid contains an upgrade.
     */
    public void setContainsUpgrade(boolean containsUpgrade){ this.containsUpgrade = containsUpgrade; }
}
